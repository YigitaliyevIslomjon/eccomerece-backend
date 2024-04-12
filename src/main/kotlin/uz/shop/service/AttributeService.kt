package uz.shop.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import uz.shop.*

interface AttributeService {
    fun add(dto: AttributeDto): Result
    fun getOne(id: Long): AttributeDtoResponse
    fun edit(id: Long, dto: AttributeDto): Result
    fun getAll(): List<AttributeDtoResponse>
    fun delete(id: Long): Result
}

@Service
class AttributeServiceImpl(
    private val attributeRepository: AttributeRepository
) : AttributeService {
    override fun add(dto: AttributeDto): Result = dto.run {
        attributeRepository.findByName(name)?.run {
            throw AttributeNameExistException(" attribute name $name is already exist")
        }
        attributeRepository.save(Attribute(name))
        Result("data saved successfully")
    }

    override fun getOne(id: Long): AttributeDtoResponse {
        val attribute = attributeRepository.findByIdOrNull(id) ?: throw AttributeNotFoundException("attribute id $id is not found")
        return AttributeDtoResponse.toResponse(attribute)
    }


    override fun edit(id: Long, dto: AttributeDto): Result = dto.run {
        val attribute = attributeRepository.findByIdOrNull(id) ?: throw RoleNotFoundException("attribute id $id not found")
        if (attribute.name != name && attributeRepository.findByName(name) != null) {
            throw AttributeNameExistException("attribute name $name is already exist")
        }
        attribute.name = name
        attributeRepository.save(attribute)
        Result("data edit successfully")
    }

    override fun getAll(): List<AttributeDtoResponse> =
        attributeRepository.findAll().map(AttributeDtoResponse.Companion::toResponse)


    override fun delete(id: Long): Result {
        attributeRepository.findByIdOrNull(id) ?: throw RoleNotFoundException("role id $id not found")
        attributeRepository.deleteById(id)
        return Result("data deleted successfully")
    }
}