package com.bank.model.accounts;

import java.util.Random;

public class IBANGenerator {
    public static String generate(String typeCode) {
        String country = "GR"; // GR για Ελλάδα
        Random rand = new Random();

        String accountNumber = "";
        for (int i = 0; i < 15; i++) {
            accountNumber += rand.nextInt(10); // 0-9
        }

        return country + typeCode + accountNumber;
    }
}
