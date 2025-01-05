package cn.altawk.nbt.tag

/**
 * NbtFloat
 *
 * @author TheFloodDragon
 * @since 2025/1/5 16:43
 */
public interface NbtFloat : NbtTag {

    /**
     * The content of the tag.
     */
    public override val content: Float

    /**
     * Clone the tag.
     */
    public override fun clone(): NbtFloat

    /**
     * The type of the tag.
     */
    public override val type: NbtType get() = NbtType.FLOAT

}