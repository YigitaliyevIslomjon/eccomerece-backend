package uz.shop.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import uz.shop.PaymentTypeDto
import uz.shop.service.PaymentTypeService


@RestController
@RequestMapping("/${BASE_PREFIX}payment-type")
class PaymentTypeController(
    private val paymentTypeService: PaymentTypeService
) {
    @PostMapping("add")
    fun add(@Valid @RequestBody dto: PaymentTypeDto) = paymentTypeService.add(dto)

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long) = paymentTypeService.getOne(id)

    @GetMapping("/all")
    fun getAll() = paymentTypeService.getAll()

    @PutMapping("/{id}")
    fun edit(@Valid @RequestBody dto: PaymentTypeDto, @PathVariable id: Long) = paymentTypeService.edit(id, dto)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = paymentTypeService.delete(id)
}

