package me.herry.zombieAC;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Zombie;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class MutationHandler {
    private static final NamespacedKey MUTATION_KEY = new NamespacedKey(ZombieAC.getInstance(), "mutation");
    private static final NamespacedKey SPLIT_TIER = new NamespacedKey(ZombieAC.getInstance(), "split_tier");
    private final PersistentDataContainer pdc;
    private final Zombie zombie;

    public MutationHandler(Zombie zombie) {
        this.zombie = zombie;
        this.pdc = zombie.getPersistentDataContainer();
    }

    public MUTATION_TYPE getMutation() {
        String mt = pdc.get(MUTATION_KEY, PersistentDataType.STRING);

        if (mt == null) return MUTATION_TYPE.NORMAL;    // PDC 에 저장된 값이 없으면 NORMAL 반환

        try {
            return MUTATION_TYPE.valueOf(mt);   // 저장된 String 을 Enum 으로 바꿔서 반환
        } catch (IllegalArgumentException e) {
            return MUTATION_TYPE.NORMAL;        // Enum 목록에 없는 값이면 NORMAL 반환
        }
    }

    public void setMutation(MUTATION_TYPE mt) {
        pdc.set(MUTATION_KEY, PersistentDataType.STRING, mt.name());    // Enum 을 String 으로 변환해서 저장

        switch (mt) {
            case BOOMER -> setBoomer();
            case JOCKEY -> setJockey();
            case SHOOTER -> setShooter();
            case JUMPER -> setJumper();
            case SPRINT ->  setSprint();
            case TANKER ->  setTanker();
            case PARASITE ->  setParasite();
            case NORMAL -> {}
        }
    }

    public void setSplitTier(int tier) {
        MUTATION_TYPE mt = getMutation();

        if (mt != MUTATION_TYPE.PARASITE) return;
        if (tier > 3 || tier < 1) return;

        pdc.set(SPLIT_TIER, PersistentDataType.INTEGER, tier);
    }

    public int getSplitTier() {
        return pdc.getOrDefault(SPLIT_TIER, PersistentDataType.INTEGER, 0);
    }

    private void setBoomer() {
        Objects.requireNonNull(zombie.getAttribute(Attribute.MOVEMENT_SPEED)).setBaseValue(0.2);
        Objects.requireNonNull(zombie.getAttribute(Attribute.MAX_HEALTH)).setBaseValue(3);
    }

    private void setJockey() {
        Objects.requireNonNull(zombie.getAttribute(Attribute.SCALE)).setBaseValue(0.8);
        PotionEffect jumpEff = new PotionEffect(PotionEffectType.JUMP_BOOST, PotionEffect.INFINITE_DURATION, 1, false, false);
        zombie.addPotionEffect(jumpEff);
    }

    private void setShooter() {

    }

    private void setJumper() {
        PotionEffect jumpEff = new PotionEffect(PotionEffectType.JUMP_BOOST, PotionEffect.INFINITE_DURATION, 10, false, false);
        zombie.addPotionEffect(jumpEff);
    }

    private void setSprint() {
        Objects.requireNonNull(zombie.getAttribute(Attribute.MOVEMENT_SPEED)).setBaseValue(0.35);
        Objects.requireNonNull(zombie.getAttribute(Attribute.MAX_HEALTH)).setBaseValue(15.0);
    }

    private void setTanker() {
        Objects.requireNonNull(zombie.getAttribute(Attribute.SCALE)).setBaseValue(1.5);
        Objects.requireNonNull(zombie.getAttribute(Attribute.MAX_HEALTH)).setBaseValue(100.0);
        Objects.requireNonNull(zombie.getAttribute(Attribute.KNOCKBACK_RESISTANCE)).setBaseValue(0.8);
        Objects.requireNonNull(zombie.getAttribute(Attribute.ATTACK_KNOCKBACK)).setBaseValue(2.5);

        zombie.setHealth(zombie.getMaxHealth());
    }

    private void setParasite() {

    }
}
