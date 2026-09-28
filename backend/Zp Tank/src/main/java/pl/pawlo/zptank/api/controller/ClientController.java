package pl.pawlo.zptank.api.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.pawlo.zptank.api.dto.client.ClientDTO;
import pl.pawlo.zptank.api.mapper.ClientMapper;
import pl.pawlo.zptank.domain.client.Client;
import pl.pawlo.zptank.service.ClientService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
@AllArgsConstructor
public class ClientController {

    ClientService clientService;
    ClientMapper clientMapper;

    @PostMapping
    public ClientDTO save(@RequestBody ClientDTO clientDTO){
        Client client = clientMapper.mapToDomain(clientDTO);
        Client saveClient = clientService.save(client);
        return clientMapper.mapToDTO(saveClient);
    }

    @GetMapping
    public List<ClientDTO> findAll(){
        List<Client> clients = clientService.findAll();
        return clients.stream()
                .map(clientMapper::mapToDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ClientDTO findById(@PathVariable Long id){
        Client client = clientService.findById(id);
        return clientMapper.mapToDTO(client);
    }

    @PatchMapping("/{id}")
    public ClientDTO update(@PathVariable Long id,
                            @RequestBody ClientDTO clientDTO){
        Client client = clientMapper.mapToDomain(clientDTO);
        Client updatedClient = clientService.update(id, client);
        return clientMapper.mapToDTO(updatedClient);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        clientService.delete(id);
    }

}
