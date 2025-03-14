@file:OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)

package cn.altawk.nbt.internal

import cn.altawk.nbt.NbtFormat
import cn.altawk.nbt.tag.*
import cn.altawk.nbt.tag.NbtType.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.modules.SerializersModule

/**
 * NbtWriterEncoder
 *
 * @author TheFloodDragon
 * @since 2025/1/25 11:25
 */
internal class NbtWriterEncoder(
    override val nbt: NbtFormat,
    private val writer: NbtWriter,
) : AbstractNbtEncoder() {

    private lateinit var elementName: String
    private var encodingMapKey: Boolean = false

    private val structureTypeStack = ArrayDeque<NbtType>()

    override val serializersModule: SerializersModule get() = nbt.serializersModule

    override fun encodeSerializableElement(descriptor: SerialDescriptor, index: Int): Boolean {
        when (descriptor.kind as StructureKind) {
            StructureKind.CLASS, StructureKind.OBJECT -> {
                elementName = nbt.configuration.nameDeterminer.determineName(descriptor, index)
            }

            StructureKind.MAP -> {
                println(descriptor)
                if (index % 2 == 0) encodingMapKey = true
            }

            else -> Unit
        }
        return true
    }

    override fun encodeElement(descriptor: SerialDescriptor, index: Int): Boolean {
        when (val structureType = structureTypeStack.last()) {
            COMPOUND -> if (!encodingMapKey) writer.beginCompoundEntry(elementName)
            LIST -> writer.beginListEntry()
            BYTE_ARRAY -> writer.beginByteArrayEntry()
            INT_ARRAY -> writer.beginIntArrayEntry()
            LONG_ARRAY -> writer.beginLongArrayEntry()
            else -> error("Unhandled structure type: $structureType")
        }
        return true
    }

    override fun beginCompound(descriptor: SerialDescriptor): CompositeEncoder {
        writer.beginCompound()
        structureTypeStack += COMPOUND
        return this
    }

    override fun beginList(descriptor: SerialDescriptor, size: Int): CompositeEncoder {
        writer.beginList(size)
        structureTypeStack += LIST
        return this
    }

    override fun endStructure(descriptor: SerialDescriptor) {
        when (val structureType = structureTypeStack.removeLast()) {
            COMPOUND -> writer.endCompound()
            LIST -> writer.endList()
            BYTE_ARRAY -> writer.endByteArray()
            INT_ARRAY -> writer.endIntArray()
            LONG_ARRAY -> writer.endLongArray()
            else -> error("Unhandled structure type: $structureType")
        }
    }

    override fun encodeString(value: String) {
        if (encodingMapKey) {
            elementName = value
            encodingMapKey = false
        } else writer.writeString(value)
    }

    override fun encodeByte(value: Byte) = writer.writeByte(value)
    override fun encodeBoolean(value: Boolean) = writer.writeByte(if (value) 1 else 0)
    override fun encodeChar(value: Char) = writer.writeString(value.toString())
    override fun encodeDouble(value: Double) = writer.writeDouble(value)
    override fun encodeFloat(value: Float) = writer.writeFloat(value)
    override fun encodeInt(value: Int) = writer.writeInt(value)
    override fun encodeLong(value: Long) = writer.writeLong(value)
    override fun encodeShort(value: Short) = writer.writeShort(value)

    override fun encodeByteArray(value: ByteArray) = writer.writeByteArray(value)
    override fun encodeIntArray(value: IntArray) = writer.writeIntArray(value)
    override fun encodeLongArray(value: LongArray) = writer.writeLongArray(value)

    override fun encodeNbtTag(tag: NbtTag) {
        when (tag.type) {
            BYTE -> encodeByte((tag as NbtByte).content)
            SHORT -> encodeShort((tag as NbtShort).content)
            INT -> encodeInt((tag as NbtInt).content)
            LONG -> encodeLong((tag as NbtLong).content)
            FLOAT -> encodeFloat((tag as NbtFloat).content)
            DOUBLE -> encodeDouble((tag as NbtDouble).content)
            STRING -> encodeString((tag as NbtString).content)
            BYTE_ARRAY -> encodeByteArray((tag as NbtByteArray).content)
            INT_ARRAY -> encodeIntArray((tag as NbtIntArray).content)
            LONG_ARRAY -> encodeLongArray((tag as NbtLongArray).content)
            LIST -> {
                writer.beginList((tag as NbtList).size)
                for (element in tag) {
                    encodeNbtTag(element)
                }
                writer.endList()
            }

            COMPOUND -> {
                writer.beginCompound()
                for ((name, value) in tag as NbtCompound) {
                    writer.beginCompoundEntry(name)
                    encodeNbtTag(value)
                }
                writer.endCompound()
            }

            END -> Unit
        }
    }

}
