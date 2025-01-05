package cn.altawk.nbt.tag

/**
 * NbtList
 *
 * @since 2025/1/5 16:43
 */
public interface NbtList<T : NbtTag> : NbtTag {

    /**
     * The content of the tag, for compatibility.
     */
    public override val content: List<T>

    /**
     * The type of elements in the list.
     */
    public val elementType: NbtType

    /**
     * Get the tag at the index.
     */
    public fun get(index: Int): T

    /**
     * Set the tag at the index.
     */
    public fun set(index: Int, tag: T)

    /**
     * Add a tag to the list.
     */
    public fun add(tag: T)

    /**
     * Remove a tag from the list at the index.
     */
    public fun removeAt(index: Int)

    /**
     * Check if the list contains a tag.
     */
    public fun contains(tag: NbtTag): Boolean

    /**
     * The size of the list.
     */
    public val size: Int

    /**
     * Check if the list is empty.
     */
    public fun isEmpty(): Boolean

    /**
     * Clear the list.
     */
    public fun clear()

    /**
     * Clone the tag.
     */
    override fun clone(): NbtList<T>

    /**
     * The type of the tag.
     */
    override val type: NbtType get() = NbtType.LIST

}