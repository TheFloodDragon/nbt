package cn.altawk.nbt.internal

import cn.altawk.nbt.NbtFormat
import cn.altawk.nbt.internal.Tokens.ARRAY_BEGIN
import cn.altawk.nbt.internal.Tokens.ARRAY_END
import cn.altawk.nbt.internal.Tokens.ARRAY_SIGNATURE_SEPARATOR
import cn.altawk.nbt.internal.Tokens.COMPOUND_BEGIN
import cn.altawk.nbt.internal.Tokens.COMPOUND_END
import cn.altawk.nbt.internal.Tokens.COMPOUND_KEY_TERMINATOR
import cn.altawk.nbt.internal.Tokens.DOUBLE_QUOTE
import cn.altawk.nbt.internal.Tokens.ESCAPE_MARKER
import cn.altawk.nbt.internal.Tokens.PRETTY_PRINT_INDENT
import cn.altawk.nbt.internal.Tokens.PRETTY_PRINT_SPACE
import cn.altawk.nbt.internal.Tokens.SINGLE_QUOTE
import cn.altawk.nbt.internal.Tokens.TYPE_BYTE
import cn.altawk.nbt.internal.Tokens.TYPE_BYTE_ARRAY
import cn.altawk.nbt.internal.Tokens.TYPE_DOUBLE
import cn.altawk.nbt.internal.Tokens.TYPE_FLOAT
import cn.altawk.nbt.internal.Tokens.TYPE_INT_ARRAY
import cn.altawk.nbt.internal.Tokens.TYPE_LONG
import cn.altawk.nbt.internal.Tokens.TYPE_SHORT
import cn.altawk.nbt.internal.Tokens.VALUE_SEPARATOR
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
            builder.append(VALUE_SEPARATOR)
            if (inArray) builder.append(prettySpace)
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

    override fun beginCompound(): Unit = beginCollection(COMPOUND_BEGIN, false)

    override fun beginCompoundEntry(type: NbtType, name: String) {
        beginCollectionEntry()
        builder.appendNbtString(name).append(COMPOUND_KEY_TERMINATOR + prettySpace)
    }

    override fun endCompound(): Unit = endCollection(COMPOUND_END, true)

    override fun beginList(type: NbtType, size: Int): Unit = beginCollection(ARRAY_BEGIN, false)
    override fun beginListEntry(): Unit = beginCollectionEntry()
    override fun endList(): Unit = endCollection(ARRAY_END, true)

    override fun beginByteArray(size: Int): Unit =
        beginCollection(ARRAY_BEGIN + TYPE_BYTE_ARRAY + ARRAY_SIGNATURE_SEPARATOR + prettySpace, true)

    override fun beginByteArrayEntry(): Unit = beginCollectionEntry()
    override fun endByteArray(): Unit = endCollection(ARRAY_END, false)

    override fun beginIntArray(size: Int): Unit =
        beginCollection(ARRAY_BEGIN + TYPE_INT_ARRAY + ARRAY_SIGNATURE_SEPARATOR + prettySpace, true)

    override fun beginIntArrayEntry(): Unit = beginCollectionEntry()
    override fun endIntArray(): Unit = endCollection(ARRAY_END, false)

    override fun beginLongArray(size: Int): Unit =
        beginCollection(ARRAY_BEGIN + TYPE_LONG + ARRAY_SIGNATURE_SEPARATOR + prettySpace, true)

    override fun beginLongArrayEntry(): Unit = beginCollectionEntry()
    override fun endLongArray(): Unit = endCollection(ARRAY_END, false)

    override fun writeByte(value: Byte) {
        builder.append(value.toString()).append(if (inArray) TYPE_BYTE_ARRAY else TYPE_BYTE)
    }

    override fun writeShort(value: Short) {
        builder.append(value.toString()).append(TYPE_SHORT)
    }

    override fun writeInt(value: Int) {
        builder.append(value.toString())
    }

    override fun writeLong(value: Long) {
        builder.append(value.toString()).append(TYPE_LONG)
    }

    override fun writeFloat(value: Float) {
        builder.append(value.toString()).append(TYPE_FLOAT)
    }

    override fun writeDouble(value: Double) {
        builder.append(value.toString()).append(TYPE_DOUBLE)
    }

    override fun writeString(value: String) {
        builder.appendNbtString(value, forceQuote = true)
    }

}

private fun Appendable.appendQuoted(value: String): Appendable = apply {
    append(DOUBLE_QUOTE)
    value.forEach {
        if (it == DOUBLE_QUOTE) append(ESCAPE_MARKER)
        append(it)
    }
    append(DOUBLE_QUOTE)
}

internal fun Appendable.appendNbtString(value: String, forceQuote: Boolean = false): Appendable {
    return when {
        forceQuote -> appendQuoted(value)
        value.isEmpty() -> append(DOUBLE_QUOTE).append(DOUBLE_QUOTE)
        value.all { Tokens.id(it) } -> append(value)
        !value.contains(DOUBLE_QUOTE) -> append(DOUBLE_QUOTE).append(value).append(DOUBLE_QUOTE)
        !value.contains(SINGLE_QUOTE) -> append(SINGLE_QUOTE).append(value).append(SINGLE_QUOTE)
        else -> appendQuoted(value)
    }
}