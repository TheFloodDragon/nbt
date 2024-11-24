package net.benwoodworth.knbt.tag

import kotlinx.serialization.Serializable
import net.benwoodworth.knbt.internal.appendNbtString
import kotlin.jvm.JvmStatic

/**
 * NbtCompound
 *
 * @author TheFloodDragon
 * @since 2024/11/24 10:54
 */
@Serializable(NbtCompoundSerializer::class)
public class NbtCompound(public override val content: MutableMap<String, NbtTag>) : NbtTag,
    MutableMap<String, NbtTag> by content {

    public constructor() : this(LinkedHashMap())

    /**
     * 克隆数据
     */
    public override fun clone(): NbtCompound =
        NbtCompound().also { new -> this.forEach { new[it.key] = it.value.clone() } }

    /**
     * 浅克隆数据
     */
    public fun cloneShallow(): NbtCompound = NbtCompound().also { new -> new.putAll(this) }

    /**
     * 合并目标数据
     * @param replace 是否替换原有的数据 (false时不会 替换/删除 任何一个节点)
     */
    public fun merge(target: NbtCompound, replace: Boolean = true): NbtCompound {
        for ((key, targetValue) in target) {
            // 获取自身的数据
            val ownValue = this[key]
            // 自身数据不存在时, 直接替换为目标值
            if (ownValue == null) {
                this[key] = targetValue
            } else if (ownValue is NbtCompound && targetValue is NbtCompound) {
                // 同复合类型合并
                ownValue.merge(targetValue, replace)
            } else if (replace) {
                // 基础类型替换 (如果允许替换)
                this[key] = targetValue
            }
        }
        return this
    }

    /**
     * 合并目标数据 (浅合并)
     * @param replace 是否替换原有的标签
     */
    public fun mergeShallow(target: NbtCompound, replace: Boolean = true): NbtCompound {
        for ((key, value) in target) {
            // 如果当前数据中不存在, 或者允许替换
            if (!this.containsKey(key) || replace) {
                // 直接设置值
                this[key] = value
            }
        }
        return this
    }

    override fun equals(other: Any?): Boolean = this === other || (other is NbtCompound && content == other.content)

    override fun hashCode(): Int = content.hashCode()

    override fun toString(): String =
        content.entries.joinToString(separator = ",", prefix = "{", postfix = "}") { (name, value) ->
            buildString {
                appendNbtString(name)
                append(':')
                append(value)
            }
        }

    override val type: NbtType get() = NbtType.COMPOUND

    public companion object {

        @JvmStatic
        public fun of(map: Map<String, NbtTag>): NbtCompound = NbtCompound(map.toMutableMap())

    }

}