package cn.altawk.nbt

import cn.altawk.nbt.tag.NbtTag
import cn.altawk.nbt.tag.nbtTagSerializersModule
import kotlinx.serialization.BinaryFormat
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.StringFormat
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.plus

/**
 * NbtFormat
 *
 * @author TheFloodDragon
 * @since 2025/1/25 11:31
 */
public class NbtFormat(
    public val configuration: NbtConfiguration,
    serializersModule: SerializersModule
) : StringFormat, BinaryFormat {

    override val serializersModule: SerializersModule = nbtTagSerializersModule.plus(serializersModule)

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