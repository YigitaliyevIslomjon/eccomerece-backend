package uz.shop.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import uz.shop.*
import uz.shop.User


interface UserService {
    fun add(dto: UserDto): Result
    fun getOne(id: Long): UserDtoResponse
    fun edit(id: Long, dto: UserDto): Result
    fun getAll(pageable: Pageable): Page<UserDtoResponse>
    fun delete(id: Long): Result
}

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {
    override fun add(dto: UserDto) = dto.run {
        val roleList = mutableListOf<Role>()
        roles.forEach {
            val role = roleRepository.findByIdOrNull(it) ?: throw RoleNotFoundException("role id $it is not found")
            roleList.add(role)
        }
        val password = passwordEncoder.encode(password)
        userRepository.findByUsername(username)
            ?.run { throw UsernameExistException("username $username already exist") }
        userRepository.findByPhone(email)
            ?.run { throw UsernameExistException("email $email already exist") }
        userRepository.findByUsername(phone)
            ?.run { throw UsernameExistException("phone $phone already exist") }

        userRepository.save(
            User(
                firstName,
                lastName,
                phone,
                username,
                email,
                password,
                roleList
            )
        )
        Result("data saved successfully")
    }

    override fun getOne(id: Long): UserDtoResponse {
        val user = userRepository.findByIdOrNull(id) ?: throw UsernameNotFoundException("user id $id not found")
        return UserDtoResponse.toResponse(user)
    }

    override fun edit(id: Long, dto: UserDto): Result {
        val user = userRepository.findByIdOrNull(id) ?: throw UsernameNotFoundException("user id $id not found")
        dto.apply {
            if (username != user.username && userRepository.findByUsername(username) != null) {
                throw UsernameExistException("username $username already exist")
            }
            if (phone != user.phone && userRepository.findByPhone(phone) != null) {
                throw UsernameExistException("phone $phone already exist")
            }
            if (email != user.email && userRepository.findByEmail(email) != null) {
                throw UsernameExistException("email $email already exist")
            }
            val roleList = mutableListOf<Role>()
            roles.forEach {
                val role = roleRepository.findByIdOrNull(it) ?: throw RoleNotFoundException("role id $it is not found")
                roleList.add(role)
            }

            user.lastName = lastName
            user.firstName = firstName
            user.roles = roleList
            user.email = email
            user.phone = phone
            user.username = username
            user.password = passwordEncoder.encode(password)
            userRepository.save(user)
            return Result("data edit successfully")
        }
    }

    override fun getAll(pageable: Pageable): Page<UserDtoResponse> =
        userRepository.findAll(pageable).map(UserDtoResponse.Companion::toResponse)


    override fun delete(id: Long): Result {
        userRepository.findByIdOrNull(id) ?: throw UserNotFoundException("user id $id not found")
        userRepository.deleteById(id)
        return Result("data deleted successfully")
    }
}