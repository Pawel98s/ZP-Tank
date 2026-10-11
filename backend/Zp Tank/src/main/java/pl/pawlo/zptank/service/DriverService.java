package pl.pawlo.zptank.service;

import lombok.AllArgsConstructor;
import pl.pawlo.zptank.api.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import pl.pawlo.zptank.domain.transport.Driver;
import pl.pawlo.zptank.domain.transport.TransportSet;
import pl.pawlo.zptank.service.dao.DriverDAO;

import java.util.List;

@Service
@AllArgsConstructor
public class DriverService {

    private final DriverDAO driverDAO;

    public Driver save(Driver driver) {
        validateTransportSetAvailability(driver.getTransportSet(), null);
        return driverDAO.save(driver);
    }

    public Driver findById(Long id) {
        return driverDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));
    }

    public List<Driver> findAll() {
        return driverDAO.findAll();
    }

    public List<Driver> findByLastName(String lastName) {
        return driverDAO.findByLastName(lastName);
    }

    public Driver update(Long id, Driver driver) {
        Driver existingDriver = findById(id);
        TransportSet transportSet = driver.getTransportSet() != null
                ? driver.getTransportSet()
                : existingDriver.getTransportSet();
        validateTransportSetAvailability(transportSet, id);

        Driver updatedDriver = Driver.builder()
                .id(existingDriver.getId())
                .firstName(driver.getFirstName() != null
                        ? driver.getFirstName()
                        : existingDriver.getFirstName())
                .lastName(driver.getLastName() != null
                        ? driver.getLastName()
                        : existingDriver.getLastName())
                .phone(driver.getPhone() != null
                        ? driver.getPhone()
                        : existingDriver.getPhone())
                .login(driver.getLogin() != null
                        ? driver.getLogin()
                        : existingDriver.getLogin())
                .password(driver.getPassword() != null
                        ? driver.getPassword()
                        : existingDriver.getPassword())
                .active(driver.getActive() != null
                        ? driver.getActive()
                        : existingDriver.getActive())
                .transportSet(transportSet)
                .driverCardValidUntil(driver.getDriverCardValidUntil() != null
                        ? driver.getDriverCardValidUntil()
                        : existingDriver.getDriverCardValidUntil())
                .adrValidUntil(driver.getAdrValidUntil() != null
                        ? driver.getAdrValidUntil()
                        : existingDriver.getAdrValidUntil())
                .identityDocumentValidUntil(driver.getIdentityDocumentValidUntil() != null
                        ? driver.getIdentityDocumentValidUntil()
                        : existingDriver.getIdentityDocumentValidUntil())
                .psychologicalExaminationValidUntil(driver.getPsychologicalExaminationValidUntil() != null
                        ? driver.getPsychologicalExaminationValidUntil()
                        : existingDriver.getPsychologicalExaminationValidUntil())
                .tdtValidUntil(driver.getTdtValidUntil() != null
                        ? driver.getTdtValidUntil()
                        : existingDriver.getTdtValidUntil())
                .drivingLicenseValidUntil(driver.getDrivingLicenseValidUntil() != null
                        ? driver.getDrivingLicenseValidUntil()
                        : existingDriver.getDrivingLicenseValidUntil())
                .healthAndSafetyTrainingValidUntil(driver.getHealthAndSafetyTrainingValidUntil() != null
                        ? driver.getHealthAndSafetyTrainingValidUntil()
                        : existingDriver.getHealthAndSafetyTrainingValidUntil())
                .medicalExaminationValidUntil(driver.getMedicalExaminationValidUntil() != null
                        ? driver.getMedicalExaminationValidUntil()
                        : existingDriver.getMedicalExaminationValidUntil())
                .build();

        return driverDAO.update(updatedDriver);
    }

    private void validateTransportSetAvailability(TransportSet transportSet, Long driverId) {
        if (transportSet == null || transportSet.getId() == null) {
            return;
        }

        boolean assignedToAnotherDriver = driverId == null
                ? driverDAO.existsByTransportSetId(transportSet.getId())
                : driverDAO.existsByTransportSetIdAndIdNot(transportSet.getId(), driverId);

        if (assignedToAnotherDriver) {
            throw new IllegalStateException(
                    "Transport set " + transportSet.getId() + " is already assigned to another driver"
            );
        }
    }

    public void delete(Long id) {
        Driver driver = findById(id);
        driverDAO.delete(driver.getId());
    }

}