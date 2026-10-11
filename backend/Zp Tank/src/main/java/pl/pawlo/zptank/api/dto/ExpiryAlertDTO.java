package pl.pawlo.zptank.api.dto;

import java.time.LocalDate;

public record ExpiryAlertDTO(String resourceType, Long resourceId, String resourceName,
                             String document, LocalDate expiresAt, long daysRemaining,
                             boolean expired, boolean unavailable) {}
