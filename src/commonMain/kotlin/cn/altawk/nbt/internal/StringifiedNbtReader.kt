//package cn.altawk.nbt.internal
//
//import cn.altawk.nbt.internal.Tokens.COMPOUND_BEGIN
//import cn.altawk.nbt.internal.Tokens.COMPOUND_END
//import cn.altawk.nbt.internal.Tokens.COMPOUND_KEY_TERMINATOR
//import cn.altawk.nbt.internal.Tokens.EOF
//import cn.altawk.nbt.internal.Tokens.UNKNOWN_SIZE
//import cn.altawk.nbt.internal.Tokens.VALUE_SEPARATOR
//
///**
// * StringifiedNbtReader
// *
// * @author TheFloodDragon
// * @since 2025/3/15 11:13
// */
//internal class StringifiedNbtReader(private val buffer: CharBuffer) : NbtReader {
//    private var firstEntry = true
//
//    override fun beginCompound() {
//        buffer.skipWhitespace().expect(COMPOUND_BEGIN)
//        firstEntry = true
//    }
//
//    override fun beginCompoundEntry(): CharSequence {
//        buffer.skipWhitespace()
//
//        return if (buffer.peek() == COMPOUND_END) EOF else {
//            if (firstEntry) {
//                firstEntry = false
//            } else {
//                val char = buffer.take()
//                if (char != VALUE_SEPARATOR) throw buffer.makeError("Expected ',' or '}', but got '$char'")
//            }
//
//            return buffer.skipWhitespace().takeUntil(COMPOUND_KEY_TERMINATOR)
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
//
//        firstEntry = true
//
//        return if (buffer.skipWhitespace().peek() == ']') 0 else UNKNOWN_SIZE
//    }
//
//    private fun beginCollectionEntry(): Boolean {
//        buffer.skipWhitespace()
//
//        return if (buffer.peek() == ']') {
//            false
//        } else {
//            if (firstEntry) {
//                firstEntry = false
//            } else {
//                val char = buffer.take()
//                if (char != ',') throw buffer.makeError("Expected ',' or ']', but got '$char'")
//            }
//            true
//        }
//    }
//
//    private fun endCollection() {
//        buffer.skipWhitespace().expect(']')
//    }
//
//    override fun beginList() {
//        buffer.skipWhitespace().expect('[')
//        buffer.skipWhitespace()
//
//        firstEntry = true
//
//        val type = source.peekTagType() ?: TAG_End
//        val size = if (type == TAG_End) 0 else NbtReader.UNKNOWN_SIZE
//
//        return NbtReader.ListInfo(type, size)
//    }
//
//    override fun beginListEntry(): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun endList() {
//        TODO("Not yet implemented")
//    }
//
//    override fun beginByteArray() {
//        TODO("Not yet implemented")
//    }
//
//    override fun beginByteArrayEntry(): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun endByteArray() {
//        TODO("Not yet implemented")
//    }
//
//    override fun beginIntArray() {
//        TODO("Not yet implemented")
//    }
//
//    override fun beginIntArrayEntry(): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun endIntArray() {
//        TODO("Not yet implemented")
//    }
//
//    override fun beginLongArray() {
//        TODO("Not yet implemented")
//    }
//
//    override fun beginLongArrayEntry(): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun endLongArray() {
//        TODO("Not yet implemented")
//    }
//
//    override fun readByte(): Byte {
//        TODO("Not yet implemented")
//    }
//
//    override fun readShort(): Short {
//        TODO("Not yet implemented")
//    }
//
//    override fun readInt(): Int {
//        TODO("Not yet implemented")
//    }
//
//    override fun readLong(): Long {
//        TODO("Not yet implemented")
//    }
//
//    override fun readFloat(): Float {
//        TODO("Not yet implemented")
//    }
//
//    override fun readDouble(): Double {
//        TODO("Not yet implemented")
//    }
//
//    override fun readString(): String {
//        TODO("Not yet implemented")
//    }
//
//
//}