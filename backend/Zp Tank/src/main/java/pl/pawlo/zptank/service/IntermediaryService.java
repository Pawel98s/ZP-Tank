package pl.pawlo.zptank.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pawlo.zptank.domain.intermediary.Intermediary;
import pl.pawlo.zptank.service.dao.IntermediaryDAO;

import java.util.List;

@Service
@AllArgsConstructor
public class IntermediaryService {

    private final IntermediaryDAO intermediaryDAO;

    public Intermediary save(Intermediary intermediary) {
        return intermediaryDAO.save(intermediary);
    }

    public Intermediary findById(Long id) {
        return intermediaryDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Intermediary not found"));
    }

    public List<Intermediary> findAll() {
        return  intermediaryDAO.findAll();
    }

    public void  delete(Long id) {
        Intermediary intermediary = findById(id);
        intermediaryDAO.delete(intermediary.getId());
    }

    public Intermediary update(Long id, Intermediary intermediary) {
        Intermediary existingIntermediary = findById(id);

        Intermediary updatedIntermediary = Intermediary.builder()
                .id(existingIntermediary.getId())
                .name(intermediary.getName() != null
                        ? intermediary.getName()
                        : existingIntermediary.getName())
                .discountPrice(intermediary.getDiscountPrice() != null
                        ? intermediary.getDiscountPrice()
                        : existingIntermediary.getDiscountPrice())
                .build();

        intermediaryDAO.update(updatedIntermediary);
        return updatedIntermediary;
    }
}
