package me.herry.zombieAC.mutation.types;

import me.herry.zombieAC.ZombieAC;
import me.herry.zombieAC.mutation.MutationType;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class BoomerMutation implements ZombieMutation {

    @Override
    public MutationType type() {
        return MutationType.BOOMER;
    }

    @Override
    public void apply(Zombie zombie) {
        setMaxHealth(zombie, 3.0);
        setAttribute(zombie, Attribute.MOVEMENT_SPEED, 0.2);
    }

    @Override
    public void onDeath(EntityDeathEvent event, Zombie zombie) {
        Location loc = zombie.getLocation();

        event.setCancelled(true);
        zombie.setInvulnerable(true);
        zombie.setAI(false);

        loc.getWorld().playSound(loc, Sound.ENTITY_CREEPER_PRIMED, 1.0f, 1.0f);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (zombie.isDead()) return;

                loc.getWorld().createExplosion(loc, 3.0f);
                zombie.remove();
            }
        }.runTaskLater(ZombieAC.getInstance(), 20L);
    }

}
