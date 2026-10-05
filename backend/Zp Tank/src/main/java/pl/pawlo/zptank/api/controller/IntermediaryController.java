package pl.pawlo.zptank.api.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.pawlo.zptank.api.dto.intermediary.IntermediaryDTO;
import pl.pawlo.zptank.api.mapper.IntermediaryMapper;
import pl.pawlo.zptank.domain.intermediary.Intermediary;
import pl.pawlo.zptank.service.IntermediaryService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/intermediaries")
public class IntermediaryController {

    private final IntermediaryService intermediaryService;
    private final IntermediaryMapper intermediaryMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public IntermediaryDTO createIntermediary(@RequestBody IntermediaryDTO intermediaryDTO) {
        Intermediary save = intermediaryService.save(intermediaryMapper.mapToDomain(intermediaryDTO));
        return intermediaryMapper.mapToDTO(save);
    }

    @GetMapping("/{id}")
    public IntermediaryDTO findById(@PathVariable Long id) {
        Intermediary byId = intermediaryService.findById(id);
        return  intermediaryMapper.mapToDTO(byId);
    }

    @GetMapping
    public List<IntermediaryDTO> findAll() {
        return intermediaryService.findAll()
                .stream()
                .map(intermediaryMapper::mapToDTO)
                .toList();
    }

    @PatchMapping("/{id}")
    public IntermediaryDTO updateIntermediary(@PathVariable Long id,
                                              @RequestBody IntermediaryDTO intermediaryDTO) {
        Intermediary intermediary = intermediaryMapper.mapToDomain(intermediaryDTO);
        Intermediary update = intermediaryService.update(id, intermediary);
        return intermediaryMapper.mapToDTO(update);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        intermediaryService.delete(id);
    }
}
