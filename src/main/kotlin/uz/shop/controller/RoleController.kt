package uz.shop.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import uz.shop.RoleDto
import uz.shop.service.RoleService


@RestController
@RequestMapping("/${BASE_PREFIX}role")
class RoleController(
    private val roleService: RoleService
) {
    @PostMapping("add")
    fun add(@Valid @RequestBody dto: RoleDto) = roleService.add(dto)

    @GetMapping("/all")
    fun getAll() = roleService.getAll()

    @PutMapping("/{id}")
    fun edit(@Valid @RequestBody dto: RoleDto, @PathVariable id: Long) = roleService.edit(id, dto)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = roleService.delete(id)
}