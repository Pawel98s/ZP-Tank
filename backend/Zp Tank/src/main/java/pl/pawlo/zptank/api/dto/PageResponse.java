package pl.pawlo.zptank.api.dto;

import java.util.List;

public record PageResponse<T>(List<T> content, int page, int size, long totalElements, int totalPages) {
    public static <T> PageResponse<T> of(List<T> all, int page, int size) {
        int safeSize = Math.max(1, size);
        int safePage = Math.max(0, page);
        int from = Math.min(safePage * safeSize, all.size());
        int to = Math.min(from + safeSize, all.size());
        return new PageResponse<>(all.subList(from, to), safePage, safeSize, all.size(),
                (int) Math.ceil((double) all.size() / safeSize));
    }
}
