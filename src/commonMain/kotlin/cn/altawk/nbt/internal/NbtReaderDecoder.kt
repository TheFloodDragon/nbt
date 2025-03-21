@file:OptIn(ExperimentalSerializationApi::class)

package cn.altawk.nbt.internal

import cn.altawk.nbt.NbtFormat
import cn.altawk.nbt.exception.NbtDecodingException
import cn.altawk.nbt.tag.NbtTag
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.encoding.CompositeDecoder

/**
 * NbtReaderDecoder
 *
 * @author TheFloodDragon
 * @since 2025/3/15 11:05
 */
internal open class NbtReaderDecoder(
    override val nbt: NbtFormat,
    val reader: NbtReader
) : AbstractNbtDecoder() {

    override fun beginStructure(descriptor: SerialDescriptor) = when (descriptor.kind as StructureKind) {
        StructureKind.CLASS, StructureKind.OBJECT -> ClassDecoder(nbt, reader)
        StructureKind.MAP -> MapDecoder(nbt, reader)
        StructureKind.LIST -> ListDecoder(nbt, reader)
    }

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int = 0

    override fun decodeBoolean() =
        when (val byte = reader.readByte()) {
            0.toByte() -> false
            1.toByte() -> true
            else -> throw NbtDecodingException("Expected a byte(0 or 1) to be a Boolean, but was $byte")
        }

    override fun decodeNbtTag(): NbtTag = reader.readTag()
    override fun decodeChar() = reader.readString().first()
    override fun decodeString() = reader.readString()
    override fun decodeByte() = reader.readByte()
    override fun decodeDouble() = reader.readDouble()
    override fun decodeFloat() = reader.readFloat()
    override fun decodeInt() = reader.readInt()
    override fun decodeLong() = reader.readLong()
    override fun decodeShort() = reader.readShort()

    override fun decodeByteArray() = reader.readByteArray()
    override fun decodeIntArray() = reader.readIntArray()
    override fun decodeLongArray() = reader.readLongArray()

    private class ClassDecoder(
        nbt: NbtFormat,
        reader: NbtReader
    ) : NbtReaderDecoder(nbt, reader) {
        var forceNull: Boolean = false
        var postion: Int = 0

        init {
            reader.beginCompound()
        }

        private fun SerialDescriptor.keyName(): String {
            return nbt.configuration.nameDeterminer.mapName(reader.beginCompoundEntry(), this)
        }

        override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
            if (++postion >= descriptor.elementsCount) return CompositeDecoder.DECODE_DONE

            var entryKey: String
            var index: Int
            forceNull = false

            do {
                entryKey = descriptor.keyName()
                index = descriptor.getElementIndex(entryKey)

                // The end of elements decoding
                if (index == CompositeDecoder.UNKNOWN_NAME && entryKey == NbtReader.EOF) {
                    println(postion)
                    println(descriptor.getElementDescriptor(postion))
                    return if (!nbt.configuration.explicitNulls
                        && !descriptor.isElementOptional(postion)
                        && descriptor.getElementDescriptor(postion).isNullable
                    ) {
                        forceNull = true
                        postion
                    } else CompositeDecoder.DECODE_DONE
                }
            } while (index == CompositeDecoder.UNKNOWN_NAME)

            return index
        }

        override fun decodeNotNullMark() = !forceNull && super.decodeNotNullMark()

        override fun endStructure(descriptor: SerialDescriptor) = reader.endCompound()
    }

    private class MapDecoder(nbt: NbtFormat, reader: NbtReader) : NbtReaderDecoder(nbt, reader) {
        private var index = 0
        private var decodingMapKey: Boolean = false
        private lateinit var entryKey: String

        init {
            reader.beginCompound()
        }

        override fun decodeElementIndex(descriptor: SerialDescriptor): Int =
            if (index % 2 == 0) {
                entryKey = reader.beginCompoundEntry()
                if (entryKey == NbtReader.EOF) {
                    CompositeDecoder.DECODE_DONE
                } else {
                    decodingMapKey = true
                    index++
                }
            } else {
                index++
            }

        override fun decodeString(): String =
            if (decodingMapKey) {
                decodingMapKey = false
                entryKey
            } else super.decodeString()

        override fun endStructure(descriptor: SerialDescriptor) = reader.endCompound()
    }

    private class ListDecoder(nbt: NbtFormat, reader: NbtReader) : NbtReaderDecoder(nbt, reader) {
        init {
            reader.beginList()
        }

        private var index = -1

        override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
            if (reader.beginListEntry()) {
                index++
                return index
            } else return CompositeDecoder.DECODE_DONE
        }

        override fun endStructure(descriptor: SerialDescriptor): Unit = reader.endList()
    }

}