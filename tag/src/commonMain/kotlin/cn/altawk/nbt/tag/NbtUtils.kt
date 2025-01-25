@file:Suppress("NOTHING_TO_INLINE")

package cn.altawk.nbt.tag

public inline fun NbtCompound.put(key: String, value: Byte): NbtTag? = this.put(key, NbtByte(value))
public inline fun NbtCompound.put(key: String, value: Boolean): NbtTag? = this.put(key, NbtByte(value))
public inline fun NbtCompound.put(key: String, value: Double): NbtTag? = this.put(key, NbtDouble(value))
public inline fun NbtCompound.put(key: String, value: Float): NbtTag? = this.put(key, NbtFloat(value))
public inline fun NbtCompound.put(key: String, value: Int): NbtTag? = this.put(key, NbtInt(value))
public inline fun NbtCompound.put(key: String, value: Long): NbtTag? = this.put(key, NbtLong(value))
public inline fun NbtCompound.put(key: String, value: String): NbtTag? = this.put(key, NbtString(value))
public inline fun NbtCompound.put(key: String, value: Short): NbtTag? = this.put(key, NbtShort(value))