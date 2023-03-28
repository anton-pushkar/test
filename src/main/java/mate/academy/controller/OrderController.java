package mate.academy.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import mate.academy.dto.mapper.GoodsMapper;
import mate.academy.dto.mapper.OrderMapper;
import mate.academy.dto.request.GoodsRequestDto;
import mate.academy.dto.request.OrderRequestDto;
import mate.academy.dto.request.OrderRequestDtoForCreate;
import mate.academy.dto.response.OrderResponseDto;
import mate.academy.model.Goods;
import mate.academy.model.Order;
import mate.academy.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderMapper orderMapper;
    private final OrderService service;
    private final GoodsMapper goodsMapper;

    public OrderController(OrderMapper mapper, OrderService service, GoodsMapper goodsMapper) {
        this.orderMapper = mapper;
        this.service = service;
        this.goodsMapper = goodsMapper;
    }

    @PostMapping
    @ApiOperation(value = "create order")
    public OrderResponseDto create(@RequestBody OrderRequestDtoForCreate dto) {
        Order order = orderMapper.toModelForCreate(dto);
        service.create(order);
        return orderMapper.toResponseDto(order);
    }

    @PostMapping("/add-goods/{id}")
    @ApiOperation(value = "add goods to order by id ")
    public OrderResponseDto addGoodsToOrder(@PathVariable Long id,
                                            @ApiParam(value = "goods request dto")
                                            @RequestBody GoodsRequestDto dto) {
        Goods goods = goodsMapper.toModel(dto);
        Order order = service.addGoodsToOrder(id, goods);
        return orderMapper.toResponseDto(order);
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "update order by id")
    public OrderResponseDto update(@PathVariable Long id,
                                   @RequestBody OrderRequestDto dto) {
        Order order = orderMapper.toModel(dto);
        order.setId(id);
        service.update(order);
        return orderMapper.toResponseDto(order);
    }

    @PutMapping("/change-status/{id}")
    @ApiOperation(value = "change order status by id")
    public OrderResponseDto changeStatus(@PathVariable Long id,
                                         String status) {
        Order order = service.changeStatusById(id, status);
        return orderMapper.toResponseDto(order);
    }

    @GetMapping("/order-cost/{id}")
    @ApiOperation(value = "get order cost by id")
    public BigDecimal getOrderCost(@PathVariable Long id) {
        return service.getOrderCostById(id);
    }
}
