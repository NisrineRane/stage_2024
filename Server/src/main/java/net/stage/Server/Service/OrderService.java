package net.stage.Server.Service;

import net.stage.Server.Dto.OrderDto;
import net.stage.Server.Entity.OrderEntity;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    OrderEntity addOrder(OrderDto orderDto);
    Optional<OrderDto> getOrder(Long id);
    List<OrderDto> getAllOrders();
    OrderEntity updateOrder(Long id, OrderDto orderDto);
    void deleteOrder(Long id);
}
