package me.herry.zombieAC.events;

import me.herry.zombieAC.ZombieAC;
import me.herry.zombieAC.mutation.MutationHandler;
import me.herry.zombieAC.mutation.types.ZombieMutation;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class OnFallDamage implements Listener {

    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) return;
        if (!(event.getEntity() instanceof Zombie zombie)) return;

        MutationHandler mh = new MutationHandler(zombie);
        ZombieMutation mutation = ZombieAC.getInstance().getMutationRegistry().get(mh.getMutation());

        mutation.onFallDamage(event);
    }
}
