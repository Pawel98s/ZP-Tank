package pl.pawlo.zptank.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.intermediary.IntermediaryEntity;
import pl.pawlo.zptank.database.repository.jpa.intermediary.IntermediaryJpaRepository;
import pl.pawlo.zptank.database.repository.mapper.IntermediaryEntityMapper;
import pl.pawlo.zptank.domain.intermediary.Intermediary;
import pl.pawlo.zptank.service.dao.IntermediaryDAO;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class IntermediaryRepository implements IntermediaryDAO {

    private final IntermediaryJpaRepository intermediaryJpaRepository;
    private final IntermediaryEntityMapper intermediaryEntityMapper;

    @Override
    public Intermediary save(Intermediary intermediary) {
        IntermediaryEntity save = intermediaryJpaRepository.save(intermediaryEntityMapper.mapToEntity(intermediary));
        return  intermediaryEntityMapper.mapToDomain(save);
    }

    @Override
    public Optional<Intermediary> findById(Long id) {
        return intermediaryJpaRepository.findById(id)
                .map(intermediaryEntityMapper::mapToDomain);
    }

    @Override
    public List<Intermediary> findAll() {
        return intermediaryJpaRepository.findAll()
                .stream()
                .map(intermediaryEntityMapper::mapToDomain)
                .toList();
    }

    @Override
    public Intermediary update(Intermediary intermediary) {
        IntermediaryEntity save = intermediaryJpaRepository.save(intermediaryEntityMapper.mapToEntity(intermediary));
        return  intermediaryEntityMapper.mapToDomain(save);
    }

    @Override
    public void delete(Long id) {
        intermediaryJpaRepository.deleteById(id);
    }
}
