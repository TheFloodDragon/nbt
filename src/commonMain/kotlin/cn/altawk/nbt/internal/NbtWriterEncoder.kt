@file:OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)

package cn.altawk.nbt.internal

import cn.altawk.nbt.NbtEncoder
import cn.altawk.nbt.NbtFormat
import cn.altawk.nbt.tag.NbtTag
import cn.altawk.nbt.tag.NbtType
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.internal.NamedValueEncoder
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
) : NamedValueEncoder(), NbtEncoder {

    override val serializersModule: SerializersModule
        get() = nbt.serializersModule

    override fun elementName(descriptor: SerialDescriptor, index: Int): String {
        return nbt.configuration.nameDeterminer.determineName(descriptor, index)
    }

    override fun shouldEncodeElementDefault(descriptor: SerialDescriptor, index: Int): Boolean {
        return nbt.configuration.encodeDefaults
    }

    override fun composeName(parentName: String, childName: String): String = childName

    override fun encodeTaggedInt(tag: String, value: Int) {
        writer.beginCompoundEntry(NbtType.INT, tag)
        writer.writeInt(value)
    }

//    override fun encodeTaggedByte(tag: String, value: Byte) = putElement(tag, NbtByte(value))
//    override fun encodeTaggedShort(tag: String, value: Short) = putElement(tag, NbtShort(value))
//    override fun encodeTaggedLong(tag: String, value: Long) = putElement(tag, NbtLong(value))
//    override fun encodeTaggedFloat(tag: String, value: Float) = putElement(tag, NbtFloat(value))
//    override fun encodeTaggedDouble(tag: String, value: Double) = putElement(tag, NbtDouble(value))
//    override fun encodeTaggedBoolean(tag: String, value: Boolean) = putElement(tag, NbtByte(value))
//    override fun encodeTaggedChar(tag: String, value: Char) = putElement(tag, NbtString(value.toString()))
//    override fun encodeTaggedString(tag: String, value: String) = putElement(tag, NbtString(value))
//    override fun encodeTaggedEnum(tag: String, enumDescriptor: SerialDescriptor, ordinal: Int) =
//        putElement(tag, NbtString(enumDescriptor.getElementName(ordinal)))
//
//    override fun encodeTaggedValue(tag: String, value: Any) =
//        putElement(tag, NbtString(value.toString()))

    override fun encodeNbtTag(tag: NbtTag) {
        TODO("Not yet implemented")
    }

}
