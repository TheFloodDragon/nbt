package net.benwoodworth.knbt.external

import com.benwoodworth.parameterize.parameter
import kotlinx.serialization.builtins.serializer
import net.benwoodworth.knbt.tag.NbtByte
import net.benwoodworth.knbt.tag.NbtString
import net.benwoodworth.knbt.test.parameterizeTest
import net.benwoodworth.knbt.test.parameters.*
import kotlin.test.Test
import kotlin.test.assertEquals

class PrimitiveSerializerTest {
    @Test
    fun should_serialize_Byte_correctly() = parameterizeTest {
        val nbt by parameterOfVerifyingNbt()
        val nbtByte by parameterOfNbtByteEdgeCases()

        nbt.verifyEncoderOrDecoder(
            Byte.serializer(),
            nbtByte.content,
            nbtByte,
            testDecodedValue = { value, decodedValue ->
                assertEquals(value, decodedValue, "decodedValue")
            }
        )
    }

    @Test
    fun should_serialize_Short_correctly() = parameterizeTest {
        val nbt by parameterOfVerifyingNbt()
        val nbtShort by parameterOfNbtShortEdgeCases()

        nbt.verifyEncoderOrDecoder(
            Short.serializer(),
            nbtShort.content,
            nbtShort,
            testDecodedValue = { value, decodedValue ->
                assertEquals(value, decodedValue, "decodedValue")
            }
        )
    }

    @Test
    fun should_serialize_Int_correctly() = parameterizeTest {
        val nbt by parameterOfVerifyingNbt()
        val nbtInt by parameterOfNbtIntEdgeCases()

        nbt.verifyEncoderOrDecoder(
            Int.serializer(),
            nbtInt.content,
            nbtInt,
            testDecodedValue = { value, decodedValue ->
                assertEquals(value, decodedValue, "decodedValue")
            }
        )
    }

    @Test
    fun should_serialize_Long_correctly() = parameterizeTest {
        val nbt by parameterOfVerifyingNbt()
        val nbtLong by parameterOfNbtLongEdgeCases()

        nbt.verifyEncoderOrDecoder(
            Long.serializer(),
            nbtLong.content,
            nbtLong,
            testDecodedValue = { value, decodedValue ->
                assertEquals(value, decodedValue, "decodedValue")
            }
        )
    }

    @Test
    fun should_serialize_Float_correctly() = parameterizeTest {
        val nbt by parameterOfVerifyingNbt()
        val nbtFloat by parameterOfNbtFloatEdgeCases()

        nbt.verifyEncoderOrDecoder(
            Float.serializer(),
            nbtFloat.content,
            nbtFloat,
            testDecodedValue = { value, decodedValue ->
                assertEquals(value, decodedValue, "decodedValue")
            }
        )
    }

    @Test
    fun should_serialize_Double_correctly() = parameterizeTest {
        val nbt by parameterOfVerifyingNbt()
        val nbtDouble by parameterOfNbtDoubleEdgeCases()

        nbt.verifyEncoderOrDecoder(
            Double.serializer(),
            nbtDouble.content,
            nbtDouble,
            testDecodedValue = { value, decodedValue ->
                assertEquals(value, decodedValue, "decodedValue")
            }
        )
    }

    @Test
    fun should_serialize_booleans_according_to_NbtByte_boolean_converter() = parameterizeTest {
        val nbt by parameterOfVerifyingNbt()
        val boolean by parameterOfBooleans()

        nbt.verifyEncoderOrDecoder(
            Boolean.serializer(),
            boolean,
            NbtByte(boolean),
            testDecodedValue = { value, decodedValue ->
                assertEquals(value, decodedValue, "decodedValue")
            }
        )
    }

    @Test
    fun should_deserialize_booleans_according_to_NbtByte_boolean_converter() = parameterizeTest {
        val nbt by parameterOfDecoderVerifyingNbt()
        val nbtByte by parameterOfNbtByteEdgeCases()

        nbt.verifyDecoder(
            Boolean.serializer(),
            nbtByte,
            testDecodedValue = { decodedValue ->
                assertEquals(nbtByte.toBoolean(), decodedValue)
            }
        )
    }

    @Test
    fun should_serialize_Char_correctly() = parameterizeTest {
        val nbt by parameterOfVerifyingNbt()

        // Character that appears in the NbtString edge cases, potentially being an incomplete part of a surrogate pair.
        val char by parameter {
            this@parameterizeTest.parameterOfNbtStringEdgeCases().arguments
                .flatMap { it.content.asIterable() }
        }

        nbt.verifyEncoderOrDecoder(
            Char.serializer(),
            char,
            NbtString(char.toString()),
            testDecodedValue = { value, decodedValue ->
                assertEquals(value, decodedValue, "decodedValue")
            }
        )
    }
}
