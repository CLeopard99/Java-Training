package com.javatraining.code;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


/**********************************************************************
 * Order object class
 *
 * @author Charlie Leopard
 *********************************************************************/
final public class Order implements Comparable<Order> {
    @NotNull
    private final String accountName;
    @Min(1)
    private int quantity;
    @Min(value = 1, message="This isn't > 0")
    private final int price;
    @Pattern(regexp = "Buy|Sell")
    private final String action;

    /**
     * Construct an Order product when given an account name, quantity, price, and action
     *
     * @param accountName the account name from which order is made
     * @param quantity    quantity order is asking for
     * @param price       price which order is buying or selling at
     * @param action      action of "Buy" or "Sell"
     */
    public Order(String accountName, int quantity, int price, String action) {
        this.accountName = accountName;
        this.quantity = quantity;
        this.price = price;
        this.action = action;
    }

    @Override
    public int compareTo(Order o) {
        if (getAction().equals("Buy")) return o.getPrice() - getPrice();
        else return getPrice() - o.getPrice();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Acc: " + accountName + ", Quantity: " + quantity + ", Price: " + price + ", Action: " + action + " |";
    }

    /**
     * Returns the field accountName
     * @return Order's <Code>String accountName</Code>
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Returns the field quantity
     * @return Order's <Code>int quantity</Code>
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Returns the field moduleCredits
     * @return Order's <Code>int price</Code>
     */
    public int getPrice() {
        return price;
    }

    /**
     * Returns the field action
     * @return Order's <Code>String action</Code>
     */
    public String getAction() {
        return action;
    }

    /**
     * Updates the order's quantity field
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
