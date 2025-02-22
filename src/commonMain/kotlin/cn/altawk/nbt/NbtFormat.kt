package cn.altawk.nbt

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

    public fun <T> encodeToNbtTag(serializer: SerializationStrategy<T>, value: T): NbtTag {
        TODO("Not yet implemented")
    }

    public fun <T> decodeFromNbtTag(deserializer: DeserializationStrategy<T>, tag: NbtTag): T {
        TODO("Not yet implemented")
    }

    override fun <T> decodeFromString(deserializer: DeserializationStrategy<T>, string: String): T {
        TODO("Not yet implemented")
    }

    override fun <T> encodeToString(serializer: SerializationStrategy<T>, value: T): String {
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
     * Specifies whether default values of Kotlin properties should be encoded.
     * This option does not affect decoding.
     */
    public var encodeDefaults: Boolean = nbt.configuration.encodeDefaults

    /**
     * Specifies whether `null` values should be encoded for nullable properties and must be present in JSON object
     * during decoding.
     *
     * When this flag is disabled properties with `null` values are not encoded;
     * during decoding, the absence of a field value is treated as `null` for nullable properties without a default value.
     */
    public var explicitNulls: Boolean = nbt.configuration.explicitNulls

    /**
     * Specifies whether encounters of unknown properties in the input NBT
     */
    public var ignoreUnknownKeys: Boolean = nbt.configuration.ignoreUnknownKeys

    /**
     * Specifies whether resulting JSON should be pretty-printed: formatted and optimized for human readability.
     */
    public var prettyPrint: Boolean = nbt.configuration.prettyPrint

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
            encodeDefaults, ignoreUnknownKeys, explicitNulls,
            prettyPrint, nameDeterminer
        )
    }

}