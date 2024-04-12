package uz.shop.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import uz.shop.Result
import uz.shop.Variant
import uz.shop.ValueDto
import uz.shop.ValueDtoResponse

interface ValueService {
    fun add(dto: ValueDto): Result
    fun getOne(id: Long): Variant
    fun edit(id: Long, dto: ValueDto): ValueDtoResponse
    fun getAll(pageable: Pageable): Page<ValueDtoResponse>
    fun delete(id: Long): Result
}

@Service
class ValueServiceImpl: ValueService {
    override fun add(dto: ValueDto): Result {
        TODO("Not yet implemented")
    }

    override fun getOne(id: Long): Variant {
        TODO("Not yet implemented")
    }

    override fun edit(id: Long, dto: ValueDto): ValueDtoResponse {
        TODO("Not yet implemented")
    }

    override fun getAll(pageable: Pageable): Page<ValueDtoResponse> {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long): Result {
        TODO("Not yet implemented")
    }
}