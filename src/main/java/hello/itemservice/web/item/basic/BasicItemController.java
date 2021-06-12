package hello.itemservice.web.item.basic;

import hello.itemservice.domain.Item;
import hello.itemservice.domain.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Controller가 생성되면서 생성자를 통해서 itemRepository에 대한 주입이 수행된다.
 * 하지만 @RequiredArgsConstructor을 통해서 생성자를 롬복이 만들어줄 수 있음
 */
@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

    //@Autowired -> 생성자가 하나만 있으면 생략 가능
    /*public BasicItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }*/

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    // 테스트용 데이터 삽입
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemsA", 10000, 10));
        itemRepository.save(new Item("itemsB", 20000, 20));
    }
}
