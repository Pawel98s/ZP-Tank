package pl.pawlo.zptank.api.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.pawlo.zptank.api.dto.address.AddressDTO;
import pl.pawlo.zptank.api.mapper.AddressMapper;
import pl.pawlo.zptank.domain.address.Address;
import pl.pawlo.zptank.service.AddressService;

@RestController
@RequestMapping("/api/address")
@AllArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final AddressMapper addressMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AddressDTO save(@RequestBody AddressDTO addressDTO) {
        Address address = addressMapper.mapToDomain(addressDTO);
        Address save = addressService.save(address);
        return addressMapper.matToDTO(save);
    }

    @GetMapping("/{id}")
    public AddressDTO findById(@PathVariable Long id) {
        Address address = addressService.findById(id);
        return addressMapper.matToDTO(address);
    }

    @PatchMapping("/{id}")
    public AddressDTO update(@PathVariable Long id,
                             @RequestBody AddressDTO addressDTO) {
        Address address = addressMapper.mapToDomain(addressDTO);
        Address update = addressService.update(id, address);
        return addressMapper.matToDTO(update);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        addressService.delete(id);
    }
}
