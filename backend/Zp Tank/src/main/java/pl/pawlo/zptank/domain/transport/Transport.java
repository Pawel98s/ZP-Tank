package pl.pawlo.zptank.domain.transport;

import lombok.*;
import pl.pawlo.zptank.domain.TransportStatus;
import pl.pawlo.zptank.domain.order.Order;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
public class Transport {

    private Long id;
    private TransportStatus status;
    private Driver driver;
    private List<Order> orders;
    private LoadingTerminal loadingTerminal;
    private LocalDate loadingDate;
    private LocalDate unloadingDate;

}
