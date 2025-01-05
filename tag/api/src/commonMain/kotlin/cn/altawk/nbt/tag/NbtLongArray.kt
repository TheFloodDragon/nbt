package cn.altawk.nbt.tag

/**
 * NbtLongArray
 *
 * @author TheFloodDragon
 * @since 2025/1/5 16:43
 */
public interface NbtLongArray : NbtTag {

    /**
     * The content of the tag.
     */
    public override val content: LongArray

    /**
     * Get the data at the specified index.
     */
    public operator fun get(index: Int): Long

    /**
     * The size of the tag.
     */
    public val size: Int

    /**
     * Clone the tag.
     */
    public override fun clone(): NbtLongArray

    /**
     * The type of the tag.
     */
    public override val type: NbtType get() = NbtType.LONG_ARRAY

}