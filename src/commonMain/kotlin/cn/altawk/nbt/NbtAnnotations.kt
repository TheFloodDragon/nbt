@file:OptIn(ExperimentalSerializationApi::class)

package cn.altawk.nbt

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialInfo

/**
 * NbtArray
 *
 * @author TheFloodDragon
 * @since 2025/1/25 12:22
 */
@SerialInfo
@Target(AnnotationTarget.PROPERTY)
public annotation class NbtArray