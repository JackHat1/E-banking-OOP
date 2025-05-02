package com.bank.order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private List<StandingOrder> orders;

    public OrderManager() {
        this.orders = new ArrayList<>();
    }

    public void addOrder(StandingOrder order) {
        orders.add(order);
    }

    public void processOrders(LocalDate today) {
        for (StandingOrder order : orders) {
            order.execute(today);
        }
    }

    public List<StandingOrder> getAllOrders() {
        return orders;
    }
}
