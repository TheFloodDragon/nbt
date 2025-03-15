package cn.altawk.nbt.internal

import cn.altawk.nbt.NbtFormat
import cn.altawk.nbt.exception.NbtDecodingException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder

/**
 * NbtReaderDecoder
 *
 * @author TheFloodDragon
 * @since 2025/3/15 11:05
 */
internal class NbtReaderDecoder(
    override val nbt: NbtFormat,
    val reader: NbtReader
) : AbstractNbtDecoder() {

    override fun beginCompound(descriptor: SerialDescriptor): CompositeDecoder {
        TODO("Not yet implemented")
    }

    override fun beginList(descriptor: SerialDescriptor): CompositeDecoder {
        TODO("Not yet implemented")
    }

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        TODO("Not yet implemented")
    }

    override fun decodeEnum(enumDescriptor: SerialDescriptor): Int {
        return super.decodeEnum(enumDescriptor)
    }

    override fun decodeBoolean() =
        when (val byte = reader.readByte()) {
            0.toByte() -> false
            1.toByte() -> true
            else -> throw NbtDecodingException("Expected TAG_Byte to be a Boolean (0 or 1), but was $byte")
        }

    override fun decodeChar() = reader.readString().first()

    override fun decodeByte() = reader.readByte()
    override fun decodeDouble() = reader.readDouble()
    override fun decodeFloat() = reader.readFloat()
    override fun decodeInt() = reader.readInt()
    override fun decodeLong() = reader.readLong()
    override fun decodeShort() = reader.readShort()

//    override fun decodeByteArray() = reader.readByteArray()
//    override fun decodeIntArray() = reader.readIntArray()
//    override fun decodeLongArray() = reader.readLongArray()

}