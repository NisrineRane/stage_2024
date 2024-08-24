package net.stage.Server.Controller;

import net.stage.Server.Dto.OrderDto;
import net.stage.Server.Entity.OrderEntity;
import net.stage.Server.Response.ApiResponse;
import net.stage.Server.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderEntity>> createOrder(@RequestBody OrderDto orderDto) {
        OrderEntity createdOrder = orderService.addOrder(orderDto);
        ApiResponse<OrderEntity> response = ApiResponse.<OrderEntity>builder()
                .message("Order created successfully")
                .code(HttpStatus.CREATED.value())
                .data(createdOrder)
                .error(null)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrderById(@PathVariable Long id) {
        Optional<OrderDto> order = orderService.getOrder(id);
        ApiResponse<OrderDto> response;

        if (order.isPresent()) {
            response = ApiResponse.<OrderDto>builder()
                    .message("Order found")
                    .code(HttpStatus.OK.value())
                    .data(order.get())
                    .error(null)
                    .timestamp(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(response);
        } else {
            response = ApiResponse.<OrderDto>builder()
                    .message("Order not found")
                    .code(HttpStatus.NOT_FOUND.value())
                    .data(null)
                    .error("Order with ID " + id + " not found")
                    .timestamp(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderDto>>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();
        ApiResponse<List<OrderDto>> response = ApiResponse.<List<OrderDto>>builder()
                .message("Orders retrieved successfully")
                .code(HttpStatus.OK.value())
                .data(orders)
                .error(null)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderEntity>> updateOrder(
            @PathVariable Long id,
            @RequestBody OrderDto orderDto
    ) {
        OrderEntity updatedOrder = orderService.updateOrder(id, orderDto);
        if (updatedOrder != null) {
            ApiResponse<OrderEntity> response = ApiResponse.<OrderEntity>builder()
                    .message("Order updated successfully")
                    .code(HttpStatus.OK.value())
                    .data(updatedOrder)
                    .error(null)
                    .timestamp(LocalDateTime.now())
                    .build();
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<OrderEntity> response = ApiResponse.<OrderEntity>builder()
                    .message("Order not found")
                    .code(HttpStatus.NOT_FOUND.value())
                    .data(null)
                    .error("Order with ID " + id + " not found")
                    .timestamp(LocalDateTime.now())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Order deleted successfully")
                .code(HttpStatus.NO_CONTENT.value())
                .data(null)
                .error(null)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.noContent().build();
    }
}
