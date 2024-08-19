package net.benwoodworth.knbt.external

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.*
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.benwoodworth.knbt.*
import net.benwoodworth.knbt.test.parameterizeTest
import net.benwoodworth.knbt.test.parameters.parameterOfSerializableTypeEdgeCases
import net.benwoodworth.knbt.test.parameters.parameterOfVerifyingNbt
import kotlin.test.Test
import kotlin.test.assertContentEquals

class ListSerializerTest {
    @Test
    fun should_serialize_List_to_NbtList() = parameterizeTest {
        val nbt by parameterOfVerifyingNbt()

        nbt.verifyEncoderOrDecoder(
            ListSerializer(Unit.serializer()),
            listOf(),
            NbtList(listOf()),
            testDecodedValue = { value, decodedValue ->
                assertContentEquals(value, decodedValue, "decodedValue")
            }
        )
    }

    @Test
    @OptIn(UnsafeNbtApi::class)
    fun should_serialize_List_of_value_correctly() = parameterizeTest {
        val nbt by parameterOfVerifyingNbt()
        val serializableType by parameterOfSerializableTypeEdgeCases()

        val serializer = object : KSerializer<Unit> {
            override val descriptor: SerialDescriptor = serializableType.baseDescriptor

            override fun serialize(encoder: Encoder, value: Unit): Unit =
                serializableType.encodeValue(encoder, descriptor)

            override fun deserialize(decoder: Decoder): Unit =
                serializableType.decodeValue(decoder, descriptor)
        }

        nbt.verifyEncoderOrDecoder(
            ListSerializer(serializer),
            listOf(Unit),
            NbtList(listOf(serializableType.valueTag)),
            testDecodedValue = { value, decodedValue ->
                assertContentEquals(value, decodedValue, "decodedValue")
            }
        )
    }

    @Test
    fun should_serialize_List_of_Lists_correctly() = parameterizeTest {
        val nbt by parameterOfVerifyingNbt()

        nbt.verifyEncoderOrDecoder(
            ListSerializer(ListSerializer(Byte.serializer())),
            listOf(listOf(1.toByte()), listOf()),
            buildNbtList<NbtList<*>> {
                addNbtList<NbtByte> { add(1.toByte()) }
                addNbtList<NbtInt> { add(1) } // Different in case the entry type carries over somehow
            },
            testDecodedValue = { value, decodedValue ->
                assertContentEquals(value, decodedValue, "decodedValue")
            }
        )
    }
}
