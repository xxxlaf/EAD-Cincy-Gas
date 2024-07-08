package com.propane_company.propane_company;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.sql.Date;


@Entity
@Table(name = "propane_tank")
public class PropaneTank {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer propaneTankId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @NotNull
    private Date deliveryDate;

    @NotNull
    private String deliveryStatus;

    @NotNull
    private Integer tankSize;

    public void setTankSize(Integer TankSize) {
        this.TankSize = TankSize;
    }

    public Integer getTankSize() {
        return TankSize;
    }

    public void getPropaneTankId(Integer propaneTankId) {
        this.propaneTankId = propaneTankId;
    }

    public Integer getPropaneTankId(){
        return this.propaneTankId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }
}
