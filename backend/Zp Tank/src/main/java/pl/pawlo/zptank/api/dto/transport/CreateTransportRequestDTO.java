package pl.pawlo.zptank.api.dto.transport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransportRequestDTO {

    private Long driverId;
    private Long loadingTerminalId;
    private List<Long> orderIds;
    private LocalDate loadingDate;
    private LocalDate unloadingDate;
}
