package com.moon.minecraft;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class WeepingAngelsMain extends JavaPlugin implements Listener {
    private boolean blacklist;
    private Set<UUID> angels = new HashSet<>();
    private Set<UUID> statues = new HashSet<>();
    private Set<String> worlds = new HashSet<>();
    private double dotProductMax = 0.0d;
    private int speedAmplifier = 10;
    private boolean ignoreCreativeAndSpectator = true;

    BukkitRunnable entityManager = new BukkitRunnable() {
        @Override
        public void run() {
            Iterator<UUID> it = angels.iterator();
            while (it.hasNext()) {
                Entity entity = Bukkit.getEntity(it.next());
                if (entity != null) {
                    if (worlds.contains(entity.getWorld().getName()) ^ blacklist) {
                        boolean isStatue = false;
                        Iterator<Player> playerIterator = Bukkit.getOnlinePlayers().iterator();
                        while (playerIterator.hasNext()) {
                            Player player = playerIterator.next();
                            if (!player.hasPermission("weepingangels.bypass") &&
                                    ((player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR) ||
                                            !ignoreCreativeAndSpectator)) {
                                if (entity.getWorld().getUID().equals(player.getWorld().getUID())) {
                                    Vector toMob = entity.getLocation().subtract(player.getLocation()).toVector().normalize();
                                    Vector facing = player.getLocation().getDirection();
                                    if (facing.dot(toMob) > dotProductMax && player.hasLineOfSight(entity)) {
                                        if (!statues.contains(entity.getUniqueId())) {
                                            statues.add(entity.getUniqueId());
                                            editEntity(entity, true);
                                        }
                                        isStatue = true;
                                    }
                                }
                            }
                        }
                        if (!isStatue && statues.contains(entity.getUniqueId())) {
                            statues.remove(entity.getUniqueId());
                            editEntity(entity, false);
                        }
                    } else {
                        return;
                    }
                } else {
                    it.remove();
                }
            }
        }
    };

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (isStatue(entity)) {
                    angels.add(entity.getUniqueId());
                }
            }
        }
        entityManager.runTaskTimer(this, 0L, 1L);
    }

    private void loadConfig() {
        YamlConfiguration loadConfiguration = YamlConfiguration.loadConfiguration(new File(getDataFolder() + "/config.yml"));
        dotProductMax = loadConfiguration.getDouble("dot-product-maximum");
        speedAmplifier = loadConfiguration.getInt("monster-speed");
        ignoreCreativeAndSpectator = loadConfiguration.getBoolean("ignore-creative-and-spectator");
        worlds.addAll(loadConfiguration.getStringList("worlds"));
        blacklist = loadConfiguration.getBoolean("world-blacklist");
    }

    @EventHandler
    public void onEntitySpawnEvent(EntitySpawnEvent event) {
        if (isStatue(event.getEntity())) {
            angels.add(event.getEntity().getUniqueId());
        }
    }

    @EventHandler
    public void onEntityDie(ExplosionPrimeEvent event) {
        LivingEntity entity = event.getEntity();
        if (statues.contains(entity.getUniqueId()) && (entity instanceof LivingEntity)) {
            entity.removePotionEffect(PotionEffectType.SPEED);
        }
    }

    public boolean isStatue(Entity entity) {
        return entity instanceof Monster;
    }

    public void editEntity(Entity entity, boolean lookingAt) {
        if (lookingAt) {
            NBTEditor.setEntityTag(entity, (byte) 1, "NoAI");
            if ((entity instanceof LivingEntity) && (entity instanceof Monster)) {
                LivingEntity monster = (LivingEntity) entity;
                monster.removePotionEffect(PotionEffectType.SPEED);
            }
        } else {
            NBTEditor.setEntityTag(entity, (byte) 0, "NoAI");
            if ((entity instanceof LivingEntity) && (entity instanceof Monster)) {
                LivingEntity monster2 = (LivingEntity) entity;
                monster2.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, speedAmplifier, true), true);
            }
        }
    }
}
