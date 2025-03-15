package cn.altawk.nbt.internal

import cn.altawk.nbt.internal.NbtReader.Companion.EOF
import cn.altawk.nbt.tag.*

/**
 * TreeNbtReader
 *
 * @author TheFloodDragon
 * @since 2025/3/15 12:50
 */
internal class TreeNbtReader(tag: NbtTag) : NbtReader {
    private var reader: TagReader = RootReader(tag)

    override fun beginCompound() = reader.beginCompound()
    override fun beginCompoundEntry() = reader.beginCompoundEntry()
    override fun endCompound() = reader.endCompound()
    override fun beginList() = reader.beginList()
    override fun beginListEntry(): Boolean = reader.beginListEntry()
    override fun endList() = reader.endList()
    override fun beginByteArray() = reader.beginByteArray()
    override fun beginByteArrayEntry(): Boolean = reader.beginByteArrayEntry()
    override fun endByteArray() = reader.endByteArray()
    override fun beginIntArray() = reader.beginIntArray()
    override fun beginIntArrayEntry(): Boolean = reader.beginIntArrayEntry()
    override fun endIntArray() = reader.endIntArray()
    override fun beginLongArray() = reader.beginLongArray()
    override fun beginLongArrayEntry(): Boolean = reader.beginLongArrayEntry()
    override fun endLongArray() = reader.endLongArray()
    override fun readByte(): Byte = reader.readByte()
    override fun readShort(): Short = reader.readShort()
    override fun readInt(): Int = reader.readInt()
    override fun readLong(): Long = reader.readLong()
    override fun readFloat(): Float = reader.readFloat()
    override fun readDouble(): Double = reader.readDouble()
    override fun readString(): String = reader.readString()

    private open inner class TagReader : NbtReader {

        open fun readTag(): NbtTag = error("${this::class} does not support readTag()")

        override fun beginCompound() {
            val tag = readTag() as NbtCompound
            reader = CompoundReader(this, tag)
        }

        override fun beginList(): Int {
            val tag = readTag() as NbtList
            reader = ListReader(this, tag)
            return tag.size
        }

        override fun beginByteArray(): Int {
            val tag = readTag() as NbtByteArray
            reader = ByteArrayReader(this, tag)
            return tag.size
        }

        override fun beginIntArray(): Int {
            val tag = readTag() as NbtIntArray
            reader = IntArrayReader(this, tag)
            return tag.size
        }

        override fun beginLongArray(): Int {
            val tag = readTag() as NbtLongArray
            reader = LongArrayReader(this, tag)
            return tag.size
        }

        override fun beginCompoundEntry(): CharSequence = error("${this::class} does not support beginCompoundEntry()")
        override fun endCompound(): Unit = error("${this::class} does not support endCompound()")
        override fun beginListEntry(): Boolean = error("${this::class} does not support beginListEntry()")
        override fun endList(): Unit = error("${this::class} does not support endList()")
        override fun beginByteArrayEntry(): Boolean = error("${this::class} does not support beginByteArrayEntry()")
        override fun endByteArray(): Unit = error("${this::class} does not support endByteArray()")
        override fun beginIntArrayEntry(): Boolean = error("${this::class} does not support beginIntArrayEntry()")
        override fun endIntArray(): Unit = error("${this::class} does not support endIntArray()")
        override fun beginLongArrayEntry(): Boolean = error("${this::class} does not support beginLongArrayEntry()")
        override fun endLongArray(): Unit = error("${this::class} does not support endLongArray()")

        override fun readByte(): Byte = (readTag() as NbtByte).content
        override fun readShort(): Short = (readTag() as NbtShort).content
        override fun readInt(): Int = (readTag() as NbtInt).content
        override fun readLong(): Long = (readTag() as NbtLong).content
        override fun readFloat(): Float = (readTag() as NbtFloat).content
        override fun readDouble(): Double = (readTag() as NbtDouble).content
        override fun readString(): String = (readTag() as NbtString).content

    }

    private inner class RootReader(val tag: NbtTag) : TagReader() {
        override fun readTag() = this.tag
    }


    private inner class CompoundReader(val parent: TagReader, tag: NbtCompound) : TagReader() {
        private val iterator = tag.iterator()
        private var next: Map.Entry<String, NbtTag>? = if (iterator.hasNext()) iterator.next() else null

        override fun readTag(): NbtTag {
            return (next!!.value).also { next = if (iterator.hasNext()) iterator.next() else null }
        }

        override fun beginCompoundEntry(): CharSequence {
            val entry = next ?: return EOF
            return entry.key
        }

        override fun endCompound() {
            reader = parent
        }

    }

    private inner class ListReader(val parent: TagReader, tag: NbtList) : TagReader() {
        private val iterator = tag.iterator()
        private var next: NbtTag? = if (iterator.hasNext()) iterator.next() else null

        override fun readTag(): NbtTag {
            val tag = if (iterator.hasNext()) iterator.next() else null
            next = tag
            return tag!!
        }

        override fun beginListEntry(): Boolean = next != null

        override fun endList() {
            reader = parent
        }
    }

    private inner class ByteArrayReader(val parent: TagReader, tag: NbtByteArray) : TagReader() {
        private val array: ByteArray = tag.content
        private var index = 0

        override fun beginByteArrayEntry(): Boolean = index <= array.lastIndex

        override fun endByteArray() {
            reader = parent
        }

        override fun readByte(): Byte = array[index++]
    }


    private inner class IntArrayReader(val parent: TagReader, tag: NbtIntArray) : TagReader() {
        private val array: IntArray = tag.content
        private var index = 0

        override fun beginIntArrayEntry(): Boolean = index <= array.lastIndex

        override fun endIntArray() {
            reader = parent
        }

        override fun readInt(): Int = array[index++]
    }


    private inner class LongArrayReader(val parent: TagReader, tag: NbtLongArray) : TagReader() {
        private val array: LongArray = tag.content
        private var index = 0

        override fun beginLongArrayEntry(): Boolean = index <= array.lastIndex

        override fun endLongArray() {
            reader = parent
        }

        override fun readLong(): Long = array[index++]
    }

}