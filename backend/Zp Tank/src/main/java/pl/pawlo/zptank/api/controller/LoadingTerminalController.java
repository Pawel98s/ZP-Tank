package pl.pawlo.zptank.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.pawlo.zptank.api.dto.transport.LoadingTerminalDTO;
import pl.pawlo.zptank.api.mapper.LoadingTerminalMapper;
import pl.pawlo.zptank.domain.transport.LoadingTerminal;
import pl.pawlo.zptank.service.LoadingTerminalService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/loading-terminals")
public class LoadingTerminalController {

    private final LoadingTerminalService loadingTerminalService;
    private final LoadingTerminalMapper loadingTerminalMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public LoadingTerminalDTO save(@RequestBody LoadingTerminalDTO loadingTerminalDTO) {
        LoadingTerminal save = loadingTerminalService.save(loadingTerminalMapper.mapToDomain(loadingTerminalDTO));
        return loadingTerminalMapper.mapToDTO(save);
    }

    @GetMapping("/{id}")
    public LoadingTerminalDTO findById(@PathVariable Long id) {
        LoadingTerminal loadingTerminal = loadingTerminalService.findById(id);
        return loadingTerminalMapper.mapToDTO(loadingTerminal);
    }

    @GetMapping
    public List<LoadingTerminalDTO> findAll() {
        return loadingTerminalService.findAll()
                .stream()
                .map(loadingTerminalMapper::mapToDTO)
                .toList();
    }

    @PatchMapping("/{id}")
    public LoadingTerminalDTO update(@PathVariable Long id,
                                     @RequestBody LoadingTerminalDTO loadingTerminalDTO) {
        LoadingTerminal loadingTerminal = loadingTerminalMapper.mapToDomain(loadingTerminalDTO);
        LoadingTerminal update = loadingTerminalService.update(id, loadingTerminal);
        return loadingTerminalMapper.mapToDTO(update);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        loadingTerminalService.delete(id);
    }
}
