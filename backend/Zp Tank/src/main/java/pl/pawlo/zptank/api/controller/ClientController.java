package pl.pawlo.zptank.api.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pawlo.zptank.api.dto.client.ClientDTO;
import pl.pawlo.zptank.api.mapper.ClientMapper;
import pl.pawlo.zptank.domain.client.Client;
import pl.pawlo.zptank.service.ClientService;

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
}
