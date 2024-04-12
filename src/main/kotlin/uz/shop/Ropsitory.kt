package uz.shop

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun findByEmail(email: String): User?
    fun findByPhone(email: String): User?
}

interface CategoryRepository : JpaRepository<Category, Long> {
    fun findByName(name: String): Category?
}

interface ProductRepository : JpaRepository<Product, Long> {
}

interface StockRepository : JpaRepository<Stock, Long> {
}

interface AttributeRepository : JpaRepository<Attribute, Long> {
    fun findByName(name: String): Attribute?
}

interface VariantRepository : JpaRepository<Variant, Long> {
}

interface RoleRepository : JpaRepository<Role, Long> {
    fun findByName(name: UserRole): Role?
}

interface ProductUserRepository : JpaRepository<ProductUser, Long> {
}

interface DeliveryMethodRepository : JpaRepository<DeliveryMethod, Long> {
    fun findByName(name: DeliveryMethodType): DeliveryMethod?
}

interface OrderRepository : JpaRepository<Order, Long> {
}

interface PaymentTypeRepository : JpaRepository<PaymentType, Long> {
    fun findByName(name: PaymentTypeEnum): PaymentType?
}

interface UserCardRepository : JpaRepository<UserCard, Long> {
}


interface PaymentCardTypeRepository : JpaRepository<PaymentCardType, Long> {
    fun findByName(name: CardType): PaymentCardType?
}


interface FileAttachmentRepository : JpaRepository<FileAttachment, Long> {
}

interface StatusRepository : JpaRepository<Status, Long> {
    fun existsByNameAndType(name: String, type: StatusType): Boolean
}

interface DiscountRepository : JpaRepository<Discount, Long> {
}

interface AddressRepository : JpaRepository<Address, Long> {
}


