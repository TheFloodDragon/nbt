package cn.altawk.nbt.tag.impl

import cn.altawk.nbt.tag.NbtCompound
import cn.altawk.nbt.tag.NbtTag
import net.kyori.adventure.nbt.CompoundBinaryTag

/**
 * AdventureNbtCompound
 *
 * @author TheFloodDragon
 * @since 2025/1/5 20:42
 */
public class AdventureNbtCompound(
    public override val source: CompoundBinaryTag
) : NbtCompound, AdventureNbtTag() {

    override val content: Map<String, NbtTag>
        get() = source.keySet().associateWith {
            AdventureAdapter.adaptFrom(source.get(it)!!)
        }

    override fun get(key: String): NbtTag? = source.get(key)?.let { AdventureAdapter.adaptFrom(it) }

    override fun put(key: String, value: NbtTag) {
        source.put(key, AdventureAdapter.adaptTo(value))
    }

    override fun remove(key: String) {
        source.remove(key)
    }

    override fun contains(key: String): Boolean = source.get(key) != null

    override val size: Int get() = source.size()

    override fun isEmpty(): Boolean = source.isEmpty

    override fun clear() {
        source.removeAll { true }
    }

    override fun mergeShallow(target: NbtCompound, replace: Boolean): NbtCompound {
        TODO("Not yet implemented")
    }

    override fun merge(target: NbtCompound, replace: Boolean): NbtCompound {
        TODO("Not yet implemented")
    }

    override fun cloneShallow(): NbtCompound {
        TODO("Not yet implemented")
    }

    override fun clone(): NbtCompound {
        TODO("Not yet implemented")
    }

}