package me.herry.zombieAC.mutation.types;

import me.herry.zombieAC.mutation.MutationType;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JockeyMutation implements ZombieMutation{

    @Override
    public MutationType type() {
        return MutationType.JOCKEY;
    }

    @Override
    public void apply(Zombie zombie) {
        setAttribute(zombie, Attribute.SCALE, 0.8);
        PotionEffect jumpEff = new PotionEffect(PotionEffectType.JUMP_BOOST, PotionEffect.INFINITE_DURATION, 1, false, false);
        zombie.addPotionEffect(jumpEff);
    }

    @Override
    public void onAttackPlayer(EntityDamageByEntityEvent event, Zombie zombie, Player player) {
        if (!player.getPassengers().isEmpty()) return;
        player.addPassenger(zombie);
    }
}
