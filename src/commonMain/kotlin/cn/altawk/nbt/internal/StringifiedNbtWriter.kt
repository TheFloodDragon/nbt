package cn.altawk.nbt.internal

import cn.altawk.nbt.NbtFormat
import cn.altawk.nbt.internal.Tokens.SINGLE_QUOTE
import cn.altawk.nbt.internal.Tokens.ESCAPE_MARKER
import cn.altawk.nbt.internal.Tokens.DOUBLE_QUOTE
import cn.altawk.nbt.internal.Tokens.PRETTY_PRINT_INDENT
import cn.altawk.nbt.internal.Tokens.PRETTY_PRINT_SPACE
import cn.altawk.nbt.tag.NbtType

/**
 * StringifiedNbtWriter
 *
 * @author TheFloodDragon
 * @since 2025/2/22 15:23
 */
internal class StringifiedNbtWriter(
    val nbt: NbtFormat,
    val builder: Appendable,
) : NbtWriter {
    private var firstEntry = false
    private var inArray = false
    private var level = 0

    private val prettySpace: String =
        if (nbt.configuration.prettyPrint) PRETTY_PRINT_SPACE else ""

    private fun Appendable.appendPrettyNewLine(): Appendable {
        if (nbt.configuration.prettyPrint) {
            builder.appendLine()
            repeat(level) { builder.append(PRETTY_PRINT_INDENT) }
        }
        return this
    }

    private fun beginCollection(prefix: String, array: Boolean) {
        builder.append(prefix)
        firstEntry = true
        inArray = array
        level++
    }

    private fun beginCollectionEntry() {
        if (!firstEntry) {
            if (inArray) {
                builder.append(",$prettySpace")
            } else {
                builder.append(',')
            }
        }

        if (!inArray) {
            builder.appendPrettyNewLine()
        }

        firstEntry = false
    }

    private fun endCollection(suffix: String, separateLine: Boolean) {
        level--
        if (separateLine) builder.appendPrettyNewLine()
        builder.append(suffix)

        firstEntry = false
        inArray = false
    }

    override fun beginRootTag(type: NbtType): Unit = Unit

    override fun beginCompound(): Unit = beginCollection("{", false)

    override fun beginCompoundEntry(type: NbtType, name: String) {
        beginCollectionEntry()
        builder.appendNbtString(name).append(":$prettySpace")
    }

    override fun endCompound(): Unit = endCollection("}", true)

    override fun beginList(type: NbtTagType, size: Int): Unit = beginCollection("[", false)
    override fun beginListEntry(): Unit = beginCollectionEntry()
    override fun endList(): Unit = endCollection("]", true)

    override fun beginByteArray(size: Int): Unit = beginCollection("[B;$prettySpace", true)
    override fun beginByteArrayEntry(): Unit = beginCollectionEntry()
    override fun endByteArray(): Unit = endCollection("]", false)

    override fun beginIntArray(size: Int): Unit = beginCollection("[I;$prettySpace", true)
    override fun beginIntArrayEntry(): Unit = beginCollectionEntry()
    override fun endIntArray(): Unit = endCollection("]", false)

    override fun beginLongArray(size: Int): Unit = beginCollection("[L;$prettySpace", true)
    override fun beginLongArrayEntry(): Unit = beginCollectionEntry()
    override fun endLongArray(): Unit = endCollection("]", false)

    override fun writeByte(value: Byte) {
        builder.append(value.toString()).append(if (inArray) 'B' else 'b')
    }

    override fun writeShort(value: Short) {
        builder.append(value.toString()).append('s')
    }

    override fun writeInt(value: Int) {
        builder.append(value.toString())
    }

    override fun writeLong(value: Long) {
        builder.append(value.toString()).append('L')
    }

    override fun writeFloat(value: Float) {
        builder.append(value.toString()).append('f')
    }

    override fun writeDouble(value: Double) {
        builder.append(value.toString()).append('d')
    }

    override fun writeString(value: String) {
        builder.appendNbtString(value, forceQuote = true)
    }

    companion object {

        private fun Appendable.appendQuoted(): Appendable = apply {
            append(DOUBLE_QUOTE)
            value.forEach {
                if (it == DOUBLE_QUOTE) append(ESCAPE_MARKER)
                append(it)
            }
            append(DOUBLE_QUOTE)
        }

        internal fun Appendable.appendNbtString(value: String, forceQuote: Boolean = false): Appendable {
            return when {
                forceQuote -> appendQuoted()
                value.isEmpty() -> append(DOUBLE_QUOTE).append(DOUBLE_QUOTE)
                value.all { Tokens.id(it) } -> append(value)
                !value.contains(DOUBLE_QUOTE) -> append(DOUBLE_QUOTE).append(value).append(DOUBLE_QUOTE)
                !value.contains(SINGLE_QUOTE) -> append(SINGLE_QUOTE).append(value).append(SINGLE_QUOTE)
                else -> appendQuoted()
            }
        }

    }

}