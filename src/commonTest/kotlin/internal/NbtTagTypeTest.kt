package net.benwoodworth.knbt.internal

import com.benwoodworth.parameterize.parameter
import net.benwoodworth.knbt.tag.*
import net.benwoodworth.knbt.test.assume
import net.benwoodworth.knbt.test.parameterizeTest
import net.benwoodworth.knbt.test.parameters.parameterOfBytes
import net.benwoodworth.knbt.test.reportedAs
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class NbtTagTypeTest {
    @Test
    fun converting_to_NbtTagType_or_null_from_a_valid_ID_byte_should_return_the_correct_tag() = parameterizeTest {
        val expectedType by parameter(NbtType.entries)
        val id = expectedType.id

        assertEquals(expectedType, NbtType.from(id))
    }

    @Test
    fun converting_to_NbtTagType_or_null_from_an_invalid_ID_byte_should_return_null() = parameterizeTest {
        val invalidId by parameterOfBytes()
        assume(NbtType.entries.none { it.id == invalidId })

        assertNull(NbtType.from(invalidId))
    }

    @Test
    fun converting_class_should_return_the_correct_tag_type() = parameterizeTest {
        fun NbtType.nbtTagClass() = when (this) {
            NbtType.END -> Nothing::class
            NbtType.BYTE -> NbtByte::class
            NbtType.SHORT -> NbtShort::class
            NbtType.INT -> NbtInt::class
            NbtType.LONG -> NbtLong::class
            NbtType.FLOAT -> NbtFloat::class
            NbtType.DOUBLE -> NbtDouble::class
            NbtType.BYTE_ARRAY -> NbtByteArray::class
            NbtType.STRING -> NbtString::class
            NbtType.LIST -> NbtList::class
            NbtType.COMPOUND -> NbtCompound::class
            NbtType.INT_ARRAY -> NbtIntArray::class
            NbtType.LONG_ARRAY -> NbtLongArray::class
        }

        val expectedConversions = NbtType.entries.asSequence()
            .map { it.nbtTagClass() to it }

        val expectedConversion by parameter(expectedConversions)
            .reportedAs(this, "class") { it.first }

        val (nbtTagClass, nbtTagType) = expectedConversion

        assertEquals(nbtTagType, NbtType.from(nbtTagClass))
    }
}
