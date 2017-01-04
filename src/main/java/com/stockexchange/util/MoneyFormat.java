package com.stockexchange.util;

public class MoneyFormat {

    public static String shortened(double money) {
        if (money > 1e9) {
            return (String.format("$%.2fB", money / 1e9));
        } else if (money > 1e6) {
            return (String.format("$%.2fM", money / 1e6));
        } else if (money > 1e3) {
            return (String.format("$%.2fK", money / 1e3));
        } else {
            return (String.format("$%.2f", money));
        }
    }
}
