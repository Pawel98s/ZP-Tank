package pl.pawlo.zptank.domain.transport;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
@Builder
public class CreateTransportRequest {

    Long driverId;
    Long loadingTerminalId;
    List<Long> orderIds;
    LocalDate loadingDate;
    LocalDate unloadingDate;
}
