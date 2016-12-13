package org.spaconference.rts.solutions;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.spaconference.rts.runner.ExampleRunner;
import org.spaconference.rts.runner.ExampleRunner.Way;


@RunWith(ExampleRunner.class)
public class ExJ_FlatMapping2 {
	
	public static class Item {
		public String product;
		public int quantity;
		
		public Item(String product, int quantity) {
			this.product = product;
			this.quantity = quantity;
		}
	}
	
	public static class Order {
		public List<Item> items = new ArrayList<>();
		
		public Order(List<Item> items) {
			this.items = items;
		}
	}
	
    @Way
    public static List<String> oldWay_toFindItemNamesWithPositiveQuantity(List<Order> orders) {
    	List<String> names = new ArrayList<>();
    	for (Order order : orders) {
    		for (Item item: order.items) {
    			if (item.quantity > 0) {
    				names.add(item.product);
    			}
    		}
		}
    	return names;
    }
    
    @Way
    public static List<String> newWay(List<Order> orders) {
    	return orders.stream()
    		.flatMap(order -> order.items.stream())
    		.filter(item -> item.quantity > 0)
    		.map(i -> i.product)
    		.collect(Collectors.toList());
    }
    
    // Example orders
    Order appleOrder = new Order(asList(new Item("Apples", 4), new Item("Pears", 0)));
    Order lemonOrder = new Order(asList(new Item("Lemons", 4), new Item("Apples", 3)));
    
    @Test
    public void find_apples_in_one_order(Function<List<Order>, List<String>> f) {
        assertThat(f.apply(asList(appleOrder)), 
        		equalTo(asList("Apples")));
    }
    
    @Test
    public void find_apples_lemons_in_two_orders(Function<List<Order>, List<String>> f) {
    	assertThat(f.apply(asList(appleOrder, lemonOrder)), 
    			equalTo(asList("Apples", "Lemons", "Apples")));
    	
    	// Bonus exersize: Remove duplicate Apples entry above and implement solution. (Hint: distinct).
    }
    
}
