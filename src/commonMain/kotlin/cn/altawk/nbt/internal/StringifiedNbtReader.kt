//package cn.altawk.nbt.internal
//
//import cn.altawk.nbt.internal.NbtReader.Companion.EOF
//import cn.altawk.nbt.internal.NbtReader.Companion.UNKNOWN_SIZE
//import cn.altawk.nbt.internal.Tokens.ARRAY_BEGIN
//import cn.altawk.nbt.internal.Tokens.ARRAY_END
//import cn.altawk.nbt.internal.Tokens.COMPOUND_BEGIN
//import cn.altawk.nbt.internal.Tokens.COMPOUND_END
//import cn.altawk.nbt.internal.Tokens.COMPOUND_KEY_TERMINATOR
//import cn.altawk.nbt.internal.Tokens.TYPE_BYTE_ARRAY
//import cn.altawk.nbt.internal.Tokens.TYPE_INT_ARRAY
//import cn.altawk.nbt.internal.Tokens.TYPE_LONG_ARRAY
//import cn.altawk.nbt.internal.Tokens.VALUE_SEPARATOR
//import cn.altawk.nbt.tag.*
//import java.nio.CharBuffer
//
///**
// * StringifiedNbtReader
// *
// * @since 2025/3/15 11:13
// */
//internal class StringifiedNbtReader(private val buffer: CharBuffer) : NbtReader {
//    private var firstEntry = true
//
//    override fun readTag(): NbtTag {
//        return readTag(buffer) ?: throw NbtDecodingException("Unexpected end of input")
//    }
//
//    private fun readTag(buf: CharBuffer): NbtTag? {
//        when (val peek = buf.take()) {
//            EOF.first() -> return null
//            '[' -> when (buf.skipWhitespace().take()) {
//                'B' -> if (buf.skipWhitespace().take() == ';') {
//                    return NbtByteArray(buf.takeUntil(',').toByteArray())
//                }
//
//                'I' -> if (buf.skipWhitespace().take() == ';') NbtIntArray(buf.takeUntil(',').toIntArray()) else NbtList()
//                'L' -> if (buf.skipWhitespace().take() == ';') NbtLongArray(buf.takeUntil(',').toLongArray()) else NbtList()
//                ']' -> NbtList()
//                else -> NbtList()
//            }
//
//            '{' -> return NbtCompound().apply { readCompoundEntries(this) }
//            '\'', '"' -> return NbtString(buf.takeUntil(peek))
//            else -> {
//                buf.bufferUnquotedString()
//                return when {
//                    buf.isEmpty() -> null
//                    FLOAT_A.matches(buf) -> NbtFloat(buf.toString().toFloat())
//                    FLOAT_B.matches(buf) -> NbtFloat(buf.toString().toFloat())
//                    BYTE.matches(buf) -> NbtByte(buf.toString().toByte())
//                    LONG.matches(buf) -> NbtLong(buf.toString().toLong())
//                    SHORT.matches(buf) -> NbtShort(buf.toString().toShort())
//                    INT.matches(buf) -> NbtInt(buf.toString().toInt())
//                    DOUBLE_A.matches(buf) -> NbtDouble(buf.toString().toDouble())
//                    DOUBLE_B.matches(buf) -> NbtDouble(buf.toString().toDouble())
//                    "true".contentEquals(buf, true) -> NbtByte(1)
//                    "false".contentEquals(buf, true) -> NbtByte(0)
//                    else -> NbtString(buf.toString())
//                }
//            }
//        }
//    }
//
//    private fun readCompoundEntries(compound: NbtCompound) {
//        beginCompound()
//        while (true) {
//            val key = beginCompoundEntry()
//            if (key == EOF) break
//            val value = readTag(buffer) ?: throw NbtDecodingException("Unexpected end of input")
//            compound.put(key, value)
//        }
//        endCompound()
//    }
//
//    override fun beginCompound() {
//        buffer.skipWhitespace().expect(COMPOUND_BEGIN)
//        firstEntry = true
//    }
//
//    override fun beginCompoundEntry(): String {
//        buffer.skipWhitespace()
//        return if (buffer.peek() == COMPOUND_END) EOF else {
//            if (firstEntry) {
//                firstEntry = false
//            } else {
//                val char = buffer.take()
//                if (char != VALUE_SEPARATOR) throw buffer.makeError("Expected ',' or '}', but got '$char'")
//            }
//            buffer.skipWhitespace().takeUntil(COMPOUND_KEY_TERMINATOR)
//        }
//    }
//
//    override fun endCompound() {
//        buffer.expect(COMPOUND_END)
//    }
//
//    private fun beginArray(type: Char): Int {
//        buffer.skipWhitespace().expect('[')
//        buffer.skipWhitespace().expect(type, true)
//        buffer.skipWhitespace().expect(';')
//        firstEntry = true
//        return if (buffer.skipWhitespace().peek() == ']') 0 else UNKNOWN_SIZE
//    }
//
//    private fun beginCollectionEntry(): Boolean {
//        val isEnd = buffer.skipWhitespace().peek() == ARRAY_END
//        if (firstEntry) {
//            firstEntry = false
//        } else {
//            val char = buffer.take()
//            if (char != VALUE_SEPARATOR) throw buffer.makeError("Expected ',' or ']', but got '$char'")
//        }
//        return isEnd
//    }
//
//    private fun endCollection() {
//        buffer.skipWhitespace().expect(ARRAY_END)
//    }
//
//    override fun beginList(): Int {
//        buffer.skipWhitespace().expect(ARRAY_BEGIN)
//        buffer.skipWhitespace()
//        firstEntry = true
//        return if (buffer.peek() == EOF) 0 else NbtReader.UNKNOWN_SIZE
//    }
//
//    override fun beginListEntry() = beginCollectionEntry()
//
//    override fun endList() = endCollection()
//
//    override fun beginByteArray(): Int = beginArray(TYPE_BYTE_ARRAY)
//    override fun beginByteArrayEntry() = beginCollectionEntry()
//    override fun endByteArray() = endCollection()
//
//    override fun beginIntArray() = beginArray(TYPE_INT_ARRAY)
//    override fun beginIntArrayEntry() = beginCollectionEntry()
//    override fun endIntArray() = endCollection()
//
//    override fun beginLongArray() = beginArray(TYPE_LONG_ARRAY)
//    override fun beginLongArrayEntry() = beginCollectionEntry()
//    override fun endLongArray() = endCollection()
//
//    override fun readByte(): Byte {
//        buffer.skipWhitespace().bufferUnquotedString()
//        return when {
//            buffer.contentEquals("true", true) -> 1
//            buffer.contentEquals("false", true) -> 0
//            else -> {
//                if (!BYTE.matches(buffer)) throw NbtDecodingException("Expected Byte, but was '$buffer'")
//                buffer.setLength(buffer.length - 1)
//                buffer.toString().toByte()
//            }
//        }
//    }
//
//    override fun readShort(): Short {
//        buffer.skipWhitespace().bufferUnquotedString()
//        if (!SHORT.matches(buffer)) throw NbtDecodingException("Expected Short, but was '$buffer'")
//        buffer.setLength(buffer.length - 1)
//        return buffer.toString().toShort()
//    }
//
//    override fun readInt(): Int {
//        buffer.skipWhitespace().bufferUnquotedString()
//        if (!INT.matches(buffer)) throw NbtDecodingException("Expected Int, but was '$buffer'")
//        buffer.setLength(buffer.length - 1)
//        return buffer.toString().toInt()
//    }
//
//    override fun readLong(): Long {
//        buffer.skipWhitespace().bufferUnquotedString()
//        if (!LONG.matches(buffer)) throw NbtDecodingException("Expected Long, but was '$buffer'")
//        buffer.setLength(buffer.length - 1)
//        return buffer.toString().toLong()
//    }
//
//    override fun readFloat(): Float {
//        buffer.skipWhitespace().bufferUnquotedString()
//        if (!FLOAT_A.matches(buffer) && !FLOAT_B.matches(buffer)) throw NbtDecodingException("Expected Float, but was '$buffer'")
//        buffer.setLength(buffer.length - 1)
//        return buffer.toString().toFloat()
//    }
//
//    override fun readDouble(): Double {
//        buffer.skipWhitespace().bufferUnquotedString()
//        if (!DOUBLE_A.matches(buffer) && !DOUBLE_B.matches(buffer)) throw NbtDecodingException("Expected Double, but was '$buffer'")
//        buffer.setLength(buffer.length - 1)
//        return buffer.toString().toDouble()
//    }
//
//    override fun readString(): String {
//        buffer.skipWhitespace()
//        val quote = buffer.take()
//        if (quote != '\'' && quote != '"') throw NbtDecodingException("Expected String, but was '$buffer'")
//        return buffer.takeUntil(quote)
//    }
//}