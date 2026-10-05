package pl.pawlo.zptank.service.dao;

import pl.pawlo.zptank.domain.order.Waybill;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WaybillDAO {

    Waybill save(Waybill waybill);

    Optional<Waybill> findById(Long id);

    List<Waybill> findAll();

    Waybill update(Waybill waybill);

    void delete(Long id);

    Optional<Waybill> findLastByIssueDateBetween(LocalDate startDate, LocalDate endDate);

    void lockWaybillNumberGeneration(String lockKey);
}
