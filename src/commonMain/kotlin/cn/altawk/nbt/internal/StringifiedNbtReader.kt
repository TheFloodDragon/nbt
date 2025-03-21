package cn.altawk.nbt.internal

import cn.altawk.nbt.internal.NbtReader.Companion.EOF
import cn.altawk.nbt.internal.NbtReader.Companion.UNKNOWN_SIZE
import cn.altawk.nbt.internal.Tokens.ARRAY_BEGIN
import cn.altawk.nbt.internal.Tokens.ARRAY_END
import cn.altawk.nbt.internal.Tokens.ARRAY_SIGNATURE_SEPARATOR
import cn.altawk.nbt.internal.Tokens.COMPOUND_BEGIN
import cn.altawk.nbt.internal.Tokens.COMPOUND_END
import cn.altawk.nbt.internal.Tokens.COMPOUND_KEY_TERMINATOR
import cn.altawk.nbt.internal.Tokens.DOUBLE_QUOTE
import cn.altawk.nbt.internal.Tokens.ESCAPE_MARKER
import cn.altawk.nbt.internal.Tokens.LITERAL_FALSE
import cn.altawk.nbt.internal.Tokens.LITERAL_TRUE
import cn.altawk.nbt.internal.Tokens.SINGLE_QUOTE
import cn.altawk.nbt.internal.Tokens.TYPE_BYTE
import cn.altawk.nbt.internal.Tokens.TYPE_BYTE_ARRAY
import cn.altawk.nbt.internal.Tokens.TYPE_DOUBLE
import cn.altawk.nbt.internal.Tokens.TYPE_FLOAT
import cn.altawk.nbt.internal.Tokens.TYPE_INT_ARRAY
import cn.altawk.nbt.internal.Tokens.TYPE_LONG
import cn.altawk.nbt.internal.Tokens.TYPE_LONG_ARRAY
import cn.altawk.nbt.internal.Tokens.TYPE_SHORT
import cn.altawk.nbt.internal.Tokens.VALUE_SEPARATOR
import cn.altawk.nbt.tag.*

/**
 * StringifiedNbtReader
 * TODO sync with Minecraft NBT format
 *
 * @since 2025/3/15 11:13
 */
internal class StringifiedNbtReader(private val buffer: CharBuffer) : NbtReader {

    constructor(snbt: String) : this(CharBuffer(snbt))

    private var firstEntry = true

    override fun readTag(): NbtTag = when (buffer.skipWhitespace().peek()) {
        COMPOUND_BEGIN -> readCompoundTag()
        ARRAY_BEGIN -> {
            val breakpoint = buffer.breakpoint()
            buffer.advance()
            val possibleType = buffer.skipWhitespace().take()
            if (buffer.hasMore() && buffer.skipWhitespace().peek() == ARRAY_SIGNATURE_SEPARATOR) {
                buffer.reset(breakpoint)
                when (possibleType) {
                    TYPE_BYTE_ARRAY -> NbtByteArray(readByteArray())
                    TYPE_INT_ARRAY -> NbtIntArray(readIntArray())
                    TYPE_LONG_ARRAY -> NbtLongArray(readLongArray())
                    else -> buffer.makeError("Unknown error!")
                }
            } else {
                buffer.reset(breakpoint); readListTag()
            }
        }

        DOUBLE_QUOTE, SINGLE_QUOTE -> NbtString(buffer.bufferQuotedString())
        else -> {
            val content = buffer.readIdString()
            when (content.last()) {
                TYPE_BYTE -> NbtByte(byte(content))
                TYPE_SHORT -> NbtShort(short(content))
                TYPE_LONG -> NbtLong(long(content))
                TYPE_FLOAT -> NbtFloat(float(content))
                TYPE_DOUBLE -> NbtDouble(double(content))
                else -> when {
                    content.all { Tokens.numeric(it) } -> if ('.' in content) NbtDouble(double(content)) else NbtInt(int(content))
                    content.contentEquals(LITERAL_TRUE, true) -> NbtByte(1)
                    content.contentEquals(LITERAL_FALSE, true) -> NbtByte(0)
                    else -> NbtString(content)
                }
            }
        }
    }

    fun readCompoundTag(): NbtCompound {
        beginCompound()
        val compound = NbtCompound()
        var key = beginCompoundEntry()
        while (key != EOF) {
            compound[key] = readTag()
            key = beginCompoundEntry()
        }
        endCompound()
        return compound
    }

    fun readListTag(): NbtList {
        val size = beginList()
        val list = if (size == UNKNOWN_SIZE) {
            NbtList().apply { while (beginListEntry()) add(readTag()) }
        } else {
            NbtList(size).apply { repeat(size) { add(readTag()) } }
        }
        endList()
        return list
    }

    override fun beginCompound() {
        buffer.skipWhitespace().expect(COMPOUND_BEGIN)
        firstEntry = true
    }

