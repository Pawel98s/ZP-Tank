package pl.pawlo.zptank.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransportStatus {
    PLANNED("Planned"),
    ASSIGNED("Assigned"),
    READY_FOR_LOADING("Ready for loading"),
    LOADING("Loading"),
    LOADED("Loaded"),
    IN_TRANSIT("In transit"),
    DELIVERED("Delivered"),
    CANCELLED("Canceled");

    final String label;
}
