package pl.pawlo.zptank.api.dto.transport;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransportSetDTO {

    private Long id;
    private TractorDTO tractor;
    private TrailerDTO trailer;
    private TankerTruckDTO tankerTruck;
    private boolean active;

}
