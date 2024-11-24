package net.benwoodworth.knbt.tag

import kotlinx.serialization.Serializable

/**
 * NbtTag
 *
 * @author TheFloodDragon
 * @since 2024/11/24 10:27
 */
@Serializable(NbtTagSerializer::class)
public sealed interface NbtTag {

    /**
     * The type of the tag.
     */
    public val type: NbtType

    /**
     * The content of the tag.
     */
    public val content: Any

}