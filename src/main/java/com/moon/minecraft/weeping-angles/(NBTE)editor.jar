package com.moon.minecraft;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public class NBTEditor {
    private static final String VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    private static final HashMap<Class<?>, Class<?>> NBT_CLASSES = new HashMap<>();
    private static final HashMap<Class<?>, Constructor<?>> CONSTRUCTOR_CACHE = new HashMap<>();
    private static final HashMap<Class<?>, Method> METHOD_CACHE = new HashMap<>();
    private static final HashMap<Class<?>, Field> FIELD_CACHE = new HashMap<>();
    private static final HashMap<String, Class<?>> CLASS_CACHE = new HashMap<>();

    static {
        try {
            CLASS_CACHE.put("NBTTagCompound", Class.forName("net.minecraft.server." + VERSION + ".NBTTagCompound"));
            CLASS_CACHE.put("NBTTagList", Class.forName("net.minecraft.server." + VERSION + ".NBTTagList"));
            CLASS_CACHE.put("NBTTagByte", Class.forName("net.minecraft.server." + VERSION + ".NBTTagByte"));
            CLASS_CACHE.put("NBTTagString", Class.forName("net.minecraft.server." + VERSION + ".NBTTagString"));
            CLASS_CACHE.put("NBTTagDouble", Class.forName("net.minecraft.server." + VERSION + ".NBTTagDouble"));
            CLASS_CACHE.put("NBTTagInt", Class.forName("net.minecraft.server." + VERSION + ".NBTTagInt"));
            CLASS_CACHE.put("NBTTagLong", Class.forName("net.minecraft.server." + VERSION + ".NBTTagLong"));
            CLASS_CACHE.put("NBTTagShort", Class.forName("net.minecraft.server." + VERSION + ".NBTTagShort"));
            CLASS_CACHE.put("NBTTagFloat", Class.forName("net.minecraft.server." + VERSION + ".NBTTagFloat"));
            CLASS_CACHE.put("NBTTagByteArray", Class.forName("net.minecraft.server." + VERSION + ".NBTTagByteArray"));
            CLASS_CACHE.put("NBTTagIntArray", Class.forName("net.minecraft.server." + VERSION + ".NBTTagIntArray"));
            CLASS_CACHE.put("ItemStack", Class.forName("net.minecraft.server." + VERSION + ".ItemStack"));
            CLASS_CACHE.put("CraftItemStack", Class.forName("org.bukkit.craftbukkit." + VERSION + ".inventory.CraftItemStack"));
            CLASS_CACHE.put("Entity", Class.forName("net.minecraft.server." + VERSION + ".Entity"));
            CLASS_CACHE.put("CraftEntity", Class.forName("org.bukkit.craftbukkit." + VERSION + ".entity.CraftEntity"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        NBT_CLASSES.put(Byte.class, CLASS_CACHE.get("NBTTagByte"));
        NBT_CLASSES.put(String.class, CLASS_CACHE.get("NBTTagString"));
        NBT_CLASSES.put(Double.class, CLASS_CACHE.get("NBTTagDouble"));
        NBT_CLASSES.put(Integer.class, CLASS_CACHE.get("NBTTagInt"));
        NBT_CLASSES.put(Long.class, CLASS_CACHE.get("NBTTagLong"));
        NBT_CLASSES.put(Short.class, CLASS_CACHE.get("NBTTagShort"));
        NBT_CLASSES.put(Float.class, CLASS_CACHE.get("NBTTagFloat"));
        NBT_CLASSES.put(byte[].class, CLASS_CACHE.get("NBTTagByteArray"));
        NBT_CLASSES.put(int[].class, CLASS_CACHE.get("NBTTagIntArray"));

        for (Class<?> clazz : CLASS_CACHE.values()) {
            try {
                FIELD_CACHE.put(clazz, clazz.getDeclaredField("data"));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

    public static Object getNMSItemStack(ItemStack itemStack) {
        try {
            Method asNMSCopyMethod = CLASS_CACHE.get("CraftItemStack").getMethod("asNMSCopy", ItemStack.class);
            return asNMSCopyMethod.invoke(null, itemStack);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ItemStack getBukkitItemStack(Object nmsItemStack) {
        try {
            Method asBukkitCopyMethod = CLASS_CACHE.get("CraftItemStack").getMethod("asBukkitCopy", CLASS_CACHE.get("ItemStack"));
            return (ItemStack) asBukkitCopyMethod.invoke(null, nmsItemStack);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Implement other methods as needed...
}
