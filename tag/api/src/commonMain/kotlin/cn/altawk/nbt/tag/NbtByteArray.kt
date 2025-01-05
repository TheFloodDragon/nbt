package cn.altawk.nbt.tag

/**
 * NbtByteArray
 *
 * @author TheFloodDragon
 * @since 2025/1/5 16:43
 */
public interface NbtByteArray : NbtTag {

    /**
     * The content of the tag.
     */
    public override val content: ByteArray

    /**
     * Get the data at the specified index.
     */
    public operator fun get(index: Int): Byte = content[index]

    /**
     * The size of the tag.
     */
    public val size: Int get() = content.size

    /**
     * Clone the tag.
     */
    public override fun clone(): NbtByteArray

    /**
     * The type of the tag.
     */
    public override val type: NbtType get() = NbtType.BYTE_ARRAY

}