package net.benwoodworth.knbt.tag

import net.benwoodworth.knbt.NbtNamed
import kotlin.reflect.KClass

//region NbtTag casting properties
private inline fun <reified T : NbtTag> NbtTag.cast(): T =
    this as? T ?: throw IllegalArgumentException("Element ${this::class.simpleName} is not an ${T::class.simpleName}")

/**
 * Convenience property to get this tag as an [NbtByte].
 * @throws IllegalArgumentException if this tag is not an [NbtByte].
 */
public val NbtTag.nbtByte: NbtByte get() = cast()

/**
 * Convenience property to get this tag as an [NbtShort].
 * @throws IllegalArgumentException if this tag is not an [NbtShort].
 */
public val NbtTag.nbtShort: NbtShort get() = cast()

/**
 * Convenience property to get this tag as an [NbtInt].
 * @throws IllegalArgumentException if this tag is not an [NbtInt].
 */
public val NbtTag.nbtInt: NbtInt get() = cast()

/**
 * Convenience property to get this tag as an [NbtLong].
 * @throws IllegalArgumentException if this tag is not an [NbtLong].
 */
public val NbtTag.nbtLong: NbtLong get() = cast()

/**
 * Convenience property to get this tag as an [NbtFloat].
 * @throws IllegalArgumentException if this tag is not an [NbtFloat].
 */
public val NbtTag.nbtFloat: NbtFloat get() = cast()

/**
 * Convenience property to get this tag as an [NbtDouble].
 * @throws IllegalArgumentException if this tag is not an [NbtDouble].
 */
public val NbtTag.nbtDouble: NbtDouble get() = cast()

/**
 * Convenience property to get this tag as an [NbtByteArray].
 * @throws IllegalArgumentException if this tag is not an [NbtByteArray].
 */
public val NbtTag.nbtByteArray: NbtByteArray get() = cast()

/**
 * Convenience property to get this tag as an [NbtString].
 * @throws IllegalArgumentException if this tag is not an [NbtString].
 */
public val NbtTag.nbtString: NbtString get() = cast()

/**
 * Convenience property to get this tag as an [NbtList].
 * @throws IllegalArgumentException if this tag is not an [NbtList].
 */
public val NbtTag.nbtList: NbtList<*> get() = cast()

/**
 * Convenience property to get this tag as an [NbtCompound].
 * @throws IllegalArgumentException if this tag is not an [NbtCompound].
 */
public val NbtTag.nbtCompound: NbtCompound get() = cast()

/**
 * Convenience property to get this tag as an [NbtIntArray].
 * @throws IllegalArgumentException if this tag is not an [NbtIntArray].
 */
public val NbtTag.nbtIntArray: NbtIntArray get() = cast()

/**
 * Convenience property to get this tag as an [NbtLongArray].
 * @throws IllegalArgumentException if this tag is not an [NbtLongArray].
 */
public val NbtTag.nbtLongArray: NbtLongArray get() = cast()

/**
 * Convenience property to get this tag as an [NbtList]<[T]>.
 * @throws IllegalArgumentException if this tag is not an [NbtList]<[T]>.
 */
public inline fun <reified T : NbtTag> NbtTag.nbtList(): NbtList<T> = nbtList(T::class)

@PublishedApi
@Suppress("UNCHECKED_CAST")
internal fun <T : NbtTag> NbtTag.nbtList(type: KClass<T>): NbtList<T> = when {
    this !is NbtList<*> -> throw IllegalArgumentException("Element ${this::class.simpleName} is not an NbtList<${type.simpleName}>")
    size > 0 && !type.isInstance(this[0]) -> throw IllegalArgumentException("Element NbtList<${this[0]::class.simpleName}> is not an NbtList<${type.simpleName}>")
    else -> this as NbtList<T>
}
//endregion

//region NbtNamed<NbtTag> casting properties
/**
 * Convenience property to get this named tag as an [NbtByte].
 * @throws IllegalArgumentException if this named tag is not an [NbtByte].
 */
public val NbtNamed<NbtTag>.nbtByte: NbtByte get() = value.nbtByte

/**
 * Convenience property to get this named tag as an [NbtShort].
 * @throws IllegalArgumentException if this named tag is not an [NbtShort].
 */
public val NbtNamed<NbtTag>.nbtShort: NbtShort get() = value.nbtShort

/**
 * Convenience property to get this named tag as an [NbtInt].
 * @throws IllegalArgumentException if this named tag is not an [NbtInt].
 */
public val NbtNamed<NbtTag>.nbtInt: NbtInt get() = value.nbtInt

/**
 * Convenience property to get this named tag as an [NbtLong].
 * @throws IllegalArgumentException if this named tag is not an [NbtLong].
 */
public val NbtNamed<NbtTag>.nbtLong: NbtLong get() = value.nbtLong

/**
 * Convenience property to get this named tag as an [NbtFloat].
 * @throws IllegalArgumentException if this named tag is not an [NbtFloat].
 */
public val NbtNamed<NbtTag>.nbtFloat: NbtFloat get() = value.nbtFloat

/**
 * Convenience property to get this named tag as an [NbtDouble].
 * @throws IllegalArgumentException if this named tag is not an [NbtDouble].
 */
public val NbtNamed<NbtTag>.nbtDouble: NbtDouble get() = value.nbtDouble

/**
 * Convenience property to get this named tag as an [NbtByteArray].
 * @throws IllegalArgumentException if this named tag is not an [NbtByteArray].
 */
public val NbtNamed<NbtTag>.nbtByteArray: NbtByteArray get() = value.nbtByteArray

/**
 * Convenience property to get this named tag as an [NbtString].
 * @throws IllegalArgumentException if this named tag is not an [NbtString].
 */
public val NbtNamed<NbtTag>.nbtString: NbtString get() = value.nbtString

/**
 * Convenience property to get this named tag as an [NbtList].
 * @throws IllegalArgumentException if this named tag is not an [NbtList].
 */
public val NbtNamed<NbtTag>.nbtList: NbtList<*> get() = value.nbtList

/**
 * Convenience property to get this named tag as an [NbtCompound].
 * @throws IllegalArgumentException if this named tag is not an [NbtCompound].
 */
public val NbtNamed<NbtTag>.nbtCompound: NbtCompound get() = value.nbtCompound

/**
 * Convenience property to get this named tag as an [NbtIntArray].
 * @throws IllegalArgumentException if this named tag is not an [NbtIntArray].
 */
public val NbtNamed<NbtTag>.nbtIntArray: NbtIntArray get() = value.nbtIntArray

/**
 * Convenience property to get this named tag as an [NbtLongArray].
 * @throws IllegalArgumentException if this named tag is not an [NbtLongArray].
 */
public val NbtNamed<NbtTag>.nbtLongArray: NbtLongArray get() = value.nbtLongArray

/**
 * Convenience property to get this named tag as an [NbtList]<[T]>.
 * @throws IllegalArgumentException if this named tag is not an [NbtList]<[T]>.
 */
public inline fun <reified T : NbtTag> NbtNamed<NbtTag>.nbtList(): NbtList<T> = value.nbtList<T>()
//endregion
