package com.javatraining.code;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.*;
import java.util.*;

@RestController
public class Controller {

    JavaTrainingApplication matcher = new JavaTrainingApplication();

    @GetMapping("/buyorders")
    public List<Order> buyorders() {
        return matcher.buyList;
    }

    @GetMapping("/sellorders")
    public List<Order> sellorders() {
        return matcher.sellList;
    }

    @GetMapping("/allorders")
    public Map<String, List<Order>> allorders() {
        Map<String, List<Order>> ordersMap = new HashMap();
        ordersMap.put("buyList", matcher.buyList);
        ordersMap.put("sellList", matcher.sellList);
        return ordersMap;
    }

    @GetMapping("/aggregateBuy")
    public List<Order> aggBuy() {
        return matcher.aggregatedBuyData;
    }

    @GetMapping("/aggregateSell")
    public List<Order> aggSell() {
        return matcher.aggregatedSellData;
    }

    @PostMapping("/neworder")
    public void neworder(@RequestBody @Valid Order order) {
        System.out.println("New order made: " + order);
        matcher.matchOrder(order);
    }

}

