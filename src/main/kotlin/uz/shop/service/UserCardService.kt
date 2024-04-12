package uz.shop.service


import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import uz.shop.*

interface UserCardService {
    fun add(dto: UserCardDto): Result
    fun getOne(id: Long): UserCardDtoResponse
    fun edit(id: Long, dto: UserCardDto): Result
    fun getAll(): List<UserCardDtoResponse>
    fun delete(id: Long): Result
}

@Service
class UserCardServiceImpl(
    private val userCardRepository: UserCardRepository,
    private val paymentCardTypeRepository: PaymentCardTypeRepository,
    private val userRepository: UserRepository
) : UserCardService {
    override fun add(dto: UserCardDto): Result = dto.run {
        val paymentCardType =
            paymentCardTypeRepository.findByIdOrNull(paymentCardTypeId) ?: throw PaymentCardTypeNotFoundException(
                "" +
                        "paymentCardType id $paymentCardTypeId is not found "
            )
        val user = userRepository.findByUsername(SecurityContextHolder.getContext().authentication.name)
            ?: throw UserNotFoundException("user not found")
        userCardRepository.save(
            UserCard(
                user,
                paymentCardType,
                number,
                lastForNumber,
                expiredDate,
                holderName,
                cvv
            )
        )
        Result("data saved successfully")
    }

    override fun getOne(id: Long): UserCardDtoResponse {
        val userCard =  
            userCardRepository.findByIdOrNull(id)
                ?: throw UserCardNotFoundException("UserCard id $id not found")
        return UserCardDtoResponse.toResponse(userCard)
    }

    override fun edit(id: Long, dto: UserCardDto): Result = dto.run {
        val userCard =
            userCardRepository.findByIdOrNull(id)
                ?: throw UserCardNotFoundException("UserCard id $id not found")

        val paymentCardType =
            paymentCardTypeRepository.findByIdOrNull(paymentCardTypeId) ?: throw PaymentCardTypeNotFoundException(
                "" +
                        "paymentCardType id $paymentCardTypeId is not found "
            )

        userCard.paymentCardType = paymentCardType
        userCard.number = number
        userCard.holderName = holderName
        userCard.expiredDate = expiredDate
        userCard.cvv = cvv
        userCardRepository.save(userCard)
        Result("data edit successfully")
    }

    override fun getAll(): List<UserCardDtoResponse> =
        userCardRepository.findAll().map(UserCardDtoResponse.Companion::toResponse)

    override fun delete(id: Long): Result {
        userCardRepository.findByIdOrNull(id) ?: throw UserCardNotFoundException("UserCard type $id not found")
        userCardRepository.deleteById(id)
        return Result("data deleted successfully")
    }
}