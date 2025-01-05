package cn.altawk.nbt.tag.impl

import cn.altawk.nbt.tag.*
import net.kyori.adventure.nbt.*

/**
 * AdventureAdapter
 *
 * @author TheFloodDragon
 * @since 2025/1/5 19:58
 */
public object AdventureAdapter {

    private val typeMap = hashMapOf<Byte, BinaryTagType<*>>(
        0.toByte() to BinaryTagTypes.END,
        1.toByte() to BinaryTagTypes.BYTE,
        2.toByte() to BinaryTagTypes.SHORT,
        3.toByte() to BinaryTagTypes.INT,
        4.toByte() to BinaryTagTypes.LONG,
        5.toByte() to BinaryTagTypes.FLOAT,
        6.toByte() to BinaryTagTypes.DOUBLE,
        7.toByte() to BinaryTagTypes.BYTE_ARRAY,
        8.toByte() to BinaryTagTypes.STRING,
        9.toByte() to BinaryTagTypes.LIST,
        10.toByte() to BinaryTagTypes.COMPOUND,
        11.toByte() to BinaryTagTypes.INT_ARRAY,
        12.toByte() to BinaryTagTypes.LONG_ARRAY
    )

    public fun typeTo(type: NbtType): BinaryTagType<*> = typeMap[type.id]!!

    public fun typeFrom(type: BinaryTagType<*>): NbtType = NbtType.from(type.id())!!

    public fun adaptTo(tag: NbtTag): BinaryTag = when (tag) {
        is AdventureNbtTag -> tag.source
        is NbtByte -> ByteBinaryTag.byteBinaryTag(tag.content)
        is NbtShort -> ShortBinaryTag.shortBinaryTag(tag.content)
        is NbtInt -> IntBinaryTag.intBinaryTag(tag.content)
        is NbtLong -> LongBinaryTag.longBinaryTag(tag.content)
        is NbtFloat -> FloatBinaryTag.floatBinaryTag(tag.content)
        is NbtDouble -> DoubleBinaryTag.doubleBinaryTag(tag.content)
        is NbtByteArray -> ByteArrayBinaryTag.byteArrayBinaryTag(*tag.content)
        is NbtString -> StringBinaryTag.stringBinaryTag(tag.content)
        is NbtList<*> -> ListBinaryTag.from(tag.content.map { adaptTo(it) })
        is NbtCompound -> CompoundBinaryTag.from(tag.content.mapValues { adaptTo(it.value) })
        is NbtIntArray -> IntArrayBinaryTag.intArrayBinaryTag(*tag.content)
        is NbtLongArray -> LongArrayBinaryTag.longArrayBinaryTag(*tag.content)
        else -> EndBinaryTag.endBinaryTag()
    }

    public fun adaptFrom(tag: BinaryTag): NbtTag = when (tag) {
        is ByteBinaryTag -> AdventureNbtByte(tag)
        is ShortBinaryTag -> AdventureNbtShort(tag)
        is IntBinaryTag -> AdventureNbtInt(tag)
        is LongBinaryTag -> AdventureNbtLong(tag)
        is FloatBinaryTag -> AdventureNbtFloat(tag)
        is DoubleBinaryTag -> AdventureNbtDouble(tag)
        is ByteArrayBinaryTag -> AdventureNbtByteArray(tag)
        is StringBinaryTag -> AdventureNbtString(tag)
        is ListBinaryTag -> AdventureNbtList(tag)
        is CompoundBinaryTag -> AdventureNbtCompound(tag)
        is IntArrayBinaryTag -> AdventureNbtIntArray(tag)
        is LongArrayBinaryTag -> AdventureNbtLongArray(tag)
        else -> AdventureNbtTag.End
    }

}