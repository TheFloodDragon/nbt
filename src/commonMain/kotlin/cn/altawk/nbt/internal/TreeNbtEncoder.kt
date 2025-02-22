@file:OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)

package cn.altawk.nbt.internal

import cn.altawk.nbt.NbtEncoder
import cn.altawk.nbt.NbtFormat
import cn.altawk.nbt.tag.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.encoding.AbstractEncoder
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.internal.NamedValueEncoder
import kotlinx.serialization.modules.SerializersModule

/**
 * TreeNbtEncoder
 *
 * @author TheFloodDragon
 * @since 2025/2/22 14:04
 */
private sealed class AbstractNbtTreeEncoder(
    final override val nbt: NbtFormat,
    protected val nodeConsumer: (NbtTag) -> Unit
) : NamedValueEncoder(), NbtEncoder {

    final override val serializersModule: SerializersModule get() = nbt.serializersModule

    @JvmField
    protected val configuration = nbt.configuration

    override fun elementName(descriptor: SerialDescriptor, index: Int): String {
        return configuration.nameDeterminer.determineName(descriptor, index)
    }

    override fun encodeNbtTag(tag: NbtTag) {
        if (polymorphicDiscriminator != null && element !is JsonObject) {
            throwNbtTagPolymorphicException(polymorphicSerialName, element)
        }
        encodeSerializableValue(NbtTagSerializer, element)
    }

    override fun shouldEncodeElementDefault(descriptor: SerialDescriptor, index: Int): Boolean =
        configuration.encodeDefaults

    override fun composeName(parentName: String, childName: String): String = childName

    abstract fun putElement(key: String, element: NbtTag)
    abstract fun getCurrent(): NbtTag

    override fun encodeTaggedInt(tag: String, value: Int) = putElement(tag, NbtInt(value))
    override fun encodeTaggedByte(tag: String, value: Byte) = putElement(tag, NbtByte(value))
    override fun encodeTaggedShort(tag: String, value: Short) = putElement(tag, NbtShort(value))
    override fun encodeTaggedLong(tag: String, value: Long) = putElement(tag, NbtLong(value))
    override fun encodeTaggedFloat(tag: String, value: Float) = putElement(tag, NbtFloat(value))
    override fun encodeTaggedDouble(tag: String, value: Double) = putElement(tag, NbtDouble(value))
    override fun encodeTaggedBoolean(tag: String, value: Boolean) = putElement(tag, NbtByte(value))
    override fun encodeTaggedChar(tag: String, value: Char) = putElement(tag, NbtString(value.toString()))
    override fun encodeTaggedString(tag: String, value: String) = putElement(tag, NbtString(value))
    override fun encodeTaggedEnum(tag: String, enumDescriptor: SerialDescriptor, ordinal: Int) =
        putElement(tag, NbtString(enumDescriptor.getElementName(ordinal)))
    override fun encodeTaggedValue(tag: String, value: Any) =
        putElement(tag, NbtString(value.toString()))

    override fun <T> encodeSerializableValue(serializer: SerializationStrategy<T>, value: T) {
        super<NamedValueEncoder>.encodeSerializableValue(serializer, value)
    }

    override fun beginStructure(descriptor: SerialDescriptor): CompositeEncoder {
        val consumer =
            if (currentTagOrNull == null) nodeConsumer
            else { node -> putElement(currentTag, node) }

        val encoder = when (descriptor.kind) {
            StructureKind.LIST, is PolymorphicKind -> JsonTreeListEncoder(json, consumer)
            StructureKind.MAP -> json.selectMapMode(
                descriptor,
                { JsonTreeMapEncoder(json, consumer) },
                { JsonTreeListEncoder(json, consumer) }
            )

            else -> JsonTreeEncoder(json, consumer)
        }

        val discriminator = polymorphicDiscriminator
        if (discriminator != null) {
            if (encoder is JsonTreeMapEncoder) {
                // first parameter of `putElement` is ignored in JsonTreeMapEncoder
                encoder.putElement("key", JsonPrimitive(discriminator))
                encoder.putElement("value", JsonPrimitive(polymorphicSerialName ?: descriptor.serialName))

            } else {
                encoder.putElement(discriminator, JsonPrimitive(polymorphicSerialName ?: descriptor.serialName))
            }
            polymorphicDiscriminator = null
            polymorphicSerialName = null
        }

        return encoder
    }

}