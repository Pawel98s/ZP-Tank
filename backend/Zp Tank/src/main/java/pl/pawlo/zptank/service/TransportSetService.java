package pl.pawlo.zptank.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pawlo.zptank.domain.transport.TankerTruck;
import pl.pawlo.zptank.domain.transport.Tractor;
import pl.pawlo.zptank.domain.transport.Trailer;
import pl.pawlo.zptank.domain.transport.TransportSet;
import pl.pawlo.zptank.service.dao.TransportSetDAO;

import java.util.List;

@Service
@AllArgsConstructor
public class TransportSetService {

    private final TransportSetDAO transportSetDAO;
    private final TrailerService trailerService;
    private final TractorService tractorService;
    private final TankerTruckService tankerTruckService;


    public TransportSet save(TransportSet transportSet) {
        validateTransportSet(transportSet);
        return transportSetDAO.save(transportSet);
    }

    public TransportSet findById(Long id) {
        return transportSetDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Transport set not found"));
    }

    public List<TransportSet> findAll() {
        return transportSetDAO.findAll();
    }

    @Transactional
    public TransportSet update(Long id, TransportSet transportSet) {
        TransportSet existingTransportSet = findById(id);

        TransportSet updatedTransportSet = TransportSet.builder()
                .id(existingTransportSet.getId())
                .tractor(transportSet.getTractor())
                .trailer(transportSet.getTrailer())
                .tankerTruck(transportSet.getTankerTruck())
                .active(transportSet.isActive())
                .build();

        validateTransportSet(updatedTransportSet);
        return transportSetDAO.update(updatedTransportSet);
    }

    public void delete(Long id) {
        TransportSet transportSet = findById(id);
        transportSetDAO.delete(transportSet.getId());
    }

    @Transactional
    public TransportSet create(TransportSet transportSet) {
        validateTransportSet(transportSet);

        Tractor tractor = null;
        Trailer trailer = null;
        TankerTruck tankerTruck = null;

        if (transportSet.getTractor() != null) {
            if (transportSet.getTractor().getId() != null) {
                tractor = tractorService.findById(
                        transportSet.getTractor().getId()
                );
            } else {
                tractor = tractorService.save(
                        transportSet.getTractor()
                );
            }
        }

        if (transportSet.getTrailer() != null) {
            if (transportSet.getTrailer().getId() != null) {
                trailer = trailerService.findById(
                        transportSet.getTrailer().getId()
                );
            } else {
                trailer = trailerService.save(
                        transportSet.getTrailer()
                );
            }
        }

        if (transportSet.getTankerTruck() != null) {
            if (transportSet.getTankerTruck().getId() != null) {
                tankerTruck = tankerTruckService.findById(
                        transportSet.getTankerTruck().getId()
                );
            } else {
                tankerTruck = tankerTruckService.save(
                        transportSet.getTankerTruck()
                );
            }
        }

        TransportSet newTransportSet = TransportSet.builder()
                .tractor(tractor)
                .trailer(trailer)
                .tankerTruck(tankerTruck)
                .active(transportSet.isActive())
                .build();

        return transportSetDAO.save(newTransportSet);
    }

    private void validateTransportSet(TransportSet transportSet) {

        boolean hasTankerTruck = transportSet.getTankerTruck() != null;
        boolean hasTractor = transportSet.getTractor() != null;
        boolean hasTrailer = transportSet.getTrailer() != null;

        boolean validTankerTruckSet =
                hasTankerTruck && !hasTractor && !hasTrailer;

        boolean validTractorTrailerSet =
                !hasTankerTruck && hasTractor && hasTrailer;

        if (!validTankerTruckSet && !validTractorTrailerSet) {
            throw new IllegalArgumentException(
                    "Transport set must contain either a tanker truck or a tractor with a trailer"
            );
        }
    }

}
