package pl.pawlo.zptank.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pawlo.zptank.domain.order.Waybill;
import pl.pawlo.zptank.service.dao.WaybillDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WaybillServiceTest {

    @Mock
    private WaybillDAO waybillDAO;

    @InjectMocks
    private WaybillService waybillService;


    @Test
    void shouldSaveWaybill() {
        Waybill waybill = Waybill.builder()
                .waybillNumber("1/10/2026")
                .issueDate(LocalDate.of(2026, 10, 5))
                .build();

        Mockito.when(waybillDAO.save(waybill)).thenReturn(waybill);


        Waybill result = waybillService.save(waybill);


        assertEquals(waybill, result);
        verify(waybillDAO).save(waybill);
    }


    @Test
    void shouldFindWaybillById() {
        Long id = 1L;

        Waybill waybill = Waybill.builder()
                .id(id)
                .waybillNumber("1/10/2026")
                .issueDate(LocalDate.of(2026, 10, 5))
                .build();

        when(waybillDAO.findById(id))
                .thenReturn(Optional.of(waybill));


        Waybill result = waybillService.findById(id);

        assertEquals(waybill, result);
        verify(waybillDAO).findById(id);
    }


    @Test
    void shouldThrowExceptionWhenWaybillDoesNotExist() {
        Long id = 1L;

        when(waybillDAO.findById(id))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> waybillService.findById(id)
        );

        assertEquals("Waybill set not found", exception.getMessage());

        verify(waybillDAO).findById(id);
    }


    @Test
    void shouldFindAllWaybills() {
        Waybill firstWaybill = Waybill.builder()
                .id(1L)
                .waybillNumber("1/10/2026")
                .issueDate(LocalDate.of(2026, 10, 5))
                .build();

        Waybill secondWaybill = Waybill.builder()
                .id(2L)
                .waybillNumber("2/10/2026")
                .issueDate(LocalDate.of(2026, 10, 5))
                .build();

        List<Waybill> waybills = List.of(
                firstWaybill,
                secondWaybill
        );

        when(waybillDAO.findAll()).thenReturn(waybills);

        List<Waybill> result = waybillService.findAll();

        assertEquals(2, result.size());
        assertEquals(waybills, result);

        verify(waybillDAO).findAll();
    }


    @Test
    void shouldUpdateWaybillWithNewValues() {
        Long id = 1L;

        Waybill existingWaybill = Waybill.builder()
                .id(id)
                .waybillNumber("1/10/2026")
                .issueDate(LocalDate.of(2026, 10, 5))
                .build();

        Waybill newData = Waybill.builder()
                .waybillNumber("10/10/2026")
                .issueDate(LocalDate.of(2026, 10, 6))
                .build();

        when(waybillDAO.findById(id))
                .thenReturn(Optional.of(existingWaybill));

        when(waybillDAO.update(any(Waybill.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Waybill result = waybillService.update(id, newData);

        assertEquals(id, result.getId());
        assertEquals("10/10/2026", result.getWaybillNumber());
        assertEquals(
                LocalDate.of(2026, 10, 6),
                result.getIssueDate()
        );

        verify(waybillDAO).findById(id);
        verify(waybillDAO).update(result);
    }


    @Test
    void shouldKeepExistingValuesWhenNewValuesAreNull() {
        Long id = 1L;

        Waybill existingWaybill = Waybill.builder()
                .id(id)
                .waybillNumber("1/10/2026")
                .issueDate(LocalDate.of(2026, 10, 5))
                .build();

        Waybill newData = Waybill.builder()
                .waybillNumber(null)
                .issueDate(null)
                .build();

        when(waybillDAO.findById(id))
                .thenReturn(Optional.of(existingWaybill));

        when(waybillDAO.update(any(Waybill.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Waybill result = waybillService.update(id, newData);

        assertEquals(id, result.getId());
        assertEquals("1/10/2026", result.getWaybillNumber());
        assertEquals(
                LocalDate.of(2026, 10, 5),
                result.getIssueDate()
        );

        verify(waybillDAO).findById(id);
        verify(waybillDAO).update(result);
    }


    @Test
    void shouldGenerateFirstWaybillOfTheMonth() {
        when(waybillDAO.findLastByIssueDateBetween(any(), any()))
                .thenReturn(Optional.empty());

        Waybill savedWaybill = Waybill.builder()
                .id(1L)
                .waybillNumber("1/" + LocalDate.now().getMonthValue()
                        + "/" + LocalDate.now().getYear())
                .issueDate(LocalDate.now())
                .build();

        when(waybillDAO.save(any(Waybill.class)))
                .thenReturn(savedWaybill);

        Waybill result = waybillService.generate();

        assertEquals(
                "1/" + LocalDate.now().getMonthValue()
                        + "/" + LocalDate.now().getYear(),
                result.getWaybillNumber()
        );

        assertEquals(LocalDate.now(), result.getIssueDate());

        verify(waybillDAO).findLastByIssueDateBetween(
                LocalDate.now().withDayOfMonth(1),
                LocalDate.now().withDayOfMonth(
                        LocalDate.now().lengthOfMonth()
                )
        );

        verify(waybillDAO).save(any(Waybill.class));
    }


    @Test
    void shouldGenerateNextWaybillNumber() {
        LocalDate today = LocalDate.now();

        Waybill lastWaybill = Waybill.builder()
                .id(10L)
                .waybillNumber(
                        "5/" +
                                today.getMonthValue() +
                                "/" +
                                today.getYear()
                )
                .issueDate(today)
                .build();

        when(waybillDAO.findLastByIssueDateBetween(any(), any()))
                .thenReturn(Optional.of(lastWaybill));

        when(waybillDAO.save(any(Waybill.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Waybill result = waybillService.generate();

        assertEquals(
                "6/" +
                        today.getMonthValue() +
                        "/" +
                        today.getYear(),
                result.getWaybillNumber()
        );

        assertEquals(today, result.getIssueDate());

        verify(waybillDAO).findLastByIssueDateBetween(
                today.withDayOfMonth(1),
                today.withDayOfMonth(today.lengthOfMonth())
        );

        verify(waybillDAO).save(any(Waybill.class));
    }
}
