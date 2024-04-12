package uz.shop.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import uz.shop.*

interface CategoryService {
    fun add(dto: CategoryDto): Result
    fun getOne(id: Long): CategoryDtoResponse
    fun edit(id: Long, dto: CategoryDto): Result
    fun getAll(): List<CategoryDtoResponse>
    fun delete(id: Long): Result
}

@Service
class CategoryServiceImpl(
    private val categoryRepository: CategoryRepository,
    private val statusRepository: StatusRepository
) : CategoryService {
    override fun add(dto: CategoryDto): Result = dto.run {
        if (parentId != null && categoryRepository.findByIdOrNull(parentId) == null) {
            throw CategoryNotFoundException("parentId $parentId not found")
        }

        val status =
            statusRepository.findByIdOrNull(statusId) ?: throw StatusNotFoundException("status id $statusId not found")


        categoryRepository.findByName(name)?.run {
            throw CategoryNameExistException("category name $name is exist, change name")
        }
        categoryRepository.save(
            Category(
                parentId,
                name,
                icon,
                listOf(status)
            )
        )
        Result("data saved successfully")
    }

    override fun getOne(id: Long): CategoryDtoResponse {
        val category = categoryRepository.findByIdOrNull(id)
            ?: throw CategoryNotFoundException("category id $id not found")
        return CategoryDtoResponse.toResponse(category)

    }

    override fun edit(id: Long, dto: CategoryDto): Result = dto.run {
        val category = categoryRepository.findByIdOrNull(id)
            ?: throw CategoryNotFoundException("category id $id not found")
        if (parentId != null && categoryRepository.findByIdOrNull(parentId) == null) {
            throw CategoryNotFoundException("parentId $parentId not found")
        }

        if (category.name != name && categoryRepository.findByName(name) != null) {
            throw CategoryNameExistException("category name $name is exist, change name")
        }

        val status =
            statusRepository.findByIdOrNull(statusId) ?: throw StatusNotFoundException("status id $statusId not found")

        categoryRepository.save(category.also {
            it.parentId = parentId
            it.name = name
            it.icon = icon
            it.statuses = listOf(status)
        })
        Result("data edit successfully")
    }

    override fun getAll(): List<CategoryDtoResponse> =
        categoryRepository.findAll().map(CategoryDtoResponse.Companion::toResponse)


    override fun delete(id: Long): Result {
        categoryRepository.findByIdOrNull(id)
            ?: throw CategoryNotFoundException("category id $id not found")
        categoryRepository.deleteById(id)
        return Result("data deleted successfully")
    }
}