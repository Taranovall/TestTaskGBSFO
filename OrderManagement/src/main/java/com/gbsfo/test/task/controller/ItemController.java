package com.gbsfo.test.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbsfo.test.task.payload.request.ItemRequest;
import com.gbsfo.test.task.payload.response.ItemResponse;
import com.gbsfo.test.task.entity.Item;
import com.gbsfo.test.task.service.ItemService;
import com.gbsfo.test.task.util.Converter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ObjectMapper objectMapper;
    private final Converter converter;

    @PostMapping
    public ResponseEntity<ItemResponse> createItem(@RequestBody @Valid ItemRequest itemRequest) {
        Item item = objectMapper.convertValue(itemRequest, Item.class);
        return ResponseEntity.ok(converter.convertItemToDto(itemService.create(item)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItemById(@PathVariable Long id) {
        Item item = itemService.getById(id);
        return ResponseEntity.ok(converter.convertItemToDto(item));
    }

    @GetMapping
    public ResponseEntity<Set<ItemResponse>> getAllItems() {
        Set<ItemResponse> responseList = itemService.getAll()
                .stream()
                .map(converter::convertItemToDto)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(responseList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@RequestBody @Valid ItemRequest itemRequest, @PathVariable Long id) {
        Item item = objectMapper.convertValue(itemRequest, Item.class);
        item.setId(id);
        return ResponseEntity.ok(itemService.update(item));
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemService.delete(id);
    }
}
