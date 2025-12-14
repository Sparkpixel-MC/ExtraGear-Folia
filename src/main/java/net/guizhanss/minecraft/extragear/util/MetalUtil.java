package net.guizhanss.minecraft.extragear.util;

import org.apache.commons.lang3.Validate;

public class MetalUtil {
    private MetalUtil() {}

    public static String getName(String id) {
        Validate.notNull(id, "Metal id cannot be null");

        if (id.equals("HARDENED")) {
            return "硬化金属";
        } else if (id.equals("REINFORCED")) {
            return "强化合金";
        }
        return id;
    }
}