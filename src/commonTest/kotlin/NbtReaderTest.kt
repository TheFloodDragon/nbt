import cn.altawk.nbt.tag.NbtCompound
import cn.altawk.nbt.tag.NbtTag
import cn.altawk.nbt.tag.NbtTagSerializer
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * NbtReaderTest
 *
 * @author TheFloodDragon
 * @since 2025/3/15 13:29
 */
class NbtReaderTest {

    @Test
    fun Decode() {

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