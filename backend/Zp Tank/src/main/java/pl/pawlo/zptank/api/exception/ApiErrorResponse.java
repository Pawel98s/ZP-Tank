package pl.pawlo.zptank.api.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiErrorResponse(
        String code,
        String message,
        LocalDateTime timestamp,
        Map<String, String> fieldErrors
) {
}
