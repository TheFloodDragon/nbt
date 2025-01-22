package cn.altawk.nbt.tag

/**
 * NbtList
 *
 * @since 2025/1/5 16:43
 */
public class NbtList<T : NbtTag>(
    /**
     * The content of the tag, for compatibility.
     */
    public override val content: MutableList<T>
) : NbtTag, MutableList<T> by content {

    /**
     * Create an empty list.
     */
    public constructor() : this(ArrayList())

    /**
     * Create a list with a size.
     */
    public constructor(size: Int) : this(ArrayList(size))

    /**
     * The type of elements in the list.
     */
    public val elementType: NbtType = if (content.isEmpty()) NbtType.END else content.first().type

    /**
     * Clone the tag.
     */
    override fun clone(): NbtList<T> {
        val newList = NbtList<T>(this.size)
        for (element in this.content) {
            @Suppress("UNCHECKED_CAST")
            newList.add(element.clone() as T)
        }
        return newList
    }

    /**
     * Get the string representation of the tag.
     */
    override fun toString(): String = content.joinToString(prefix = "[", postfix = "]", separator = ",")

    /**
     * Check if the tag equals another.
     */
    override fun equals(other: Any?): Boolean =
        this === other || (other is NbtList<*> && content == other.content)

    /**
     * Get the hash code of the tag.
     */
    override fun hashCode(): Int = content.hashCode()

    /**
     * The type of the tag.
     */
    override val type: NbtType get() = NbtType.LIST

    public companion object {

        @JvmStatic
        public fun <T : NbtTag> of(list: Collection<T>): NbtList<T> = NbtList(list.toMutableList())

    }

}