package pl.pawlo.zptank.api;

import org.junit.jupiter.api.Test;
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

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Test
    void shouldFindAllOrders() throws Exception{
        Order order1 = Order.builder()
                .id(1L)
                .status(OrderStatus.NEW)
                .build();

        Order order2 = Order.builder()
                .id(2L)
                .status(OrderStatus.NEW)
                .build();

        OrderDTO orderDTO1 = OrderDTO.builder()
                .id(1L)
                .status(OrderStatus.NEW)
                .build();

        OrderDTO orderDTO2 = OrderDTO.builder()
                .id(2L)
                .status(OrderStatus.NEW)
                .build();

        Mockito.when(orderService.findAll()).thenReturn(List.of(order1, order2));
        Mockito.when(orderMapper.mapToDTO(order1)).thenReturn(orderDTO1);
        Mockito.when(orderMapper.mapToDTO(order2)).thenReturn(orderDTO2);

        mockMvc.perform(
                        get("/api/orders")
                                .with(user("testUser"))
                                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].status").value("NEW"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].status").value("NEW"));

        Mockito.verify(orderService).findAll();
        Mockito.verify(orderMapper).mapToDTO(order1);
        Mockito.verify(orderMapper).mapToDTO(order2);



    }

    @Test
    void shouldUpdateOrder() throws Exception {

        OrderDTO orderDTO = OrderDTO.builder()
                .deliveryDate(LocalDate.of(2012, 3, 14))
                .build();

        Order orderUpdate = Order.builder()
                .id(1L)
                .status(OrderStatus.NEW)
                .deliveryDate(LocalDate.of(2012, 3, 14))
                .build();

        OrderDTO updatedOrderDTO = OrderDTO.builder()
                .id(1L)
                .status(OrderStatus.NEW)
                .deliveryDate(LocalDate.of(2012, 3, 14))
                .build();

        Mockito.when(orderMapper.mapToDomain(orderDTO)).thenReturn(orderUpdate);

        Mockito.when(orderService.update(1L, orderUpdate)).thenReturn(orderUpdate);

        Mockito.when(orderMapper.mapToDTO(orderUpdate)).thenReturn(updatedOrderDTO);

        mockMvc.perform(
                        patch("/api/orders/1")
                                .with(user("testUser"))
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                                "deliveryDate": "2012-03-14"
                            }
                            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("NEW"))
                .andExpect(jsonPath("$.deliveryDate").value("2012-03-14"));

        Mockito.verify(orderMapper).mapToDomain(orderDTO);

        Mockito.verify(orderService).update(1L, orderUpdate);

        Mockito.verify(orderMapper).mapToDTO(orderUpdate);
    }


    @Test
    void shouldUpdateOrderStatus() throws Exception {

        Order order = Order.builder()
                .id(1L)
                .status(OrderStatus.NEW)
                .build();

        OrderDTO orderDTO = OrderDTO.builder()
                .id(1L)
                .status(OrderStatus.NEW)
                .build();

        Mockito.when(orderService.updateStatus(1L, OrderStatus.NEW)).thenReturn(order);

        Mockito.when(orderMapper.mapToDTO(order)).thenReturn(orderDTO);

        mockMvc.perform(
                        patch("/api/orders/1/status")
                                .with(user("testUser"))
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                    {
                                        "orderStatus": "NEW"
                                    }
                                    """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("NEW"));

        Mockito.verify(orderService).updateStatus(1L, OrderStatus.NEW);

        Mockito.verify(orderMapper).mapToDTO(order);
    }

    @Test
    void shouldDeleteOrder() throws Exception {
        mockMvc.perform(
                        delete("/api/orders/1")
                                .with(user("testUser"))
                                .with(csrf()))
                .andExpect(status().isOk());

        Mockito.verify(orderService).deleteById(1L);
    }
}
