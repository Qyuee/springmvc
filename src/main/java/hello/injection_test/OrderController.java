package hello.injection_test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/di")
public class OrderController {
    private OrderRepository orderRepository;
    private OrderItem orderItem;

    // 생성자를 통해서 주입하기
    // 생성자가 1개 일 때는 @Autowired를 생략 할 수 있음
    /*@Autowired
    public OrderController(OrderRepository orderRepository) {
        log.info("생성자를 통한 의존관계 주입");
        this.orderRepository = orderRepository;
    }*/

    // Setter를 통해서 의존관계 주입하기
    /*@Autowired  // 생략불가
    public void setOrderRepository(OrderRepository orderRepository) {
        log.info("수정자를 통한 의존관계 주입");
        this.orderRepository = orderRepository;
    }*/

    // 일반 메소드를 통한 주입
    @Autowired
    public void di(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // OrderItem은 빈으로 관리되지 않음
    // 자동 주입이 실패하는 경우 pass
    // 해당 메소드가 호출되지 않음(수정자가 호출되지 않음)
    @Autowired(required = false)
    public void di2(OrderItem orderItem) {
        System.out.println("OrderItem bean1 = " + orderItem);
        this.orderItem = orderItem;
    }

    // 자동주입을 시도하지만 null을 입력한다.
    @Autowired
    public void di3(@Nullable OrderItem orderItem) {
        System.out.println("OrderItem bean2 = " + orderItem);
        // OrderItem bean2 = null
        this.orderItem = orderItem;
    }

    // 자동 주입 할 대상이 없으면 Optional.empty가 입력된다.
    @Autowired(required = false)
    public void di4(Optional<OrderItem> orderItem) {
        System.out.println("OrderItem bean3 = " + orderItem);
        // OrderItem bean3 = Optional.empty
    }

    @GetMapping("/getOrder/{orderNo}")
    public OrderItem getOrder(@PathVariable Long orderNo) {
        OrderItem order = orderRepository.getOrder(orderNo);
        order.setOrderNo(orderNo);
        return order;
    }

    @PostConstruct
    public void init() {
        orderRepository.save(new OrderItem("20210613-00000001", new Date()));
        orderRepository.save(new OrderItem("20210613-00000002", new Date()));
    }
}
