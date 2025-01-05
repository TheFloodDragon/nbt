package net.benwoodworth.knbt

import com.benwoodworth.parameterize.ParameterizeScope
import com.benwoodworth.parameterize.parameter
import com.benwoodworth.parameterize.parameterOf
import net.benwoodworth.knbt.tag.*
import net.benwoodworth.knbt.test.assume
import net.benwoodworth.knbt.test.parameterizeTest
import net.benwoodworth.knbt.test.parameters.*
import kotlin.reflect.KProperty1
import kotlin.test.*

class NbtTagTest {
    private fun ParameterizeScope.parameterOfNbtTagsWithSameContent() = parameter {
        listOf(
            NbtList<NbtTag>(),
            NbtByteArray(byteArrayOf()),
            NbtIntArray(intArrayOf()),
            NbtLongArray(longArrayOf())
        )
    }

    @Test
    fun should_not_equal_NbtTag_with_different_type_but_same_content() = parameterizeTest {
        val nbtTag by parameterOfNbtTagsWithSameContent()
        val differentNbtTagWithSameContent by parameterOfNbtTagsWithSameContent()
        assume(nbtTag.type != differentNbtTagWithSameContent.type)

        assertNotEquals(differentNbtTagWithSameContent, nbtTag)
    }
}

class NbtByteTest {
    @Test
    fun should_equal_another_NbtByte_with_the_same_value() = parameterizeTest {
        val nbtByte by parameterOfNbtByteEdgeCases()
        val nbtByteWithSameValue = NbtByte(nbtByte.content)

        assertEquals(nbtByteWithSameValue, nbtByte)
    }

    @Test
    fun should_not_equal_another_NbtByte_with_different_value() = parameterizeTest {
        val nbtByte by parameterOfNbtByteEdgeCases()
        val differentNbtByte by parameterOfNbtByteEdgeCases()
        assume(nbtByte.content != differentNbtByte.content)

        assertNotEquals(nbtByte, differentNbtByte)
    }

    @Test
    fun hash_code_should_be_the_value_hash_code() = parameterizeTest {
        val nbtByte by parameterOfNbtByteEdgeCases()

        assertEquals(nbtByte.content.hashCode(), nbtByte.hashCode())
    }

    @Test
    fun creating_from_false_boolean_should_return_0b() {
        assertEquals(NbtByte(0), NbtByte(false))
    }

    @Test
    fun creating_from_true_boolean_should_return_1b() {
        assertEquals(NbtByte(1), NbtByte(true))
    }

    @Test
    fun converting_0b_to_boolean_should_be_false() {
        assertEquals(false, NbtByte(0).toBoolean())
    }

    @Test
    fun converting_non_zero_to_boolean_should_be_true() = parameterizeTest {
        val nonZeroNbtByte by parameterOfNbtByteEdgeCases()
        assume(nonZeroNbtByte.content != 0.toByte())

        assertEquals(true, nonZeroNbtByte.toBoolean())
    }
}

class NbtShortTest {
    @Test
    fun should_equal_another_NbtShort_with_the_same_value() = parameterizeTest {
        val nbtShort by parameterOfNbtShortEdgeCases()
        val nbtShortWithSameValue = NbtShort(nbtShort.content)

        assertEquals(nbtShortWithSameValue, nbtShort)
    }

    @Test
    fun should_not_equal_another_NbtShort_with_different_value() = parameterizeTest {
        val nbtShort by parameterOfNbtShortEdgeCases()
        val differentNbtShort by parameterOfNbtShortEdgeCases()
        assume(nbtShort.content != differentNbtShort.content)

        assertNotEquals(nbtShort, differentNbtShort)
    }

    @Test
    fun hash_code_should_be_the_value_hash_code() = parameterizeTest {
        val nbtShort by parameterOfNbtShortEdgeCases()

        assertEquals(nbtShort.content.hashCode(), nbtShort.hashCode())
    }
}

class NbtIntTest {
    @Test
    fun should_equal_another_NbtInt_with_the_same_value() = parameterizeTest {
        val nbtInt by parameterOfNbtIntEdgeCases()
        val nbtIntWithSameValue = NbtInt(nbtInt.content)

        assertEquals(nbtIntWithSameValue, nbtInt)
    }

    @Test
    fun should_not_equal_another_NbtInt_with_different_value() = parameterizeTest {
        val nbtInt by parameterOfNbtIntEdgeCases()
        val differentNbtInt by parameterOfNbtIntEdgeCases()
        assume(nbtInt.content != differentNbtInt.content)

        assertNotEquals(nbtInt, differentNbtInt)
    }

