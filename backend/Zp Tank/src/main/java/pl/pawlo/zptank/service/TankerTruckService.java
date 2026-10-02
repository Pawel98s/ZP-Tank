package pl.pawlo.zptank.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pawlo.zptank.domain.transport.TankerTruck;
import pl.pawlo.zptank.service.dao.TankerTruckDAO;

import java.util.List;

@Service
@AllArgsConstructor
public class TankerTruckService {

    private final TankerTruckDAO tankerTruckDAO;

    public TankerTruck save(TankerTruck tankerTruck) {
        return tankerTruckDAO.save(tankerTruck);
    }

    public TankerTruck findById(Long id) {
        return tankerTruckDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Tanker Truck not found"));
    }

    public List<TankerTruck> findAll() {
        return tankerTruckDAO.findAll();
    }


    @Transactional
    public TankerTruck update(Long id, TankerTruck tankerTruck) {
        TankerTruck existingTankerTruck = findById(id);

        TankerTruck updatedTankerTruck = TankerTruck.builder()
                .id(existingTankerTruck.getId())
                .registrationNumber(tankerTruck.getRegistrationNumber() != null
                        ? tankerTruck.getRegistrationNumber()
                        : existingTankerTruck.getRegistrationNumber())
                .brand(tankerTruck.getBrand() != null
                        ? tankerTruck.getBrand()
                        : existingTankerTruck.getBrand())
                .model(tankerTruck.getModel() != null
                        ? tankerTruck.getModel()
                        : existingTankerTruck.getModel())
                .capacity(tankerTruck.getCapacity() != null
                        ? tankerTruck.getCapacity()
                        : existingTankerTruck.getCapacity())
                .insuranceValidUntil(tankerTruck.getInsuranceValidUntil() != null
                        ? tankerTruck.getInsuranceValidUntil()
                        : existingTankerTruck.getInsuranceValidUntil())
                .periodicInspectionDate(tankerTruck.getPeriodicInspectionDate() != null
                        ? tankerTruck.getPeriodicInspectionDate()
                        : existingTankerTruck.getPeriodicInspectionDate())
                .intermediateInspectionDate(tankerTruck.getIntermediateInspectionDate() != null
                        ? tankerTruck.getIntermediateInspectionDate()
                        : existingTankerTruck.getIntermediateInspectionDate())
                .redStripeValidUntil(tankerTruck.getRedStripeValidUntil() != null
                        ? tankerTruck.getRedStripeValidUntil()
                        : existingTankerTruck.getRedStripeValidUntil())
                .onLegalizationValidUntil(tankerTruck.getOnLegalizationValidUntil() != null
                        ? tankerTruck.getOnLegalizationValidUntil()
                        : existingTankerTruck.getOnLegalizationValidUntil())
                .pbLegalizationValidUntil(tankerTruck.getPbLegalizationValidUntil() != null
                        ? tankerTruck.getPbLegalizationValidUntil()
                        : existingTankerTruck.getPbLegalizationValidUntil())
                .tachographReadingDate(tankerTruck.getTachographReadingDate() != null
                        ? tankerTruck.getTachographReadingDate()
                        : existingTankerTruck.getTachographReadingDate())
                .tachographCalibrationDate(tankerTruck.getTachographCalibrationDate() != null
                        ? tankerTruck.getTachographCalibrationDate()
                        : existingTankerTruck.getTachographCalibrationDate())
                .gps(tankerTruck.getGps() != null
                        ? tankerTruck.getGps()
                        : existingTankerTruck.getGps())
                .build();

        return tankerTruckDAO.update(updatedTankerTruck);
    }

    public void delete(Long id) {
        TankerTruck byId = findById(id);
        tankerTruckDAO.delete(byId.getId());
    }
}
