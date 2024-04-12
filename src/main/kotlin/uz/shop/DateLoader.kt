package uz.shop

import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component


@Component
class DateLoader(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val roleRepository: RoleRepository
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val defaultUsername = "dev"
        val encoderPassword = passwordEncoder.encode("123")
        userRepository.findByUsername(defaultUsername) ?: run {
            val user = User(
                "test",
                "test",
                "+998999080499",
                defaultUsername,
                "salom2@gmail.com",
                encoderPassword,
                listOf()
            )
            val role = Role(UserRole.ADMIN, listOf(user))
            user.roles = listOf(role)
            roleRepository.save(role)
        }
    }
}


