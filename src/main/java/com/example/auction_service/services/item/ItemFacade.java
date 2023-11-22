package com.example.auction_service.services.item;

import com.example.auction_service.models.item.Item;
import com.example.auction_service.models.item.dtos.*;
import com.example.auction_service.models.provider.Provider;
import com.example.auction_service.services.provider.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import static com.example.auction_service.models.item.dtos.ItemMapper.mapToItemResponseDTO;


@Component
@RequiredArgsConstructor
public class ItemFacade {

    private final ItemService itemService;
    private final ProviderService providerService;

    public ItemResponseDTO createItem(ItemInputDTO itemInputDTO) {
        Provider provider = providerService.getProviderById(itemInputDTO.providerId());
        Item item = itemService.createItem(itemInputDTO, provider);
        return mapToItemResponseDTO(item);
    }

    public ItemResponseDTO getItemById(Long itemId) {
        Item item = itemService.getItemById(itemId);
        return mapToItemResponseDTO(item);
    }

    public void deleteItemById(Long itemId) {
        itemService.deleteItemById(itemId);
    }

    public ItemResponseDTO updateItemById(Long itemId, ItemInputDTO itemInputDTO) {
        Item updatedItem = itemService.updateItemById(itemId, itemInputDTO);
        return mapToItemResponseDTO(updatedItem);
    }

    public ItemPurchaseDTO buyNow(Long itemId, Long customerId) {
        ItemPurchaseDTO purchaseDTO = itemService.buyNow(itemId, customerId);
        itemService.assignItemToCustomer(itemId, customerId); // Przypisanie przedmiotu do klienta
        return purchaseDTO;
    }

    public Page<ItemResponseDTO> getItems(ItemRequestDTO itemRequestDTO) {
        return itemService.getItems(itemRequestDTO).map(ItemMapper::mapToItemResponseDTO);
    }

    // Metoda do przypisania przedmiotu do klienta
    public void assignItemToCustomer(Long itemId, Long customerId) {
        itemService.assignItemToCustomer(itemId, customerId);
    }

    //TODO enpointy do ProviderItemController oraz ItemController
}
