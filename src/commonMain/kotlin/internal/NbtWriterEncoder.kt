package net.benwoodworth.knbt.internal

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.internal.AbstractPolymorphicSerializer
import kotlinx.serialization.modules.SerializersModule
import net.benwoodworth.knbt.AbstractNbtEncoder
import net.benwoodworth.knbt.ExperimentalNbtApi
import net.benwoodworth.knbt.NbtFormat
import net.benwoodworth.knbt.tag.NbtTag
import net.benwoodworth.knbt.tag.NbtType

@OptIn(ExperimentalSerializationApi::class)
internal class NbtWriterEncoder(
    override val nbt: NbtFormat,
    private val context: SerializationNbtContext,
    private val writer: NbtWriter,
) : AbstractNbtEncoder() {
    override val serializersModule: SerializersModule
        get() = nbt.serializersModule

    private lateinit var elementName: String
    private var encodingMapKey: Boolean = false

    private val structureTypeStack = ArrayDeque<NbtType>()

    private var serializerListKind: NbtListKind? = null
    private val listTypeStack = ArrayDeque<NbtType>() // NbtType.END when uninitialized
    private var listSize: Int = 0

    private var nbtNameToWrite: String? = null
    private var nbtNameToWriteWasDynamicallyEncoded = false

    override fun encodeElement(descriptor: SerialDescriptor, index: Int): Boolean {
        when (descriptor.kind as StructureKind) {
            StructureKind.CLASS,
            StructureKind.OBJECT,
            -> {
                elementName = descriptor.getElementName(index)
            }

            StructureKind.MAP -> {
                if (index % 2 == 0) encodingMapKey = true
            }

            StructureKind.LIST -> {}
        }

        if (descriptor.getElementDescriptor(index).kind == StructureKind.LIST) {
            serializerListKind = descriptor.getElementNbtListKind(context, index)
        }

        return true
    }

    private fun beginEncodingValue(type: NbtType) {
        when (val structureType = structureTypeStack.lastOrNull()) {
            null -> {
                val name = checkNotNull(nbtNameToWrite) { "${::nbtNameToWrite.name} was not set" }
                writer.beginRootTag(type, name)
            }

            NbtType.COMPOUND -> {
                if (encodingMapKey) throw NbtEncodingException(context, "Only String tag names are supported")
                writer.beginCompoundEntry(type, elementName)
            }

            NbtType.LIST -> when (val listType = listTypeStack.last()) {
                NbtType.END -> {
                    listTypeStack[listTypeStack.lastIndex] = type
                    writer.beginList(type, listSize)
                    writer.beginListEntry()
                }

                type -> {
                    writer.beginListEntry()
                }

                else -> throw NbtEncodingException(context, "Cannot encode $type within a ${NbtType.LIST} of $listType")
            }

            NbtType.BYTE_ARRAY -> {
                if (type != NbtType.BYTE) {
                    throw NbtEncodingException(context, "Cannot encode $type within a ${NbtType.BYTE_ARRAY}")
                }
                writer.beginByteArrayEntry()
            }

            NbtType.INT_ARRAY -> {
                if (type != NbtType.INT) {
                    throw NbtEncodingException(context, "Cannot encode $type within a ${NbtType.INT_ARRAY}")
                }
                writer.beginIntArrayEntry()
            }

            NbtType.LONG_ARRAY -> {
                if (type != NbtType.LONG) {
                    throw NbtEncodingException(context, "Cannot encode $type within a ${NbtType.LONG_ARRAY}")
                }
                writer.beginLongArrayEntry()
            }

            else -> error("Unhandled structure type: $structureType")
        }
    }

    private fun endEncodingValue() {
    }

    override fun beginStructure(descriptor: SerialDescriptor): CompositeEncoder =
        when (descriptor.kind) {
            is PolymorphicKind -> throw UnsupportedOperationException(
                "Unable to serialize type with serial name '${descriptor.serialName}'. " +
                        "beginning structures with polymorphic serial kinds is not supported."
            )

            else -> beginCompound()
        }

    override fun beginCollection(descriptor: SerialDescriptor, collectionSize: Int): CompositeEncoder =
        if (descriptor.kind == StructureKind.LIST) {
            when (serializerListKind ?: descriptor.getNbtListKind(context)) {
                NbtListKind.List -> beginList(collectionSize)
                NbtListKind.ByteArray -> beginByteArray(collectionSize)
                NbtListKind.IntArray -> beginIntArray(collectionSize)
                NbtListKind.LongArray -> beginLongArray(collectionSize)
            }
        } else {
            beginStructure(descriptor)
        }

    override fun endStructure(descriptor: SerialDescriptor): Unit =
        when (val structureType = structureTypeStack.removeLast()) {
            NbtType.COMPOUND -> endCompound()
            NbtType.LIST -> endList()
            NbtType.BYTE_ARRAY -> endByteArray()
            NbtType.INT_ARRAY -> endIntArray()
            NbtType.LONG_ARRAY -> endLongArray()
            else -> error("Unhandled structure type: $structureType")
        }

    private fun beginCompound(): CompositeEncoder {
        beginEncodingValue(NbtType.COMPOUND)
        context.onBeginStructure()
        writer.beginCompound()
        structureTypeStack += NbtType.COMPOUND
        return this
    }

    private fun endCompound() {
        writer.endCompound()
        context.onEndStructure()
        endEncodingValue()
    }

    private fun beginList(size: Int): CompositeEncoder {
        beginEncodingValue(NbtType.LIST)
        context.onBeginStructure()
        structureTypeStack += NbtType.LIST
        listTypeStack += NbtType.END // writer.beginList(TYPE, size) is postponed until the first element is encoded, or the list is ended
        listSize = size
        return this
    }

    private fun endList() {
        if (listTypeStack.removeLast() == NbtType.END) writer.beginList(NbtType.END, listSize)
        writer.endList()
        context.onEndStructure()
        endEncodingValue()
    }

    private fun beginByteArray(size: Int): CompositeEncoder {
        beginEncodingValue(NbtType.BYTE_ARRAY)
        context.onBeginStructure()
        writer.beginByteArray(size)
        structureTypeStack += NbtType.BYTE_ARRAY
        return this
    }

    private fun endByteArray() {
        writer.endByteArray()
        context.onEndStructure()
        endEncodingValue()
    }

    private fun beginIntArray(size: Int): CompositeEncoder {
        beginEncodingValue(NbtType.INT_ARRAY)
        context.onBeginStructure()
        writer.beginIntArray(size)
        structureTypeStack += NbtType.INT_ARRAY
        return this
    }

    private fun endIntArray() {
        writer.endIntArray()
        context.onEndStructure()
        endEncodingValue()
    }

    private fun beginLongArray(size: Int): CompositeEncoder {
        beginEncodingValue(NbtType.LONG_ARRAY)
        context.onBeginStructure()
        writer.beginLongArray(size)
        structureTypeStack += NbtType.LONG_ARRAY
        return this
    }

    private fun endLongArray() {
        writer.endLongArray()
        context.onEndStructure()
        endEncodingValue()
    }

    override fun shouldEncodeElementDefault(descriptor: SerialDescriptor, index: Int): Boolean =
        nbt.configuration.encodeDefaults

    override fun encodeByte(value: Byte) {
        beginEncodingValue(NbtType.BYTE)
        writer.writeByte(value)
        endEncodingValue()
    }

    override fun encodeBoolean(value: Boolean) {
        beginEncodingValue(NbtType.BYTE)
        writer.writeByte(if (value) 1 else 0)
        endEncodingValue()
    }

    override fun encodeShort(value: Short) {
        beginEncodingValue(NbtType.SHORT)
        writer.writeShort(value)
        endEncodingValue()
    }

    override fun encodeInt(value: Int) {
        beginEncodingValue(NbtType.INT)
        writer.writeInt(value)
        endEncodingValue()
    }

    override fun encodeLong(value: Long) {
        beginEncodingValue(NbtType.LONG)
        writer.writeLong(value)
        endEncodingValue()
    }

    override fun encodeFloat(value: Float) {
        beginEncodingValue(NbtType.FLOAT)
        writer.writeFloat(value)
        endEncodingValue()
    }

    override fun encodeDouble(value: Double) {
        beginEncodingValue(NbtType.DOUBLE)
        writer.writeDouble(value)
        endEncodingValue()
    }

    override fun encodeString(value: String) {
        if (encodingMapKey) {
            encodingMapKey = false
            elementName = value
        } else {
            beginEncodingValue(NbtType.STRING)
            writer.writeString(value)
            endEncodingValue()
        }
    }

    override fun encodeChar(value: Char): Unit =
        encodeString(value.toString())

    @ExperimentalNbtApi
    override fun encodeNbtName(name: String) {
        context.checkDynamicallySerializingNbtName()

        if (!nbtNameToWriteWasDynamicallyEncoded) {
            nbtNameToWrite = name
            nbtNameToWriteWasDynamicallyEncoded = true
        }
    }

    override fun encodeNbtTag(tag: NbtTag) {
        beginEncodingValue(tag.type)
        writer.writeNbtTag(context, tag)
        endEncodingValue()
    }

    @OptIn(InternalSerializationApi::class)
    override fun <T> encodeSerializableValue(serializer: SerializationStrategy<T>, value: T) {
        if (nbtNameToWrite == null) {
            nbtNameToWrite = serializer.descriptor.nbtName
        }

        return when {
            serializer is AbstractPolymorphicSerializer<*> ->
                throw UnsupportedOperationException(
                    "Unable to serialize type with serial name '${serializer.descriptor.serialName}'. " +
                            "The builtin polymorphic serializers are not yet supported."
                )

            else -> {
                context.decorateValueSerialization(serializer.descriptor) {
                    super.encodeSerializableValue(serializer, value)
                }
            }
        }
    }
}
