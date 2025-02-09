package team.dovecotmc.metropolis.abstractinterface.util;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

/**
 * @author Arrokoth
 * @project Metropolis
 * @copyright Copyright © 2025 Arrokoth All Rights Reserved.
 */
public class MALocalizationUtil {
    public static MutableText translatableText(String key) {
        return Text.translatable(key);
    }

    public static MutableText translatableText(String key, Object... args) {
        return Text.translatable(key, args);
    }

    public static MutableText literalText(String text) {
        return Text.literal(text);
    }

    public static MutableText empty() {
        return Text.empty();
    }
}
