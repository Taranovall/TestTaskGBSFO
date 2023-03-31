package com.gbsfo.test.task.service.impl;

import com.gbsfo.test.task.entity.Item;
import com.gbsfo.test.task.entity.Role;
import com.gbsfo.test.task.entity.User;
import com.gbsfo.test.task.repository.ItemRepository;
import com.gbsfo.test.task.service.ItemService;
import com.gbsfo.test.task.util.EntityRetriever;
import com.gbsfo.test.task.util.UserUtil;
import com.gbsfo.test.task.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final EntityRetriever entityRetriever;
    private final ValidationUtil validationUtil;

    @Override
    public Item create(Item item) {
        item.setUser(UserUtil.getCurrentUser());
        return itemRepository.save(item);
    }

    @Transactional(readOnly = true)
    @Override
    public Item getById(Long itemId) {
        return entityRetriever.retrieveEntityById(itemId, itemRepository);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Item> getAll() {
        User currentUser = UserUtil.getCurrentUser();
        if (currentUser.getRole() == Role.USER) {
            return itemRepository.findAllByCreatorId(currentUser.getId());
        }
        return itemRepository.findAll();
    }

    @Override
    public Item update(Item item) {
        validationUtil.validateEntityUpdating(item);
        return itemRepository.save(item);
    }

    @Override
    public void delete(Long itemId) {
        validationUtil.validateEntityUpdating(getById(itemId));
        itemRepository.deleteById(itemId);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<Item> findByIds(List<Long> ids) {
        return itemRepository.findByIds(ids);
    }
}
