package cn.altawk.nbt.tag

/**
 * NbtString
 *
 * @author TheFloodDragon
 * @since 2025/1/5 16:43
 */
public class NbtString(
    /**
     * The content of the tag.
     */
    public override val content: String
) : NbtTag {

    /**
     * Clone the tag.
     */
    public override fun clone(): NbtString = NbtString(content)

    /**
     * Get the string representation of the tag.
     */
    override fun toString(): String = content

    /**
     * Check if the tag equals another.
     */
    override fun equals(other: Any?): Boolean = this === other || (other is NbtString && this.content == other.content)

    /**
     * Get the hash code of the tag.
     */
    override fun hashCode(): Int = content.hashCode()

    /**
     * The type of the tag.
     */
    public override val type: NbtType get() = NbtType.STRING

}