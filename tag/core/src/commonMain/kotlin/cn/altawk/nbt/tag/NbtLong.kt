package cn.altawk.nbt.tag

/**
 * NbtLong
 *
 * @author TheFloodDragon
 * @since 2025/1/5 16:43
 */
public interface NbtLong : NbtTag {

    /**
     * The content of the tag.
     */
    public override val content: Long

    /**
     * Clone the tag.
     */
    public override fun clone(): NbtLong

    /**
     * The type of the tag.
     */
    public override val type: NbtType get() = NbtType.LONG

}