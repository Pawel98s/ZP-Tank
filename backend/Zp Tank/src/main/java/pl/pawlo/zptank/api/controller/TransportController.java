package pl.pawlo.zptank.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.pawlo.zptank.api.dto.transport.CreateTransportRequestDTO;
import pl.pawlo.zptank.api.dto.transport.TransportDTO;
import pl.pawlo.zptank.api.dto.transport.UpdateTransportRequestDTO;
import pl.pawlo.zptank.api.dto.transport.UpdateTransportStatusDTO;
import pl.pawlo.zptank.api.mapper.TransportMapper;
import pl.pawlo.zptank.domain.TransportStatus;
import pl.pawlo.zptank.domain.transport.Transport;
import pl.pawlo.zptank.service.TransportService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/transports")
public class TransportController {

    private final TransportService transportService;
    private final TransportMapper transportMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TransportDTO create(@RequestBody CreateTransportRequestDTO transportDTO) {
        Transport transport = transportService.createTransport(transportMapper.mapToRequest(transportDTO));
        return transportMapper.mapToDTO(transport);
    }

    @GetMapping("/{id}")
    public TransportDTO findById(@PathVariable Long id) {
        Transport transport = transportService.findById(id);
        return transportMapper.mapToDTO(transport);
    }

    @GetMapping
    public List<TransportDTO> findAll(@RequestParam(required = false) TransportStatus status) {
        List<Transport> transports;

        if (status != null) {
            transports = transportService.findByStatus(status);
        } else {
            transports = transportService.findAll();
        }
        return transports.stream()
                .map(transportMapper::mapToDTO)
                .toList();
    }


    @PatchMapping("/{id}")
    public TransportDTO update(@PathVariable Long id,
                               @RequestBody UpdateTransportRequestDTO updateTransportRequestDTO) {
        Transport transport = transportService.updateTransport(id, transportMapper.mapToUpdateRequest(updateTransportRequestDTO));
        return transportMapper.mapToDTO(transport);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        transportService.deleteTransport(id);
    }

    @PatchMapping("/{id}/status")
    public TransportDTO updateStatus(@PathVariable Long id,
                                    @RequestBody UpdateTransportStatusDTO updateTransportStatusDTO) {
        Transport transport = transportService.updateStatus(id, updateTransportStatusDTO.getStatus());
        return transportMapper.mapToDTO(transport);
    }

    @GetMapping("/driver/{driverId}")
    public List<TransportDTO> findByDriverId(@PathVariable Long driverId) {
        List<Transport> transports = transportService.findByDriverId(driverId);
        return transports.stream()
                .map(transportMapper::mapToDTO)
                .toList();

    }
}
