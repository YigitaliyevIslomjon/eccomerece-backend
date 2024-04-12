package uz.shop

import jakarta.persistence.*
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.util.*

data class Result(
    val message: String
)

data class UserDto(
    @field:NotBlank()
    var firstName: String,
    @field:NotBlank()
    var lastName: String,
    @field:NotBlank()
    var phone: String,
    @field:NotBlank()
    var username: String,
    @field:NotBlank()
    var email: String,
    @field:NotBlank()
    var password: String,
    @field:NotEmpty()
    var roles: List<Long>
)


data class UserDtoResponse(
    var id: Long,
    var firstName: String,
    var lastName: String,
    var phone: String,
    var username: String,
    var email: String,
) {
    companion object {
        fun toResponse(user: User): UserDtoResponse {
            return UserDtoResponse(
                user.id!!,
                user.firstName,
                user.lastName,
                user.phone,
                user.username,
                user.email
            )
        }
    }
}

data class RoleDto(
    var name: UserRole,
)

data class RoleDtoResponse(
    var id: Long,
    var name: UserRole,
) {
    companion object {
        fun toResponse(role: Role): RoleDtoResponse {
            return RoleDtoResponse(
                role.id!!,
                role.name,
            )
        }
    }
}


data class CategoryDto(
    var parentId: Long?,
    @field:NotBlank
    var name: String,
    @field:NotBlank
    var icon: String,
    @field:NotNull
    var statusId: Long,
)

data class CategoryDtoResponse(
    var id: Long,
    var parentId: Long?,
    var name: String,
    var icon: String,
    var category: Status
) {
    companion object {
        fun toResponse(category: Category): CategoryDtoResponse {
            return CategoryDtoResponse(
                category.id!!,
                category.parentId,
                category.name,
                category.icon,
                category.statuses.first(),
            )

        }
    }
}


data class VariantDto(
    @field:NotNull
    var attributeId: Long,
    @field:NotBlank
    var value: String,
)

data class DiscountDto(
    var sum: Long? = null,
    var percent: Long? = null,
)

data class ProductDto(
    @field:NotNull
    var categoryId: Long,
    @field:NotBlank
    var name: String,
    @field:NotNull
    var price: Long,
    @field:NotBlank
    var description: String,
    @field:NotNull
    var statusId: Long,
    @field:Valid
    var variants: List<VariantDto>,
    @field:NotNull
    var quantity: Long,
    @field:Valid
    var discount: DiscountDto,
    @field:NotNull
    var imgId: Long
)

data class ProductDtoResponse(
    var id: Long,
    var categoryId: Long,
    var name: String,
    var price: Long,
    var description: String,
) {
    companion object {
        fun toResponse(product: Product): ProductDtoResponse {
            return ProductDtoResponse(
                product.id!!,
                product.category.id!!,
                product.name,
                product.price,
                product.description,
            )
        }
    }
}

data class AttributeDto(
    var name: String,
)

data class AttributeDtoResponse(
    var id: Long,
    var name: String,
) {
    companion object {
        fun toResponse(attribute: Attribute): AttributeDtoResponse {
            return AttributeDtoResponse(
                attribute.id!!,
                attribute.name,
            )
        }
    }
}

data class ValueDto(
    @field:NotNull
    var attributeId: Long,
    @field:NotNull
    var productId: Long,
    @field:NotBlank
    var name: String,
)

data class ValueDtoResponse(
    var id: Long,
    var attributeId: Long,
    var productId: Long,
    var value: String,
) {
    companion object {
        fun toResponse(variant: Variant): ValueDtoResponse {
            return ValueDtoResponse(
                variant.id!!,
                variant.attribute.id!!,
                variant.product.id!!,
                variant.value
            )
        }
    }
}

data class StockDto(
    @field:NotNull
    var variant: Variant,
    @field:NotNull
    var product: Product,
    @field:NotNull
    var quantity: Long,
)

data class StockDtoResponse(
    var id: Long,
    var value: List<Variant>,
    var product: Product?,
    var quantity: Long,
) {
    companion object {
        fun toResponse(stock: Stock): StockDtoResponse {
            return StockDtoResponse(
                stock.id!!,
                stock.value,
                stock.product,
                stock.quantity
            )
        }
    }
}

//
//data class ProductUserDto(
//    @field:NotNull
//    var userId: Long,
//    @field:NotNull
//    var productId: Long,
//)

data class DeliveryMethodDto(
    var name: DeliveryMethodType,
    @field:NotBlank
    var estimatedTime: String,
    @field:NotNull
    var price: Long,
)

data class DeliveryMethodDtoResponse(
    var id: Long,
    var name: DeliveryMethodType,
    var estimatedTime: String,
    var price: Long,
) {
    companion object {
        fun toResponse(deliveryMethod: DeliveryMethod): DeliveryMethodDtoResponse {
            return DeliveryMethodDtoResponse(
                deliveryMethod.id!!,
                deliveryMethod.name,
                deliveryMethod.estimatedTime,
                deliveryMethod.price
            )
        }
    }
}

data class PaymentTypeDto(
    @field:Enumerated
    var name: PaymentTypeEnum,
)

data class PaymentTypeDtoResponse(
    var id: Long,
    var name: PaymentTypeEnum,
) {
    companion object {
        fun toResponse(paymentType: PaymentType): PaymentTypeDtoResponse {
            return PaymentTypeDtoResponse(
                paymentType.id!!,
                paymentType.name,
            )
        }
    }
}

