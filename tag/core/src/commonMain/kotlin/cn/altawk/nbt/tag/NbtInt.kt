package cn.altawk.nbt.tag

/**
 * NbtInt
 *
 * @author TheFloodDragon
 * @since 2025/1/5 16:43
 */
public interface NbtInt : NbtTag {

    /**
     * The content of the tag.
     */
    public override val content: Int

    /**
     * Clone the tag.
     */
    public override fun clone(): NbtInt

    /**
     * The type of the tag.
     */
    public override val type: NbtType get() = NbtType.INT

}