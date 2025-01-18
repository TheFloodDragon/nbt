package cn.altawk.nbt.tag

/**
 * NbtIntArray
 *
 * @author TheFloodDragon
 * @since 2025/1/5 16:43
 */
public interface NbtIntArray : NbtTag {

    /**
     * The content of the tag.
     */
    public override val content: IntArray

    /**
     * Get the data at the specified index.
     */
    public operator fun get(index: Int): Int

    /**
     * The size of the tag.
     */
    public val size: Int

    /**
     * Clone the tag.
     */
    public override fun clone(): NbtIntArray

    /**
     * The type of the tag.
     */
    public override val type: NbtType get() = NbtType.INT_ARRAY

}