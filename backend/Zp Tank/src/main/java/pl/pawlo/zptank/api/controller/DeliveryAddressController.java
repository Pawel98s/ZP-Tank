package pl.pawlo.zptank.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.pawlo.zptank.api.dto.address.DeliveryAddressDTO;
import pl.pawlo.zptank.api.mapper.DeliveryAddressMapper;
import pl.pawlo.zptank.domain.address.DeliveryAddress;
import pl.pawlo.zptank.service.DeliveryAddressService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/delivery-address")
public class DeliveryAddressController {

    private final DeliveryAddressService deliveryAddressService;
    private final DeliveryAddressMapper deliveryAddressMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public DeliveryAddressDTO save(@RequestBody DeliveryAddressDTO deliveryAddress) {
        DeliveryAddress address = deliveryAddressMapper.mapToDomain(deliveryAddress);
        DeliveryAddress save = deliveryAddressService.save(address);
        return deliveryAddressMapper.mapToDTO(save);
    }

    @GetMapping("/{id}")
    public DeliveryAddressDTO findById(@PathVariable Long id) {
        DeliveryAddress deliveryAddress = deliveryAddressService.findById(id);
        return deliveryAddressMapper.mapToDTO(deliveryAddress);
    }

    @PatchMapping("/{id}")
    public DeliveryAddressDTO update(@PathVariable Long id,
                                      @RequestBody DeliveryAddressDTO deliveryAddress) {
        DeliveryAddress address = deliveryAddressMapper.mapToDomain(deliveryAddress);
        DeliveryAddress update = deliveryAddressService.update(id, address);
        return deliveryAddressMapper.mapToDTO(update);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        deliveryAddressService.delete(id);
    }


}
