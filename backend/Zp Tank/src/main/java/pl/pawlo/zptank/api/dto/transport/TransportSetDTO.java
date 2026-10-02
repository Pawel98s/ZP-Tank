package pl.pawlo.zptank.api.dto.transport;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String displayName;

}
