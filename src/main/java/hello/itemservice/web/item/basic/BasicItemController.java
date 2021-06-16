package hello.itemservice.web.item.basic;

import hello.itemservice.domain.Item;
import hello.itemservice.domain.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    //@PostMapping("/add")
    public String addItemV4(Item item) {
        itemRepository.save(item);

        // redirect 없이 POST /add를 호출하면 상품이 중복으로 저장된다.
        // redirect하여 저장 후, 경로를 변경해줘야 한다.
        // return "basic/item";

        // rediect 처리
        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String addItemV5(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);

        // rediect 처리를 할 때, 아래와 같이 URL에 변수를 단순히 더하는 것은 URL 인코딩이 안되어있기에 위험하다.
        // return "redirect:/basic/items/" + item.getId();
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        // RedirectAttributes를 사용하여 URL인코딩 및 pathVariable, 쿼리 파라미터까지 함께 사용 할 수 있다.
        return "redirect:/basic/items/{itemId}";
        // http://localhost:8080/basic/items/3?status=true
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

    @DeleteMapping("/{itemId}/delete")
    public String deleteItem(@PathVariable Long itemId) {
        itemRepository.delete(itemId);
        return "redirect:/basic/items";
    }

    // 테스트용 데이터 삽입
    // TODO: 2021-06-14 - 빈 생명주기 학습 필요
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemsA", 10000, 10));
        itemRepository.save(new Item("itemsB", 20000, 20));
    }
}
