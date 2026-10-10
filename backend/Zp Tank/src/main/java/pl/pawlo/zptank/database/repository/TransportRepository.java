package pl.pawlo.zptank.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.transport.TransportEntity;
import pl.pawlo.zptank.database.repository.jpa.transport.TransportJpaRepository;
import pl.pawlo.zptank.database.repository.mapper.TransportEntityMapper;
import pl.pawlo.zptank.domain.transport.Transport;
import pl.pawlo.zptank.service.dao.TransportDAO;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TransportRepository implements TransportDAO {

    private final TransportJpaRepository transportJpaRepository;
    private final TransportEntityMapper transportEntityMapper;

    @Override
    public Transport save(Transport transport) {
        TransportEntity save = transportJpaRepository.save(transportEntityMapper.mapToEntity(transport));
        return transportEntityMapper.mapToDomain(save);
    }

    @Override
    public Optional<Transport> findById(Long id) {
        return transportJpaRepository.findById(id)
                .map(transportEntityMapper::mapToDomain);
    }

    @Override
    public List<Transport> findAll() {
       return transportJpaRepository.findAll()
                .stream()
                .map(transportEntityMapper::mapToDomain)
                .toList();
    }

    @Override
    public Transport update(Transport transport) {
        TransportEntity save = transportJpaRepository.save(transportEntityMapper.mapToEntity(transport));
        return transportEntityMapper.mapToDomain(save);
    }

    @Override
    public void delete(Long id) {
        transportJpaRepository.deleteById(id);
    }
}
