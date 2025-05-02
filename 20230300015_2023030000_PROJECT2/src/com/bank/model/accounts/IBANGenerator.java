package com.bank.model.accounts;

import java.util.Random;

public class IBANGenerator {
    public static String generate(String typeCode) {
        String country = "GR";
        String accountNumber = String.format("%015d", new Random().nextLong() & Long.MAX_VALUE);
        return country + typeCode + accountNumber.substring(0, 15);
    }
}
