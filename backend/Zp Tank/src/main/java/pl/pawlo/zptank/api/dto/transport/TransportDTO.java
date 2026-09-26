package pl.pawlo.zptank.api.dto.transport;

import lombok.*;
import pl.pawlo.zptank.api.dto.order.OrderDTO;
import pl.pawlo.zptank.domain.TransportStatus;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransportDTO {

    private Long id;
    private TransportStatus status;
    private DriverDTO driver;
    private List<OrderDTO> orders;
    private LoadingTerminalDTO loadingTerminal;
    private LocalDate loadingDate;
    private LocalDate unloadingDate;

}
