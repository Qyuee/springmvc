package hello.itemservice.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository // component scan의 대상이된다.
public class ItemRepository {
    // 멀티쓰레드 환경에서는 HashMap이 아닌 ConcurrentHashMap을 사용해야 동시성 이슈를 해결 할 수 있다.
    private static final Map<Long, Item> store = new HashMap<>();
    private static long sequence = 0L;

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    // 원래는 DTO를 따로 생성하여 업데이트에 사용되는 필드만 가진 객체를 사용하는게 맞다.
    // 단, 프로젝트의 규모가 작거나 개발자가 인지하고 있을 수 있는 범위내에서는 domain을 공유한다.
    // DTO를 따로 생성하면 기존의 domain과 중복되어 보이지만 명확하게 그 사용에 따른 구분을 나눌 수 있으므로
    // 명확성을 따라가는 것이 좋다.
    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }
}
