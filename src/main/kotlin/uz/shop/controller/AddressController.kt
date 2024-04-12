package uz.shop.controller

import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import uz.shop.AddressDto
import uz.shop.service.AddressService


@RestController
@RequestMapping("/${BASE_PREFIX}address")
class AddressController(
    private val addressService: AddressService
) {
    @PostMapping("add")
    fun add(@Valid @RequestBody dto: AddressDto) = addressService.add(dto)

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long) = addressService.getOne(id)

    @GetMapping("/pageable")
    fun getAll(pageable: Pageable) = addressService.getAll(pageable)

    @PutMapping("/{id}")
    fun edit(@Valid @RequestBody dto: AddressDto, @PathVariable id: Long) = addressService.edit(id, dto)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = addressService.delete(id)
}

