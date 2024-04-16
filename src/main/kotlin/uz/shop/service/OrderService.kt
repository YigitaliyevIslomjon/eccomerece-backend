package uz.shop.service

import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import uz.shop.*

interface OrderService {
    fun add(dto: OrderDto): Result
    fun getOne(id: Long): OrderDtoResponse
    fun edit(id: Long, dto: OrderDto): OrderDtoResponse
    fun getAll(pageable: Pageable): Page<OrderDtoResponse>
    fun delete(id: Long): Result
}

@Service
class OrderServiceImpl(
    private val deliveryMethodRepository: DeliveryMethodRepository,
    private val paymentTypeRepository: PaymentTypeRepository,
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository,
    private val addressRepository: AddressRepository,
    private val stockRepository: StockRepository,
    private val statusRepository: StatusRepository
) : OrderService {

    @Transactional
    override fun add(dto: OrderDto): Result = dto.run {

        val deliveryMethod = deliveryMethodRepository.findByIdOrNull(deliveryMethodId)
            ?: throw DeliveryMethodNotFoundException("deliveryMethod id $deliveryMethodId is not found")


        val paymentType = paymentTypeRepository.findByIdOrNull(paymentTypeId)
            ?: throw PaymentTypeNotFoundException("paymentType id $paymentTypeId is not found")

        val address =
            addressRepository.findByIdOrNull(addressId)
                ?: throw AddressNotFoundException("address id $addressId is not found")

        val user = userRepository.findByUsername(SecurityContextHolder.getContext().authentication.name)
            ?: throw UserNotFoundException("user not found expextion")

        var sum = 0L

        val productOrderList = mutableListOf<ProductOrder>()
        products.forEach {

            val product = productRepository.findByIdOrNull(it.productId)
                ?: throw ProductNotFoundException("product id ${it.productId} is not found")

            val stock = stockRepository.findByProduct(product)
                ?: throw StatusNotFoundException("stock id ${product.stock!!.id} is not found")
            sum += (product.price - product.discount)

            if (it.quantity > stock.quantity) {
                throw ProductQuantityIsNotEnoughException("product ${it.productId} quantity is not enough,  product in stock ${product.stock!!.quantity} ")
            }

            productOrderList.add(ProductOrder(it.quantity, product))
            stock.quantity -= it.quantity
            stockRepository.save(stock)
        }

        var status = statusRepository.findByNameAndType(StatusValue.PROCCESS, StatusType.ORDER)
        if (status == null) {
            status = statusRepository.save(Status(StatusValue.PROCCESS, StatusType.ORDER))
        }

        if (sum != 0L) {

            sum += deliveryMethod.price
            val order = Order(
                sum,
                user,
                listOf(status),
                deliveryMethod,
                paymentType,
                address,
                productOrderList
            )

            productOrderList.forEach {
                it.order = order
            }

            orderRepository.save(
                order
            )
        }

        Result("order saved successfully")
    }

    override fun getOne(id: Long): OrderDtoResponse {
        val order =
            orderRepository.findByIdOrNull(id)
                ?: throw OrderNotFoundException("order id $id not found")
        return OrderDtoResponse.toResponse(order)
    }

    override fun edit(id: Long, dto: OrderDto): OrderDtoResponse {
        TODO("Not yet implemented")
    }

    override fun getAll(pageable: Pageable): Page<OrderDtoResponse> {
        return orderRepository.findAll(pageable).map(OrderDtoResponse.Companion::toResponse)
    }

    override fun delete(id: Long): Result {
        orderRepository.findByIdOrNull(id) ?: throw OrderNotFoundException("order id $id not found")
        orderRepository.deleteById(id)
        return Result("order deleted successfully")
    }
}