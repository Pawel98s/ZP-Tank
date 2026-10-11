package pl.pawlo.zptank.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pawlo.zptank.domain.transport.Driver;
import pl.pawlo.zptank.domain.transport.TransportSet;
import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpiryAlertServiceTest {
    @Mock DriverService driverService;
    @Mock TransportSetService transportSetService;
    @InjectMocks ExpiryAlertService service;

    @Test
    void returnsExpiringAndExpiredDocuments() {
        when(driverService.findAll()).thenReturn(List.of(Driver.builder().id(1L).firstName("A").lastName("B")
                .active(true).driverCardValidUntil(LocalDate.now().plusDays(7))
                .adrValidUntil(LocalDate.now().minusDays(1)).build()));
        when(transportSetService.findAll()).thenReturn(List.of());

        var alerts = service.find(30, null, null);

        assertThat(alerts).extracting(a -> a.document()).contains("driverCard", "adr");
        assertThat(alerts.stream().filter(a -> a.expired()).count()).isEqualTo(1);
    }
}
