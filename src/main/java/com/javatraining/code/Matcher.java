package com.javatraining.code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Matcher {

    public static List<Order> buyList = new ArrayList<>();;
    public static List<Order> sellList = new ArrayList<>();;

    public static List<Order> aggregatedBuyData = new ArrayList<>();;
    public static List<Order> aggregatedSellData = new ArrayList<>();;

    public static List<Order[]> tradeList = new ArrayList<>();;


    public static void matchOrder(Order order) {
        if (order.getAction().equals("Buy") && sellList != null) {
            for (int i = 0; i < sellList.size(); i++) {
                if (sellList.get(i).getPrice() <= order.getPrice()) {
                    Order matchedOrder = sellList.get(i);
                    sellList = sellList.stream().skip(i + 1).collect(Collectors.toList());
                    orderMatched(order, matchedOrder);
                    return;
                }
            }
        } else if(buyList != null) {
            for (int i = 0; i < buyList.size(); i++) {
                if (buyList.get(i).getPrice() >= order.getPrice()) {
                    Order matchedOrder = buyList.get(i);
                    buyList = buyList.stream().skip(i + 1).collect(Collectors.toList());
                    orderMatched(order, matchedOrder);
                    return;
                }
            }
        }
        System.out.println("No matches for this order, order added to list.");
        updateOrders(order);
    }

    public static void orderMatched(Order currentOrder, Order matchedOrder) {
        if (matchedOrder.getQuantity() == (currentOrder.getQuantity())) System.out.println("Both orders fulfilled!");
        else if (matchedOrder.getQuantity() > currentOrder.getQuantity()) {
            matchedOrder.setQuantity(matchedOrder.getQuantity() - currentOrder.getQuantity());
            System.out.println("Order fulfilled, leftovers updated.");
            updateOrders(matchedOrder);
        } else {
            currentOrder.setQuantity(currentOrder.getQuantity() - matchedOrder.getQuantity());
            System.out.println("Order partially fulfilled, searching for another match.");
            matchOrder(currentOrder);
        }
        Order[] trade = {currentOrder, matchedOrder};
        tradeList.add(trade);
    }

    public static void updateOrders(Order order) {
        if (order.getAction().equals("Buy")) {
            System.out.println("Buy order list updated");
            buyList.add(order);
            Collections.sort(buyList);
            aggregatedBuyData = aggregateList(buyList);
        } else if (order.getAction().equals("Sell")) {
            System.out.println("Sell order list updated");
            sellList.add(order);
            Collections.sort(sellList);
            aggregatedSellData = aggregateList(sellList);
        }
    }

    public static List<Order> aggregateList(List<Order> orderList) {
        List<Order> aggList = new ArrayList<>();
        List<Order> aList = new ArrayList<>(orderList);
        if (aList.get(0).getAction().equals("Sell")) {
            for (int i = 0; i < aList.size(); i++) {
                aList.set(i, new Order(aList.get(i).getAccountName(), aList.get(i).getQuantity(), aList.get(i).getPrice(), aList.get(i).getAction()));
                for (int j = i + 1; j < aList.size(); j++) {
                    if (aList.get(i).getPrice() <= aList.get(j).getPrice()) {
                        aList.get(i).setQuantity(aList.get(i).getQuantity() + aList.get(j).getQuantity());
                        if (aList.get(i).getPrice() == aList.get(j).getPrice()) {
                            aList.remove(j);
                            j = j - 1;
                        }
                    }
                }
                aggList.add(aList.get(i));
            }
        } else {
            for (int i = 0; i < aList.size(); i++) {
                aList.set(i, new Order(aList.get(i).getAccountName(), aList.get(i).getQuantity(), aList.get(i).getPrice(), aList.get(i).getAction()));
                for (int j = i + 1; j < aList.size(); j++) {
                    if (aList.get(i).getPrice() >= aList.get(j).getPrice()) {
                        aList.get(i).setQuantity(aList.get(i).getQuantity() + aList.get(j).getQuantity());
                        if (aList.get(i).getPrice() == aList.get(j).getPrice()) {
                            aList.remove(j);
                            j = j - 1;
                        }
                    }
                }
                aggList.add(aList.get(i));
            }
        }
        return aggList;
    }
}
