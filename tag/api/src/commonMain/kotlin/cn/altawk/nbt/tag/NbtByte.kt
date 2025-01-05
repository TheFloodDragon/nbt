package cn.altawk.nbt.tag

/**
 * NbtByte
 *
 * @author TheFloodDragon
 * @since 2025/1/5 16:43
 */
public interface NbtByte : NbtTag {

    /**
     * The content of the tag.
     */
    public override val content: Byte

    /**
     * Clone the tag.
     */
    public override fun clone(): NbtByte

    /**
     * The type of the tag.
     */
    public override val type: NbtType get() = NbtType.BYTE

}