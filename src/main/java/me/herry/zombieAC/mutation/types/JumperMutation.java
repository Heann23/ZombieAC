package me.herry.zombieAC.mutation.types;

import me.herry.zombieAC.mutation.MutationType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JumperMutation implements ZombieMutation{
    @Override
    public MutationType type() {
        return MutationType.JUMPER;
    }

    @Override
    public void apply(Zombie zombie) {
        PotionEffect jumpEff = new PotionEffect(PotionEffectType.JUMP_BOOST, PotionEffect.INFINITE_DURATION, 10, false, false);
        zombie.addPotionEffect(jumpEff);
    }

    @Override
    public void onFallDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }
}
