import cn.altawk.nbt.NbtArray
import cn.altawk.nbt.NbtFormat
import cn.altawk.nbt.tag.NbtCompound
import cn.altawk.nbt.tag.NbtTag
import cn.altawk.nbt.tag.NbtTagSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.test.Test
import kotlin.test.assertEquals

class StringifiedNbtWriterTest {

    @Serializable
    @SerialName("Example")
    private data class Example<T>(
        val name: String?,
        val list: List<String> = emptyList(),
        val map: Map<String, Int> = emptyMap(),
        val byteArray: ByteArray = ByteArray(0),
        @NbtArray
        val byteList: List<Byte> = emptyList(),
        val duo: T,
    )

    private val format = NbtFormat {
    }

    @Test
    fun Encode() {

        val example = Example<NbtTag>(
            null,
            listOf("1", "2"),
            mapOf("Ket1" to 1, "Key2" to 2),
            byteArrayOf(1, 2, 3),
            listOf(2, 3, 4),
            NbtCompound()
        )

        val serializer = Example.serializer(NbtTagSerializer)

        val tag = format.encodeToNbtTag(serializer, example)

        val str = format.encodeToString(serializer, example)

        println(tag)
        println(str)

        assertEquals(str, tag.toString())
    }

}
