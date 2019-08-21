package com.mx.pro.lib.mvp.network.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import static okhttp3.internal.Util.UTF_8;

/**
 * @author Mingxun
 * @time on 2018/12/25 10:39
 */
public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private Gson mGson;
    private TypeAdapter<T> mTypeAdapter;

    public GsonResponseBodyConverter(Gson gson, TypeAdapter<T> typeAdapter) {
        mGson = gson;
        mTypeAdapter = typeAdapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String result = value.string();
        MediaType contentType = value.contentType();
        Charset charset = contentType == null ? UTF_8 : contentType.charset(UTF_8);
        InputStream inputStream = new ByteArrayInputStream(result.getBytes());
        Reader reader = new InputStreamReader(inputStream, charset);
        JsonReader jsonReader = mGson.newJsonReader(reader);
        jsonReader.setLenient(true);
        try {
            return mTypeAdapter.read(jsonReader);
        } finally {
            inputStream.close();
            value.close();
        }
    }
}
