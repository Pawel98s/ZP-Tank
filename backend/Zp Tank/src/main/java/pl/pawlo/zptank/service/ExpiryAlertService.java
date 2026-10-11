package pl.pawlo.zptank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pawlo.zptank.api.dto.ExpiryAlertDTO;
import pl.pawlo.zptank.domain.transport.*;
import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ExpiryAlertService {
    private final DriverService driverService;
    private final TransportSetService transportSetService;

    public List<ExpiryAlertDTO> find(Integer window, Boolean expired, Boolean unavailable) {
        LocalDate today = LocalDate.now();
        long days = window == null ? 30 : Math.max(0, window);
        List<ExpiryAlertDTO> result = new ArrayList<>();
        driverService.findAll().forEach(d -> {
            String name = safe(d.getFirstName()) + " " + safe(d.getLastName());
            boolean inactive = !Boolean.TRUE.equals(d.getActive()) || d.getTransportSet() == null
                    || !d.getTransportSet().isActive();
            if (inactive) {
                result.add(new ExpiryAlertDTO("DRIVER", d.getId(), name, "availability",
                        null, 0, false, true));
            }
            add(result, "DRIVER", d.getId(), name, "driverCard", d.getDriverCardValidUntil(), today, days, inactive);
            add(result, "DRIVER", d.getId(), name, "adr", d.getAdrValidUntil(), today, days, inactive);
            add(result, "DRIVER", d.getId(), name, "identityDocument", d.getIdentityDocumentValidUntil(), today, days, inactive);
            add(result, "DRIVER", d.getId(), name, "psychologicalExamination", d.getPsychologicalExaminationValidUntil(), today, days, inactive);
            add(result, "DRIVER", d.getId(), name, "tdt", d.getTdtValidUntil(), today, days, inactive);
            add(result, "DRIVER", d.getId(), name, "drivingLicense", d.getDrivingLicenseValidUntil(), today, days, inactive);
            add(result, "DRIVER", d.getId(), name, "healthAndSafetyTraining", d.getHealthAndSafetyTrainingValidUntil(), today, days, inactive);
            add(result, "DRIVER", d.getId(), name, "medicalExamination", d.getMedicalExaminationValidUntil(), today, days, inactive);
        });
        transportSetService.findAll().forEach(s -> {
            boolean inactive = !s.isActive();
            if (inactive) {
                result.add(new ExpiryAlertDTO("TRANSPORT_SET", s.getId(), String.valueOf(s.getId()),
                        "availability", null, 0, false, true));
            }
            if (s.getTractor() != null) add(result, "TRACTOR", s.getTractor().getId(), s.getTractor().getRegistrationNumber(),
                    "insurance", s.getTractor().getInsuranceValidUntil(), today, days, inactive);
            if (s.getTractor() != null) {
                add(result, "TRACTOR", s.getTractor().getId(), s.getTractor().getRegistrationNumber(), "periodicInspection",
                        s.getTractor().getPeriodicInspectionDate(), today, days, inactive);
                add(result, "TRACTOR", s.getTractor().getId(), s.getTractor().getRegistrationNumber(), "redStripe",
                        s.getTractor().getRedStripeValidUntil(), today, days, inactive);
                add(result, "TRACTOR", s.getTractor().getId(), s.getTractor().getRegistrationNumber(), "tachographReading",
                        s.getTractor().getTachographReadingDate(), today, days, inactive);
                add(result, "TRACTOR", s.getTractor().getId(), s.getTractor().getRegistrationNumber(), "tachographCalibration",
                        s.getTractor().getTachographCalibrationDate(), today, days, inactive);
            }
            if (s.getTrailer() != null) add(result, "TRAILER", s.getTrailer().getId(), s.getTrailer().getRegistrationNumber(),
                    "insurance", s.getTrailer().getInsuranceValidUntil(), today, days, inactive);
            if (s.getTrailer() != null) {
                add(result, "TRAILER", s.getTrailer().getId(), s.getTrailer().getRegistrationNumber(), "periodicInspection", s.getTrailer().getPeriodicInspectionDate(), today, days, inactive);
                add(result, "TRAILER", s.getTrailer().getId(), s.getTrailer().getRegistrationNumber(), "intermediateInspection", s.getTrailer().getIntermediateInspectionDate(), today, days, inactive);
                add(result, "TRAILER", s.getTrailer().getId(), s.getTrailer().getRegistrationNumber(), "redStripe", s.getTrailer().getRedStripeValidUntil(), today, days, inactive);
                add(result, "TRAILER", s.getTrailer().getId(), s.getTrailer().getRegistrationNumber(), "onLegalization", s.getTrailer().getOnLegalizationValidUntil(), today, days, inactive);
                add(result, "TRAILER", s.getTrailer().getId(), s.getTrailer().getRegistrationNumber(), "pbLegalization", s.getTrailer().getPbLegalizationValidUntil(), today, days, inactive);
            }
            if (s.getTankerTruck() != null) add(result, "TANKER_TRUCK", s.getTankerTruck().getId(), s.getTankerTruck().getRegistrationNumber(),
                    "insurance", s.getTankerTruck().getInsuranceValidUntil(), today, days, inactive);
            if (s.getTankerTruck() != null) {
                var v = s.getTankerTruck();
                add(result, "TANKER_TRUCK", v.getId(), v.getRegistrationNumber(), "periodicInspection", v.getPeriodicInspectionDate(), today, days, inactive);
                add(result, "TANKER_TRUCK", v.getId(), v.getRegistrationNumber(), "intermediateInspection", v.getIntermediateInspectionDate(), today, days, inactive);
                add(result, "TANKER_TRUCK", v.getId(), v.getRegistrationNumber(), "redStripe", v.getRedStripeValidUntil(), today, days, inactive);
                add(result, "TANKER_TRUCK", v.getId(), v.getRegistrationNumber(), "onLegalization", v.getOnLegalizationValidUntil(), today, days, inactive);
                add(result, "TANKER_TRUCK", v.getId(), v.getRegistrationNumber(), "pbLegalization", v.getPbLegalizationValidUntil(), today, days, inactive);
                add(result, "TANKER_TRUCK", v.getId(), v.getRegistrationNumber(), "tachographReading", v.getTachographReadingDate(), today, days, inactive);
                add(result, "TANKER_TRUCK", v.getId(), v.getRegistrationNumber(), "tachographCalibration", v.getTachographCalibrationDate(), today, days, inactive);
            }
        });
        return result.stream().filter(a -> expired == null || a.expired() == expired)
                .filter(a -> unavailable == null || a.unavailable() == unavailable).toList();
    }
    private void add(List<ExpiryAlertDTO> out, String type, Long id, String name, String doc, LocalDate date, LocalDate today, long window, boolean inactive) {
        if (date == null) {
            out.add(new ExpiryAlertDTO(type, id, name, doc, null, 0, false, true));
            return;
        }
        long remaining = Duration.between(today.atStartOfDay(), date.atStartOfDay()).toDays();
        if (remaining <= window) out.add(new ExpiryAlertDTO(type, id, name, doc, date, remaining, remaining < 0,
                inactive || remaining < 0));
    }
    private String safe(String value) { return value == null ? "" : value; }
}
