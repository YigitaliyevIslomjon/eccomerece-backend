package uz.shop

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    var createdDate: Date? = null,
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    var modifiedDate: Date? = null,
    @LastModifiedBy
    var modifiedBy: Date? = null,
    @Column(nullable = false)
    @ColumnDefault(value = "false")
    var deleted: Boolean = false
)


@Entity(name = "users")
class User(
    @Column(nullable = false)
    var firstName: String,
    @Column(nullable = false)
    var lastName: String,
    @Column(nullable = false, unique = true)
    var phone: String,
    @Column(nullable = false, unique = true)
    var username: String,
    @Column(nullable = false, unique = true)
    var email: String,
    @Column(nullable = false)
    var password: String,
    @ManyToMany(fetch = FetchType.EAGER)
    var roles: List<Role>
) : BaseEntity()

@Entity(name = "roles")
class Role(
    @Enumerated(EnumType.STRING)
    var name: UserRole,
    @ManyToMany(mappedBy = "roles", cascade = [CascadeType.ALL])
    var users: List<User> = listOf(),
) : BaseEntity()

@Entity
class Category(
    var parentId: Long? = null,
    @Column(nullable = false, unique = true)
    var name: String,
    @Column(nullable = false)
    var icon: String,
    @ManyToMany()
    var statuses: List<Status> = listOf()
) : BaseEntity()


@Entity
class Product(
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    var price: Long,
    @Column(nullable = false)
    var description: String,
    var discount: Long = 0,
    @ManyToOne()
    var category: Category,

    @ManyToOne()
    var status: Status,
    @OneToMany()
    var fileAttachment: List<FileAttachment>,
    @OneToOne(mappedBy = "product", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var stock: Stock? = null,
) : BaseEntity()

@Entity
class Attribute(
    @Column(nullable = false, unique = false)
    var name: String,
    @OneToMany(mappedBy = "attribute", cascade = [CascadeType.ALL])
    var value: List<Variant> = listOf(),
) : BaseEntity()

@Entity
class Variant(
    @ManyToOne()
    var attribute: Attribute,
    @ManyToOne()
    var product: Product,
    @Column(nullable = false)
    var value: String,
) : BaseEntity()

@Entity
class Stock(
    @OneToMany(fetch = FetchType.EAGER)
    var values: List<Variant>,
    @OneToOne(fetch = FetchType.LAZY)
    var product: Product? = null,
    @Column(nullable = false)
    var quantity: Long,
) : BaseEntity()


@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["name", "type"])])
class Status(
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var name: StatusValue,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var type: StatusType,
) : BaseEntity()


@Entity(name = "orders")
class Order(
    @Column(nullable = false)
    var sum: Long,
    @ManyToOne()
    var user: User,
    @ManyToMany()
    var status: List<Status>,
    @ManyToOne()
    var deliveryMethod: DeliveryMethod,
    @ManyToOne()
    var paymentType: PaymentType,
    @ManyToOne()
    var address: Address,
/*    @ManyToMany()
    var products: List<Product>,*/
    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    var productOrders: List<ProductOrder> = listOf()
) : BaseEntity()

@Entity()
class ProductOrder(
    @Column(nullable = false)
    var quantity: Long,
    @ManyToOne()
    var product: Product,
    @ManyToOne()
    var order: Order? = null,
) : BaseEntity()

@Entity
class PaymentType(
    @Enumerated(EnumType.STRING)
    var name: PaymentTypeEnum,
) : BaseEntity()

@Entity
class DeliveryMethod(
    @Enumerated(EnumType.STRING)
    var name: DeliveryMethodType,
    @Column(nullable = false)
    var estimatedTime: String,
    @Column(nullable = false)
    var price: Long,
) : BaseEntity()


@Entity
class Address(
    @Column(nullable = false)
    var region: String,
    @Column(nullable = false)
    var district: String,
    @Column(nullable = false)
    var street: String,
    @Column(nullable = false)
    var home: String,
    var latitude: Double? = null,
    var longitude: Double? = null,
) : BaseEntity()


@Entity
class UserCard(
    @ManyToOne()
    var user: User,
    @ManyToOne()
    var paymentCardType: PaymentCardType,
    @Column(nullable = false)
    var number: Long,
    @Column(nullable = false)
    var lastForNumber: Long,
    @Column(nullable = false)
    var expiredDate: Date,
    var holderName: String,
    var cvv: String,
) : BaseEntity()

@Entity
class PaymentCardType(
    @Enumerated(EnumType.STRING)
    var name: CardType,
    @Column(nullable = false)
    var code: Int,
    @Column(nullable = false)
    var icon: String,
) : BaseEntity()


@Entity
class FileAttachment(
    @Column(nullable = false)
    val originalFileName: String,
    @Column(nullable = false)
    val path: String,
    @Column(nullable = false)
    val size: Long,
    @Column(nullable = false)
    var contentType: String,
) : BaseEntity()
