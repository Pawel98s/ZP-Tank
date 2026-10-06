package pl.pawlo.zptank.api;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.pawlo.zptank.api.controller.OrderController;
import pl.pawlo.zptank.api.dto.order.OrderDTO;
import pl.pawlo.zptank.api.mapper.OrderMapper;
import pl.pawlo.zptank.domain.OrderStatus;
import pl.pawlo.zptank.domain.order.Order;
import pl.pawlo.zptank.service.OrderService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private OrderMapper orderMapper;

    @Test
    void shouldCreateOrder() throws Exception {

        Order order = Order.builder()
                .id(1L)
                .status(OrderStatus.NEW)
                .build();

        OrderDTO orderDTO = OrderDTO.builder()
                .id(1L)
                .status(OrderStatus.NEW)
                .build();

        when(orderService.create(1L, 2L, 3L))
                .thenReturn(order);

        when(orderMapper.mapToDTO(order))
                .thenReturn(orderDTO);


        mockMvc.perform(
                        post("/api/orders")
                                .with(user("testUser"))
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "orderRequestId": 1,
                                            "intermediaryId": 2,
                                            "deliveryAddressId": 3
                                        }
                                        """)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("NEW"));

        verify(orderService)
                .create(1L, 2L, 3L);

        verify(orderMapper)
                .mapToDTO(order);
    }

    @Test
    void shouldFindById() throws Exception{
        Order order = Order.builder()
                .id(1L)
                .status(OrderStatus.NEW)
                .build();

        OrderDTO orderDTO = OrderDTO.builder()
                .id(1L)
                .status(OrderStatus.NEW)
                .build();

        Mockito.when(orderService.findById(1L)).thenReturn(order);
        Mockito.when(orderMapper.mapToDTO(order)).thenReturn(orderDTO);


        mockMvc.perform(
                get("/api/orders/1")
                        .with(user("testUser"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("NEW"));

        Mockito.verify(orderService).findById(1L);
        Mockito.verify(orderMapper).mapToDTO(order);
    }
}
