package com.propane_company.propane_company;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer orderId;
    public Integer customerId;
    public Date orderDate;
    public Integer quantity;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<PropaneTank> propaneTanks;
    /**
     * Order order = new Order();
     * // Set other properties of the order
     *
     * List<PropaneTank> tanks = new ArrayList<>();
     * tanks.add(new PropaneTank());
     * tanks.add(new PropaneTank());
     * // Add more tanks as needed
     *
     * order.setPropaneTanks(tanks);
     *
     * // Save the order entity using your JPA repository
     * orderRepository.save(order);
     * @return
     */

    /**
     * Sets the quantity of the order.
     * @param quantity the quantity of the order
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns the quantity of the order.
     * @return the quantity of the order
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Returns the order ID.
     * @return the order ID
     */
    public Integer getOrderId(){
        return orderId;
    }

    /**
     * Sets the order ID.
     * @param orderId the order ID
     */
    public void setOrderId(Integer orderId){
        this.orderId = orderId;
    }

    /**
     * Returns the list of propane tanks associated with the order.
     * @return the list of propane tanks
     */
    public List<PropaneTank> getPropaneTanks() {
        return propaneTanks;
    }

    /**
     * Sets the list of propane tanks associated with the order.
     * @param propaneTanks the list of propane tanks
     */
    public void setPropaneTanks(List<PropaneTank> propaneTanks) {
        this.propaneTanks = propaneTanks;
    }

    /**
     * Returns the customer ID.
     * @return the customer ID
     */
    public Integer getCustomerId(){
        return customerId;
    }

    /**
     * Sets the customer ID.
     * @param customerId the customer ID
     */
    public void setCustomerId(Integer customerId){
        this.customerId = customerId;
    }

    /**
     * Returns the order date.
     * @return the order date
     */
    public Date getOrderDate(){
        return orderDate;
    }

    /**
     * Sets the order date.
     * @param orderDate the order date
     */
    public void setOrderDate(Date orderDate){
        this.orderDate = orderDate;
    }
}