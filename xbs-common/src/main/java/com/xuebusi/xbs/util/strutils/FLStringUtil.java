package com.xuebusi.xbs.util.strutils;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

/**
 * @author Administrator 2014/12/16 10:51
 * @description !TODO描述
 */
public final class FLStringUtil extends StringUtils {

    private final static Logger log = LoggerFactory.getLogger(FLStringUtil.class);

    /**金额为分的格式 */
    public static final String CURRENCY_FEN_REGEX = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$";


    public static String toString(Object obj) {
        return toString(obj, null);
    }

    public static boolean notEquals(CharSequence cs1, CharSequence cs2) {
        return !equals(cs1, cs2);
    }

    public static String toString(Object obj, String def) {
        return isNotNull(obj) ? String.valueOf(obj) : def;
    }

    public static String toString2Utf8(Object obj, String def) throws UnsupportedEncodingException {
        return isNotNull(obj) ? trim(new String(toString(obj).getBytes(), "UTF-8")) : def;
    }

    public static String toString2Utf8(Object obj) throws UnsupportedEncodingException {
        return toString2Utf8(obj, "");
    }

    public static String toString2Trim(Object obj) {
        return trim(obj);
    }

    public static String trim(Object obj) {
        return trim(toString(obj, null));
    }

    public static String trim(String str) {
        return isNull(str) ? null : toString(str, "").trim();
    }

    public static boolean isNotBlank(Object obj) {
        return isNotBlank(toString(obj));
    }

    public static boolean isNotBlank(String str) {
        return StringUtils.isNotBlank(str);
    }

    public static boolean isBlank(String str) {
        return StringUtils.isBlank(str);
    }

    public static boolean isBlank(Object obj) {
        return isBlank(toString(obj));
    }

    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean equals(Object obj1, Object obj2) {
        if (obj1 != null) {
            return trim(obj1).equals(trim(obj2));
        } else if (obj2 != null) {
            return trim(obj2).equals(trim(obj1));
        } else {
            return true;
        }
    }

    public static String replaceAll(String permissions, String regex, String replacement) {
        if (isNotBlank(permissions)) {
            permissions = permissions.replaceAll(regex, replacement);
        }
        return permissions;
    }

    public static String toInSql(List<Long> lsIds) {
        if (lsIds != null) {
            return Joiner.on(",").join(lsIds);
        }
        return "";
    }

    public static String toInSql(Set<Long> setIds) {
        if (setIds != null) {
            return Joiner.on(",").join(setIds);
        }
        return "";
    }

    public static String toInSql(List<Long> lsIds, boolean withScale) {
        if (lsIds != null) {
            if (withScale) {
                return "(" + toInSql(lsIds) + ")";
            } else {
                return toInSql(lsIds);
            }
        }
        return "";
    }



    public static String set2StringSikpNull(Set<String> setIds, String regx, boolean wrap) {
        if (setIds == null) {
            return "";
        }
        StringBuilder sbu = new StringBuilder(setIds.size());
        Iterator<String> itl = setIds.iterator();
        int i = 0;
        while (itl.hasNext()) {
            String str = itl.next();
            if (isBlank(str)) {
                log.info("set2IdsSikpNull skip " + str);
                continue;
            }
            if (i == 0) {
                sbu.append(str);
                i++;
            } else {
                sbu.append(regx).append(str);
            }
        }
        if (wrap) {
            sbu.append(regx).insert(0, regx);
        }
        return sbu.toString();
    }

    public static String set2StringSikpNull(Set<String> setIds, String regx) {
        return set2StringSikpNull(setIds, regx, false);
    }

    /**
     * 检测是否为手机号码
     *
     * @param @param  tel
     * @param @return
     * @return boolean
     * @throws
     * @Title: checkTelephone
     * @Description: TODO
     * @Date 2014年3月20日 下午2:46:47
     */
    public static boolean isMobile(String tel) {
        if (isBlank(tel)) {
            return false;
        }
        String regExp = "^((1[3-9][0-9]))\\d{8}$";
        Pattern p = compile(regExp);
        Matcher m = p.matcher(trim(tel));
        return m.find();
    }

    /**
     * 计算两个字符串最长相同子串
     *
     * @param @param  s1
     * @param @param  s2
     * @param @return
     * @return String
     * @throws
     * @Title: search
     * @Description: TODO
     * @Date 2014年3月17日 下午1:44:33
     */
    public static String search(String s1, String s2) {
        String max = "";
        for (int i = 0; i < s1.length(); i++) {
            for (int j = i; j <= s1.length(); j++) {
                String sub = s1.substring(i, j);
                // System.out.println(sub);
                if ((s2.indexOf(sub) != -1) && sub.length() > max.length()) {
                    max = sub;
                }
            }
        }
        return max;
    }

    /**
     * 验证是否为空
     *
     * @param value
     * @return true null or '' or ' ' false 'a'
     */
    public static boolean isEmpty(String value) {
        return value == null || value.length() == 0
                || value.toString().trim().length() == 0;
    }

    /**
     * 验证是否不为空
     *
     * @param value
     * @return true 'a' false null or '' or ' '
     */
    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    /**
     * 生成随机数
     */
    public static String generateRandomInt2String(int length) {
        String seed = "0123456789";
        int subIndex = 0;
        String retString = "";
        for (int i = 0; i < length; i++) {
            subIndex = (int) (Math.random() * 100 % seed.length());
            retString += seed.substring(subIndex, subIndex + 1);
        }
        return retString;
    }

