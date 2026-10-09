package pl.pawlo.zptank.service.dao;

import pl.pawlo.zptank.domain.transport.LoadingTerminal;

import java.util.List;
import java.util.Optional;

public interface LoadingTerminalDAO {

    LoadingTerminal save(LoadingTerminal loadingTerminal);

    Optional<LoadingTerminal> findById(Long id);

    List<LoadingTerminal> findAll();

    LoadingTerminal update(LoadingTerminal loadingTerminal);

    void delete(Long id);
}
