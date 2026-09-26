package pl.pawlo.zptank.api.dto.order;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WaybillDTO {

    private Long id;
    private String waybillNumber;
    private LocalDate issueDate;
}
