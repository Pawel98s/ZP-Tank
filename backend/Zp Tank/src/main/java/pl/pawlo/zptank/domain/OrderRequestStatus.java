package pl.pawlo.zptank.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderRequestStatus {
    NEW("New"),
    CONFIRMED("Confirmed"),
    IN_PROGRESS("In progress"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    final String label;
}
