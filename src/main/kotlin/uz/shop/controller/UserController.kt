package uz.shop.controller

import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import uz.shop.UserDto
import uz.shop.service.UserService

const val BASE_PREFIX = "api/v1/"

@RestController
@RequestMapping("/${BASE_PREFIX}user")
class UserController(
    private val userService: UserService
) {
    @PostMapping("add")
    fun add(@Valid @RequestBody dto: UserDto) = userService.add(dto)

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long) = userService.getOne(id)

    @GetMapping("/pageable")
    fun getAll(pageable: Pageable) = userService.getAll(pageable)

    @PutMapping("/{id}")
    fun edit(@Valid @RequestBody dto: UserDto, @PathVariable id: Long) = userService.edit(id, dto)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = userService.delete(id)
}

