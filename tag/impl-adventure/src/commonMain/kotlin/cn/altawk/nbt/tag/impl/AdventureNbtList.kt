package cn.altawk.nbt.tag.impl

import cn.altawk.nbt.tag.NbtList
import cn.altawk.nbt.tag.NbtTag
import cn.altawk.nbt.tag.NbtType
import net.kyori.adventure.nbt.ListBinaryTag

/**
 * AdventureNbtList
 *
 * @author TheFloodDragon
 * @since 2025/1/5 19:49
 */
public class AdventureNbtList(
    public override val source: ListBinaryTag
) : NbtList<NbtTag>, AdventureNbtTag() {

    override val content: List<NbtTag> get() = source.map { AdventureAdapter.adaptFrom(it) }

    override val elementType: NbtType get() = AdventureAdapter.typeFrom(source.elementType())

    override fun clone(): AdventureNbtList = AdventureNbtList(ListBinaryTag.from(source))

    override fun get(index: Int): NbtTag = AdventureAdapter.adaptFrom(source.get(index))

    override fun add(tag: NbtTag) {
        source.add(AdventureAdapter.adaptTo(tag))
    }

    override fun set(index: Int, tag: NbtTag) {
        source.set(index, AdventureAdapter.adaptTo(tag)) {}
    }

    override fun removeAt(index: Int) {
        source.remove(index) {}
    }

    override fun contains(tag: NbtTag): Boolean = source.contains(AdventureAdapter.adaptTo(tag))

    override val size: Int get() = source.size()

    override fun isEmpty(): Boolean = source.isEmpty

    override fun clear() {
        source.removeAll { true }
    }


}