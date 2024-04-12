package uz.shop.service

import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import uz.shop.*
import java.io.FileNotFoundException

interface ProductService {
    fun add(dto: ProductDto): Result
    fun getOne(id: Long): Product
    fun edit(id: Long, dto: ProductDto): ProductDtoResponse
    fun getAll(pageable: Pageable): Page<ProductDtoResponse>
    fun delete(id: Long): Result
}

@Service
class ProductServiceImpl(
    private val categoryRepository: CategoryRepository,
    private val statusRepository: StatusRepository,
    private val variantRepository: VariantRepository,
    private val discountRepository: DiscountRepository,
    private val attributeRepository: AttributeRepository,
    private val productRepository: ProductRepository,
    private val fileAttachmentRepository: FileAttachmentRepository,
    private val stockRepository: StockRepository
) : ProductService {
    @Transactional
    override fun add(dto: ProductDto): Result = dto.run {
        val category = categoryRepository.findByIdOrNull(categoryId)
            ?: throw CategoryNotFoundException("category id $categoryId not found")

        val status =
            statusRepository.findByIdOrNull(statusId) ?: throw StatusNotFoundException("status id $statusId not found")

        val fileAttachment =
            fileAttachmentRepository.findByIdOrNull(imgId) ?: throw FileNotFoundException("file id $imgId is not found")

        val discount = discountRepository.save(
            Discount(
                discount.sum,
                discount.percent
            )
        )

        val product = Product(
            name,
            price,
            description,
            discount,
            category,
            listOf(status),
            listOf(fileAttachment),
        )

        val variants = variants.map {
            val attribute = attributeRepository.findByIdOrNull(it.attributeId)
                ?: throw AttributeNotFoundException("attribute id ${it.attributeId} is not found ")
            variantRepository.save(
                Variant(
                    attribute,
                    product,
                    it.value
                )
            )
        }
        println(variants)
        val stock = Stock(
            variants,
            product,
            quantity
        )

        product.stock = stock
        productRepository.save(
            product
        )
        Result("successs")
    }

    override fun getOne(id: Long): Product {
        return productRepository.findByIdOrNull(id) ?: throw ProductNotFoundException("product id $id is not found")
    }

    override fun edit(id: Long, dto: ProductDto): ProductDtoResponse {
        TODO("Not yet implemented")
    }

    override fun getAll(pageable: Pageable): Page<ProductDtoResponse> {
        return productRepository.findAll(pageable).map(ProductDtoResponse.Companion::toResponse)
    }

    override fun delete(id: Long): Result {
        TODO("Not yet implemented")
    }
}