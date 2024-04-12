package uz.shop.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import uz.shop.Result
import uz.shop.Order
import uz.shop.OrderDto
import uz.shop.OrderDtoResponse

interface OrderService {
    fun add(dto: OrderDto): Result
    fun getOne(id: Long): Order
    fun edit(id: Long, dto: OrderDto): OrderDtoResponse
    fun getAll(pageable: Pageable): Page<OrderDtoResponse>
    fun delete(id: Long): Result
}

@Service
class OrderServiceImpl: OrderService {
    override fun add(dto: OrderDto): Result {
        TODO("Not yet implemented")
    }

    override fun getOne(id: Long): Order {
        TODO("Not yet implemented")
    }

    override fun edit(id: Long, dto: OrderDto): OrderDtoResponse {
        TODO("Not yet implemented")
    }

    override fun getAll(pageable: Pageable): Page<OrderDtoResponse> {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long): Result {
        TODO("Not yet implemented")
    }
}