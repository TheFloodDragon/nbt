import cn.altawk.nbt.NbtFormat
import cn.altawk.nbt.internal.NbtWriterEncoder
import cn.altawk.nbt.internal.StringifiedNbtWriter
import cn.altawk.nbt.tag.NbtCompound
import cn.altawk.nbt.tag.NbtTag
import cn.altawk.nbt.tag.NbtTagSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.test.Test

class StringifiedNbtWriterTest {

    @Serializable
    @SerialName("Example")
    private data class Example<T>(
        val name: String?,
        val list: List<String> = emptyList(),
        val map: Map<String, Int> = emptyMap(),
        val byteArray: ByteArray = ByteArray(0),
        val duo: T,
    )

    val format = NbtFormat {
    }

    @Test
    fun Encode() {
        val builder = StringBuilder()
        val writer = StringifiedNbtWriter(builder)
        val encoder = NbtWriterEncoder(format, writer)

        Example.serializer<NbtTag>(NbtTagSerializer).serialize(
            encoder,
            Example<NbtTag>(null,
                listOf("1", "2"),
                mapOf("Ket1" to 1, "Key2" to 2),
                byteArrayOf(1, 2, 3),
                NbtCompound())
        )

        println(builder)
    }

}