    /**
     * 生成二维码id
     *
     * @return 格式 yyyyMMddHHmmssSSS + XXX(随机数）
     */
//    public static String generateQrcode() {
//        String now = new DateTime().toString("yyyyMMddHHmmssSSS");
//        String qrcodesId = new StringBuilder(String.valueOf(now)).append(generateRandomInt2String(3)).toString();
//        return qrcodesId;
//    }

    /**
     * 生成随机数字
     *
     * @param length 生成长度
     * @return
     */
    public static String randomNumber(int length) {
        char[] numbersAndLetters = null;
        Random randGen = null;
        if (length < 1) {
            return null;
        }
        // Init of pseudo random number generator.
        if (randGen == null) {
            if (randGen == null) {
                randGen = new Random();
                // Also initialize the numbersAndLetters array
                numbersAndLetters = ("0123456789").toCharArray();
            }
        }
        // Create a char buffer to put random letters and numbers in.
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(9)];
        }
        return new String(randBuffer);
    }

    /**
     * Gets the random string.
     *
     * @param randomNumberSize the random number size
     * @param ipAddress        the ip address
     * @param port             the port
     * @return the random string
     */
    public static String getRandomString(int randomNumberSize,
                                         String ipAddress, int port) {
        long number = 0;
        number += ipToLong(ipAddress);
        number += port;
        Random randomGen = new Random();
        number += randomGen.nextLong();

        /**
         * 取mod
         */
        String defaultString = getZeroString(randomNumberSize);
        StringBuilder modStringBuilder = new StringBuilder();
        modStringBuilder.append("1").append(defaultString);
        long mod = Long.parseLong(modStringBuilder.toString());

        /**
         * 算随机值
         */
        number = number > 0 ? number % mod : Math.abs(number) % mod;

        /**
         * 格式化返回值 为randomNumberSize位
         */
        DecimalFormat df = new DecimalFormat(defaultString);
        return df.format(number);
    }

    /**
     * Ip to long.
     *
     * @param ipAddress the ip address
     * @return the long
     */
    public static long ipToLong(String ipAddress) {
        long result = 0;
        String[] atoms = ipAddress.split("\\.");

        for (int i = atoms.length - 1, j = 0; i >= j; i--) {
            result |= (Long.parseLong(atoms[atoms.length - 1 - i]) << (i * 8));
        }

        return result & 0xFFFFFFFF;
    }

    /**
     * Gets the zero string.
     *
     * @param length the length
     * @return the zero string
     */
    public static String getZeroString(int length) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buffer.append("0");
        }
        return buffer.toString();
    }

    /**
     * 获取一定长度的随机字符串
     *
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String randomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 手机号4到7位使用*替换
     * @param phone
     * @return
     */
    public static String  getPhoneCode(String phone) {
        if (isEmpty(phone)){
            return null;
        }
        String phoneNumber = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
        return phoneNumber;
    }

    /**
     * 分转换元
     * @param amount
     * @return
     */
    public static BigDecimal changeF2Y(Long amount) throws Exception{
        if(null == amount){
            throw new Exception("金额不能为null");
        }
        //设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(进位处理)
        BigDecimal bPrice = new BigDecimal(amount);
        bPrice=bPrice.divide(new BigDecimal(10000),2,BigDecimal.ROUND_UP);
        return bPrice;
    }

    /**
     * 元转换成分
     * @param amount
     * @return
     */
    public static BigDecimal changeY2F(String amount) throws Exception{
        if(!amount.matches(CURRENCY_FEN_REGEX)) {
            throw new Exception("金额格式有误");
        }
        BigDecimal bPrice=new BigDecimal(amount);
        bPrice=bPrice.multiply(new BigDecimal(10000));

        return bPrice;
    }


    /**
     * 替换身份证号用*号替换
     *
     * @param idCardNo
     * @return
     * @author
     */
    public static String replaceIdCardNo(String idCardNo) {
        // 校验原字符串与替换字符串是否为空，其一为空则返回原字符串
        if (!isNotEmpty(idCardNo)) {
            return idCardNo;
        }
        String tmp = idCardNo.substring(6, idCardNo.length() - 4);
        String rep = "";
        for (int i = 0; i < tmp.length(); i++) {
            rep += "*";
        }
        return idCardNo.replace(tmp, rep);
    }


    /**
     * 替换字符中的表情符号
     *
     * @param source
     * @param target
     * @return
     */
    public static String filterEmoji(String source, String target) {
        if (isEmpty(source)) {
            return source;
        }
        Pattern emoji = compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", UNICODE_CASE | CASE_INSENSITIVE);
        Matcher emojiMatcher = emoji.matcher(source);
        if (emojiMatcher.find()) {
            source = emojiMatcher.replaceAll(target);
            return source;
        }
        return source;
    }

    /**
     * 判断字符是否有表情符号
     *
     * @param source
     * @return
     */
    public static boolean isContainEmoji(String source) {
        if (isEmpty(source)) {
            return false;
        }
        Pattern emoji = compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", UNICODE_CASE | CASE_INSENSITIVE);
        Matcher emojiMatcher = emoji.matcher(source);
        if (emojiMatcher.find()) {
            return true;
        }
        return false;
    }

    /**
     * 自动生成32位的UUid，对应数据库的主键id进行插入用。
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * MD5字符串类型加密
     *
     * @param plainText
     * @return
     */
    public static String encode(String plainText) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(plainText.getBytes());
        byte b[] = md.digest();
        int i;
        StringBuffer buf = new StringBuffer("");
        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0) {
                i += 256;
            }
            if (i < 16) {
                buf.append("0");
            }
            buf.append(Integer.toHexString(i));
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        System.out.println(randomNumber(30));
    }


}



