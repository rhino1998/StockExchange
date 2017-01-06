package com.stockexchange.server.orders.comparators;

import java.util.Comparator;

import com.stockexchange.server.orders.ExecutableOrder;
import com.stockexchange.stocks.orders.enums.OrderType;

public class BuyComparator implements Comparator<ExecutableOrder> {

    public int compare(ExecutableOrder a, ExecutableOrder b) {
        if (a == null && b == null) {
            return 0;
        }

        if (a == null) {
            return -1;
        }

        if (b == null) {
            return 1;
        }

        if (a.isMarket()) {
            return 1;
        } else if (b.isMarket()) {
            return -1;
        }
        return Double.compare(a.getPrice(), b.getPrice());
    }

}
