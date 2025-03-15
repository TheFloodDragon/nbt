@file:OptIn(ExperimentalSerializationApi::class)

package cn.altawk.nbt.internal

import cn.altawk.nbt.NbtDecoder
import cn.altawk.nbt.tag.NbtTag
import cn.altawk.nbt.tag.NbtTagSerializer
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ByteArraySerializer
import kotlinx.serialization.builtins.IntArraySerializer
import kotlinx.serialization.builtins.LongArraySerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.encoding.AbstractDecoder
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.modules.SerializersModule

/**
 * AbstractNbtDecoder
 *
 * @author TheFloodDragon
 * @since 2025/3/15 11:04
 */
internal abstract class AbstractNbtDecoder : NbtDecoder, AbstractDecoder() {

    override val serializersModule: SerializersModule get() = nbt.serializersModule

    abstract fun beginCompound(descriptor: SerialDescriptor): CompositeDecoder
    abstract fun beginList(descriptor: SerialDescriptor): CompositeDecoder

    override fun beginStructure(descriptor: SerialDescriptor): CompositeDecoder =
        if (descriptor.kind == StructureKind.LIST) {
            beginList(descriptor)
        } else beginCompound(descriptor)

    override fun decodeNbtTag(): NbtTag = decodeValue() as NbtTag
    override fun decodeByteArray(): ByteArray = decodeValue() as ByteArray
    override fun decodeIntArray(): IntArray = decodeValue() as IntArray
    override fun decodeLongArray(): LongArray = decodeValue() as LongArray

    override fun <T> decodeSerializableValue(deserializer: DeserializationStrategy<T>): T =
        @Suppress("UNCHECKED_CAST")
        when (deserializer) {
            ByteArraySerializer() -> decodeByteArray() as T
            IntArraySerializer() -> decodeIntArray() as T
            LongArraySerializer() -> decodeLongArray() as T

            NbtTagSerializer -> decodeNbtTag() as T

            else -> super<AbstractDecoder>.decodeSerializableValue(deserializer)
        }

}