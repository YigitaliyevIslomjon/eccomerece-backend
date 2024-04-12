package uz.shop.service

import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.util.FileCopyUtils
import org.springframework.web.multipart.MultipartHttpServletRequest
import java.io.FileInputStream
import uz.shop.FileAttachment
import uz.shop.FileAttachmentDtoResponse
import uz.shop.FileAttachmentRepository
import java.io.File
import java.io.FileNotFoundException
import org.apache.commons.io.FileUtils
import java.util.*

interface FileAttachmentService {
    fun add(request: MultipartHttpServletRequest): FileAttachmentDtoResponse
    fun getOne(id: Long, response: HttpServletResponse)
}

@Service
class FileAttachmentServiceImpl(
    private val fileAttachmentRepository: FileAttachmentRepository,
    @Value("\${saved-file-folder}") var savedFileFolder: String,
) : FileAttachmentService {

    override fun add(request: MultipartHttpServletRequest): FileAttachmentDtoResponse {
        val fileNames = request.fileNames
        val file = request.getFile(fileNames.next())
        val fileAttachment: FileAttachment
        if (file != null) {
            try {
                val fileOriginalName = file.originalFilename
                val size = file.size
                val contentType = file.contentType
                val split = fileOriginalName!!.split("\\.")
                val name = UUID.randomUUID().toString() + split[split.size - 1]
                val path = "${savedFileFolder}/$name"
                val newAttachment = FileAttachment(
                    fileOriginalName,
                    path,
                    size,
                    contentType!!,
                )
                fileAttachment = fileAttachmentRepository.save(newAttachment)
                byteArrayToFile(file.bytes, path)
                return FileAttachmentDtoResponse.toResponse(fileAttachment)
            } finally {
                file.inputStream.close()
            }
        } else {
            throw FileNotFoundException("file is null")
        }
    }

    override fun getOne(id: Long, response: HttpServletResponse) {
        val existFileAttachment =
            fileAttachmentRepository.findByIdOrNull(id) ?: throw FileNotFoundException("File id $id is not found")
        response.setHeader("Content-Disposition","inline; filename=\"${existFileAttachment.originalFileName}\" ")
        response.contentType = existFileAttachment.contentType
        val fileInputStream = FileInputStream(existFileAttachment.path)
        FileCopyUtils.copy(fileInputStream, response.outputStream)
    }

    private fun byteArrayToFile(data: ByteArray, pathName: String) =
        FileUtils.writeByteArrayToFile(File(pathName), data)

}