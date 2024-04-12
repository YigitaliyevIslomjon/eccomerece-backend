package uz.shop.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import uz.shop.Stock
import uz.shop.StockDto
import uz.shop.StockDtoResponse
import uz.shop.Result


interface StockService {
    fun add(dto: StockDto): Result
    fun getOne(id: Long): Stock
    fun edit(id: Long, dto: StockDto): StockDtoResponse
    fun getAll(pageable: Pageable): Page<StockDtoResponse>
    fun delete(id: Long): Result
}

@Service
class StockServiceImpl: StockService {
    override fun add(dto: StockDto): Result {
        TODO("Not yet implemented")
    }

    override fun getOne(id: Long): Stock {
        TODO("Not yet implemented")
    }

    override fun edit(id: Long, dto: StockDto): StockDtoResponse {
        TODO("Not yet implemented")
    }

    override fun getAll(pageable: Pageable): Page<StockDtoResponse> {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long): Result {
        TODO("Not yet implemented")
    }
}