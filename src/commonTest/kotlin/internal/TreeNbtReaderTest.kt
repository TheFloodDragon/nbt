package net.benwoodworth.knbt.internal

import net.benwoodworth.knbt.*
import net.benwoodworth.knbt.internal.NbtReader.*
import net.benwoodworth.knbt.internal.NbtType.*
import net.benwoodworth.knbt.test.parameterizeTest
import net.benwoodworth.knbt.test.parameters.*
import net.benwoodworth.knbt.test.shouldReturn
import kotlin.test.Test

class TreeNbtReaderTest {
    private val rootName = "root_name"

    private inline fun expectNbtReaderCalls(tag: NbtTag, assertCalls: NbtReader.() -> Unit) {
        TreeNbtReader(NbtNamed(rootName, tag)).assertCalls()
    }

    @Test
    fun should_read_Byte_correctly() = parameterizeTest {
        val value by parameterOfBytes()

        expectNbtReaderCalls(NbtByte(value)) {
            beginRootTag() shouldReturn NamedTagInfo(NbtType.BYTE, rootName)
            readByte() shouldReturn value
        }
    }

    @Test
    fun should_read_Short_correctly() = parameterizeTest {
        val value by parameterOfShorts()

        expectNbtReaderCalls(NbtShort(value)) {
            beginRootTag() shouldReturn NamedTagInfo(NbtType.SHORT, rootName)
            readShort() shouldReturn value
        }
    }

    @Test
    fun should_read_Int_correctly() = parameterizeTest {
        val value by parameterOfInts()

        expectNbtReaderCalls(NbtInt(value)) {
            beginRootTag() shouldReturn NamedTagInfo(NbtType.INT, rootName)
            readInt() shouldReturn value
        }
    }

    @Test
    fun should_read_Long_correctly() = parameterizeTest {
        val value by parameterOfLongs()

        expectNbtReaderCalls(NbtLong(value)) {
            beginRootTag() shouldReturn NamedTagInfo(NbtType.LONG, rootName)
            readLong() shouldReturn value
        }
    }

    @Test
    fun should_read_Float_correctly() = parameterizeTest {
        val value by parameterOfFloats()

        expectNbtReaderCalls(NbtFloat(value)) {
            beginRootTag() shouldReturn NamedTagInfo(NbtType.FLOAT, rootName)
            readFloat() shouldReturn value
        }
    }

    @Test
    fun should_read_Double_correctly() = parameterizeTest {
        val value by parameterOfDoubles()

        expectNbtReaderCalls(NbtDouble(value)) {
            beginRootTag() shouldReturn NamedTagInfo(NbtType.DOUBLE, rootName)
            readDouble() shouldReturn value
        }
    }

    @Test
    fun should_read_ByteArray_correctly() = parameterizeTest {
        val value by parameterOfByteArrays()

        expectNbtReaderCalls(NbtByteArray(value.asList())) {
            beginRootTag() shouldReturn NamedTagInfo(NbtType.BYTE_ARRAY, rootName)
            beginByteArray() shouldReturn ArrayInfo(value.size)
            repeat(value.size) { index ->
                readByte() shouldReturn value[index]
            }
            endByteArray()
        }
    }

    @Test
    fun should_read_IntArray_correctly() = parameterizeTest {
        val value by parameterOfIntArrays()

        expectNbtReaderCalls(NbtIntArray(value.asList())) {
            beginRootTag() shouldReturn NamedTagInfo(NbtType.INT_ARRAY, rootName)
            beginIntArray() shouldReturn ArrayInfo(value.size)
            repeat(value.size) { index ->
                readInt() shouldReturn value[index]
            }
            endIntArray()
        }
    }

    @Test
    fun should_read_LongArray_correctly() = parameterizeTest {
        val value by parameterOfLongArrays()

        expectNbtReaderCalls(NbtLongArray(value.asList())) {
            beginRootTag() shouldReturn NamedTagInfo(NbtType.LONG_ARRAY, rootName)
            beginLongArray() shouldReturn ArrayInfo(value.size)
            repeat(value.size) { index ->
                readLong() shouldReturn value[index]
            }
            endLongArray()
        }
    }

    @Test
    fun should_read_Compound_with_no_entries_correctly() {
        expectNbtReaderCalls(buildNbtCompound {}) {
            beginRootTag() shouldReturn NamedTagInfo(NbtType.COMPOUND, rootName)
            beginCompound()
            beginCompoundEntry() shouldReturn NamedTagInfo.End
            endCompound()
        }
    }

    @Test
    fun should_read_Compound_with_one_entry_correctly() {
        expectNbtReaderCalls(
            buildNbtCompound { put("entry", 5) }
        ) {
            beginRootTag() shouldReturn NamedTagInfo(NbtType.COMPOUND, rootName)
            beginCompound()
            beginCompoundEntry() shouldReturn NamedTagInfo(NbtType.INT, "entry")
            readInt() shouldReturn 5
            beginCompoundEntry() shouldReturn NamedTagInfo.End
            endCompound()
        }
    }

    @Test
    fun should_read_List_with_no_entries_correctly() {
        expectNbtReaderCalls(NbtList(emptyList())) {
            beginRootTag() shouldReturn NamedTagInfo(NbtType.LIST, rootName)
            beginList() shouldReturn ListInfo(NbtType.END, 0)
            endList()
        }
    }

    @Test
    fun should_read_List_with_one_entry_correctly() {
        expectNbtReaderCalls(NbtList(listOf("entry").map { NbtString(it) })) {
            beginRootTag() shouldReturn NamedTagInfo(NbtType.LIST, rootName)
            beginList() shouldReturn ListInfo(NbtType.STRING, 1)
            readString() shouldReturn "entry"
            endList()
        }
    }

    @Test
    fun should_read_List_of_Lists_correctly() {
        val list = buildNbtList<NbtList<*>> {
            addNbtList<NbtString> {
                add("hello")
                add("world")
            }
            addNbtList<Nothing> {}
            addNbtList<NbtInt> {
                add(42)
            }
        }

        expectNbtReaderCalls(list) {
            beginRootTag() shouldReturn NamedTagInfo(NbtType.LIST, rootName)
            beginList() shouldReturn ListInfo(NbtType.LIST, 3)

            beginList() shouldReturn ListInfo(NbtType.STRING, 2)
            readString() shouldReturn "hello"
            readString() shouldReturn "world"
            endList()

            beginList() shouldReturn ListInfo(NbtType.END, 0)
            endList()

            beginList() shouldReturn ListInfo(NbtType.INT, 1)
            readInt() shouldReturn 42
            endList()

            endList()
        }
    }
}
