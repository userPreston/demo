package com.example.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Test {

    public static void main(String[] args) {
        String str = "123456";
        String[] split = str.split(",");
        for (int i = 0; i < split.length; i++) {
            System.err.println(split[i]);
        }
    }

    /*public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String OperatorValues = "2099-12-31 23:59:59.0";
        Date expire_date = format.parse(OperatorValues);
        Date date = new Date();
        System.err.println(expire_date);
        System.err.println(date);
        System.err.println(date.before(expire_date));
    }*/

}
