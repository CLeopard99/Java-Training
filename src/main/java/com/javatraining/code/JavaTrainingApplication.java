package com.javatraining.code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class JavaTrainingApplication {

    public static List<Order> buyList = new ArrayList<>();
    public static List<Order> sellList = new ArrayList<>();

    public static List<Order> aggregatedBuyData = new ArrayList<>();
    public static List<Order> aggregatedSellData = new ArrayList<>();
    public static List<Order[]> tradeList = new ArrayList<>();

    public static void main(String[] args) {
        SpringApplication.run(JavaTrainingApplication.class, args);
        System.out.println("Running from main... ");

        // Add to buy list (initial orders)
        buyList.add(new Order("Account 2", 10, 40, "Buy"));
        buyList.add(new Order("Account 2", 20, 25, "Buy"));
        buyList.add(new Order("Account 1", 40, 10, "Buy"));
        buyList.add(new Order("Account 1", 20, 5, "Buy"));

        // add to sell list (initial orders)
        sellList.add(new Order("Account 3", 10, 45, "Sell"));
        sellList.add(new Order("Account 3", 20, 60, "Sell"));
        sellList.add(new Order("Account 1", 60, 80, "Sell"));
        sellList.add(new Order("Account 1", 0, 0, "Sell"));

        // No matches for these orders
//        matchOrder(new Order("Account 3", 15, 30, "Buy"));
//        matchOrder(new Order("Account 2", 15, 55, "Sell"));
        // print new lists
        System.out.println("buyList before: " + buyList);
        System.out.println("sellList before: " + sellList);
        // These order should match fully then partially
       // matchOrder(new Order("Account 2", 15, 55, "Buy"));
//        matchOrder(new Order("Account 2", 20, 25, "Sell"));
        System.out.println("===========================================================");

        System.out.println("buyList after: " + buyList);
        System.out.println("sellList after: " + sellList);
        System.out.println("===========================================================");
        System.out.println("buy agg list after: " + aggregatedBuyData);
        System.out.println("sell agg list after: " + aggregatedSellData);
        //System.out.println("trade list after: " + tradeList);
    }

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
        } else if (buyList != null) {
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
