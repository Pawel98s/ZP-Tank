package pl.pawlo.zptank.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pawlo.zptank.domain.transport.Driver;
import pl.pawlo.zptank.service.dao.DriverDAO;

import java.util.List;

@Service
@AllArgsConstructor
public class DriverService {

    private final DriverDAO driverDAO;

    public Driver save(Driver driver) {
        return driverDAO.save(driver);
    }

    public Driver findById(Long id) {
        return driverDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
    }

    public List<Driver> findAll() {
        return driverDAO.findAll();
    }

    public List<Driver> findByLastName(String lastName) {
        return driverDAO.findByLastName(lastName);
    }

    public Driver update(Long id, Driver driver) {
        Driver existingDriver = findById(id);

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
                .transportSet(driver.getTransportSet() != null
                        ? driver.getTransportSet()
                        : existingDriver.getTransportSet())
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

    public void delete(Long id) {
        Driver driver = findById(id);
        driverDAO.delete(driver.getId());
    }

}