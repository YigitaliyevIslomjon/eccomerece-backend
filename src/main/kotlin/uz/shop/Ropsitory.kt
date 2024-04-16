package uz.shop

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun findByEmail(email: String): User?
    fun findByPhone(email: String): User?
}

interface CategoryRepository : JpaRepository<Category, Long> {
    fun findByName(name: String): Category?
    @Query(value = "select c from Category c where c.name ilike CONCAT('%',:search,'%')")
    fun findAllBySearch(@Param("search") search: String, pageable: Pageable): Page<Category>
}
interface ProductRepository : JpaRepository<Product, Long> {
    fun findByFileAttachment(fileAttachment: MutableList<FileAttachment>): Product?
    @Query(value = "select p from Product p where p.name ilike CONCAT('%',:search,'%') or p.category.name ilike CONCAT('%',:search,'%') or cast(p.price as string) ilike CONCAT('%',:search,'%')")
    fun findAllBySearch(@Param("search") search: String, pageable: Pageable): Page<Product>
    @Query(value = "select p from Product p where p.status.id = :statusId and p.status.type = 'PRODUCT'")
    fun findAllByStatus(@Param("statusId") statusId: Long, pageable: Pageable): Page<Product>

    @Query(value = "select p from Product p where p.category.id = :categoryId")
    fun findAllByCategory(@Param("categoryId") categoryId: Long, pageable: Pageable): Page<Product>

    @Query(value = "select p from Product p where p.category.id = :categoryId and p.status.id = :statusId")
    fun findAllByCategoryAndStatus(@Param("statusId") statusId: Long, @Param("categoryId") categoryId: Long, pageable: Pageable): Page<Product>
}

interface StockRepository : JpaRepository<Stock, Long> {
    fun findByProduct(product: Product): Stock?
}

interface AttributeRepository : JpaRepository<Attribute, Long> {
    fun findByName(name: String): Attribute?
}

interface VariantRepository : JpaRepository<Variant, Long> {
}

interface RoleRepository : JpaRepository<Role, Long> {
    fun findByName(name: UserRole): Role?
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
    fun findByNameAndType(name: StatusValue, type: StatusType): Status?
}

interface AddressRepository : JpaRepository<Address, Long> {
}


