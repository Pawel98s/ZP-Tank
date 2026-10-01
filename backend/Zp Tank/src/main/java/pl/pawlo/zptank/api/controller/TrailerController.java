package pl.pawlo.zptank.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.pawlo.zptank.api.dto.transport.TrailerDTO;
import pl.pawlo.zptank.api.mapper.TrailerMapper;
import pl.pawlo.zptank.domain.transport.Trailer;
import pl.pawlo.zptank.service.TrailerService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/trailer")
public class TrailerController {

    private final TrailerService trailerService;
    private final TrailerMapper trailerMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TrailerDTO save(@RequestBody TrailerDTO trailerDTO) {
        Trailer save = trailerService.save(trailerMapper.mapToDomain(trailerDTO));
        return trailerMapper.mapToDTO(save);

    }

    @GetMapping("/{id}")
    public TrailerDTO findById(@PathVariable Long id) {
        Trailer trailer = trailerService.findById(id);
        return trailerMapper.mapToDTO(trailer);
    }

    @GetMapping
    public List<TrailerDTO> findAll() {
        return trailerService.findAll()
                .stream()
                .map(trailerMapper::mapToDTO)
                .toList();
    }


    @PatchMapping("/{id}")
    public TrailerDTO update(@PathVariable Long id,
                             @RequestBody TrailerDTO trailerDTO) {
        Trailer trailer = trailerMapper.mapToDomain(trailerDTO);
        Trailer update = trailerService.update(id, trailer);
        return trailerMapper.mapToDTO(update);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        trailerService.delete(id);
    }
}
