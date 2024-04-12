package uz.shop.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import uz.shop.*

interface RoleService {
    fun add(dto: RoleDto): Result
    fun edit(id: Long, dto: RoleDto): Result
    fun getAll(): List<RoleDtoResponse>
    fun delete(id: Long): Result

}

@Service
class RoleServiceImpl(
    private val roleRepository: RoleRepository
) : RoleService {
    override fun add(dto: RoleDto) = dto.run {
        roleRepository.findByName(name)?.run {
            throw RoleExistException("role name $name is already exist")
        }
        roleRepository.save(Role(name))
        Result("data saved successfully")
    }

    override fun edit(id: Long, dto: RoleDto): Result = dto.run {
        val role = roleRepository.findByIdOrNull(id) ?: throw RoleNotFoundException("role id $id not found")
        if (role.name != name && roleRepository.findByName(name) != null) {
            throw RoleExistException("role name $name is already exist")
        }
        role.name = name
        roleRepository.save(role)
        Result("data edit successfully")
    }

    override fun getAll(): List<RoleDtoResponse> =
        roleRepository.findAll().map(RoleDtoResponse.Companion::toResponse)

    override fun delete(id: Long): Result {
        roleRepository.findByIdOrNull(id) ?: throw RoleNotFoundException("role id $id not found")
        roleRepository.deleteById(id)
        return Result("data deleted successfully")
    }

}