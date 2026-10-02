package pl.pawlo.zptank.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.transport.TransportSetEntity;
import pl.pawlo.zptank.database.repository.jpa.transport.TransportSetJpaRepository;
import pl.pawlo.zptank.database.repository.mapper.TransportSetEntityMapper;
import pl.pawlo.zptank.domain.transport.TransportSet;
import pl.pawlo.zptank.service.dao.TransportSetDAO;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TransportSetRepository implements TransportSetDAO {

    private final TransportSetJpaRepository transportSetJpaRepository;
    private final TransportSetEntityMapper transportSetEntityMapper;

    @Override
    public TransportSet save(TransportSet transportSet) {
        TransportSetEntity save = transportSetJpaRepository.save(transportSetEntityMapper.mapToEntity(transportSet));
        return transportSetEntityMapper.mapToDomain(save);
    }

    @Override
    public Optional<TransportSet> findById(Long id) {
        return transportSetJpaRepository.findById(id)
                .map(transportSetEntityMapper::mapToDomain);
    }

    @Override
    public List<TransportSet> findAll() {
       return transportSetJpaRepository.findAll()
                .stream()
                .map(transportSetEntityMapper::mapToDomain)
                .toList();
    }

    @Override
    public TransportSet update(TransportSet transportSet) {
        TransportSetEntity save = transportSetJpaRepository.save(transportSetEntityMapper.mapToEntity(transportSet));
        return transportSetEntityMapper.mapToDomain(save);
    }

    @Override
    public void delete(Long id) {
        transportSetJpaRepository.deleteById(id);
    }
}

