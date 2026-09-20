package pl.pawlo.zptank.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {
    NEW("New"),
    CONFIRMED("Completed"),
    PLANNED("Planned"),
    READY_FOR_LOADING("Ready for loading"),
    LOADING("Loading"),
    IN_TRANSIT("In transit"),
    DELIVERED("Delivered"),
    CANCELLED("Cancelled");

    final String label;
}
