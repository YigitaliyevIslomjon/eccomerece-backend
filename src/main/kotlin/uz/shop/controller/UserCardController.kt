package uz.shop.controller

import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import uz.shop.UserCardDto
import uz.shop.service.UserCardService


@RestController
@RequestMapping("/${BASE_PREFIX}user-card")
class UserCardController(
    private val userCardService: UserCardService
) {
    @PostMapping("add")
    fun add(@Valid @RequestBody dto: UserCardDto) = userCardService.add(dto)

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long) = userCardService.getOne(id)

    @GetMapping("/all")
    fun getAll(pageable: Pageable) = userCardService.getAll()

    @PutMapping("/{id}")
    fun edit(@Valid @RequestBody dto: UserCardDto, @PathVariable id: Long) = userCardService.edit(id, dto)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = userCardService.delete(id)
}

