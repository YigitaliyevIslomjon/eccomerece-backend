package uz.shop.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import uz.shop.*

interface PaymentTypeService {
    fun add(dto: PaymentTypeDto): Result
    fun getOne(id: Long): PaymentTypeDtoResponse
    fun edit(id: Long, dto: PaymentTypeDto): Result
    fun getAll(): List<PaymentTypeDtoResponse>
    fun delete(id: Long): Result
}

@Service
class PaymentTypeServiceImpl(
    private val paymentTypeRepository: PaymentTypeRepository
) : PaymentTypeService {
    override fun add(dto: PaymentTypeDto): Result = dto.run {
        paymentTypeRepository.findByName(name)?.run {
            throw PaymentTypeNameExistException("payment type name $name is already exist")
        }
        paymentTypeRepository.save(
            PaymentType(
                name
            )
        )
        Result("data saved successfully")
    }

    override fun getOne(id: Long): PaymentTypeDtoResponse {
        val paymentType =
            paymentTypeRepository.findByIdOrNull(id)
                ?: throw PaymentTypeNotFoundException("payment type id $id not found")
        return PaymentTypeDtoResponse.toResponse(paymentType)
    }

    override fun edit(id: Long, dto: PaymentTypeDto): Result = dto.run {
        val paymentType =
            paymentTypeRepository.findByIdOrNull(id)
                ?: throw PaymentTypeNotFoundException("payment type id $id not found")

        if (paymentType.name != name && paymentTypeRepository.findByName(name) != null) {
            throw PaymentTypeNameExistException("payment type name $name is already exist")
        }
        paymentType.name = name
        paymentTypeRepository.save(paymentType)
        Result("data edit successfully")
    }

    override fun getAll(): List<PaymentTypeDtoResponse> =
        paymentTypeRepository.findAll().map(PaymentTypeDtoResponse.Companion::toResponse)


    override fun delete(id: Long): Result {
        paymentTypeRepository.findByIdOrNull(id) ?: throw PaymentTypeNotFoundException("payment type $id not found")
        paymentTypeRepository.deleteById(id)
        return Result("data deleted successfully")
    }
}