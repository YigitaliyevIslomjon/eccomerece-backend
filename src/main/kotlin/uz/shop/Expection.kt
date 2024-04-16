package uz.shop


import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler(BaseException::class)
    fun handleBaseException(baseException: BaseException): ResponseEntity<*> {
        return ResponseEntity.badRequest().body(baseException.getModel())
    }

    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNotFoundException(ex: NoHandlerFoundException): ResponseEntity<String> {
        return ResponseEntity("This URL does not exist", HttpStatus.NOT_FOUND)
    }


    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    protected fun handleHttpMediaTypeNotSupported(
        ex: HttpMediaTypeNotSupportedException?, headers: HttpHeaders?, status: HttpStatus?, request: WebRequest?
    ): ResponseEntity<Any> {
        val errorMessage = "Unsupported media type. Please use a valid media type."
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
            .body(ErrorMessageModel(message = errorMessage, status = HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String>> {
        val errors = HashMap<String, String>()
        ex.bindingResult.allErrors.forEach { error ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.defaultMessage
            errors[fieldName] = errorMessage ?: "Validation error"
        }
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(AccessDeniedException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleAccessDeniedException(ex: AccessDeniedException): ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            HttpStatus.FORBIDDEN.value(),
            ex.message
        )
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(errorMessage)
    }
}

abstract class BaseException(private val msg: String? = null) : RuntimeException(msg) {
    abstract fun errorCode(): ErrorCode
    abstract fun getModel(): ErrorMessageModel
}



class UsernameExistException(msg: String) : BaseException(msg) {
    override fun errorCode() = ErrorCode.USERNAME_EXIST
    override fun getModel(): ErrorMessageModel {
        return ErrorMessageModel(errorCode().code, message)
    }
}

class AttributeNameExistException(msg: String) : BaseException(msg) {
    override fun errorCode() = ErrorCode.ATTRIBUTE_NAME_EXIST
    override fun getModel(): ErrorMessageModel {
        return ErrorMessageModel(errorCode().code, message)
    }
}

class AttributeNotFoundException(msg: String) : BaseException(msg) {
    override fun errorCode() = ErrorCode.ATTRIBUTE_NOT_FOUND
    override fun getModel(): ErrorMessageModel {
        return ErrorMessageModel(errorCode().code, message)
    }
}

class UserNotFoundException(msg: String) : BaseException(msg) {
    override fun errorCode() = ErrorCode.USER_NOT_FOUND
    override fun getModel(): ErrorMessageModel {
        return ErrorMessageModel(errorCode().code, message)
    }
}

class CategoryNameExistException(msg: String) : BaseException(msg) {
    override fun errorCode() = ErrorCode.CATEGORY_NAME_EXIST
    override fun getModel(): ErrorMessageModel {
        return ErrorMessageModel(errorCode().code, message)
    }
}


class CategoryNotFoundException(msg: String) : BaseException(msg) {
    override fun errorCode() = ErrorCode.CATEGORY_NOT_FOUND
    override fun getModel(): ErrorMessageModel {
        return ErrorMessageModel(errorCode().code, message)
    }
}


class StatusNotFoundException(msg: String) : BaseException(msg) {
    override fun errorCode() = ErrorCode.STATUS_NOT_FOUND
    override fun getModel(): ErrorMessageModel {
        return ErrorMessageModel(errorCode().code, message)
    }
}
class StatusAndTypeExistException(msg: String) : BaseException(msg) {
    override fun errorCode() = ErrorCode.STATUS_AND_TYPE_EXIST
    override fun getModel(): ErrorMessageModel {
        return ErrorMessageModel(errorCode().code, message)
    }
}

class RoleExistException(msg: String) : BaseException(msg) {
    override fun errorCode() = ErrorCode.ROLE_EXIST
    override fun getModel(): ErrorMessageModel {
        return ErrorMessageModel(errorCode().code, message)
    }
}

class RoleNotFoundException(msg: String) : BaseException(msg) {
    override fun errorCode() = ErrorCode.ROLE_NOT_FOUND
    override fun getModel(): ErrorMessageModel {
        return ErrorMessageModel(errorCode().code, message)
    }
}

class ProductNotFoundException(msg: String) : BaseException(msg) {
    override fun errorCode() = ErrorCode.PRODUCT_NOT_FOUND
    override fun getModel(): ErrorMessageModel {
        return ErrorMessageModel(errorCode().code, message)
    }
}

class AddressNotFoundException(msg: String) : BaseException(msg) {
    override fun errorCode() = ErrorCode.ADDRESS_NOT_FOUND
    override fun getModel(): ErrorMessageModel {
        return ErrorMessageModel(errorCode().code, message)
    }
}

class PaymentTypeNameExistException(msg: String) : BaseException(msg) {
    override fun errorCode() = ErrorCode.PAYMENT_TYPE_NAME_EXIST
    override fun getModel(): ErrorMessageModel {
        return ErrorMessageModel(errorCode().code, message)
    }
}

class PaymentTypeNotFoundException(msg: String) : BaseException(msg) {
    override fun errorCode() = ErrorCode.PAYMENT_TYPE_NOT_FOUND
    override fun getModel(): ErrorMessageModel {
        return ErrorMessageModel(errorCode().code, message)
    }
}

class PaymentCardTypeNameExistException(msg: String) : BaseException(msg) {
    override fun errorCode() = ErrorCode.PAYMENT_CARD_TYPE_NAME_EXIST
    override fun getModel(): ErrorMessageModel {
        return ErrorMessageModel(errorCode().code, message)
    }
}

class PaymentCardTypeNotFoundException(msg: String) : BaseException(msg) {
    override fun errorCode() = ErrorCode.PAYMENT_CARD_TYPE_NOT_FOUND
    override fun getModel(): ErrorMessageModel {
        return ErrorMessageModel(errorCode().code, message)
    }
}


class DeliveryMethodNameExistException(msg: String) : BaseException(msg) {
    override fun errorCode() = ErrorCode.DELIVERY_METHOD_NAME_EXIST_FOUND
    override fun getModel(): ErrorMessageModel {
        return ErrorMessageModel(errorCode().code, message)
    }
}


class DeliveryMethodNotFoundException(msg: String) : BaseException(msg) {
    override fun errorCode() = ErrorCode.DELIVERY_METHOD_NOT_FOUND
    override fun getModel(): ErrorMessageModel {
        return ErrorMessageModel(errorCode().code, message)
    }
}

class UserCardNotFoundException(msg: String) : BaseException(msg) {
    override fun errorCode() = ErrorCode.USER_CARD_NOT_FOUND
    override fun getModel(): ErrorMessageModel {
        return ErrorMessageModel(errorCode().code, message)
    }
}

class ProductQuantityIsNotEnoughException(msg: String) : BaseException(msg) {
    override fun errorCode() = ErrorCode.PRODUCT_QUANTITY_NOT_FOUND
    override fun getModel(): ErrorMessageModel {
        return ErrorMessageModel(errorCode().code, message)
    }
}

class OrderNotFoundException(msg: String) : BaseException(msg) {
    override fun errorCode() = ErrorCode.ORDER_NOT_FOUND
    override fun getModel(): ErrorMessageModel {
        return ErrorMessageModel(errorCode().code, message)
    }
}

class FileAttachmentNotFoundException(msg: String) : BaseException(msg) {
    override fun errorCode() = ErrorCode.FILE_ATTACHMENT_NOT_FOUND
    override fun getModel(): ErrorMessageModel {
        return ErrorMessageModel(errorCode().code, message)
    }
}

class FileAttachmentExistException(msg: String) : BaseException(msg) {
    override fun errorCode() = ErrorCode.FILE_ATTACHMENT_EXIST
    override fun getModel(): ErrorMessageModel {
        return ErrorMessageModel(errorCode().code, message)
    }
}

class ErrorMessageModel(
    var status: Int? = null,
    var message: String? = null
)