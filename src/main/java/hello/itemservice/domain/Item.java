package hello.itemservice.domain;

import lombok.Getter;
import lombok.Setter;

// @Data -> 약간 주의 할 필요가 있음. 내용을 확실히 알고 쓸 것.
@Getter @Setter
public class Item {
    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    // 기본 생성자
    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
