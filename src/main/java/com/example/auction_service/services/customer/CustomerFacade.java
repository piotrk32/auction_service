package com.example.auction_service.services.customer;

import com.example.auction_service.exceptions.user.UserAlreadyExistsException;
import com.example.auction_service.models.address.Address;
import com.example.auction_service.models.customer.Customer;
import com.example.auction_service.models.customer.dtos.CustomerInputDTO;
import com.example.auction_service.models.customer.dtos.CustomerResponseDTO;
import com.example.auction_service.models.item.Item;
import com.example.auction_service.models.item.dtos.ItemResponseDTO;
import com.example.auction_service.models.user.User;
import com.example.auction_service.models.user.enums.Status;
import com.example.auction_service.services.address.AddressService;
import com.example.auction_service.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.auction_service.models.customer.dtos.CustomerMapper.mapToCustomerResponseDTO;

@Component
@RequiredArgsConstructor
public class CustomerFacade {

    private final CustomerService customerService;
    private final UserService userService;
    private final AddressService addressService;

    public CustomerResponseDTO createCustomer(CustomerInputDTO customerInputDTO, String email) {
        User user = userService.getUserByEmail(email);
        if (user.getStatus() == Status.REGISTRATION_INCOMPLETE) {
            Address emptyAddress = addressService.createEmptyAddress();
            return mapToCustomerResponseDTO(customerService.createCustomer(emptyAddress, customerInputDTO, user));
        }
        throw new UserAlreadyExistsException("User", "User already registered.");
    }

    public void deleteCustomerById(Long customerId) {
        customerService.deleteCustomerById(customerId);
    }

    public CustomerResponseDTO updateCustomerById(Long id, CustomerInputDTO customerInputDTO) {
        Customer customer = customerService.updateCustomerById(id, customerInputDTO);
        return mapToCustomerResponseDTO(customer);
    }

    public CustomerResponseDTO getCustomerById(Long customerId) {
        return mapToCustomerResponseDTO(customerService.getCustomerById(customerId));
    }



//    public List<ItemResponseDTO> findCustomerItems(Long customerId) {
//        return mapT.findCustomerItems(customerId);
//    }

}
