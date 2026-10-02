package pl.pawlo.zptank.api.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.pawlo.zptank.api.dto.transport.TankerTruckDTO;
import pl.pawlo.zptank.api.mapper.TankerTruckMapper;
import pl.pawlo.zptank.domain.transport.TankerTruck;
import pl.pawlo.zptank.service.TankerTruckService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/tanker-trucks")
public class TankerTruckController {

    private final TankerTruckService tankerTruckService;
    private final TankerTruckMapper tankerTruckMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TankerTruckDTO save(@RequestBody TankerTruckDTO tankerTruckDTO) {
        TankerTruck save = tankerTruckService.save(tankerTruckMapper.mapToDomain(tankerTruckDTO));
        return tankerTruckMapper.mapToDTO(save);
    }

    @GetMapping("/{id}")
    public TankerTruckDTO findById(@PathVariable Long id) {
        TankerTruck tankerTruck = tankerTruckService.findById(id);
        return tankerTruckMapper.mapToDTO(tankerTruck);
    }

    @GetMapping()
    public List<TankerTruckDTO> findAll() {
        return tankerTruckService.findAll()
                .stream()
                .map(tankerTruckMapper::mapToDTO)
                .toList();
    }

    @PatchMapping("/{id}")
    public TankerTruckDTO update(@PathVariable Long id,
                                 @RequestBody TankerTruckDTO tankerTruckDTO) {
        TankerTruck tankerTruck = tankerTruckMapper.mapToDomain(tankerTruckDTO);
        TankerTruck update = tankerTruckService.update(id, tankerTruck);
        return tankerTruckMapper.mapToDTO(update);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        tankerTruckService.delete(id);
    }

}