data class UserProductDto(
    @field:NotNull
    var productId: Long,
    var quantity: Long,
)

data class OrderDto(
    @field:NotNull
    var totalSum: Long? = null,
    @field:NotEmpty
    var products: List<UserProductDto>,
    @field:NotNull
    var deliveryMethodId: Long,
    @field:NotNull
    var paymentTypeId: Long,
    @field:NotNull
    var addressId: Long,
)

data class OrderDtoResponse(
    var id: Long,
    var deliveryMethodId: Long,
    var paymentTypeId: Long,
    var sum: Long? = null,
    var comment: String,
//    @field:NotEmpty
//    var products: List<>
    var address: String,
) {
    companion object {
        fun toResponse(order: Order): OrderDtoResponse {
            return OrderDtoResponse(
                order.id!!,
                order.deliveryMethod.id!!,
                order.paymentType.id!!,
                order.sum,
                order.comment,
                order.address
            )
        }
    }
}


data class AddressDto(
    @field:NotBlank
    var region: String,
    @field:NotBlank
    var district: String,
    @field:NotBlank
    var street: String,
    @field:NotBlank
    var home: String,
    var latitude: Double? = null,
    var longitude: Double? = null,
)

data class AddressDtoResponse(
    var id: Long,
    var latitude: Double? = null,
    var longitude: Double? = null,
    var region: String,
    var district: String,
    var street: String,
    var home: String
) {
    companion object {
        fun toResponse(address: Address): AddressDtoResponse {
            return AddressDtoResponse(
                address.id!!,
                address.latitude,
                address.longitude,
                address.region,
                address.district,
                address.street,
                address.home
            )
        }
    }
}

data class StatusDto(
    @field:NotBlank
    var name: String,
    @field:Enumerated
    var type: StatusType
)

data class StatusDtoResponse(
    var id: Long,
    var name: String,
    var type: StatusType
) {
    companion object {
        fun toResponse(status: Status): StatusDtoResponse {
            return StatusDtoResponse(
                status.id!!,
                status.name,
                status.type
            )
        }
    }
}

@Entity
class ReviewDto(
    @field:NotNull
    var userId: Long,
    @field:NotNull
    var productId: Long,
    @field:NotNull
    var rating: Int,
    @field:NotBlank
    var comment: String,
) : BaseEntity()

data class ReviewDtoResponse(
    var id: Long,
    var userId: Long,
    var productId: Long,
    var rating: Int,
    var comment: String,
) {
    companion object {
        fun toResponse(review: Review): ReviewDtoResponse {
            return ReviewDtoResponse(
                review.id!!,
                review.user.id!!,
                review.product.id!!,
                review.rating,
                review.comment
            )
        }
    }
}

/*
class DiscountDto(
    @field:NotNull
    var productId: Long,
    @field:NotBlank
    var name: String,
    @field:NotNull
    var sum: Long,
    @field:NotNull
    var precent: Long,
    @field:NotBlank
    var from: Date,
    @field:NotBlank
    var to: Date,
)
*/

/*
class DiscountDtoResponse(
    var id: Long,
    var productId: Long,
    var name: String,
    var sum: Long,
    var precent: Long,
    var fromDate: Date,
    var toDate: Date,
) {
    companion object {
        fun toResponse(discount: Discount): DiscountDtoResponse {
            return DiscountDtoResponse(
                discount.id!!,
                discount.product.id!!,
                discount.name,
                discount.sum,
                discount.precent,
                discount.fromDate,
                discount.toDate
            )
        }
    }
}
*/

data class FileAttachmentDtoResponse(
    val id: Long,
    val path: String,
) {
    companion object {
        fun toResponse(fileAttachment: FileAttachment): FileAttachmentDtoResponse {
            return FileAttachmentDtoResponse(
                fileAttachment.id!!,
                fileAttachment.path,
            )
        }
    }
}

data class PaymentCardTypeDto(

    var name: CardType,
    @field:NotNull
    var code: Int,
    @field:NotBlank
    var icon: String,
)

data class PaymentCardTypeDtoResponse(
    var id: Long,
    var name: CardType,
    var code: Int,
    var icon: String,
) {
    companion object {
        fun toResponse(paymentCardType: PaymentCardType): PaymentCardTypeDtoResponse {
            return PaymentCardTypeDtoResponse(
                paymentCardType.id!!,
                paymentCardType.name,
                paymentCardType.code,
                paymentCardType.icon
            )
        }
    }
}

data class UserCardDto(
    @field:NotNull
    var paymentCardTypeId: Long,
    @field:NotNull
    var number: Long,
    @field:NotNull
    var lastForNumber: Long,
    @field:NotNull
    var expiredDate: Date,
    @field:NotNull
    var holderName: String,
    var cvv: String,
)

data class UserCardDtoResponse(
    var id: Long,
    var paymentCardTypeId: Long,
    var number: Long,
    var lastForNumber: Long,
    var expiredDate: Date,
    var holderName: String,
    var cvv: String,
) {
    companion object {
        fun toResponse(userCard: UserCard): UserCardDtoResponse {
            return UserCardDtoResponse(
                userCard.id!!,
                userCard.paymentCardType.id!!,
                userCard.number,
                userCard.lastForNumber,
                userCard.expiredDate,
                userCard.holderName,
                userCard.cvv
            )
        }
    }
}
