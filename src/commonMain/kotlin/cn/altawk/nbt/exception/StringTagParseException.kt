package cn.altawk.nbt.exception

import java.io.IOException


/**
 * StringTagParseException
 *
 * An exception thrown when parsing a string tag.
 *
 * @author TheFloodDragon
 * @since 2025/3/15 11:21
 */
public class StringTagParseException(
    message: String?,
    private val buffer: CharSequence,
    private val position: Int
) : IOException(message) {

    override val message: String get() = super.message + "(at position " + this.position + ")"

}