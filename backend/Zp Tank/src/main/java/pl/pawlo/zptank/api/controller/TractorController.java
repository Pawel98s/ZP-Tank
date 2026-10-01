package pl.pawlo.zptank.api.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.pawlo.zptank.api.dto.transport.TractorDTO;
import pl.pawlo.zptank.api.mapper.TractorMapper;
import pl.pawlo.zptank.domain.transport.Tractor;
import pl.pawlo.zptank.service.TractorService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/tractors")
public class TractorController {

    private final TractorService tractorService;
    private final TractorMapper tractorMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TractorDTO save(@RequestBody TractorDTO tractorDTO) {
        Tractor save = tractorService.save(tractorMapper.mapToDomain(tractorDTO));
        return tractorMapper.mapToDTO(save);
    }

    @GetMapping("/{id}")
    public TractorDTO findById(@PathVariable Long id) {
        Tractor tractor = tractorService.findById(id);
        return tractorMapper.mapToDTO(tractor);
    }

    @GetMapping
    public List<TractorDTO> findAll() {
        List<Tractor> tractors = tractorService.findAll();
        return tractors.stream()
                .map(tractorMapper::mapToDTO)
                .toList();
    }

    @PatchMapping("/{id}")
    public TractorDTO update(@PathVariable Long id,
                             @RequestBody TractorDTO tractorDTO) {
        Tractor tractor = tractorMapper.mapToDomain(tractorDTO);
        Tractor update = tractorService.update(id, tractor);
        return tractorMapper.mapToDTO(update);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        tractorService.delete(id);
    }

}
