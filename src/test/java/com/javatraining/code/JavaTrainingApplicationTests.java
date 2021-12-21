package com.javatraining.code;

import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JavaTrainingApplicationTests {



	@Test // add buy and sell orders that will have no matches
	void addUnmatchedOrders() {
		Order buyTest = new Order("Account 3", 15, 30, "Buy");
		Order sellTest = new Order("Account 2", 15, 55, "Sell");

		JavaTrainingApplication.matchOrder(buyTest);
		assertTrue(JavaTrainingApplication.buyList.contains(buyTest));

		JavaTrainingApplication.matchOrder(sellTest);
		assertTrue(JavaTrainingApplication.sellList.contains(sellTest));
	}

	@Test // add buy and sell orders that will have no matches
	void matchBuyOrder() {
		JavaTrainingApplication.sellList.add(new Order("Account 3", 10, 45, "Sell"));
		JavaTrainingApplication.sellList.add(new Order("Account 3", 20, 60, "Sell"));
		JavaTrainingApplication.sellList.add(new Order("Account 1", 60, 80, "Sell"));

		Order buyTest = new Order("Account 2", 10, 55, "Buy");
		JavaTrainingApplication.matchOrder(buyTest);
		assertFalse(JavaTrainingApplication.buyList.contains(buyTest));
		assertTrue(JavaTrainingApplication.sellList.size() == 2);
	}

	@Test // add buy and sell orders that will have no matches
	void matchSellOrder() {
		JavaTrainingApplication.buyList.add(new Order("Account 2", 10, 40, "Buy"));
		JavaTrainingApplication.buyList.add(new Order("Account 2", 20, 25, "Buy"));
		JavaTrainingApplication.buyList.add(new Order("Account 1", 40, 10, "Buy"));

		Order sellTest = new Order("Account 2", 30, 25, "Sell");

		JavaTrainingApplication.matchOrder(sellTest);
		assertFalse(JavaTrainingApplication.sellList.contains(sellTest));
		assertTrue(JavaTrainingApplication.buyList.size() == 1);
	}

	@Test // add buy and sell orders that will have no matches
	void aggregateBuyOrders() {
		List<Order> buyList = new ArrayList<>();
		buyList.add(new Order("Account 2", 10, 40, "Buy"));
		buyList.add(new Order("Account 2", 20, 25, "Buy"));
		buyList.add(new Order("Account 1", 40, 10, "Buy"));
		buyList.add(new Order("Account 1", 20, 10, "Buy"));

		List<Order> expectedList = new ArrayList<>();
		expectedList.add(new Order("Account 2", 90, 40, "Buy"));
		expectedList.add(new Order("Account 2", 80, 25, "Buy"));
		expectedList.add(new Order("Account 1", 60, 10, "Buy"));

		List<Order> aggList = JavaTrainingApplication.aggregateList(buyList);


		System.out.println("Agg: " + aggList);
		System.out.println("expected: " + expectedList);
		assertEquals(Arrays.asList(expectedList), Arrays.asList(aggList) );

	}

}
