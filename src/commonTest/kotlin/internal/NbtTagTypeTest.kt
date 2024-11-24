package net.benwoodworth.knbt.internal

import com.benwoodworth.parameterize.parameter
import net.benwoodworth.knbt.*
import net.benwoodworth.knbt.test.assume
import net.benwoodworth.knbt.test.parameterizeTest
import net.benwoodworth.knbt.test.parameters.parameterOfBytes
import net.benwoodworth.knbt.test.reportedAs
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class NbtTypeTest {
    @Test
    fun converting_to_NbtType_or_null_from_a_valid_ID_byte_should_return_the_correct_tag() = parameterizeTest {
        val expectedType by parameter(NbtType.entries)
        val id = expectedType.id

        assertEquals(expectedType, id.toNbtTypeOrNull())
    }

    @Test
    fun converting_to_NbtType_or_null_from_an_invalid_ID_byte_should_return_null() = parameterizeTest {
        val invalidId by parameterOfBytes()
        assume(NbtType.entries.none { it.id == invalidId })

        assertNull(invalidId.toNbtTypeOrNull())
    }

    @Test
    fun converting_class_should_return_the_correct_tag_type() = parameterizeTest {
        fun NbtType.nbtTagClass() = when (this) {
            NbtType.NbtType.END -> Nothing::class
            NbtType.NbtType.BYTE -> NbtByte::class
            NbtType.NbtType.SHORT -> NbtShort::class
            NbtType.NbtType.INT -> NbtInt::class
            NbtType.NbtType.LONG -> NbtLong::class
            NbtType.NbtType.FLOAT -> NbtFloat::class
            NbtType.NbtType.DOUBLE -> NbtDouble::class
            NbtType.NbtType.BYTE_ARRAY -> NbtByteArray::class
            NbtType.NbtType.STRING -> NbtString::class
            NbtType.NbtType.LIST -> NbtList::class
            NbtType.NbtType.COMPOUND -> NbtCompound::class
            NbtType.NbtType.INT_ARRAY -> NbtIntArray::class
            NbtType.NbtType.LONG_ARRAY -> NbtLongArray::class
        }

        val expectedConversions = NbtType.entries.asSequence()
            .map { it.nbtTagClass() to it }

        val expectedConversion by parameter(expectedConversions)
            .reportedAs(this, "class") { it.first }

        val (nbtTagClass, NbtType) = expectedConversion

        assertEquals(NbtType, nbtTagClass.toNbtType())
    }
}
