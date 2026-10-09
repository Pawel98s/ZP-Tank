package pl.pawlo.zptank.api.dto.transport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pawlo.zptank.api.dto.address.AddressDTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoadingTerminalDTO {

    private Long id;
    private AddressDTO address;

}
