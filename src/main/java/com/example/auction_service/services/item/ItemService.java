package com.example.auction_service.services.item;

import com.example.auction_service.exceptions.EntityNotFoundException;
import com.example.auction_service.models.item.Item;
import com.example.auction_service.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item getItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item", "No item found with id: " + itemId));
    }

    public Item createItem(ItemInputDTO itemInputDTO, Provider provider) {
        Item item = new Item(
                provider,
                itemInputDTO.getItemName(),
                itemInputDTO.getDescription(),
                itemInputDTO.getStartingPrice(),
                itemInputDTO.getImageUrl(),
                itemInputDTO.getIsBuyNowActive(),
                itemInputDTO.getBuyNowPrice()
        );

        return itemRepository.saveAndFlush(item);
    }

    public Item updateItemById(Long itemId, ItemInputDTO itemInputDTO) {
        Item item = getItemById(itemId);
        item.setItemName(itemInputDTO.getItemName());
        item.setDescription(itemInputDTO.getDescription());
        item.setStartingPrice(itemInputDTO.getStartingPrice());
        item.setImageUrl(itemInputDTO.getImageUrl());
        item.setIsBuyNowActive(itemInputDTO.getIsBuyNowActive());
        item.setBuyNowPrice(itemInputDTO.getBuyNowPrice());

        return itemRepository.saveAndFlush(item);
    }

    public void deleteItemById(Long itemId) {
        Item item = getItemById(itemId);
        itemRepository.delete(item);
    }