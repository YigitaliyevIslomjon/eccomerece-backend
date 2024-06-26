package uz.shop

enum class UserRole {
    USER,
    ADMIN,
    CUSTOMER,
    SHOP_MANAGER;
}

enum class DeliveryMethodType {
    STANDART,
    EXPRESS,
}

enum class StatusType {
    CATEGORY,
    ORDER,
    PRODUCT,
}

enum class StatusValue {
    PROCCESS,
    PUBLISH,
    INACTIVE,
    SECHDULED,
    DELIVERED
}
enum class CardType {
    HUMO,
    UZCARD,
    VISA
}

enum class PaymentTypeEnum {
    CASH,
    ONLINE,
}

enum class ErrorCode(val code: Int) {
    USERNAME_EXIST(100),
    ROLE_NOT_FOUND(101),
    USER_NOT_FOUND(102),
    ROLE_EXIST(103),
    CATEGORY_NOT_FOUND(104),
    CATEGORY_NAME_EXIST(105),
    ATTRIBUTE_NAME_EXIST(106),
    ATTRIBUTE_NOT_FOUND(107),
    STATUS_NOT_FOUND(108),
    STATUS_AND_TYPE_EXIST(109),
    PRODUCT_NOT_FOUND(110),
    ADDRESS_NOT_FOUND(111),
    PAYMENT_TYPE_NAME_EXIST(112),
    PAYMENT_TYPE_NOT_FOUND(113),
    DELIVERY_METHOD_NAME_EXIST_FOUND(114),
    DELIVERY_METHOD_NOT_FOUND(115),
    PAYMENT_CARD_TYPE_NAME_EXIST(116),
    PAYMENT_CARD_TYPE_NOT_FOUND(117),
    USER_CARD_NOT_FOUND(118),
    PRODUCT_QUANTITY_NOT_FOUND(119),
    ORDER_NOT_FOUND(120),
    FILE_ATTACHMENT_EXIST(121),
    FILE_ATTACHMENT_NOT_FOUND(122)
}
