package cn.altawk.nbt.internal

/**
 * NbtReader
 *
 * @author TheFloodDragon
 * @since 2025/3/14 23:33
 */
internal interface NbtReader {

    /**
     * Begin a compound.
     */
    fun beginCompound()

    /**
     * Begin a compound entry.
     */
    fun beginCompoundEntry(): CharSequence

    /**
     * End a compound.
     */
    fun endCompound()

    /**
     * Begin a list.
     */
    fun beginList(): Int

    /**
     * Begin a list entry.
     */
    fun beginListEntry(): Boolean

    /**
     * End a list.
     */
    fun endList()

    /**
     * Begin a byte array.
     */
    fun beginByteArray(): Int

    /**
     * Begin a byte array entry.
     */
    fun beginByteArrayEntry(): Boolean

    /**
     * End a byte array.
     */
    fun endByteArray()

    /**
     * Begin an int array.
     */
    fun beginIntArray(): Int

    /**
     * Begin an int array entry.
     */
    fun beginIntArrayEntry(): Boolean

    /**
     * End an int array.
     */
    fun endIntArray()

    /**
     * Begin a long array.
     */
    fun beginLongArray(): Int

    /**
     * Begin a long array entry.
     */
    fun beginLongArrayEntry(): Boolean

    /**
     * End a long array.
     */
    fun endLongArray()

    /**
     * Read a byte.
     */
    fun readByte(): Byte

    /**
     * Read a short.
     */
    fun readShort(): Short

    /**
     * Read an int.
     */
    fun readInt(): Int

    /**
     * Read a long.
     */
    fun readLong(): Long

    /**
     * Read a float.
     */
    fun readFloat(): Float

    /**
     * Read a double.
     */
    fun readDouble(): Double

    /**
     * Read a string.
     */
    fun readString(): String

}