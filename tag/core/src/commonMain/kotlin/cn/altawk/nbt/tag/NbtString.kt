package cn.altawk.nbt.tag

/**
 * NbtString
 *
 * @author TheFloodDragon
 * @since 2025/1/5 16:43
 */
public interface NbtString : NbtTag {

    /**
     * The content of the tag.
     */
    public override val content: String

    /**
     * Clone the tag.
     */
    public override fun clone(): NbtString

    /**
     * The type of the tag.
     */
    public override val type: NbtType get() = NbtType.STRING

}