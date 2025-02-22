package cn.altawk.nbt

import kotlinx.serialization.descriptors.SerialDescriptor

/**
 * SerialNameDeterminer
 *
 * @author TheFloodDragon
 * @since 2025/2/22 14:18
 */
public interface SerialNameDeterminer {

    /**
     * Retrieves the element name for a given descriptor and index.
     *
     * @param descriptor The serial descriptor.
     * @param index The index within the descriptor.
     * @return The element name.
     */
    public fun determineName(descriptor: SerialDescriptor, index: Int): String

    /**
     * Default implementation of [SerialNameDeterminer].
     */
    public object Default : SerialNameDeterminer {
        override fun determineName(descriptor: SerialDescriptor, index: Int): String {
            return descriptor.getElementName(index)
        }
    }

}