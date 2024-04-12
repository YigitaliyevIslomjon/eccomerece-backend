package uz.shop.service

import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import uz.shop.*

interface StatusService {
    fun add(dto: StatusDto): Result
    fun getOne(id: Long): StatusDtoResponse
    fun edit(id: Long, dto: StatusDto): Result
    fun getAll(pageable: Pageable): List<StatusDtoResponse>
    fun delete(id: Long): Result
}

@Service
class StatusServiceImpl(
    private val statusRepository: StatusRepository
) : StatusService {
    override fun add(dto: StatusDto): Result = dto.run {

        if (statusRepository.existsByNameAndType(name, type)) {
            throw StatusAndTypeExistException("name $name and type $type already exist")
        }
        statusRepository.save(
            Status(
                name,
                type
            )
        )

        Result("data saved successfully")
    }

    override fun getOne(id: Long): StatusDtoResponse {
        val status = statusRepository.findByIdOrNull(id) ?: throw StatusNotFoundException("status id $id not found")
        return StatusDtoResponse.toResponse(status)
    }

    override fun edit(id: Long, dto: StatusDto): Result = dto.run {
        val status = statusRepository.findByIdOrNull(id) ?: throw StatusNotFoundException("status id $id not found")
        if (status.name != name && status.type != type && statusRepository.existsByNameAndType(name, type)) {
            throw StatusAndTypeExistException("name $name and type $type already exist")
        }
        status.name = name
        status.type = type
        statusRepository.save(status)
        Result("data edit successfully")
    }

    override fun getAll(pageable: Pageable): List<StatusDtoResponse> =
        statusRepository.findAll().map(StatusDtoResponse.Companion::toResponse)


    override fun delete(id: Long): Result {
        statusRepository.findByIdOrNull(id) ?: throw StatusNotFoundException("status id $id not found")
        statusRepository.deleteById(id)
        return Result("data deleted successfully")
    }
}