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
    @OneToOne()
    var discount: Discount,
    @ManyToOne()
    var category: Category,
    @ManyToMany()
    var statuses: List<Status>,
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
    var value: List<Variant>,
    @OneToOne(fetch = FetchType.LAZY)
    var product: Product? = null,
    @Column(nullable = false)
    var quantity: Long,
) : BaseEntity()


@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["name", "type"])])
class Status(
    @Column(nullable = false)
    var name: String,
    @Enumerated(EnumType.STRING)
    var type: StatusType,
) : BaseEntity()

@Entity
class ProductUser(
    @ManyToOne()
    var user: User,
    @ManyToOne()
    var product: Product,
) : BaseEntity()


@Entity(name = "orders")
class Order(
    @ManyToOne()
    var user: User,
    @ManyToMany()
    var statuses: List<Status> = listOf(),
    @OneToOne()
    var deliveryMethod: DeliveryMethod,
    @OneToOne()
    var paymentType: PaymentType,
    var sum: Long,
    @Column(nullable = false)
    var comment: String,
//    var products:
    var address: String,
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
class Review(
    @ManyToOne()
    var user: User,
    @ManyToOne()
    var product: Product,
    @Column(nullable = false)
    var rating: Int,
    @Column(nullable = false)
    var comment: String,
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
class Discount(
//    @Column(nullable = false)
//    var name: String,
    var sum: Long? = null,
    var precent: Long? = null,
//    @Column(nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    var fromDate: Date,
//    @Column(nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    var toDate: Date,
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
