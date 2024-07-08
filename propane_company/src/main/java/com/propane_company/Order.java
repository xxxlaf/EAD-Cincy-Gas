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

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getOrderId(){
        return orderId;
    }

    public void setOrderId(Integer orderId){
        this.orderId = orderId;
    }

    // Getters and setters
    public List<PropaneTank> getPropaneTanks() {
        return propaneTanks;
    }

    public void setPropaneTanks(List<PropaneTank> propaneTanks) {
        this.propaneTanks = propaneTanks;
    }

    public Integer getCustomerId(){
        return customerId;
    }

    public void setCustomerId(Integer customerId){
        this.customerId = customerId;
    }

   public Date getOrderDate(){
        return orderDate;
    }

    public void setOrderDate(Date orderDate){
        this.orderDate = orderDate;
    }
}
