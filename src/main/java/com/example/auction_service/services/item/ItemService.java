package com.example.auction_service.services.item;

import com.example.auction_service.exceptions.EntityNotFoundException;
import com.example.auction_service.models.customer.Customer;
import com.example.auction_service.models.item.Item;
import com.example.auction_service.models.item.dtos.ItemInputDTO;
import com.example.auction_service.models.item.dtos.ItemPurchaseDTO;
import com.example.auction_service.models.item.dtos.ItemRequestDTO;
import com.example.auction_service.models.provider.Provider;
import com.example.auction_service.repositories.CustomerRepository;
import com.example.auction_service.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final CustomerRepository customerRepository;

    public Item getItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item", "No item found with id: " + itemId));
    }

    public Item createItem(ItemInputDTO itemInputDTO, Provider provider) {


        Item item = new Item(provider,
                itemInputDTO.itemName(),
                itemInputDTO.description(),
                itemInputDTO.startingPrice(),
                itemInputDTO.imageUrl(),
                itemInputDTO.isBuyNowActive(),
                itemInputDTO.buyNowPrice(),
                itemInputDTO.isSold()
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

    public ItemPurchaseDTO buyNow(Long itemId, Long customerId) {
        // Pobranie przedmiotu
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item", "No item found with id: " + itemId));

        // Sprawdzenie, czy opcja "Kup Teraz" jest aktywna
        if (!item.getIsBuyNowActive()) {
            throw new IllegalStateException("Buy now option is not active for this item.");
        }

        // Sprawdzenie, czy przedmiot nie został już sprzedany
        if (item.getIsSold()) {
            throw new IllegalStateException("Item has been sold.");
        }

        // Pobranie klienta
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer", "No customer found with id: " + customerId));

        // Ustawienie przedmiotu jako sprzedanego i przypisanie go do klienta
        item.setIsSold(true);
        item.setCustomer(customer); // Przypisanie klienta do przedmiotu
        itemRepository.save(item);

        // Tworzenie i zwracanie DTO
        return ItemPurchaseDTO.builder()
                .itemId(item.getId())
                .providerId(item.getProvider().getId())
                .itemName(item.getItemName())
                .buyNowPrice(item.getBuyNowPrice())
                .isSold(item.getIsSold())
                .build();
    }
    public Page<Item> getItems(ItemRequestDTO itemRequestDTO) {
        PageRequest pageRequest = PageRequest.of(
                Integer.parseInt(itemRequestDTO.getPage()),
                Integer.parseInt(itemRequestDTO.getSize()),
                Sort.Direction.fromString(itemRequestDTO.getDirection()),
                itemRequestDTO.getSortParam());

        Specification<Item> spec = Specification.where(null);

        if (itemRequestDTO.getItemNameSearch() != null) {
            spec = spec.and(ItemSpecification.itemNameContains(itemRequestDTO.getItemNameSearch()));
        }
        if (itemRequestDTO.getItemCategorySearch() != null) {
            spec = spec.and(ItemSpecification.itemCategoryContains(itemRequestDTO.getItemCategorySearch()));
        }
        if (itemRequestDTO.getPriceFrom() != null) {
            spec = spec.and(ItemSpecification.priceGreaterThanOrEqual(Double.parseDouble(itemRequestDTO.getPriceFrom())));
        }
        if (itemRequestDTO.getPriceTo() != null) {
            spec = spec.and(ItemSpecification.priceLessThanOrEqual(Double.parseDouble(itemRequestDTO.getPriceTo())));
        }
        if (itemRequestDTO.getIsBuyNowActive() != null) {
            spec = spec.and(ItemSpecification.isBuyNowActive(itemRequestDTO.getIsBuyNowActive()));
        }
        if (itemRequestDTO.getIsSold() != null) {
            spec = spec.and(ItemSpecification.isSold(itemRequestDTO.getIsSold()));
        }

        return itemRepository.findAll(spec, pageRequest);
    }

    public void assignItemToCustomer(Long itemId, Long customerId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item", "No item found with id: " + itemId));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer", "No customer found with id: " + customerId));

        item.setCustomer(customer); // Przypisanie klienta do przedmiotu
        itemRepository.save(item);
    }
}