    @Test
    fun hash_code_should_be_the_value_hash_code() = parameterizeTest {
        val nbtInt by parameterOfNbtIntEdgeCases()

        assertEquals(nbtInt.content.hashCode(), nbtInt.hashCode())
    }
}

class NbtLongTest {
    @Test
    fun should_equal_another_NbtLong_with_the_same_value() = parameterizeTest {
        val nbtLong by parameterOfNbtLongEdgeCases()
        val nbtLongWithSameValue = NbtLong(nbtLong.content)

        assertEquals(nbtLongWithSameValue, nbtLong)
    }

    @Test
    fun should_not_equal_another_NbtLong_with_different_value() = parameterizeTest {
        val nbtLong by parameterOfNbtLongEdgeCases()
        val differentNbtLong by parameterOfNbtLongEdgeCases()
        assume(nbtLong.content != differentNbtLong.content)

        assertNotEquals(nbtLong, differentNbtLong)
    }

    @Test
    fun hash_code_should_be_the_value_hash_code() = parameterizeTest {
        val nbtLong by parameterOfNbtLongEdgeCases()

        assertEquals(nbtLong.content.hashCode(), nbtLong.hashCode())
    }
}

class NbtFloatTest {
    @Test
    fun should_equal_another_NbtFloat_with_the_same_value_bits() = parameterizeTest {
        val nbtFloat by parameterOfNbtFloatEdgeCases()
        val nbtFloatWithSameValue = NbtFloat(nbtFloat.content)

        assertEquals(nbtFloatWithSameValue, nbtFloat)
    }

    @Test
    fun should_not_equal_another_NbtFloat_with_different_value_bits() = parameterizeTest {
        val nbtFloat by parameterOfNbtFloatEdgeCases()
        val differentNbtFloat by parameterOfNbtFloatEdgeCases()
        assume(nbtFloat.content.toRawBits() != differentNbtFloat.content.toRawBits())

        assertNotEquals(nbtFloat, differentNbtFloat)
    }

    @Test
    fun hash_code_should_be_the_value_bits_hash_code() = parameterizeTest {
        val nbtFloat by parameterOfNbtFloatEdgeCases()

        assertEquals(nbtFloat.content.toRawBits().hashCode(), nbtFloat.hashCode())
    }
}

class NbtDoubleTest {
    @Test
    fun should_equal_another_NbtDouble_with_the_same_value_bits() = parameterizeTest {
        val nbtDouble by parameterOfNbtDoubleEdgeCases()
        val nbtDoubleWithSameValue = NbtDouble(nbtDouble.content)

        assertEquals(nbtDoubleWithSameValue, nbtDouble)
    }

    @Test
    fun should_not_equal_another_NbtDouble_with_different_value_bits() = parameterizeTest {
        val nbtDouble by parameterOfNbtDoubleEdgeCases()
        val differentNbtDouble by parameterOfNbtDoubleEdgeCases()
        assume(nbtDouble.content.toRawBits() != differentNbtDouble.content.toRawBits())

        assertNotEquals(nbtDouble, differentNbtDouble)
    }

    @Test
    fun hash_code_should_be_the_value_bits_hash_code() = parameterizeTest {
        val nbtDouble by parameterOfNbtDoubleEdgeCases()

        assertEquals(nbtDouble.content.toRawBits().hashCode(), nbtDouble.hashCode())
    }
}

class NbtStringTest {
    @Test
    fun should_equal_another_NbtString_with_the_same_value() = parameterizeTest {
        val nbtString by parameterOfNbtStringEdgeCases()
        val nbtStringWithSameValue = NbtString(nbtString.content)

        assertEquals(nbtStringWithSameValue, nbtString)
    }

    @Test
    fun should_not_equal_another_NbtString_with_different_value() = parameterizeTest {
        val nbtString by parameterOfNbtStringEdgeCases()
        val differentNbtString by parameterOfNbtStringEdgeCases()
        assume(nbtString.content != differentNbtString.content)

        assertNotEquals(nbtString, differentNbtString)
    }

    @Test
    fun hash_code_should_be_the_value_hash_code() = parameterizeTest {
        val nbtString by parameterOfNbtStringEdgeCases()

        assertEquals(nbtString.content.hashCode(), nbtString.hashCode())
    }
}

class NbtTagToStringTest {
    @Test
    fun converting_NbtByte_to_string() {
        assertEquals("7b", NbtByte(7).toString())
    }

    @Test
    fun converting_NbtShort_to_string() {
        assertEquals("8s", NbtShort(8).toString())
    }

