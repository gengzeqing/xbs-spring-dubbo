/**
 * 梦想工场 版权所有 Copyright@2018
 */
package com.xuebusi.xbs.utils.RequestUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * [简短描述该类的功能]
 *
 * @ProjectName: [fl.ec.product]
 * @Author: [lenovo]
 * @CreateDate: [2018/5/24 22:13]
 * @Update: [说明本次修改内容] BY [lenovo] [2018/5/24]
 * @Version: [V1.0]
 */
public final class ChineseCharToEn {
    /**
     * 将字符串中的中文转化为拼音,其他字符不变
     *
     * @param inputString
     * @return
     */
    public static String getPingYin(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        char[] input = inputString.trim().toCharArray();
        String output = "";

        try {
            for (int i = 0; i < input.length; i++) {
                if (Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
                    output += temp[0];
                } else
                    output += Character.toString(input[i]);
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return output;
    }
    /**
     * 获取汉字串拼音首字母，英文字符不变
     * @param chinese 汉字串
     * @return 汉语拼音首字母
     */
    public static String getFirstSpell(String chinese) {
        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 128) {
                try {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                    if (temp != null) {
                        pybf.append(temp[0].charAt(0));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(arr[i]);
            }
        }
        return pybf.toString().trim();
    }
    /**
     * 获取汉字串拼音，英文字符不变
     * @param chinese 汉字串
     * @return 汉语拼音
     */
    public static String getFullSpell(String chinese) {
        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 128) {
                try {
                    pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(arr[i]);
            }
        }
        return pybf.toString();
    }

    /**
     * 获取文件首字母
     * @param fileName
     * @return
     */
    public static String getFileName(String fileName){
        if (fileName.lastIndexOf(".")>0){
            String extName = fileName.substring(fileName.lastIndexOf("."));
            fileName = fileName.substring(0,fileName.lastIndexOf("."));
            return getFirstSpell(fileName).concat(extName);
        }
        return getFirstSpell(fileName);
    }



    public static void main(String[] args)
    {
        String cnStr = "._商品上传模板-钟表珠宝-悦木之源+布龙尼齐+凝萃108款_1.jpg";
        System.out.println("讴萘-->" + getFileName(cnStr));
        String s = getFirstSpell("20809天津天士力（辽宁）制药有限责任公司（16）.rar");
        System.out.println("讴萘-->" + s);
        StringBuffer sb = new StringBuffer(s);
        if (sb.length() > 1)
        {
            String ss = sb.delete(1, sb.length()).toString();
            System.out.println("讴萘-->"
                    + Character.toUpperCase(ss.toCharArray()[0]) + "");
        }
    }

}
