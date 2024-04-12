package uz.shop.controller

import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import uz.shop.OrderDto
import uz.shop.service.OrderService


@RestController
@RequestMapping("/${BASE_PREFIX}order")
class OrderController(
    private val orderService: OrderService
) {
    @PostMapping("add")
    fun add(@Valid @RequestBody dto: OrderDto) = orderService.add(dto)

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long) = orderService.getOne(id)

    @GetMapping("/pageable")
    fun getAll(pageable: Pageable) = orderService.getAll(pageable)

    @PutMapping("/{id}")
    fun edit(@Valid @RequestBody dto: OrderDto, @PathVariable id: Long) = orderService.edit(id, dto)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = orderService.delete(id)
}

