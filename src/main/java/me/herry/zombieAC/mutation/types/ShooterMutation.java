package me.herry.zombieAC.mutation.types;

import me.herry.zombieAC.ZombieAC;
import me.herry.zombieAC.mutation.MutationType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ShooterMutation implements ZombieMutation{

    @Override
    public MutationType type() {
        return MutationType.SHOOTER;
    }

    @Override
    public void onAttackPlayer(EntityDamageByEntityEvent event, Zombie zombie, Player player) {
        final Vector vector = new Vector(zombie.getLocation().getDirection().getX(), 1.5, zombie.getLocation().getDirection().getZ());

        zombie.addPassenger(player);

        new BukkitRunnable() {
            public void run() {
                player.setVelocity(vector);
            }
        }.runTaskLater(ZombieAC.getInstance(), 2L);

        zombie.removePassenger(player);
    }
}
