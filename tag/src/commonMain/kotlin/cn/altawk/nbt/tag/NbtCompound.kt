package cn.altawk.nbt.tag

/**
 * NbtCompound
 *
 * @since 2025/1/5 16:43
 */
public class NbtCompound(
    /**
     * The content of the tag.
     */
    public override val content: MutableMap<String, NbtTag>
) : NbtTag, MutableMap<String, NbtTag> by content {

    public constructor() : this(LinkedHashMap())

    /**
     * Merge tags from another compound. (deeply)
     * @param replace Whether to replace the original tag or not.
     */
    public fun merge(target: NbtCompound, replace: Boolean = true): NbtCompound {
        for ((key, targetValue) in target) {
            // 获取自身的数据
            val ownValue = this[key]
            // 自身数据不存在时, 直接替换为目标值
            if (ownValue == null) {
                this.content[key] = targetValue
            } else if (ownValue is NbtCompound && targetValue is NbtCompound) {
                // 同复合类型合并
                ownValue.merge(targetValue, replace)
            } else if (replace) {
                // 基础类型替换 (如果允许替换)
                this.content[key] = targetValue
            }
        }
        return this
    }

    /**
     * Merge tags from another compound. (shallowly)
     * @param replace Whether to replace the original tag or not.
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

    /**
     * Clone the tag.
     */
    public override fun clone(): NbtCompound =
        NbtCompound().also { new -> this.forEach { new[it.key] = it.value.clone() } }

    /**
     * Get the string representation of the tag.
     * TODO StringNBT
     */
    override fun toString(): String =
        content.entries.joinToString(separator = ",", prefix = "{", postfix = "}") { (name, value) ->
            buildString {
                appendNbtString(name)
                append(':')
                append(value)
            }
        }

    /**
     * Check if the tag is equal to another object.
     */
    override fun equals(other: Any?): Boolean = this === other || (other is NbtCompound && content == other.content)

    /**
     * Get the hash code of the tag.
     */
    override fun hashCode(): Int = content.hashCode()

    /**
     * The type of the tag.
     */
    override val type: NbtType get() = NbtType.COMPOUND

    public companion object {

        @JvmStatic
        public fun of(map: Map<String, NbtTag>): NbtCompound = NbtCompound(map.toMutableMap())

        /**
         * TODO StringNBT
         */
        private fun Appendable.appendNbtString(value: String, forceQuote: Boolean = false): Appendable {
            fun Appendable.appendQuoted(): Appendable = apply {
                append('"')
                value.forEach {
                    if (it == '"') append("\\\"") else append(it)
                }
                append('"')
            }

            fun Char.isSafeCharacter(): Boolean = when (this) {
                '-', '_', in 'a'..'z', in 'A'..'Z', in '0'..'9' -> true
                else -> false
            }

            return when {
                forceQuote -> appendQuoted()
                value.isEmpty() -> append("\"\"")
                value.all { it.isSafeCharacter() } -> append(value)
                !value.contains('"') -> append('"').append(value).append('"')
                !value.contains('\'') -> append('\'').append(value).append('\'')
                else -> appendQuoted()
            }
        }

    }

}