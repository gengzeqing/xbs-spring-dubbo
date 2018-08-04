package com.xuebusi.xbs.common.utils;

import java.math.BigDecimal;

/**
 *金额元分之间转换工具类
 */
public class AmountUtils {

    /**金额为分的格式 */
    public static final String CURRENCY_FEN_REGEX = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$";

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

    public static void main(String[] args) {
        try {
            BigDecimal originalPrice = new BigDecimal("10780000");
            BigDecimal multiply = originalPrice.multiply(new BigDecimal("0.8"));
            BigDecimal privilegePrice = AmountUtils.changeF2Y(multiply.longValue());
            System.out.println(privilegePrice.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