    @Test
    fun converting_NbtInt_to_string() {
        assertEquals("9", NbtInt(9).toString())
    }

    @Test
    fun converting_NbtLong_to_string() {
        assertEquals("10L", NbtLong(10).toString())
    }

    @Test
    fun converting_NbtFloat_to_string() {
        assertEquals("3.1415f", NbtFloat(3.1415f).toString())
    }

    @Test
    fun converting_NbtDouble_to_string() {
        assertEquals("2.71828d", NbtDouble(2.71828).toString())
    }

    @Test
    fun converting_NbtByteArray_to_string() {
        assertEquals("[B;4B,3B,2B,1B]", NbtByteArray(byteArrayOf(4, 3, 2, 1)).toString())
    }

    @Test
    fun converting_NbtString_to_string() {
        assertEquals("\"\"", NbtString("").toString())
        assertEquals("\"hello\"", NbtString("hello").toString())
        assertEquals("\"\\\"double-quoted\\\"\"", NbtString("\"double-quoted\"").toString())
        assertEquals("\"'single-quoted'\"", NbtString("'single-quoted'").toString())
        assertEquals("\"\\\"'multi-quoted'\\\"\"", NbtString("\"'multi-quoted'\"").toString())
    }

    @Test
    fun converting_NbtList_to_string() {
        assertEquals("[]", NbtList<NbtTag>().toString())
        assertEquals("[1b]", NbtList(mutableListOf(NbtByte(1))).toString())
        assertEquals("[[]]", NbtList(mutableListOf(NbtList<NbtTag>())).toString())
    }

    @Test
    fun converting_NbtCompound_to_string() {
        assertEquals("{}", buildNbtCompound { }.toString())
        assertEquals("{a:1b}", buildNbtCompound { put("a", 1.toByte()) }.toString())
        assertEquals("{a:1b,b:7}", buildNbtCompound { put("a", 1.toByte()); put("b", 7) }.toString())
    }

    @Test
    fun converting_NbtIntArray_to_string() {
        assertEquals("[I;4,3,2,1]", NbtIntArray(intArrayOf(4, 3, 2, 1)).toString())
    }

    @Test
    fun converting_NbtLongArray_to_string() {
        assertEquals("[L;4L,3L,2L,1L]", NbtLongArray(longArrayOf(4, 3, 2, 1)).toString())
    }
}

class NbtTagTestConversions {
    @Test
    fun should_convert_correctly_with_properties() {
        fun <R : NbtTag> assertConversionSucceeds(tag: NbtTag, convert: KProperty1<NbtTag, R>): Unit =
            assertEquals(tag, convert(tag), "Converting ${tag::class.simpleName} with ${convert.name}")

        fun <R : NbtTag> assertConversionFails(tag: NbtTag, convert: KProperty1<NbtTag, R>): RuntimeException =
            assertFailsWith<IllegalArgumentException>("Converting ${tag::class.simpleName} with ${convert.name}") {
                convert(tag)
            }

        assertConversionSucceeds(NbtByte(1), NbtTag::nbtByte)
        assertConversionFails(NbtShort(1), NbtTag::nbtByte)

        assertConversionSucceeds(NbtShort(1), NbtTag::nbtShort)
        assertConversionFails(NbtInt(1), NbtTag::nbtShort)

        assertConversionSucceeds(NbtInt(1), NbtTag::nbtInt)
        assertConversionFails(NbtLong(1), NbtTag::nbtInt)

        assertConversionSucceeds(NbtLong(1), NbtTag::nbtLong)
        assertConversionFails(NbtFloat(1.0f), NbtTag::nbtLong)

        assertConversionSucceeds(NbtFloat(1.0f), NbtTag::nbtFloat)
        assertConversionFails(NbtDouble(1.0), NbtTag::nbtFloat)

        assertConversionSucceeds(NbtDouble(1.0), NbtTag::nbtDouble)
        assertConversionFails(NbtByteArray(byteArrayOf(1)), NbtTag::nbtDouble)

        assertConversionSucceeds(NbtByteArray(byteArrayOf(1)), NbtTag::nbtByteArray)
        assertConversionFails(NbtString("1"), NbtTag::nbtByteArray)

        assertConversionSucceeds(NbtString("1"), NbtTag::nbtString)
        assertConversionFails(NbtList<NbtTag>(), NbtTag::nbtString)

        assertConversionSucceeds(NbtList<NbtTag>(), NbtTag::nbtList)
        assertConversionFails(NbtCompound(), NbtTag::nbtList)

        assertConversionSucceeds(NbtCompound(), NbtTag::nbtCompound)
        assertConversionFails(NbtIntArray(intArrayOf()), NbtTag::nbtCompound)

        assertConversionSucceeds(NbtIntArray(intArrayOf()), NbtTag::nbtIntArray)
        assertConversionFails(NbtLongArray(longArrayOf()), NbtTag::nbtIntArray)

        assertConversionSucceeds(NbtLongArray(longArrayOf()), NbtTag::nbtLongArray)
        assertConversionFails(NbtByte(1), NbtTag::nbtLongArray)
    }

