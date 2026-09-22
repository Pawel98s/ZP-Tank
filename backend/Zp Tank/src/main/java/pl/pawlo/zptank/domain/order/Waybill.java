package pl.pawlo.zptank.domain.order;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
public class Waybill {

    private Long id;
    private String waybillNumber;
    private LocalDate issueDate;
}
