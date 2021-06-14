package hello.itemservice.web.item.basic;

import hello.itemservice.domain.Item;
import hello.itemservice.domain.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    /**
     * Form을 통해서 요청 데이터가 전달 됨
     * - Content-Type: application/x-www-form-urlencoded
     * - itemName=123&price=123&quantity=123
     */
    //@PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam int quantity,
                       Model model) {
        //itemRepository.save(item);
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);
        return "basic/item";
    }

    // @ModelAttribute를 사용한 버전
    // @ModelAttribute를 사용 할 때, 이름을 지정해놓으면 model객체에 item까지 모두 넣어준다.
    //@PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item) {
        itemRepository.save(item);
        // model.addAttribute("item", item);
        return "basic/item";
    }

    /**
     * @ModelAttribute의 name 속성을 지우면 해당 어노테이션이 붙은 class의 앞글자르 소문자로 변경하여 model에 바인딩한다.
     * ex)
     *  - @ModelAttribute Item item
     *  - model.addAttribute("item", item);
     *
     *  - @ModelAttribute HelloData item
     *  - model.addAttribute("helloData", item);
     */
    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

    /**
     * @ModelAttribute 생략
     * - String, Integer 등의 타입은 @RequestParam이 적용
     * - 우리가 생성한 임의의 타입은 @ModelAttribute가 적용된다.
     */
    @PostMapping("/add")
    public String addItemV4(Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    // 테스트용 데이터 삽입
    // TODO: 2021-06-14 - 빈 생명주기 학습 필요
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemsA", 10000, 10));
        itemRepository.save(new Item("itemsB", 20000, 20));
    }
}
