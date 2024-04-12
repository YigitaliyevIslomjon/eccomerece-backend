package uz.shop.controller

import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartHttpServletRequest
import uz.shop.service.FileAttachmentService
import java.util.*

@RestController
@RequestMapping("${BASE_PREFIX}file")
class FileAttachmentController(
    private val fileAttachmentService: FileAttachmentService,
) {
    @PostMapping("add")
    fun add(
        request: MultipartHttpServletRequest
    ) = fileAttachmentService.add(request)

    @GetMapping("{id}")
    fun getOne(@PathVariable id: Long, request: HttpServletResponse) = fileAttachmentService.getOne(id, request)
}
