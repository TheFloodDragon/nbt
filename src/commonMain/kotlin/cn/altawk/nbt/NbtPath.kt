package cn.altawk.nbt

import cn.altawk.nbt.tag.NbtType

/**
 * NbtPath
 *
 * @author TheFloodDragon
 * @since 2025/2/22 18:02
 */
public class NbtPath(private val path: Collection<Node>) : Collection<NbtPath.Node> by path {

    public sealed interface Node {
        public val type: NbtType
    }

    public class NameNode(
        public val name: String,
        override val type: NbtType,
    ) : Node

    public class IndexNode(
        public val index: Int,
        override val type: NbtType,
    ) : Node

}
