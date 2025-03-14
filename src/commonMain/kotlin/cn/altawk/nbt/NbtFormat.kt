package cn.altawk.nbt

import cn.altawk.nbt.internal.NbtWriterEncoder
import cn.altawk.nbt.internal.StringifiedNbtWriter
import cn.altawk.nbt.internal.TreeNbtWriter
import cn.altawk.nbt.tag.NbtTag
import kotlinx.serialization.BinaryFormat
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.StringFormat
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule

/**
 * NbtFormat
 *
 * @author TheFloodDragon
 * @since 2025/1/25 11:31
 */
public open class NbtFormat(
    public val configuration: NbtConfiguration,
    override val serializersModule: SerializersModule
) : StringFormat, BinaryFormat {

    /**
     * The default instance of [NbtFormat] with default configuration.
     */
    public companion object Default : NbtFormat(NbtConfiguration(), EmptySerializersModule())

    /**
     * Serializes and encodes the given [value] to an [NbtTag] using the given [serializer].
     */
    public fun <T> encodeToNbtTag(serializer: SerializationStrategy<T>, value: T): NbtTag {
        lateinit var result: NbtTag
        NbtWriterEncoder(this, TreeNbtWriter { result = it }).encodeSerializableValue(serializer, value)
        return result
    }

    public fun <T> decodeFromNbtTag(deserializer: DeserializationStrategy<T>, tag: NbtTag): T {
        TODO("Not yet implemented")
    }

    override fun <T> encodeToString(serializer: SerializationStrategy<T>, value: T): String {
        val builder = StringBuilder()
        NbtWriterEncoder(this, StringifiedNbtWriter(builder)).encodeSerializableValue(serializer, value)
        return builder.toString()
    }

    override fun <T> decodeFromString(deserializer: DeserializationStrategy<T>, string: String): T {
        TODO("Not yet implemented")
    }

    override fun <T> decodeFromByteArray(deserializer: DeserializationStrategy<T>, bytes: ByteArray): T {
        TODO("Not yet implemented")
    }

    override fun <T> encodeToByteArray(serializer: SerializationStrategy<T>, value: T): ByteArray {
        TODO("Not yet implemented")
    }

}

/**
 * Creates an instance of [NbtFormat] configured from the optionally given [NbtFormat instance][from] and adjusted with [builderAction].
 */
public fun NbtFormat(from: NbtFormat = NbtFormat.Default, builderAction: NbtFormatBuilder.() -> Unit): NbtFormat {
    val builder = NbtFormatBuilder(from)
    builder.builderAction()
    val configuration = builder.build()
    return NbtFormat(configuration, builder.serializersModule)
}

/**
 * Builder of the [NbtFormat] instance provided by `NbtFormat { ... }` factory function:
 *
 * ```
 * val nbt = NbtFormat { // this: NbtFormatBuilder
 *     encodeDefaults = true
 *     ignoreUnknownKeys = true
 * }
 * ```
 */
public class NbtFormatBuilder internal constructor(nbt: NbtFormat) {

    /**
     * Specifies the [SerialNameDeterminer] the coder used
     */
    public var nameDeterminer: SerialNameDeterminer = nbt.configuration.nameDeterminer

    /**
     * Module with contextual and polymorphic serializers to be used in the resulting [Json] instance.
     *
     * @see SerializersModule
     * @see Contextual
     * @see Polymorphic
     */
    public var serializersModule: SerializersModule = nbt.serializersModule

    internal fun build(): NbtConfiguration {
        return NbtConfiguration(
            nameDeterminer
        )
    }

}