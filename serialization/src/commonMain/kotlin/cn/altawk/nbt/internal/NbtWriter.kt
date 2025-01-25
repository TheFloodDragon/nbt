package cn.altawk.nbt.internal

import cn.altawk.nbt.tag.NbtType

/**
 * NbtWriter
 *
 * @author TheFloodDragon
 * @since 2025/1/25 11:21
 */
internal interface NbtWriter {

    /**
     * Begin a compound.
     */
    fun beginCompound()

    /**
     * End a compound.
     */
    fun endCompound()

    /**
     * Begin a list.
     */
    fun beginList(type: NbtType, size: Int)

    /**
     * End a list.
     */
    fun endList()

    /**
     * Begin a byte array.
     */
    fun beginByteArray(size: Int)

    /**
     * End a byte array.
     */
    fun endByteArray()

    /**
     * Begin an int array.
     */
    fun beginIntArray(size: Int)

    /**
     * End an int array.
     */
    fun endIntArray()

    /**
     * Begin a long array.
     */
    fun beginLongArray(size: Int)

    /**
     * End a long array.
     */
    fun endLongArray()

    /**
     * Write a byte.
     */
    fun writeByte(value: Byte)

    /**
     * Write a short.
     */
    fun writeShort(value: Short)

    /**
     * Write an int.
     */
    fun writeInt(value: Int)

    /**
     * Write a long.
     */
    fun writeLong(value: Long)

    /**
     * Write a float.
     */
    fun writeFloat(value: Float)

    /**
     * Write a double.
     */
    fun writeDouble(value: Double)

    /**
     * Write a string.
     */
    fun writeString(value: String)

}