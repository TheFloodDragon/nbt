package cn.altawk.nbt.internal

import cn.altawk.nbt.SerialNameDeterminer
import kotlinx.serialization.descriptors.SerialDescriptor

/**
 * DefaultNameDeterminer
 *
 * @author TheFloodDragon
 * @since 2025/2/22 14:20
 */
internal object DefaultNameDeterminer : SerialNameDeterminer {

    override fun determineName(descriptor: SerialDescriptor, index: Int): String {
        return descriptor.getElementName(index)
    }

}