package pl.pawlo.zptank.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pawlo.zptank.domain.transport.LoadingTerminal;
import pl.pawlo.zptank.service.dao.LoadingTerminalDAO;

import java.util.List;

@Service
@AllArgsConstructor
public class LoadingTerminalService {

    private final LoadingTerminalDAO loadingTerminalDAO;

    public LoadingTerminal save(LoadingTerminal loadingTerminal) {
        return loadingTerminalDAO.save(loadingTerminal);
    }

    public LoadingTerminal findById(Long id) {
        return loadingTerminalDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Loading terminal not found"));
    }

    public List<LoadingTerminal> findAll() {
        return loadingTerminalDAO.findAll();
    }

    public LoadingTerminal update(Long id, LoadingTerminal loadingTerminal) {
        LoadingTerminal existingLoadingTerminal = findById(id);

        LoadingTerminal updatedLoadingTerminal = LoadingTerminal.builder()
                .id(existingLoadingTerminal.getId())
                .address(loadingTerminal.getAddress() != null
                        ? loadingTerminal.getAddress()
                        : existingLoadingTerminal.getAddress())
                .build();

        return loadingTerminalDAO.update(updatedLoadingTerminal);
    }

    public void delete(Long id) {
        LoadingTerminal loadingTerminal = findById(id);
        loadingTerminalDAO.delete(loadingTerminal.getId());
    }
}
