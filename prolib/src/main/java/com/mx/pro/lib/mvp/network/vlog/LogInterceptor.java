package com.mx.pro.lib.mvp.network.vlog;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.mx.pro.lib.BuildConfig;
import com.mx.pro.lib.mvp.SystemUtil;
import com.orhanobut.logger.Logger;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;

/**
 * @author Mingxun
 * @time on 2018/12/24 11:26
 */
public class LogInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");
    private final String HEADER_NAME_TOKEN = "token";

    private Context mContext;
    private final StringBuffer sb = new StringBuffer();

    public LogInterceptor(Context context) {
        mContext = context;
    }


    private volatile Set<String> headersToRedact = Collections.emptySet();

    public void redactHeader(String name) {
        Set<String> newHeadersToRedact = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        newHeadersToRedact.addAll(headersToRedact);
        newHeadersToRedact.add(name);
        headersToRedact = newHeadersToRedact;
    }

    private String getUserAgent(Context context) {
        String userAgent = "";
//        APP版本
        String versionName = SystemUtil.getVersionName(context);
//        手机型号
        String systemModel = SystemUtil.getSystemModel();
//        系统版本
        String systemVersion = SystemUtil.getSystemVersion();
        String deviceBrand = SystemUtil.getDeviceBrand();
        userAgent = "Android/" + versionName + "/" + deviceBrand + "" + systemModel + "/" + systemVersion;
        return userAgent;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request;
        if (mContext != null) {
            Request.Builder builder = chain.request()
                    .newBuilder();
            builder.removeHeader("User-Agent");//移除旧的
            builder.addHeader("User-Agent", getUserAgent(mContext));//添加真正的头部

            request = builder.build();
        } else {
            request = chain.request();
        }

        if (!BuildConfig.LOG_REQUEST) {
            return chain.proceed(request);
        }

        sb.setLength(0);

        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        Connection connection = chain.connection();
        sb.append(request.method()
                + ' ' + request.url()
                + (connection != null ? connection.protocol() : ""))
                .append("\n");
        if (hasRequestBody) {
            sb.append("(" + requestBody.contentLength() + "-byte body)")
                    .append("\n");
        }

        if (hasRequestBody) {
            // Request body headers are only present when installed as a network interceptor. Force
            // them to be included (when available) so there values are known.
            if (requestBody.contentType() != null) {
                sb.append("Content-Type: " + requestBody.contentType())
                        .append("\n");
            }
            if (requestBody.contentLength() != -1) {
                sb.append("Content-Length: " + requestBody.contentLength())
                        .append("\n");
            }
        }

        Headers headers = request.headers();
        for (int i = 0, count = headers.size(); i < count; i++) {
            String name = headers.name(i);
            // Skip headers from the request body as they are explicitly logged above.
            if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                logHeader(headers, i);
            }
        }

        if (!hasRequestBody) {
            sb.append(request.method())
                    .append("\n");
        } else if (bodyHasUnknownEncoding(request.headers())) {
            sb.append(request.method() + " (encoded body omitted)")
                    .append("\n");
        } else {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);

            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }


            if (isPlaintext(buffer)) {
                sb.append(buffer.readString(charset))
                        .append("\n");
                sb.append(request.method()
                        + " (" + requestBody.contentLength() + "-byte body)")
                        .append("\n");
            } else {
                sb.append(request.method() + " (binary "
                        + requestBody.contentLength() + "-byte body omitted)")
                        .append("\n");
            }
        }

        Logger.d(sb.toString());

        long startNs = System.nanoTime();
        Response response;
        try {
            sb.setLength(0);

            response = chain.proceed(request);
        } catch (Exception e) {
            sb.append("HTTP FAILED: " + e)
                    .append("\n");
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
        sb.append(response.code()
                + (response.message().isEmpty() ? "" : ' ' + response.message())
                + ' ' + response.request().url()
                + " (" + tookMs + "ms" + (bodySize + " body") + ')')
                .append("\n");


        for (int i = 0, count = headers.size(); i < count; i++) {
            logHeader(headers, i);
        }

        if (!HttpHeaders.hasBody(response)) {
            sb.append("HTTP");
        } else if (bodyHasUnknownEncoding(response.headers())) {
            sb.append("HTTP (encoded body omitted)");
        } else {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Long gzippedLength = null;
            if ("gzip".equalsIgnoreCase(headers.get("Content-Encoding"))) {
                gzippedLength = buffer.size();
                try (GzipSource gzippedResponseBody = new GzipSource(buffer.clone())) {
                    buffer = new Buffer();
                    buffer.writeAll(gzippedResponseBody);
                }
            }

            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }

            if (!isPlaintext(buffer)) {

                sb.append("HTTP (binary " + buffer.size() + "-byte body omitted)")
                        .append("\n");
                return response;
            }

            if (contentLength != 0) {
                sb.append(buffer.clone().readString(charset))
                        .append("\n");
            }

            if (gzippedLength != null) {
                sb.append("HTTP (" + buffer.size() + "-byte, "
                        + gzippedLength + "-gzipped-byte body)")
                        .append("\n");
            } else {
                sb.append("HTTP (" + buffer.size() + "-byte body)")
                        .append("\n");
            }
        }
        Logger.d(sb.toString());
        return response;
    }

    private void logHeader(Headers headers, int i) {
        String value = headersToRedact.contains(headers.name(i)) ? "██" : headers.value(i);
        sb.append(headers.name(i) + ": " + value)
                .append("\n");
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private static boolean bodyHasUnknownEncoding(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null
                && !contentEncoding.equalsIgnoreCase("identity")
                && !contentEncoding.equalsIgnoreCase("gzip");
    }


}
