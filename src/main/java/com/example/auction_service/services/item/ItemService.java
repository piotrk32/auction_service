package com.example.auction_service.services.item;

import com.example.auction_service.exceptions.EntityNotFoundException;
import com.example.auction_service.models.item.Item;
import com.example.auction_service.models.item.dtos.ItemInputDTO;
import com.example.auction_service.models.provider.Provider;
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
                itemInputDTO.itemName(),
                itemInputDTO.description(),
                itemInputDTO.startingPrice(),
                itemInputDTO.imageUrl(),
                itemInputDTO.isBuyNowActive(),
                itemInputDTO.buyNowPrice()
        );

        return itemRepository.saveAndFlush(item);
    }

    public Item updateItemById(Long itemId, ItemInputDTO itemInputDTO) {
        Item item = getItemById(itemId);
        item.setItemName(itemInputDTO.itemName());
        item.setDescription(itemInputDTO.description());
        item.setStartingPrice(itemInputDTO.startingPrice());
        item.setImageUrl(itemInputDTO.imageUrl());
        item.setIsBuyNowActive(itemInputDTO.isBuyNowActive());
        item.setBuyNowPrice(itemInputDTO.buyNowPrice());

        return itemRepository.saveAndFlush(item);
    }

    public void deleteItemById(Long itemId) {
        Item item = getItemById(itemId);
        itemRepository.delete(item);
    }
}