package pl.pawlo.zptank.api.dto.transport;


import lombok.Getter;
import lombok.Setter;
import pl.pawlo.zptank.domain.TransportStatus;

@Getter
@Setter
public class UpdateTransportStatusDTO {

    private TransportStatus status;
}
