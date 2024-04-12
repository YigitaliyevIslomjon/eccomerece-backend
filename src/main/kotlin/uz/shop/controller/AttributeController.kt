package uz.shop.controller

import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import uz.shop.AttributeDto
import uz.shop.service.AttributeService


@RestController
@RequestMapping("/${BASE_PREFIX}attribute")
class AttributeController(
    private val attributeService: AttributeService
) {
    @PostMapping("add")
    fun add(@Valid @RequestBody dto: AttributeDto) = attributeService.add(dto)

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long) = attributeService.getOne(id)

    @GetMapping("/all")
    fun getAll() = attributeService.getAll()

    @PutMapping("/{id}")
    fun edit(@Valid @RequestBody dto: AttributeDto, @PathVariable id: Long) = attributeService.edit(id, dto)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = attributeService.delete(id)
}