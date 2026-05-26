package me.herry.zombieAC.events;

import me.herry.zombieAC.MUTATION_TYPE;
import me.herry.zombieAC.MutationHandler;
import me.herry.zombieAC.ZombieAC;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class OnDeath implements Listener {

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        Entity entity = e.getEntity();
        if (!(entity instanceof Zombie zombie)) return;
        Location loc = zombie.getLocation();

        MutationHandler mh = new MutationHandler(zombie);

        if (mh.getMutation() == MUTATION_TYPE.BOOMER) {             // Boomer
            e.setCancelled(true);
            zombie.setInvulnerable(true);
            zombie.setAI(false);

            loc.getWorld().playSound(loc, Sound.ENTITY_CREEPER_PRIMED, 1.0f, 1.0f);
            new BukkitRunnable() {
                public void run() {
                    if (!zombie.isDead()) {
                        loc.getWorld().createExplosion(loc, 3.0f);
                        zombie.remove();
                    }
                }
            }.runTaskLater(ZombieAC.getInstance(), 20L);
        } else if (mh.getMutation() == MUTATION_TYPE.PARASITE) {    // Parasite
            @SuppressWarnings("unchecked")
            Class<? extends Zombie> zombieClass = (Class<? extends Zombie>) zombie.getType().getEntityClass();

            if (zombieClass == null) return;

            int tier = mh.getSplitTier();
            if (tier < 2) return;

            for (int i = 0; i <= tier; i++) {
                // 소환 위치에 무작위 오차 부여
                double offsetX = (Math.random() - 0.5);
                double offsetZ = (Math.random() - 0.5);
                Location spawnLoc = loc.clone().add(offsetX, 0.5, offsetZ);

                loc.getWorld().spawn(spawnLoc, zombieClass, (newZombie -> {
                    MutationHandler mh1 = new MutationHandler(newZombie);
                    mh1.setMutation(MUTATION_TYPE.PARASITE);
                    mh1.setSplitTier(tier - 1);

                    // 사방으로 퍼지는 효과
                    Vector vector = new Vector((Math.random() - 0.5) * 0.5, 0.3, (Math.random() - 0.5) * 0.5);
                    newZombie.setVelocity(vector);
                }), CreatureSpawnEvent.SpawnReason.SLIME_SPLIT);
            }

            loc.getWorld().playSound(loc, Sound.ENTITY_SLIME_DEATH, 1.5f, 2.0f);
        } else if (mh.getMutation() == MUTATION_TYPE.TANKER) {
            int currentExp = e.getDroppedExp();

            e.setDroppedExp(currentExp * 25);

            if (Math.random() < 0.3) e.getDrops().add(new ItemStack(Material.GOLDEN_APPLE, 1));
        }
    }
}
