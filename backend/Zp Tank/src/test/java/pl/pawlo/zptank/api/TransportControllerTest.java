package pl.pawlo.zptank.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.pawlo.zptank.api.controller.TransportController;
import pl.pawlo.zptank.api.dto.transport.CreateTransportRequestDTO;
import pl.pawlo.zptank.api.dto.transport.UpdateTransportRequestDTO;
import pl.pawlo.zptank.api.mapper.TransportMapper;
import pl.pawlo.zptank.domain.TransportStatus;
import pl.pawlo.zptank.domain.transport.CreateTransportRequest;
import pl.pawlo.zptank.domain.transport.Transport;
import pl.pawlo.zptank.domain.transport.UpdateTransportRequest;
import pl.pawlo.zptank.service.TransportService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransportController.class)
@AutoConfigureMockMvc(addFilters = false)
class TransportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransportService transportService;

    @MockitoBean
    private TransportMapper transportMapper;

    @Test
    void shouldCreateTransport() throws Exception {
        CreateTransportRequest request = mock(CreateTransportRequest.class);
        Transport transport = mock(Transport.class);

        when(transportMapper.mapToRequest(any(CreateTransportRequestDTO.class)))
                .thenReturn(request);

        when(transportService.createTransport(request))
                .thenReturn(transport);

        mockMvc.perform(post("/api/transports")
                        .contentType("application/json")
                        .content("""
                                {
                                    "driverId": 1,
                                    "loadingTerminalId": 2,
                                    "orderIds": [3, 4],
                                    "loadingDate": "2026-10-12",
                                    "unloadingDate": "2026-10-13"
                                }
                                """))
                .andExpect(status().isCreated());

        verify(transportMapper).mapToRequest(
                any(CreateTransportRequestDTO.class)
        );
        verify(transportService).createTransport(request);
        verify(transportMapper).mapToDTO(transport);
    }

    @Test
    void shouldFindTransportById() throws Exception {
        Transport transport = mock(Transport.class);

        when(transportService.findById(1L)).thenReturn(transport);

        mockMvc.perform(get("/api/transports/{id}", 1L))
                .andExpect(status().isOk());

        verify(transportService).findById(1L);
        verify(transportMapper).mapToDTO(transport);
    }

    @Test
    void shouldFindAllTransports() throws Exception {
        Transport transport1 = mock(Transport.class);
        Transport transport2 = mock(Transport.class);

        when(transportService.findAll())
                .thenReturn(List.of(transport1, transport2));

        mockMvc.perform(get("/api/transports"))
                .andExpect(status().isOk());

        verify(transportService).findAll();
        verify(transportMapper).mapToDTO(transport1);
        verify(transportMapper).mapToDTO(transport2);
        verify(transportService, never()).findByStatus(any());
    }

    @Test
    void shouldFindTransportsByStatus() throws Exception {
        Transport transport = mock(Transport.class);

        when(transportService.findByStatus(TransportStatus.PLANNED))
                .thenReturn(List.of(transport));

        mockMvc.perform(get("/api/transports")
                        .param("status", "PLANNED"))
                .andExpect(status().isOk());

        verify(transportService).findByStatus(TransportStatus.PLANNED);
        verify(transportMapper).mapToDTO(transport);
        verify(transportService, never()).findAll();
    }

    @Test
    void shouldUpdateTransport() throws Exception {
        UpdateTransportRequest request = mock(UpdateTransportRequest.class);
        Transport transport = mock(Transport.class);

        when(transportMapper.mapToUpdateRequest(
                any(UpdateTransportRequestDTO.class)))
                .thenReturn(request);

        when(transportService.updateTransport(1L, request))
                .thenReturn(transport);

        mockMvc.perform(patch("/api/transports/{id}", 1L)
                        .contentType("application/json")
                        .content("""
                                {
                                    "driverId": 2,
                                    "loadingTerminalId": 3,
                                    "orderIds": [4, 5],
                                    "loadingDate": "2026-10-14",
                                    "unloadingDate": "2026-10-15"
                                }
                                """))
                .andExpect(status().isOk());

        verify(transportMapper).mapToUpdateRequest(
                any(UpdateTransportRequestDTO.class)
        );
        verify(transportService).updateTransport(1L, request);
        verify(transportMapper).mapToDTO(transport);
    }


    @Test
    void shouldDeleteTransport() throws Exception {
        doNothing().when(transportService).deleteTransport(1L);

        mockMvc.perform(delete("/api/transports/{id}", 1L))
                .andExpect(status().isOk());

        verify(transportService).deleteTransport(1L);
    }


    @Test
    void shouldUpdateTransportStatus() throws Exception {
        Transport transport = mock(Transport.class);

        when(transportService.updateStatus(
                1L, TransportStatus.PLANNED))
                .thenReturn(transport);

        mockMvc.perform(patch("/api/transports/{id}/status", 1L)
                        .contentType("application/json")
                        .content("""
                                {
                                    "status": "PLANNED"
                                }
                                """))
                .andExpect(status().isOk());

        verify(transportService).updateStatus(
                1L, TransportStatus.PLANNED
        );
        verify(transportMapper).mapToDTO(transport);
    }

    @Test
    void shouldFindTransportsByDriverId() throws Exception {
        Transport transport1 = mock(Transport.class);
        Transport transport2 = mock(Transport.class);

        when(transportService.findByDriverId(5L))
                .thenReturn(List.of(transport1, transport2));

        mockMvc.perform(get("/api/transports/driver/{driverId}", 5L))
                .andExpect(status().isOk());

        verify(transportService).findByDriverId(5L);
        verify(transportMapper).mapToDTO(transport1);
        verify(transportMapper).mapToDTO(transport2);
    }
}