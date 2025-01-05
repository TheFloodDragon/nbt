package cn.altawk.nbt.tag

/**
 * NbtDouble
 *
 * @author TheFloodDragon
 * @since 2025/1/5 16:43
 */
public interface NbtDouble : NbtTag {

    /**
     * The content of the tag.
     */
    public override val content: Double

    /**
     * Clone the tag.
     */
    public override fun clone(): NbtDouble

    /**
     * The type of the tag.
     */
    public override val type: NbtType get() = NbtType.DOUBLE

}