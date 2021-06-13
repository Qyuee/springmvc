package hello.injection_test;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class OrderItem {
    private Long orderNo;
    private String orderId;
    private Date orderDate;

    public OrderItem() {
    }

    public OrderItem(String orderId, Date orderDate) {
        this.orderId = orderId;
        this.orderDate = orderDate;
    }
}
