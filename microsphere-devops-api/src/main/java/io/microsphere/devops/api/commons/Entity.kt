package io.microsphere.devops.api.commons

/**
 * The Commons Entity Data Class
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @since 1.0.0
 */
interface Entity {

    /**
     * ID property
     */
    val id: Long

    /**
     * Description property
     */
    var description: String?
        get() = description
        set(value) {
            description = value
        }

    /**
     * Created At property(timestamp)
     */
    var createdAt: Long?
        get() = createdAt
        set(value) {
            createdAt = value
        }

    /**
     * Updated At property(timestamp)
     */
    var updatedAt: Long?
        get() = updatedAt
        set(value) {
            updatedAt = value
        }

}
