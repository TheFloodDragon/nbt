//package cn.altawk.nbt.internal
//
//import cn.altawk.nbt.tag.NbtByte
//import cn.altawk.nbt.tag.NbtTag
//import cn.altawk.nbt.tag.NbtType
//
///**
// * TreeNbtWriter
// *
// * @author TheFloodDragon
// * @since 2025/3/1 16:24
// */
//internal class TreeNbtWriter : NbtWriter {
//
//    private var writer: NbtWriter = this
//
//    override fun consumeTag(tag: NbtTag) = Unit
//
//    override fun beginCompound() {
//        TODO("Not yet implemented")
//    }
//
//    override fun beginCompoundEntry(type: NbtType, name: String) {
//        TODO("Not yet implemented")
//    }
//
//    override fun endCompound() {
//        TODO("Not yet implemented")
//    }
//
//    override fun beginList(type: NbtType, size: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override fun beginListEntry() {
//        TODO("Not yet implemented")
//    }
//
//    override fun endList() {
//        TODO("Not yet implemented")
//    }
//
//    override fun beginByteArray(size: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override fun beginByteArrayEntry() {
//        TODO("Not yet implemented")
//    }
//
//    override fun endByteArray() {
//        TODO("Not yet implemented")
//    }
//
//    override fun beginIntArray(size: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override fun beginIntArrayEntry() {
//        TODO("Not yet implemented")
//    }
//
//    override fun endIntArray() {
//        TODO("Not yet implemented")
//    }
//
//    override fun beginLongArray(size: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override fun beginLongArrayEntry() {
//        TODO("Not yet implemented")
//    }
//
//    override fun endLongArray() {
//        TODO("Not yet implemented")
//    }
//
//    override fun writeByte(value: Byte) = consumeTag(NbtByte(value))
//
//    override fun writeShort(value: Short) {
//        TODO("Not yet implemented")
//    }
//
//    override fun writeInt(value: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override fun writeLong(value: Long) {
//        TODO("Not yet implemented")
//    }
//
//    override fun writeFloat(value: Float) {
//        TODO("Not yet implemented")
//    }
//
//    override fun writeDouble(value: Double) {
//        TODO("Not yet implemented")
//    }
//
//    override fun writeString(value: String) {
//        TODO("Not yet implemented")
//    }
//
//}