package cn.altawk.nbt.tag

/**
 * NbtCompound
 *
 * @since 2025/1/5 16:43
 */
public interface NbtCompound : NbtTag {

    /**
     * The content of the tag, for compatibility.
     */
    public override val content: Map<String, NbtTag>

    /**
     * Get a tag by key.
     * @return The tag or null if not found.
     */
    public fun get(key: String): NbtTag?

    /**
     * Put a tag with key.
     */
    public fun put(key: String, value: NbtTag)

    /**
     * Remove a tag by key.
     */
    public fun remove(key: String)

    /**
     * Check if the compound contains a tag by key.
     */
    public fun contains(key: String): Boolean

    /**
     * The size of the compound.
     */
    public val size: Int

    /**
     * Check if the compound is empty.
     */
    public fun isEmpty(): Boolean

    /**
     * Clear the compound.
     */
    public fun clear()

    /**
     * Merge tags from another compound. (shallowly)
     * @param replace Whether to replace the original tag or not.
     */
    public fun mergeShallow(target: NbtCompound, replace: Boolean = true): NbtCompound

    /**
     * Merge tags from another compound. (deeply)
     * @param replace Whether to replace the original tag or not.
     */
    public fun merge(target: NbtCompound, replace: Boolean = true): NbtCompound

    /**
     * Clone the tag. (shallowly)
     */
    public fun cloneShallow(): NbtCompound

    /**
     * Clone the tag. (deeply)
     */
    public override fun clone(): NbtCompound

    /**
     * The type of the tag.
     */
    override val type: NbtType get() = NbtType.COMPOUND

}