package com.scj.beilu.app.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Charlie on 2016/1/21 0021.
 */
public class InputValidate {
    /**
     * 手机号码验证
     *
     * @param mobiel
     * @return
     */
    public static boolean mobileFormat(String mobiel) {
        Pattern pattern = Pattern.compile("^[1]([3-9])[0-9]{9}$");
        Matcher mc = pattern.matcher(mobiel);
        return mc.matches();
    }

    public static boolean isNumber(String mobile) {
        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher mc = pattern.matcher(mobile);
        return mc.matches();
    }

    /**
     * 传真的验证
     *
     * @param fax
     * @return
     */
    public static boolean isFax(String fax) {
        Pattern pattern = Pattern.compile("^[+]{0,1}(\\d){1,3}[ ]?([-]?((\\d)|[ ]){1,12})+$");
        Matcher mc = pattern.matcher(fax);
        return mc.matches();
    }

    public static boolean isUrl(String url) {
        Pattern pattern = Pattern.compile("^(((ht|f)tp(s?))\\://)?(www.|[a-zA-Z].)[a-zA-Z0-9\\-\\.]" +
                "+\\.(com|edu|gov|mil|net|org|biz|info|name|museum|us|ca|uk)(\\:[0-9]+)*" +
                "(/($|[a-zA-Z0-9\\.\\,\\;\\?\\'\\\\\\+&amp;%\\$#\\=~_\\-]+))*$");
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }

    /**
     * 邮箱验证
     *
     * @param email
     * @return
     */
    public static boolean emailFormat(String email) {
        Pattern pattern = Pattern.compile("^[A-Za-z\\d]+(\\.[A-Za-z\\d]+)*@([\\dA-Za-z](-[\\dA-Za-z])?)+(\\.{1,2}[A-Za-z]+)+$");
        Matcher mc = pattern.matcher(email);
        return mc.matches();
    }

    /**
     * 以字母开头，长度在1~16之间，只能包含字符、数字和下划线（w）
     *
     * @param password
     * @return
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean passwordFormat(String password) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]\\w{0,15}$");
        Matcher mc = pattern.matcher(password);
        return mc.matches();
    }

    /**
     * 以字母开头，长度在6~22之间，只能包含字符、数字和下划线（w）
     *
     * @param password
     * @return
     */
    public static boolean passwordRegisterFormat(String password) {
        Pattern pattern = Pattern.compile("^(?!\\d+$)(?![a-zA-Z]+$)\\w{6,22}$");
        Matcher mc = pattern.matcher(password);
        return mc.matches();
    }

    /**
     * 验证 验证码必须是6位
     *
     * @param code
     * @return
     */
    public static boolean verifyCode(String code) {
        Pattern pattern = Pattern.compile("^\\d{4}$");
        Matcher mc = pattern.matcher(code);
        return mc.matches();
    }

    /**
     * 用户名在3-16之间 数字字母开头
     *
     * @param name
     * @return
     */
    public static boolean nameFormat(String name) {
        Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5A-Za-z0-9_]{3,16}$");
        Matcher mc = pattern.matcher(name);
        return mc.matches();
    }

    /**
     * 获取含双字节字符的字符串字节长度
     *
     * @param s
     * @return
     */
    public static int getStringLength(String s) {
        char[] chars = s.toCharArray();
        int count = 0;
        for (char c : chars) {
            count += getSpecialCharLength(c);
        }
        return count;
    }

    /**
     * 获取字符长度：汉、日、韩文字符长度为2，ASCII码等字符长度为1
     *
     * @param c 字符
     * @return 字符长度
     */
    public static int getSpecialCharLength(char c) {
        if (isLetter(c)) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * 判断一个字符是Ascill字符还是其它字符（如汉，日，韩文字符）
     *
     * @param c, 需要判断的字符
     * @return boolean, 返回true,Ascill字符
     */
    @SuppressWarnings("RedundantConditionalExpression")
    public static boolean isLetter(char c) {
        int k = 0x80;
        //noinspection RedundantConditionalExpression
        return c / k == 0 ? true : false;
    }

    /**
     * 描述：将null转化为“”.
     *
     * @param str 指定的字符串
     * @return 字符串的String类型
     */
    public static String parseEmpty(String str) {
        if (str == null || "null".equals(str.trim())) {
            str = "";
        }
        return str.trim();
    }

    /**
     * 描述：判断一个字符串是否为null或空值.
     *
     * @param str 指定的字符串
     * @return true or false
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 获取字符串中文字符的长度（每个中文算2个字符）.
     *
     * @param str 指定的字符串
     * @return 中文字符的长度
     */
    public static int chineseLength(String str) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        if (!isEmpty(str)) {
            for (int i = 0; i < str.length(); i++) {
                /* 获取一个字符 */
                String temp = str.substring(i, i + 1);
                /* 判断是否为中文字符 */
                if (temp.matches(chinese)) {
                    valueLength += 2;
                }
            }
        }
        return valueLength;
    }

    /**
     * 描述：获取字符串的长度.
     *
     * @param str 指定的字符串
     * @return 字符串的长度（中文字符计2个）
     */
    public static int strLength(String str) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        if (!isEmpty(str)) {
            // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                // 获取一个字符
                String temp = str.substring(i, i + 1);
                // 判断是否为中文字符
                if (temp.matches(chinese)) {
                    // 中文字符长度为2
                    valueLength += 2;
                } else {
                    // 其他字符长度为1
                    valueLength += 1;
                }
            }
        }
        return valueLength;
    }

    /**
     * 描述：获取指定长度的字符所在位置.
     *
     * @param str  指定的字符串
     * @param maxL 要取到的长度（字符长度，中文字符计2个）
     * @return 字符的所在位置
     */
    public static int subStringLength(String str, int maxL) {
        int currentIndex = 0;
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < str.length(); i++) {
            // 获取一个字符
            String temp = str.substring(i, i + 1);
            // 判断是否为中文字符
            if (temp.matches(chinese)) {
                // 中文字符长度为2
                valueLength += 2;
            } else {
                // 其他字符长度为1
                valueLength += 1;
            }
            if (valueLength >= maxL) {
                currentIndex = i;
                break;
            }
        }
        return currentIndex;
    }

    /**
     * 验证固定电话号码
     *
     * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
     *              <p><b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字，
     *              数字之后是空格分隔的国家（地区）代码。</p>
     *              <p><b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
     *              对不使用地区或城市代码的国家（地区），则省略该组件。</p>
     *              <p><b>电话号码：</b>这包含从 0 到 9 的一个或多个数字 </p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPhone(String phone) {
//        String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
        String regex = "^(0\\d{2,3}-?)?\\d{7,8}$";
        return Pattern.matches(regex, phone);
    }
}
