import cn.altawk.nbt.tag.*
import kotlin.test.Test

/**
 * NbtReaderTest
 *
 * @author TheFloodDragon
 * @since 2025/3/15 13:29
 */
class NbtReaderTest {

    @Test
    fun Decode() {

        val exampleTag = NbtCompound {
            put("name", "Good")
            putList("list") {
                add("1")
                add("2")
            }
            putCompound("map") {
                put("Ket1", 1)
                put("Key2", 2)
            }
            put("byteArray", byteArrayOf(1, 2, 3))
            putList("byteList") {
                add(2)
                add(3)
                add(4)
            }
            putCompound("duo") {
            }
        }

        val serializer = Example.serializer(NbtTagSerializer)

        val example = format.decodeFromNbtTag(serializer, exampleTag)

//        val str = format.encodeToString(serializer, exampleTag)

        println(example)
//        println(str)

//        assertEquals(str, tag.toString())
    }

}