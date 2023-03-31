package com.gbsfo.test.task.service;

import com.gbsfo.test.task.entity.Item;

import java.util.List;
import java.util.Set;


public interface ItemService extends DefaultService<Item> {

    Item create(Item item);
    Set<Item> findByIds(List<Long> ids);
}
