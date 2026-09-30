package me.herry.zombieAC.events;

import me.herry.zombieAC.ZombieAC;
import me.herry.zombieAC.mutation.MutationHandler;
import me.herry.zombieAC.mutation.MutationType;
import me.herry.zombieAC.mutation.types.ZombieMutation;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class OnAttackPlayer implements Listener {

    @EventHandler
    public void onAttackPlayer(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Zombie zombie)) return;
        if (!(event.getEntity() instanceof Player player)) return;

        MutationHandler mh = new MutationHandler(zombie);
        MutationType type = mh.getMutation();

        ZombieMutation mutation = ZombieAC.getInstance().getMutationRegistry().get(type);
        mutation.onAttackPlayer(event, zombie, player);
    }
}
