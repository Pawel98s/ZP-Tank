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
import java.util.Comparator;
import java.time.LocalDate;
import pl.pawlo.zptank.api.dto.PageResponse;
import java.util.Locale;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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

    @GetMapping("/search")
    public PageResponse<TransportDTO> search(@RequestParam(required = false) TransportStatus status,
                                             @RequestParam(required = false) Long driverId,
                                             @RequestParam(required = false) Long vehicleId,
                                             @RequestParam(required = false) Long terminalId,
                                             @RequestParam(required = false) String text,
                                             @RequestParam(required = false) LocalDate from,
                                             @RequestParam(required = false) LocalDate to,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "20") int size,
                                             @RequestParam(defaultValue = "loadingDate,asc") String sort) {
        return PageResponse.of(transportService.search(status, driverId, vehicleId, terminalId, text, from, to,
                PageRequest.of(Math.max(0, page), Math.max(1, size), transportSort(sort)))
                .map(transportMapper::mapToDTO));
    }

    private Sort transportSort(String sort) {
        String[] parts = sort == null ? new String[0] : sort.split(",", 2);
        String field = parts.length == 0 ? "loadingDate" : parts[0].trim();
        boolean desc = parts.length > 1 && "desc".equalsIgnoreCase(parts[1].trim());
        String property = switch (field) {
            case "id", "status", "unloadingDate", "loadingDate" -> field;
            default -> "loadingDate";
        };
        return Sort.by(desc ? Sort.Direction.DESC : Sort.Direction.ASC, property);
    }

    private String safe(String value) { return value == null ? "" : value; }

    @GetMapping("/calendar")
    public List<TransportDTO> calendar(@RequestParam LocalDate from, @RequestParam LocalDate to) {
        return transportService.findAll().stream().filter(t -> t.getLoadingDate() != null && t.getUnloadingDate() != null
                && !t.getUnloadingDate().isBefore(from) && !t.getLoadingDate().isAfter(to))
                .map(transportMapper::mapToDTO).toList();
    }

    @GetMapping("/calendar/conflicts")
    public boolean calendarConflict(@RequestParam Long driverId, @RequestParam LocalDate from,
                                    @RequestParam LocalDate to, @RequestParam(required = false) Long excludeId) {
        return transportService.hasScheduleConflict(driverId, from, to, excludeId);
    }

    @GetMapping("/without-driver-or-vehicle")
    public List<TransportDTO> withoutDriverOrVehicle() {
        return transportService.findAll().stream().filter(t -> t.getDriver() == null ||
                t.getDriver().getTransportSet() == null ||
                !t.getDriver().getTransportSet().isActive()).map(transportMapper::mapToDTO).toList();
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
