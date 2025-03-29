package cn.altawk.nbt.internal

import cn.altawk.nbt.NbtPath
import cn.altawk.nbt.NbtPath.IndexNode
import cn.altawk.nbt.NbtPath.NameNode

/**
 * NbtPathBuilder
 *
 * @since 2025/3/29 17:34
 */
internal object NbtPathBuilder {

    fun fromString(path: String) = buildList {
        val trimmed = path.trim()
        for (segment in trimmed.split('.')) {
            if (segment.endsWith(']')) {
                val startIndex = segment.lastIndexOf('[')
                // 如果带名称则添加名称
                val name = segment.substring(0, startIndex)
                if (name.isNotEmpty()) add(NameNode(name))
                // 添加索引节点
                val index = segment.substring(startIndex + 1, segment.lastIndex).toInt()
                add(IndexNode(index))
            } else add(NameNode(segment))
        }
    }

    fun toString(path: Iterable<NbtPath.Node>) = buildString {
        var first = true
        for (node in path) {
            when (node) {
                is IndexNode -> append("[${node.index}]")
                is NameNode -> {
                    if (!first) append('.') else first = false
                    append(node.name)
                }
            }
        }
    }

}