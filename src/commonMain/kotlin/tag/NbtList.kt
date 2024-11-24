package net.benwoodworth.knbt.tag

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmStatic

/**
 * NbtList
 *
 * @author TheFloodDragon
 * @since 2024/11/24 10:51
 */
@Serializable(NbtListSerializer::class)
public class NbtList<T : NbtTag>(public override val content: MutableList<T>) : NbtTag,
    MutableList<T> by content {

    public constructor() : this(ArrayList())

    public constructor(size: Int) : this(ArrayList(size))

    public val elementType: NbtType
        get() = if (content.isEmpty()) NbtType.END else content.first().type

    /**
     * 克隆数据
     */
    override fun clone(): NbtList<T> = NbtList<T>().apply {
        this.content.forEach {
            @Suppress("UNCHECKED_CAST")
            add(it.clone() as T)
        }
    }

    override fun equals(other: Any?): Boolean =
        this === other || (other is NbtList<*> && content == other.content)

    override fun hashCode(): Int = content.hashCode()

    override fun toString(): String = content.joinToString(prefix = "[", postfix = "]", separator = ",")

    override val type: NbtType get() = NbtType.LIST

    public companion object {

        @JvmStatic
        public fun <T : NbtTag> of(list: Collection<T>): NbtList<T> = NbtList(list.toMutableList())

    }

}