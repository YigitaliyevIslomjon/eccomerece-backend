package uz.shop.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import uz.shop.DeliveryMethodDto
import uz.shop.service.DeliveryMethodService


@RestController
@RequestMapping("/${BASE_PREFIX}delivery-method")
class DeliveryMethodController(
    private val deliveryMethodService: DeliveryMethodService
) {
    @PostMapping("add")
    fun add(@Valid @RequestBody dto: DeliveryMethodDto) = deliveryMethodService.add(dto)

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long) = deliveryMethodService.getOne(id)

    @GetMapping("/all")
    fun getAll() = deliveryMethodService.getAll()

    @PutMapping("/{id}")
    fun edit(@Valid @RequestBody dto: DeliveryMethodDto, @PathVariable id: Long) = deliveryMethodService.edit(id, dto)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = deliveryMethodService.delete(id)
}

