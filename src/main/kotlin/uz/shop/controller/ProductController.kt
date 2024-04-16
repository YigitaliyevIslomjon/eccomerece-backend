package uz.shop.controller

import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import uz.shop.ProductDto
import uz.shop.service.ProductService


@RestController
@RequestMapping("/${BASE_PREFIX}product")
class ProductController(
    private val productService: ProductService
) {
    @PostMapping("add")
    fun add(@Valid @RequestBody dto: ProductDto) = productService.add(dto)

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long) = productService.getOne(id)

    @GetMapping("/pageable")
    fun getAll(pageable: Pageable, @RequestParam statusId: Long?, @RequestParam  categoryId: Long?, @RequestParam search: String?) =
        productService.getAll(pageable, statusId, categoryId, search)

    @PutMapping("/{id}")
    fun edit(@Valid @RequestBody dto: ProductDto, @PathVariable id: Long) = productService.edit(id, dto)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = productService.delete(id)
}