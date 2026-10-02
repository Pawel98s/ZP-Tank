package pl.pawlo.zptank.api.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.pawlo.zptank.api.dto.transport.TransportSetDTO;
import pl.pawlo.zptank.api.mapper.TransportSetMapper;
import pl.pawlo.zptank.domain.transport.TransportSet;
import pl.pawlo.zptank.service.TransportSetService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/transport-sets")
public class TransportSetController {

    private final TransportSetService transportSetService;
    private final TransportSetMapper transportSetMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TransportSetDTO save(@RequestBody TransportSetDTO transportSetDTO) {
        TransportSet save = transportSetService.create(transportSetMapper.mapToDomain(transportSetDTO));
        return transportSetMapper.mapToDTO(save);
    }

    @GetMapping("/{id}")
    public TransportSetDTO findById(@PathVariable Long id) {
        TransportSet transportSet = transportSetService.findById(id);
        return transportSetMapper.mapToDTO(transportSet);
    }

    @GetMapping
    public List<TransportSetDTO> findAll() {
        return transportSetService.findAll().stream()
                .map(transportSetMapper::mapToDTO)
                .toList();
    }

    @PutMapping("/{id}")
    public TransportSetDTO update(@PathVariable Long id,
                                  @RequestBody TransportSetDTO transportSetDTO) {
        TransportSet transportSet = transportSetMapper.mapToDomain(transportSetDTO);
        TransportSet update = transportSetService.update(id, transportSet);
        return transportSetMapper.mapToDTO(update);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        transportSetService.delete(id);
    }
}
