package net.stage.Server.Service.Impl;

import net.stage.Server.Dto.OrderDto;
import net.stage.Server.Entity.CustomerEntity;
import net.stage.Server.Entity.OrderEntity;
import net.stage.Server.Entity.ProductEntity;
import net.stage.Server.Repository.CustomerRepository;
import net.stage.Server.Repository.OrderRepository;
import net.stage.Server.Repository.ProductRepository;
import net.stage.Server.Service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrderEntity addOrder(OrderDto orderDto) {
        OrderEntity order = new OrderEntity();
        order.setOrderDate(orderDto.getOrderDate());
        order.setStatus(orderDto.getStatus());
        order.setQuantityProduct(orderDto.getQuantityProduct()); // Set quantityProduct

        CustomerEntity customer = customerRepository.findById(orderDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        order.setCustomer(customer);

        // Check stock quantity for each product
        Set<ProductEntity> products = orderDto.getProductIds().stream()
                .map(productId -> {
                    ProductEntity product = productRepository.findById(productId)
                            .orElseThrow(() -> new RuntimeException("Product not found"));

                    if (product.getStockQuantity() < orderDto.getQuantityProduct()) {
                        throw new RuntimeException("Insufficient stock for product ID: " + productId);
                    }

                    // Reduce stock quantity
                    product.setStockQuantity(product.getStockQuantity() - orderDto.getQuantityProduct());
                    productRepository.save(product);

                    return product;
                })
                .collect(Collectors.toSet());

        order.setProducts(products);

        return orderRepository.save(order);
    }

    @Override
    public Optional<OrderDto> getOrder(Long id) {
        return orderRepository.findById(id)
                .map(order -> modelMapper.map(order, OrderDto.class));
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderEntity updateOrder(Long id, OrderDto orderDto) {
        if (orderRepository.existsById(id)) {
            OrderEntity order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));

            order.setOrderDate(orderDto.getOrderDate());
            order.setStatus(orderDto.getStatus());
            order.setQuantityProduct(orderDto.getQuantityProduct()); // Update quantityProduct

            CustomerEntity customer = customerRepository.findById(orderDto.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            order.setCustomer(customer);

            // Update products and check stock quantity
            Set<ProductEntity> products = orderDto.getProductIds().stream()
                    .map(productId -> {
                        ProductEntity product = productRepository.findById(productId)
                                .orElseThrow(() -> new RuntimeException("Product not found"));

                        if (product.getStockQuantity() < orderDto.getQuantityProduct()) {
                            throw new RuntimeException("Insufficient stock for product ID: " + productId);
                        }

                        // Reduce stock quantity
                        product.setStockQuantity(product.getStockQuantity() - orderDto.getQuantityProduct());
                        productRepository.save(product);

                        return product;
                    })
                    .collect(Collectors.toSet());

            order.setProducts(products);

            return orderRepository.save(order);
        }
        return null;
    }

    @Override
    public void deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            // Optionally, you can handle stock quantity restoration or other cleanups here
            orderRepository.deleteById(id);
        } else {
            throw new RuntimeException("Order not found");
        }
    }
}
