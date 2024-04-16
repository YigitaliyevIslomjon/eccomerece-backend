package uz.shop.controller

import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import uz.shop.CategoryDto
import uz.shop.service.CategoryService


@RestController
@RequestMapping("/${BASE_PREFIX}category")
class CategoryController(
    private val categoryService: CategoryService
) {
    @PostMapping("add")
    fun add(@Valid @RequestBody dto: CategoryDto) = categoryService.add(dto)

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long) = categoryService.getOne(id)

    @GetMapping("/peagable")
    fun getAll(pageable: Pageable, search: String?) = categoryService.getAll(pageable,search)

    @PutMapping("/{id}")
    fun edit(@Valid @RequestBody dto: CategoryDto, @PathVariable id: Long) = categoryService.edit(id, dto)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = categoryService.delete(id)
}