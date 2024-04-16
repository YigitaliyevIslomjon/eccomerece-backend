package uz.shop.service

import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import uz.shop.*

interface ProductService {
    fun add(dto: ProductDto): Result
    fun getOne(id: Long): ProductDtoResponse
    fun edit(id: Long, dto: ProductDto): ProductDtoResponse
    fun getAll(pageable: Pageable, statusId: Long?, categoryId: Long?, search: String?): Page<ProductDtoResponse>
    fun delete(id: Long): Result
}

@Service
class ProductServiceImpl(
    private val categoryRepository: CategoryRepository,
    private val statusRepository: StatusRepository,
    private val variantRepository: VariantRepository,
    private val attributeRepository: AttributeRepository,
    private val productRepository: ProductRepository,
    private val fileAttachmentRepository: FileAttachmentRepository,
) : ProductService {
    @Transactional
    override fun add(dto: ProductDto): Result = dto.run {
        val category = categoryRepository.findByIdOrNull(categoryId)
            ?: throw CategoryNotFoundException("category id $categoryId not found")

        val status =
            statusRepository.findByIdOrNull(statusId) ?: throw StatusNotFoundException("status id $statusId not found")

        val fileAttachment =
            fileAttachmentRepository.findByIdOrNull(imgId)
                ?: throw FileAttachmentNotFoundException("file id $imgId is not found")

        productRepository.findByFileAttachment(mutableListOf(fileAttachment))
            ?.run { throw FileAttachmentExistException("file Attachment id ${fileAttachment.id} already connect to product") }


        val product = Product(
            name,
            price,
            description,
            discount,
            category,
            status,
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

        val stock = Stock(
            variants,
            product,
            quantity
        )

        product.stock = stock
        productRepository.save(
            product
        )

        Result("product saved successfully")
    }

    override fun getOne(id: Long): ProductDtoResponse {
        val product =
            productRepository.findByIdOrNull(id) ?: throw ProductNotFoundException("product id $id is not found")
        return ProductDtoResponse.toResponse(product)
    }

    override fun edit(id: Long, dto: ProductDto): ProductDtoResponse {
        TODO("Not yet implemented")
    }

    override fun getAll(
        pageable: Pageable,
        statusId: Long?,
        categoryId: Long?,
        search: String?
    ): Page<ProductDtoResponse> {
        return if (categoryId != null && statusId != null) {
            productRepository.findAllByCategoryAndStatus(statusId, categoryId, pageable)
                .map(ProductDtoResponse.Companion::toResponse)
        } else if (statusId != null) {
            productRepository.findAllByStatus(statusId, pageable).map(ProductDtoResponse.Companion::toResponse)
        } else if (categoryId != null) {
            productRepository.findAllByCategory(categoryId, pageable).map(ProductDtoResponse.Companion::toResponse)
        } else if (search != null) {
            productRepository.findAllBySearch(search, pageable).map(ProductDtoResponse.Companion::toResponse)
        } else {
            productRepository.findAll(pageable).map(ProductDtoResponse.Companion::toResponse)
        }

    }

    override fun delete(id: Long): Result {
        TODO("Not yet implemented")
    }
}