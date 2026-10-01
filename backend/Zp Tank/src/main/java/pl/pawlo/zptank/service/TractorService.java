package pl.pawlo.zptank.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pawlo.zptank.domain.transport.Tractor;
import pl.pawlo.zptank.service.dao.TractorDAO;

import java.util.List;

@Service
@AllArgsConstructor
public class TractorService {

    private final TractorDAO tractorDAO;

    public Tractor save(Tractor tractor) {
        return tractorDAO.save(tractor);
    }

    public Tractor findById(Long id) {
        return tractorDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Tractor not found"));
    }

    public List<Tractor> findAll() {
        return tractorDAO.findAll();
    }

    public Tractor update(Long id, Tractor tractor) {
        Tractor existingTractor = findById(id);

        Tractor updatedTractor = Tractor.builder()
                .id(existingTractor.getId())
                .registrationNumber(tractor.getRegistrationNumber() != null
                        ? tractor.getRegistrationNumber()
                        : existingTractor.getRegistrationNumber())
                .brand(tractor.getBrand() != null
                        ? tractor.getBrand()
                        : existingTractor.getBrand())
                .model(tractor.getModel() != null
                        ? tractor.getModel()
                        : existingTractor.getModel())
                .periodicInspectionDate(tractor.getPeriodicInspectionDate() != null
                        ? tractor.getPeriodicInspectionDate()
                        : existingTractor.getPeriodicInspectionDate())
                .insuranceValidUntil(tractor.getInsuranceValidUntil() != null
                        ? tractor.getInsuranceValidUntil()
                        : existingTractor.getInsuranceValidUntil())
                .redStripeValidUntil(tractor.getRedStripeValidUntil() != null
                        ? tractor.getRedStripeValidUntil()
                        : existingTractor.getRedStripeValidUntil())
                .tachographReadingDate(tractor.getTachographReadingDate() != null
                        ? tractor.getTachographReadingDate()
                        : existingTractor.getTachographReadingDate())
                .tachographCalibrationDate(tractor.getTachographCalibrationDate() != null
                        ? tractor.getTachographCalibrationDate()
                        : existingTractor.getTachographCalibrationDate())
                .gps(tractor.getGps() != null
                        ? tractor.getGps()
                        : existingTractor.getGps())
                .build();

        return tractorDAO.update(updatedTractor);
    }

    public void delete(Long id) {
        Tractor tractor = findById(id);
        tractorDAO.delete(tractor.getId());
    }
}
