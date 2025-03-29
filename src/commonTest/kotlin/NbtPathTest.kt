import cn.altawk.nbt.NbtPath
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * NbtPathTest
 *
 * @author TheFloodDragon
 * @since 2025/3/29 17:44
 */
class NbtPathTest {

    val path = NbtPath(
        listOf(
            NbtPath.NameNode("hello"),
            NbtPath.NameNode("world"),
            NbtPath.NameNode("list"),
            NbtPath.IndexNode(1)
        )
    ) += NbtPath.NameNode("name")

    val pathText = "hello.world.list[1].name"


    @Test
    fun `test_fromString`() {
        assertEquals(path, NbtPath(pathText))
    }

    @Test
    fun `test_toString`() {
        assertEquals(pathText, path.toString())
    }

}