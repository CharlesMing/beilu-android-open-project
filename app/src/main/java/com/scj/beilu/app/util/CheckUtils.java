package com.scj.beilu.app.util;

import java.util.ArrayList;

/**
 * Created by Charlie on 2018/1/19.
 */

public class CheckUtils {
    public static void checkStringIsNull(String content, String tipContent) throws RuntimeException {
        if (content == null) {
            throw new RuntimeException(tipContent);
        }
        if (content.isEmpty()) {
            throw new RuntimeException(tipContent);
        }
    }

    public static void checkLength(String content, int length, String... tipContent) throws RuntimeException {
        checkStringIsNull(content, tipContent[0]);
        if (content.length() < length) {
            throw new RuntimeException(tipContent[1]);
        }
    }

    public static void checkArrayListIsNull(ArrayList lists, String tipContent) throws RuntimeException {
        if (lists == null) {
            throw new RuntimeException(tipContent);
        } else if (lists.isEmpty()) {
            throw new RuntimeException(tipContent);
        }
    }

    public static void checkObjectIsNull(Object object, String tipContent) throws RuntimeException {
        if (object == null) {
            throw new RuntimeException(tipContent);
        }
    }

    public static void checkBooleanIsFalse(boolean flag, String tipContent) throws RuntimeException {
        if (!flag) {
            throw new RuntimeException(tipContent);
        }
    }

    public static void checkPhone(String phone, String tipContent) throws RuntimeException {
        if (!InputValidate.mobileFormat(phone)) {
            throw new RuntimeException(tipContent);
        }
    }
}
