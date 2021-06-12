package hello.itemservice.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    // 각 테스트마다 clear
    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        // given
        Item item = new Item("itemA", 10000, 10);

        // when
        Item savedItem = itemRepository.save(item);

        // then
        Item findItem = itemRepository.findById(savedItem.getId());
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
        // given
        Item itemA = new Item("itemA", 10000, 10);
        Item itemB = new Item("itemB", 12000, 20);

        Item savedItemA = itemRepository.save(itemA);
        Item savedItemB = itemRepository.save(itemB);

        // when
        List<Item> items = itemRepository.findAll();

        // then
        assertThat(items.size()).isEqualTo(2);
        assertThat(items).contains(savedItemA, savedItemB);
    }

    @Test
    void update() {
        // given
        Item itemA = new Item("itemA", 10000, 10);
        Item savedItem = itemRepository.save(itemA);
        Long itemId = savedItem.getId();

        // when
        Item newItem = new Item("update_itemA", 12000, 20);
        itemRepository.update(itemId, newItem);
        Item updateItem = itemRepository.findById(itemId);

        // then
        assertThat(updateItem.getItemName()).isEqualTo(newItem.getItemName());
        assertThat(updateItem.getPrice()).isEqualTo(newItem.getPrice());
        assertThat(updateItem.getQuantity()).isEqualTo(newItem.getQuantity());
    }

    @Test
    void clearStore() {
    }
}