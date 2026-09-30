package me.herry.zombieAC.mutation;

import me.herry.zombieAC.ZombieAC;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Zombie;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class MutationHandler {
    private final PersistentDataContainer pdc;
    private final Zombie zombie;
    private final NamespacedKey mutation;

    public MutationHandler(Zombie zombie) {
        this.zombie = zombie;
        this.pdc = zombie.getPersistentDataContainer();
        mutation = ZombieAC.getInstance().getMutationKeys().getMutation();
    }

    public MutationType getMutation() {
        String mt = pdc.get(mutation, PersistentDataType.STRING);

        if (mt == null) return MutationType.NORMAL;    // PDC 에 저장된 값이 없으면 NORMAL 반환

        try {
            return MutationType.valueOf(mt);   // 저장된 String 을 Enum 으로 바꿔서 반환
        } catch (IllegalArgumentException e) {
            return MutationType.NORMAL;        // Enum 목록에 없는 값이면 NORMAL 반환
        }
    }

    public void setAndApplyMutation(MutationType mt) {
        setMutation(mt);
        applyMutation(mt);
    }

    // 변이 정보 저장
    public void setMutation(MutationType mt) {
        pdc.set(mutation, PersistentDataType.STRING, mt.name());    // Enum 을 String 으로 변환해서 저장
    }

    // 변이 적용
    public void applyMutation(MutationType type) {
        ZombieAC.getInstance().getMutationRegistry().get(type).apply(zombie);
    }
}
