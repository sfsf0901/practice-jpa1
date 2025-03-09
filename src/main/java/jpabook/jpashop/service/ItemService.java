package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Long save(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    // 더티 체킹으로 업데이트
    @Transactional
    public Item update(Long itemId, Book param) {
        Book findItem = (Book) itemRepository.findById(itemId);
        findItem.setName(param.getName());
        findItem.setPrice(param.getPrice());
        findItem.setStockQuantity(param.getStockQuantity());
        findItem.setAuthor(param.getAuthor());
        findItem.setIsbn(param.getIsbn());
        // 더이상 할 게 없다! 수정 완료!

        return findItem;
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item findById(Long itemId) {
        return itemRepository.findById(itemId);
    }
}
