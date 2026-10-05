package pl.pawlo.zptank.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pawlo.zptank.domain.order.Waybill;
import pl.pawlo.zptank.service.dao.WaybillDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WaybillService {

    private final WaybillDAO waybillDAO;

    public Waybill save(Waybill waybill) {
       return waybillDAO.save(waybill);
    }

    public Waybill findById(Long id) {
        return waybillDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Waybill set not found"));
    }

    public List<Waybill> findAll() {
        return  waybillDAO.findAll();
    }

    public Waybill update(Long id, Waybill waybill) {
        Waybill existingWaybill = findById(id);
        Waybill updatedWaybill = Waybill.builder()
                .id(existingWaybill.getId())
                .waybillNumber(waybill.getWaybillNumber() != null
                        ? waybill.getWaybillNumber()
                        : existingWaybill.getWaybillNumber())
                .issueDate(waybill.getIssueDate() != null
                        ? waybill.getIssueDate()
                        : existingWaybill.getIssueDate())
                .build();

        waybillDAO.update(updatedWaybill);
        return updatedWaybill;
    }

    @Transactional
    public Waybill generate() {
        LocalDate today = LocalDate.now();

        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());

        Optional<Waybill> lastWaybill =
                waybillDAO.findLastByIssueDateBetween(
                        startOfMonth,
                        endOfMonth
                );

        int nextNumber = lastWaybill
                .map(this::extractNumber)
                .orElse(1);

        String waybillNumber =
                nextNumber + "/" +
                        today.getMonthValue() + "/" +
                        today.getYear();

        Waybill waybill = Waybill.builder()
                .waybillNumber(waybillNumber)
                .issueDate(today)
                .build();

        return waybillDAO.save(waybill);
    }

    private int extractNumber(Waybill waybill) {
        return Integer.parseInt(
                waybill.getWaybillNumber()
                        .split("/")[0]
        ) + 1;
    }


}
