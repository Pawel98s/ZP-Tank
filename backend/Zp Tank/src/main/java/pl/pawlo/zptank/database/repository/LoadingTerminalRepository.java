package pl.pawlo.zptank.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.pawlo.zptank.database.entity.transport.LoadingTerminalEntity;
import pl.pawlo.zptank.database.repository.jpa.transport.LoadingTerminalJpaRepository;
import pl.pawlo.zptank.database.repository.mapper.LoadingTerminalEntityMapper;
import pl.pawlo.zptank.domain.transport.LoadingTerminal;
import pl.pawlo.zptank.service.dao.LoadingTerminalDAO;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class LoadingTerminalRepository implements LoadingTerminalDAO {

    private final LoadingTerminalJpaRepository loadingTerminalJpaRepository;
    private final LoadingTerminalEntityMapper loadingTerminalEntityMapper;


    @Override
    public LoadingTerminal save(LoadingTerminal loadingTerminal) {
        LoadingTerminalEntity save = loadingTerminalJpaRepository.save(loadingTerminalEntityMapper.mapToEntity(loadingTerminal));
        return loadingTerminalEntityMapper.mapToDomain(save);
    }

    @Override
    public Optional<LoadingTerminal> findById(Long id) {
        return loadingTerminalJpaRepository.findById(id)
                .map(loadingTerminalEntityMapper::mapToDomain);
    }

    @Override
    public List<LoadingTerminal> findAll() {
        return loadingTerminalJpaRepository.findAll()
                .stream()
                .map(loadingTerminalEntityMapper::mapToDomain)
                .toList();
    }

    @Override
    public LoadingTerminal update(LoadingTerminal loadingTerminal) {
        LoadingTerminalEntity save = loadingTerminalJpaRepository.save(loadingTerminalEntityMapper.mapToEntity(loadingTerminal));
        return loadingTerminalEntityMapper.mapToDomain(save);
    }

    @Override
    public void delete(Long id) {
        loadingTerminalJpaRepository.deleteById(id);
    }
}
