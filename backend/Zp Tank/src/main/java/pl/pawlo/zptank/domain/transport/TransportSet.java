package pl.pawlo.zptank.domain.transport;

import lombok.*;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
public class TransportSet {

    private Long id;
    private Tractor tractor;
    private Trailer trailer;
    private TankerTruck tankerTruck;
    private boolean active;

}
