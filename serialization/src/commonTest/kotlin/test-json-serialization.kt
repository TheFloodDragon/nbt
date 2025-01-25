import cn.altawk.nbt.tag.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import kotlin.test.Test
import kotlin.test.assertEquals

@Serializable
data class NbtContainer(
    val nbt: @Serializable(ModifiedNbtTagSerializer::class) NbtTag
)

class Test {
    @Test
    fun testJsonSerialization() {
        val jsonFormat = Json { serializersModule = nbtTagSerializersModule }
        val json = """
            {
                "nbt": {
                    "type": "compound",
                    "details": [{
                        "data": {
                            "type": "string",
                            "value": "value"
                        }
                    }]
                }
            }
        """.trimIndent()
        val container = jsonFormat.decodeFromString<NbtContainer>(json)
        println(container)
        val nbtValue = NbtCompound().apply {
                put("type", "compound")
                put("details", NbtList().apply {
                    add(NbtCompound().apply {
                        put("data", NbtCompound().apply {
                            put("type", "string")
                            put("value", "value")
                        })
                    })
                })
        }
        assertEquals(container.nbt, nbtValue)
    }
}

object ModifiedNbtTagSerializer : KSerializer<NbtTag> {

    override val descriptor: SerialDescriptor get() = NbtTagSerializer.descriptor

    override fun serialize(encoder: Encoder, value: NbtTag) = NbtTagSerializer.serialize(encoder, value)

    override fun deserialize(decoder: Decoder): NbtTag {
        return if (decoder is JsonDecoder) {
            decoder.decodeJsonElement().toNbtTag()
        } else decoder.asNbtDecoder().decodeNbtTag()
    }

}