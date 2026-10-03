package pl.pawlo.zptank.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.pawlo.zptank.api.dto.transport.DriverDTO;
import pl.pawlo.zptank.api.mapper.DriverMapper;
import pl.pawlo.zptank.domain.transport.Driver;
import pl.pawlo.zptank.service.DriverService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverService driverService;
    private final DriverMapper driverMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public DriverDTO save(@RequestBody DriverDTO driverDTO) {
        Driver save = driverService.save(driverMapper.mapToDomain(driverDTO));
        return driverMapper.mapToDTO(save);
    }

    @GetMapping("/{id}")
    public DriverDTO findById(@PathVariable Long id) {
        Driver driver = driverService.findById(id);
        return driverMapper.mapToDTO(driver);
    }

    @GetMapping
    public List<DriverDTO> findAll() {
        return driverService.findAll().stream()
                .map(driverMapper::mapToDTO)
                .toList();
    }

    @GetMapping("/search")
    public List<DriverDTO> searchByLastName(@RequestParam String lastName) {
        return driverService.findByLastName(lastName)
                .stream()
                .map(driverMapper::mapToDTO)
                .toList();
    }

    @PatchMapping("/{id}")
    public DriverDTO update(@PathVariable Long id,
                            @RequestBody DriverDTO driverDTO) {
        Driver driver = driverMapper.mapToDomain(driverDTO);
        Driver update = driverService.update(id, driver);
        return driverMapper.mapToDTO(update);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        driverService.delete(id);
    }
}
