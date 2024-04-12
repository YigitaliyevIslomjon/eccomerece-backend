package uz.shop.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import uz.shop.*

interface PaymentCardTypeService {
    fun add(dto: PaymentCardTypeDto): Result
    fun getOne(id: Long): PaymentCardTypeDtoResponse
    fun edit(id: Long, dto: PaymentCardTypeDto): Result
    fun getAll(): List<PaymentCardTypeDtoResponse>
    fun delete(id: Long): Result
}

@Service
class PaymentCardTypeServiceImpl(
    private val paymentCardTypeRepository: PaymentCardTypeRepository
) : PaymentCardTypeService {
    override fun add(dto: PaymentCardTypeDto): Result = dto.run {
        paymentCardTypeRepository.findByName(name)?.run {
            throw PaymentCardTypeNameExistException("payment card type name $name is already exist")
        }
        paymentCardTypeRepository.save(
            PaymentCardType(
                name,
                code,
                icon
            )
        )
        Result("data saved successfully")
    }

    override fun getOne(id: Long): PaymentCardTypeDtoResponse {
        val paymentCardType =
            paymentCardTypeRepository.findByIdOrNull(id)
                ?: throw PaymentCardTypeNotFoundException("payment card type id $id not found")
        return PaymentCardTypeDtoResponse.toResponse(paymentCardType)
    }

    override fun edit(id: Long, dto: PaymentCardTypeDto): Result = dto.run {
        val paymentCardType =
            paymentCardTypeRepository.findByIdOrNull(id)
                ?: throw PaymentCardTypeNotFoundException("payment card type id $id not found")

        if (paymentCardType.name != name && paymentCardTypeRepository.findByName(name) != null) {
            throw PaymentCardTypeNameExistException("payment card type name $name is already exist")
        }
        paymentCardType.name = name
        paymentCardType.code = code
        paymentCardType.icon = icon

        paymentCardTypeRepository.save(paymentCardType)
        Result("data edit successfully")
    }

    override fun getAll(): List<PaymentCardTypeDtoResponse> =
        paymentCardTypeRepository.findAll().map(PaymentCardTypeDtoResponse.Companion::toResponse)


    override fun delete(id: Long): Result {
        paymentCardTypeRepository.findByIdOrNull(id)
            ?: throw PaymentCardTypeNotFoundException("payment type $id not found")
        paymentCardTypeRepository.deleteById(id)
        return Result("data deleted successfully")
    }
}