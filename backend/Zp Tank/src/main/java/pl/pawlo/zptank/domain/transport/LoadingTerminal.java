package pl.pawlo.zptank.domain.transport;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import pl.pawlo.zptank.domain.address.Address;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
public class LoadingTerminal {

    private Long id;
    private Address address;

}
