package me.herry.zombieAC.mutation.types;

import me.herry.zombieAC.ZombieAC;
import me.herry.zombieAC.mutation.MutationHandler;
import me.herry.zombieAC.mutation.MutationType;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

public class ParasiteMutation implements ZombieMutation{

    @Override
    public MutationType type() {
        return MutationType.PARASITE;
    }

    @Override
    public void onDeath(EntityDeathEvent event, Zombie zombie) {
        Location loc = event.getEntity().getLocation();

        @SuppressWarnings("unchecked")
        Class<? extends Zombie> zombieClass = (Class<? extends Zombie>) zombie.getType().getEntityClass();

        if (zombieClass == null) return;

        int tier = getSplitTier(zombie);
        if (tier < 2) return;

        for (int i = 0; i <= tier; i++) {
            // 소환 위치에 무작위 오차 부여
            double offsetX = (Math.random() - 0.5);
            double offsetZ = (Math.random() - 0.5);
            Location spawnLoc = loc.clone().add(offsetX, 0.5, offsetZ);

            loc.getWorld().spawn(spawnLoc, zombieClass, (newZombie -> {
                MutationHandler mh1 = new MutationHandler(newZombie);
                mh1.setAndApplyMutation(MutationType.PARASITE);
                setSplitTier(newZombie, tier - 1);

                // 사방으로 퍼지는 효과
                Vector vector = new Vector((Math.random() - 0.5) * 0.5, 0.3, (Math.random() - 0.5) * 0.5);
                newZombie.setVelocity(vector);
            }), CreatureSpawnEvent.SpawnReason.SLIME_SPLIT);
        }

        loc.getWorld().playSound(loc, Sound.ENTITY_SLIME_DEATH, 1.5f, 2.0f);

    }


    public static void setSplitTier(Zombie zombie, int tier) {
        if (tier > 3 || tier < 1) return;

        MutationHandler mh = new MutationHandler(zombie);
        if (mh.getMutation() != MutationType.PARASITE) return;

        pdc(zombie).set(splitTierKey(), PersistentDataType.INTEGER, tier);
    }

    public static int getSplitTier(Zombie zombie) {
        return pdc(zombie).getOrDefault(splitTierKey(), PersistentDataType.INTEGER, 0);
    }

    private static PersistentDataContainer pdc(Zombie zombie) {
        return zombie.getPersistentDataContainer();
    }

    private static NamespacedKey splitTierKey() {
        return ZombieAC.getInstance().getMutationKeys().getSplitTier();
    }
}
