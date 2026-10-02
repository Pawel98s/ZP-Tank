package pl.pawlo.zptank.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pawlo.zptank.domain.transport.Trailer;
import pl.pawlo.zptank.service.dao.TrailerDAO;

import java.util.List;

@Service
@AllArgsConstructor
public class TrailerService {

    private final TrailerDAO trailerDAO;

    public Trailer save(Trailer trailer) {
        return trailerDAO.save(trailer);
    }

    public Trailer findById(Long id) {
        return trailerDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Trailer not found"));
    }

    public List<Trailer> findAll() {
        return trailerDAO.findAll();
    }

    @Transactional
    public Trailer update(Long id, Trailer trailer) {
        Trailer existingTrailer = findById(id);

        Trailer updatedTrailer = Trailer.builder()
                .id(existingTrailer.getId())
                .registrationNumber(trailer.getRegistrationNumber() != null
                        ? trailer.getRegistrationNumber()
                        : existingTrailer.getRegistrationNumber())
                .capacity(trailer.getCapacity() != null
                        ? trailer.getCapacity()
                        : existingTrailer.getCapacity())
                .insuranceValidUntil(trailer.getInsuranceValidUntil() != null
                        ? trailer.getInsuranceValidUntil()
                        : existingTrailer.getInsuranceValidUntil())
                .periodicInspectionDate(trailer.getPeriodicInspectionDate() != null
                        ? trailer.getPeriodicInspectionDate()
                        : existingTrailer.getPeriodicInspectionDate())
                .intermediateInspectionDate(trailer.getIntermediateInspectionDate() != null
                        ? trailer.getIntermediateInspectionDate()
                        : existingTrailer.getIntermediateInspectionDate())
                .redStripeValidUntil(trailer.getRedStripeValidUntil() != null
                        ? trailer.getRedStripeValidUntil()
                        : existingTrailer.getRedStripeValidUntil())
                .onLegalizationValidUntil(trailer.getOnLegalizationValidUntil() != null
                        ? trailer.getOnLegalizationValidUntil()
                        : existingTrailer.getOnLegalizationValidUntil())
                .pbLegalizationValidUntil(trailer.getPbLegalizationValidUntil() != null
                        ? trailer.getPbLegalizationValidUntil()
                        : existingTrailer.getPbLegalizationValidUntil())
                .build();

        return trailerDAO.update(updatedTrailer);
    }

    public void delete(Long id) {
        Trailer trailer = findById(id);
        trailerDAO.delete(trailer.getId());
    }
}
