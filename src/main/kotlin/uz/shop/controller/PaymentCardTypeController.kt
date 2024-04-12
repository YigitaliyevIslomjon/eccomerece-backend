package uz.shop.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import uz.shop.PaymentCardTypeDto
import uz.shop.service.PaymentCardTypeService


@RestController
@RequestMapping("/${BASE_PREFIX}payment-card-type")
class PaymentCardTypeController(
    private val paymentCardTypeService: PaymentCardTypeService
) {
    @PostMapping("add")
    fun add(@Valid @RequestBody dto: PaymentCardTypeDto) = paymentCardTypeService.add(dto)

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long) = paymentCardTypeService.getOne(id)

    @GetMapping("/all")
    fun getAll() = paymentCardTypeService.getAll()

    @PutMapping("/{id}")
    fun edit(@Valid @RequestBody dto: PaymentCardTypeDto, @PathVariable id: Long) = paymentCardTypeService.edit(id, dto)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = paymentCardTypeService.delete(id)
}

