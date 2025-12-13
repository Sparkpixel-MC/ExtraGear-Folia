package net.guizhanss.minecraft.extragear.util;

import org.apache.commons.lang3.Validate;

import net.guizhanss.guizhanlib.slimefun.Metals;

public class MetalUtil {
    private MetalUtil() {}

    public static String getName(String id) {
        Validate.notNull(id, "Metal id cannot be null");

        if (id.equals("HARDENED")) {
            id = "HARDENED_METAL";
        } else if (id.equals("REINFORCED")) {
            id = "REINFORCED_ALLOY";
        }

        return Metals.getType(id);
    }
}
