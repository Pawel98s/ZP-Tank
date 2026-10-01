package pl.pawlo.zptank.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.transport.TrailerEntity;
import pl.pawlo.zptank.database.repository.jpa.transport.TrailerJpaRepository;
import pl.pawlo.zptank.database.repository.mapper.TrailerEntityMapper;
import pl.pawlo.zptank.domain.transport.Trailer;
import pl.pawlo.zptank.service.dao.TrailerDAO;

import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
public class TrailerRepository implements TrailerDAO {

    private final TrailerJpaRepository trailerJpaRepository;
    private final TrailerEntityMapper trailerEntityMapper;

    @Override
    public Trailer save(Trailer trailer) {
        TrailerEntity save = trailerJpaRepository.save(trailerEntityMapper.mapToEntity(trailer));
        return trailerEntityMapper.mapToDomain(save);
    }

    @Override
    public Optional<Trailer> findById(Long id) {
        return trailerJpaRepository.findById(id)
                .map(trailerEntityMapper::mapToDomain);
    }

    @Override
    public List<Trailer> findAll() {
        return trailerJpaRepository.findAll()
                .stream()
                .map(trailerEntityMapper::mapToDomain)
                .toList();
    }

    @Override
    public Trailer update(Trailer trailer) {
        TrailerEntity save = trailerJpaRepository.save(trailerEntityMapper.mapToEntity(trailer));
        return trailerEntityMapper.mapToDomain(save);
    }

    @Override
    public void delete(Long id) {
        trailerJpaRepository.deleteById(id);
    }
}


