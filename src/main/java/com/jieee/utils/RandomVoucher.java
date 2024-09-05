package com.jieee.utils;

import java.util.Random;

public class RandomVoucher {

    private static final String CODE_PREFIX = "FURRY";

    public static String generateVoucherCode() {
        Random random = new Random();
        //generate 4 number of int under 9999
        int randomNumber = random.nextInt(10000);
        //formated to string
        return CODE_PREFIX + String.format("%04d", randomNumber);
    }
}