package pl.pawlo.zptank.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.transport.TractorEntity;
import pl.pawlo.zptank.database.repository.jpa.transport.TractorJpaRepository;
import pl.pawlo.zptank.database.repository.mapper.TractorEntityMapper;
import pl.pawlo.zptank.domain.transport.Tractor;
import pl.pawlo.zptank.service.dao.TractorDAO;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TractorRepository implements TractorDAO {

    private final TractorJpaRepository tractorJpaRepository;
    private final TractorEntityMapper tractorEntityMapper;


    @Override
    public Tractor save(Tractor tractor) {
        TractorEntity save = tractorJpaRepository.save(tractorEntityMapper.mapToEntity(tractor));
        return tractorEntityMapper.mapToDomain(save);
    }

    @Override
    public Optional<Tractor> findById(Long id) {
        return tractorJpaRepository.findById(id)
                .map(tractorEntityMapper::mapToDomain);
    }

    @Override
    public List<Tractor> findAll() {
        return tractorJpaRepository.findAll()
                .stream()
                .map(tractorEntityMapper::mapToDomain)
                .toList();
    }

    @Override
    public Tractor update(Tractor tractor) {
        TractorEntity save = tractorJpaRepository.save(tractorEntityMapper.mapToEntity(tractor));
        return tractorEntityMapper.mapToDomain(save);
    }

    @Override
    public void delete(Long id) {
        tractorJpaRepository.deleteById(id);
    }
}



