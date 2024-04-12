package uz.shop.controller

import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import uz.shop.StatusDto
import uz.shop.service.StatusService


@RestController
@RequestMapping("/${BASE_PREFIX}status")
class StatusController(
    private val statusService: StatusService
) {
    @PostMapping("add")
    fun add(@Valid @RequestBody dto: StatusDto) = statusService.add(dto)

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long) = statusService.getOne(id)

    @GetMapping("/pageable")
    fun getAll(pageable: Pageable) = statusService.getAll(pageable)

    @PutMapping("/{id}")
    fun edit(@Valid @RequestBody dto: StatusDto, @PathVariable id: Long) = statusService.edit(id, dto)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = statusService.delete(id)
}