package uz.shop.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import uz.shop.*


interface AddressService {
    fun add(dto: AddressDto): Result
    fun getOne(id: Long): AddressDtoResponse
    fun edit(id: Long, dto: AddressDto): Result
    fun getAll(pageable: Pageable): Page<AddressDtoResponse>
    fun delete(id: Long): Result
}

@Service
class AddressServiceImpl(
    private val addressRepository: AddressRepository,
) : AddressService {
    override fun add(dto: AddressDto) = dto.run {
        addressRepository.save(
            Address(
                region,
                district,
                street,
                home,
                longitude,
                longitude
            )
        )
        Result("data saved successfully")
    }

    override fun getOne(id: Long): AddressDtoResponse {
        val address =
            addressRepository.findByIdOrNull(id) ?: throw AddressNotFoundException("address id $id is not found")
        return AddressDtoResponse.toResponse(address)
    }

    override fun edit(id: Long, dto: AddressDto): Result = dto.run {
        val address =
            addressRepository.findByIdOrNull(id) ?: throw AddressNotFoundException("address id $id is not found")
        addressRepository.save(address.also {
            it.region = region
            it.district = district
            it.street = street
            it.home = home
            it.longitude = longitude
            it.longitude = longitude
        })
        Result("data edit successfully")
    }

    override fun getAll(pageable: Pageable): Page<AddressDtoResponse> =
        addressRepository.findAll(pageable).map(AddressDtoResponse.Companion::toResponse)

    override fun delete(id: Long): Result {
        val address =
            addressRepository.findByIdOrNull(id) ?: throw AddressNotFoundException("address id $id is not found")
        addressRepository.deleteById(id)
        return   Result("data deleted successfully")
    }
}