package uz.shop.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import uz.shop.*

interface DeliveryMethodService {
    fun add(dto: DeliveryMethodDto): Result
    fun getOne(id: Long): DeliveryMethodDtoResponse
    fun edit(id: Long, dto: DeliveryMethodDto): Result
    fun getAll(): List<DeliveryMethodDtoResponse>
    fun delete(id: Long): Result
}

@Service
class DeliveryMethodServiceImpl(
    private val deliveryMethodRepository: DeliveryMethodRepository
) : DeliveryMethodService {
    override fun add(dto: DeliveryMethodDto): Result = dto.run {
        deliveryMethodRepository.findByName(name)?.run {
            throw DeliveryMethodNameExistException("deliveryMethod name $name is already exist")
        }
        deliveryMethodRepository.save(
            DeliveryMethod(
                name,
                estimatedTime,
                price
            )
        )
        Result("data saved successfully")
    }

    override fun getOne(id: Long): DeliveryMethodDtoResponse {
        val deliveryMethod =
            deliveryMethodRepository.findByIdOrNull(id)
                ?: throw DeliveryMethodNotFoundException("delivery method id $id not found")
        return DeliveryMethodDtoResponse.toResponse(deliveryMethod)
    }

    override fun edit(id: Long, dto: DeliveryMethodDto): Result = dto.run {
        val deliveryMethod =
            deliveryMethodRepository.findByIdOrNull(id)
                ?: throw DeliveryMethodNotFoundException("delivery method id $id not found")

        if (deliveryMethod.name != name && deliveryMethodRepository.findByName(name) != null) {
            throw DeliveryMethodNameExistException("delivery method name $name is already exist")
        }

        deliveryMethod.name = name
        deliveryMethod.estimatedTime = estimatedTime
        deliveryMethod.price = price
        deliveryMethodRepository.save(deliveryMethod)
        Result("data edit successfully")
    }

    override fun getAll(): List<DeliveryMethodDtoResponse> =
        deliveryMethodRepository.findAll().map(DeliveryMethodDtoResponse.Companion::toResponse)


    override fun delete(id: Long): Result {
        deliveryMethodRepository.findByIdOrNull(id)
            ?: throw DeliveryMethodNotFoundException("delivery method id $id not found")
        deliveryMethodRepository.deleteById(id)
        return Result("data deleted successfully")
    }
}