package me.herry.zombieAC.events;

import me.herry.zombieAC.MUTATION_TYPE;
import me.herry.zombieAC.MutationHandler;
import me.herry.zombieAC.ZombieAC;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class OnDamage implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Zombie zombie)) return;
        if (!(event.getEntity() instanceof Player player)) return;

        MutationHandler mh = new MutationHandler(zombie);
        if (mh.getMutation() == MUTATION_TYPE.SHOOTER) {
            final Vector vector = new Vector(zombie.getLocation().getDirection().getX(), 1.5, zombie.getLocation().getDirection().getZ());
    
            zombie.addPassenger(player);

            new BukkitRunnable() {
                public void run() {
                    player.setVelocity(vector);
                }
            }.runTaskLater(ZombieAC.getInstance(), 2L);

            zombie.removePassenger(player);
        } else if (mh.getMutation() == MUTATION_TYPE.JOCKEY) {
            if (!player.getPassengers().isEmpty()) return;
            player.addPassenger(zombie);
        }
    }
}