    @Test
    @OptIn(ExperimentalNbtApi::class)
    fun should_convert_to_typed_NbtList_correctly() {
        fun <R : NbtTag> assertConversionSucceeds(tag: NbtTag, convert: NbtTag.() -> R): Unit =
            assertEquals(tag, convert(tag), "Converting ${tag::class.simpleName}")

        fun <R : NbtTag> assertConversionFails(tag: NbtTag, convert: NbtTag.() -> R): RuntimeException =
            assertFailsWith<IllegalArgumentException>("Converting ${tag::class.simpleName}") {
                convert(tag)
            }

        assertConversionSucceeds(NbtList<NbtTag>()) { nbtList<NbtByte>() }
        assertConversionSucceeds(NbtList<NbtTag>()) { nbtList<NbtShort>() }
        assertConversionSucceeds(NbtList<NbtTag>()) { nbtList<NbtInt>() }
        assertConversionSucceeds(NbtList<NbtTag>()) { nbtList<NbtLong>() }
        assertConversionSucceeds(NbtList<NbtTag>()) { nbtList<NbtFloat>() }
        assertConversionSucceeds(NbtList<NbtTag>()) { nbtList<NbtDouble>() }
        assertConversionSucceeds(NbtList<NbtTag>()) { nbtList<NbtByteArray>() }
        assertConversionSucceeds(NbtList<NbtTag>()) { nbtList<NbtString>() }
        assertConversionSucceeds(NbtList<NbtTag>()) { nbtList<NbtList<*>>() }
        assertConversionSucceeds(NbtList<NbtTag>()) { nbtList<NbtCompound>() }
        assertConversionSucceeds(NbtList<NbtTag>()) { nbtList<NbtIntArray>() }
        assertConversionSucceeds(NbtList<NbtTag>()) { nbtList<NbtLongArray>() }

        assertConversionSucceeds(NbtList(mutableListOf(NbtByte(1)))) { nbtList<NbtByte>() }
        assertConversionFails(NbtList(mutableListOf(NbtShort(1)))) { nbtList<NbtByte>() }

        assertConversionSucceeds(NbtList(mutableListOf(NbtShort(1)))) { nbtList<NbtShort>() }
        assertConversionFails(NbtList(mutableListOf(NbtInt(1)))) { nbtList<NbtShort>() }

        assertConversionSucceeds(NbtList(mutableListOf(NbtInt(1)))) { nbtList<NbtInt>() }
        assertConversionFails(NbtList(mutableListOf(NbtLong(1)))) { nbtList<NbtInt>() }

        assertConversionSucceeds(NbtList(mutableListOf(NbtLong(1)))) { nbtList<NbtLong>() }
        assertConversionFails(NbtList(mutableListOf(NbtFloat(1.0f)))) { nbtList<NbtLong>() }

        assertConversionSucceeds(NbtList(mutableListOf(NbtFloat(1.0f)))) { nbtList<NbtFloat>() }
        assertConversionFails(NbtList(mutableListOf(NbtDouble(1.0)))) { nbtList<NbtFloat>() }

        assertConversionSucceeds(NbtList(mutableListOf(NbtDouble(1.0)))) { nbtList<NbtDouble>() }
        assertConversionFails(NbtList(mutableListOf(NbtByteArray(byteArrayOf(1))))) { nbtList<NbtDouble>() }

        assertConversionSucceeds(NbtList(mutableListOf(NbtByteArray(byteArrayOf(1))))) { nbtList<NbtByteArray>() }
        assertConversionFails(NbtList(mutableListOf(NbtString("1")))) { nbtList<NbtByteArray>() }

        assertConversionSucceeds(NbtList(mutableListOf(NbtString("1")))) { nbtList<NbtString>() }
        assertConversionFails(NbtList(mutableListOf(NbtList<NbtTag>()))) { nbtList<NbtString>() }

        assertConversionSucceeds(NbtList(mutableListOf(NbtList<NbtTag>()))) { nbtList<NbtList<*>>() }
        assertConversionFails(NbtList(mutableListOf(NbtCompound()))) { nbtList<NbtList<*>>() }

        assertConversionSucceeds(NbtList(mutableListOf(NbtCompound()))) { nbtList<NbtCompound>() }
        assertConversionFails(NbtList(mutableListOf(NbtIntArray(intArrayOf())))) { nbtList<NbtCompound>() }

        assertConversionSucceeds(NbtList(mutableListOf(NbtIntArray(intArrayOf())))) { nbtList<NbtIntArray>() }
        assertConversionFails(NbtList(mutableListOf(NbtLongArray(longArrayOf())))) { nbtList<NbtIntArray>() }

        assertConversionSucceeds(NbtList(mutableListOf(NbtLongArray(longArrayOf())))) { nbtList<NbtLongArray>() }
        assertConversionFails(NbtList(mutableListOf(NbtByte(1)))) { nbtList<NbtLongArray>() }
    }
}

