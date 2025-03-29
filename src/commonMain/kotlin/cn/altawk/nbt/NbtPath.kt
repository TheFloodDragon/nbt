package cn.altawk.nbt

import cn.altawk.nbt.internal.NbtPathBuilder

/**
 * NbtPath
 *
 * @author TheFloodDragon
 * @since 2025/3/29 17:18
 */
public class NbtPath(private val path: List<Node>) : List<NbtPath.Node> by path {

    public constructor(vararg nodes: Node) : this(nodes.toList())

    public constructor(path: String) : this(NbtPathBuilder.fromString(path))

    override fun toString(): String = NbtPathBuilder.toString(this.path)

    override fun hashCode(): Int = this.path.hashCode()

    override fun equals(other: Any?): Boolean = if (other is NbtPath) this.path == other.path else false

    public operator fun plus(element: Iterable<Node>): NbtPath = NbtPath(this.path.plus(element))

    public operator fun plus(element: Node): NbtPath = NbtPath(this.path.plus(element))

    /**
     * Node - 节点
     */
    public sealed interface Node

    /**
     * NameNode - 名称节点 (用于 Compound 类型)
     */
    public data class NameNode(
        /** 名称 **/
        public val name: String
    ) : Node

    /**
     * IndexNode - 索引节点 (用于 List 类型)
     */
    public data class IndexNode(
        /** 索引 **/
        public val index: Int
    ) : Node

}