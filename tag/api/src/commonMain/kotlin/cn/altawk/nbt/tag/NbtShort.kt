package cn.altawk.nbt.tag

/**
 * NbtShort
 *
 * @author TheFloodDragon
 * @since 2025/1/5 16:43
 */
public interface NbtShort : NbtTag {

    /**
     * The content of the tag.
     */
    public override val content: Short

    /**
     * Clone the tag.
     */
    public override fun clone(): NbtShort

    /**
     * The type of the tag.
     */
    public override val type: NbtType get() = NbtType.SHORT

}