class NbtByteArrayTest {
    @Test
    fun should_equal_NbtByteArray_of_equal_content() = parameterizeTest {
        val content: List<Byte> by parameterOf(
            listOf(),
            listOf(1),
            listOf(1, 2, 4, 8)
        )

        assertEquals(
            NbtByteArray(content.toByteArray()),
            NbtByteArray(content.toList().toByteArray())
        )
    }
}

class NbtListTest {
    @Test
    fun should_equal_NbtList_with_equal_content() = parameterizeTest {
        val nbtList by parameterOfNbtListContentEdgeCases()
        val nbtListWithEqualContent by parameterOfNbtListContentEdgeCases()
        assume(nbtList.content == nbtListWithEqualContent.content)

        assertEquals(nbtListWithEqualContent, nbtList)
    }

    @Test
    fun should_not_equal_NbtList_with_different_content() = parameterizeTest {
        val nbtList by parameterOfNbtListContentEdgeCases()
        val nbtListWithDifferentContent by parameterOfNbtListContentEdgeCases()
        assume(nbtList.content != nbtListWithDifferentContent.content)

        assertNotEquals(nbtListWithDifferentContent, nbtList)
    }
}

class NbtCompoundTest {
    private val containedName = "containedName"
    private val containedTag = NbtString("containedTag")

    private val nonContainedName = "nonContainedName"

    private val content = mutableMapOf(containedName to containedTag as NbtTag)
    private val nbtCompound = NbtCompound(content)

    @Test
    fun getting_a_tag_with_a_contained_name_should_return_the_tag_from_content() {
        assertSame(containedTag, nbtCompound[containedName])
    }

    @Test
    fun getting_a_tag_with_a_non_contained_name_should_throw_a_NoSuchElementException() {
        assertFailsWith<NoSuchElementException> {
            nbtCompound[nonContainedName]
        }
    }

    @Test
    fun getting_a_tag_or_null_with_a_contained_name_should_return_the_tag_from_content() {
        assertSame(containedTag, nbtCompound[containedName])
    }

    @Test
    fun getting_a_tag_or_null_with_a_non_contained_name_should_return_null() {
        assertNull(nbtCompound[nonContainedName])
    }

    @Test
    fun checking_contains_with_a_name_in_the_content_should_return_true() {
        assertTrue(containedName in nbtCompound)
    }

    @Test
    fun checking_contains_with_a_name_not_in_the_content_should_return_false() {
        assertFalse(nonContainedName in nbtCompound)
    }


    @Test
    fun should_equal_NbtCompound_of_equal_contents() = parameterizeTest {
        val content: MutableMap<String, NbtTag> by parameterOf(
            mutableMapOf(),
            mutableMapOf("one" to NbtInt(1)),
            mutableMapOf("a" to NbtString("a"), "b" to NbtString("b"))
        )

        val differentButEqualContent = content.entries
            .map { it.toPair() }
            .reversed().toMap()

        assertEquals(
            NbtCompound(content),
            NbtCompound.of(differentButEqualContent)
        )
    }
}

class NbtIntArrayTest {
    @Test
    fun should_equal_NbtIntArray_of_equal_content() = parameterizeTest {
        val content by parameterOf(
            listOf(),
            listOf(1),
            listOf(1, 2, 4, 8)
        )

        assertEquals(
            NbtIntArray(content.toIntArray()),
            NbtIntArray(content.toList().toIntArray())
        )
    }
}

class NbtLongArrayTest {
    @Test
    fun should_equal_List_of_equal_content() = parameterizeTest {
        val content by parameterOf(
            listOf(),
            listOf(1L),
            listOf(1L, 2L, 4L, 8L)
        )

        assertEquals(
            NbtLongArray(content.toLongArray()),
            NbtLongArray(content.toList().toLongArray())
        )
    }
}