    override fun beginCompoundEntry(): String {
        buffer.skipWhitespace()
        return if (buffer.peek() == COMPOUND_END) EOF else {
            if (firstEntry) {
                firstEntry = false
            } else {
                val char = buffer.take()
                if (char != VALUE_SEPARATOR) buffer.makeError("Expected ',' or '}', but got '$char'")
            }
            val key = readString()
            buffer.skipWhitespace().expect(COMPOUND_KEY_TERMINATOR)
            key
        }
    }

    override fun endCompound() {
        buffer.expect(COMPOUND_END)
    }

    private fun beginArray(type: Char): Int {
        buffer.skipWhitespace().expect(ARRAY_BEGIN)
        buffer.skipWhitespace().expect(type, true)
        buffer.skipWhitespace().expect(ARRAY_SIGNATURE_SEPARATOR)
        firstEntry = true
        return if (buffer.skipWhitespace().peek() == ARRAY_END) 0 else UNKNOWN_SIZE
    }

    private fun beginCollectionEntry(): Boolean {
        if (buffer.skipWhitespace().peek() == ARRAY_END) return false

        if (firstEntry) {
            firstEntry = false
        } else {
            val char = buffer.take()
            if (char != VALUE_SEPARATOR) buffer.makeError("Expected ',' or ']', but got '$char'")
        }
        return true
    }

    private fun endCollection() {
        buffer.skipWhitespace().expect(ARRAY_END)
    }

    override fun beginList(): Int {
        buffer.skipWhitespace().expect(ARRAY_BEGIN)
        buffer.skipWhitespace()
        firstEntry = true
        return if (buffer.peek() == ARRAY_END) 0 else UNKNOWN_SIZE
    }

    override fun beginListEntry() = beginCollectionEntry()

    override fun endList() = endCollection()

    override fun beginByteArray(): Int = beginArray(TYPE_BYTE_ARRAY)
    override fun beginByteArrayEntry() = beginCollectionEntry()
    override fun endByteArray() = endCollection()

    override fun beginIntArray() = beginArray(TYPE_INT_ARRAY)
    override fun beginIntArrayEntry() = beginCollectionEntry()
    override fun endIntArray() = endCollection()

    override fun beginLongArray() = beginArray(TYPE_LONG_ARRAY)
    override fun beginLongArrayEntry() = beginCollectionEntry()
    override fun endLongArray() = endCollection()


    override fun readByte() = byte(buffer.readIdString())

    private fun byte(content: String): Byte = when {
        content.contentEquals(LITERAL_TRUE, true) -> 1
        content.contentEquals(LITERAL_FALSE, true) -> 0
        else -> content.dropTypedLast(TYPE_BYTE).toByteOrNull() ?: buffer.makeError("Expected Byte, but was '$content'")
    }

    override fun readShort() = short(buffer.readNumericString())

    private fun short(content: String): Short = content.dropTypedLast(TYPE_SHORT).toShortOrNull() ?: buffer.makeError("Expected Short, but was '$content'")

    override fun readInt() = int(buffer.readNumericString())

    private fun int(content: String): Int = content.toIntOrNull() ?: buffer.makeError("Expected Int, but was '$content'")

    override fun readLong() = long(buffer.readNumericString())

    private fun long(content: String): Long = content.dropTypedLast(TYPE_LONG).toLongOrNull() ?: buffer.makeError("Expected Long, but was '$content'")

    override fun readFloat() = float(buffer.readNumericString())

    private fun float(content: String): Float = content.dropTypedLast(TYPE_FLOAT).toFloatOrNull() ?: buffer.makeError("Expected Float, but was '$content'")

    override fun readDouble() = double(buffer.readNumericString())

    private fun double(content: String): Double = content.dropTypedLast(TYPE_DOUBLE).toDoubleOrNull() ?: buffer.makeError("Expected Double, but was '$content'")

    override fun readString(): String {
        return when (buffer.skipWhitespace().peek()) {
            DOUBLE_QUOTE, SINGLE_QUOTE -> buffer.bufferQuotedString()
            else -> buffer.readIdString()
        }
    }

    fun CharBuffer.bufferQuotedString() = buildString {
        val quote = take()
        val backslash = ESCAPE_MARKER

        while (true) {
            when (val char = take()) {
                quote -> break
                backslash -> when (val esc = take()) {
                    quote, backslash -> append(esc)
                    else -> makeError("Invalid escape: \\$esc")
                }

                else -> append(char)
            }
        }
    }

    fun CharBuffer.readNumericString() = buildString { while (true) append(this@readNumericString.takeWhen { Tokens.numeric(it) } ?: break) }

    fun CharBuffer.readIdString() = buildString { while (true) append(this@readIdString.takeWhen { Tokens.id(it) } ?: break) }

    private fun String.dropTypedLast(type: Char) = if (this.last().equals(type, true)) this.dropLast(1) else this

}