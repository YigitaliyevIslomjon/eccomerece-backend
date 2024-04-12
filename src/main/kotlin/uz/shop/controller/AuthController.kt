package uz.shop.controller

import org.springframework.web.bind.annotation.*
import uz.shop.Result


@RestController
@RequestMapping("/${BASE_PREFIX}auth")
class AuthController(
) {
    @PostMapping("sign-in")
    fun sign() = Result("user successfully sign in")

}