package hello.injection_test;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderRepository {
    private final Map<Long, OrderItem> orderStore = new HashMap<>();
    private static long sequence = 0L;

    public OrderItem save(OrderItem orderItem) {
        orderItem.setOrderNo(++sequence);
        return orderStore.put(orderItem.getOrderNo(), orderItem);
    }

    public OrderItem getOrder(Long orderNo) {
        return orderStore.get(orderNo);
    }

    public List<OrderItem> getOrders() {
        return new ArrayList<>(orderStore.values());
    }

}
