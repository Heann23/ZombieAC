package me.herry.zombieAC.events;

import me.herry.zombieAC.MUTATION_TYPE;
import me.herry.zombieAC.MutationHandler;
import me.herry.zombieAC.ZombieAC;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class OnSpawn implements Listener {

    @EventHandler
    public void onSpawn(EntitySpawnEvent e) {
        Entity entity = e.getEntity();
        EntityType type = entity.getType();

        // 구리던전 시련 생성기에서 스폰 시 그냥 리턴
        if (entity.getEntitySpawnReason() == CreatureSpawnEvent.SpawnReason.TRIAL_SPAWNER) return;
        // 스포너에서 스폰되었고, 좀비가 아니면 리턴
        if (entity.getEntitySpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER && type != EntityType.ZOMBIE) return;
        // 엔더월드에서 스폰 시 리턴
        if (entity.getLocation().getWorld().getEnvironment() == World.Environment.THE_END) return;

        // 자연 스폰된 엔티티 이거나, 스포너에서 스폰된 좀비만 아래 코드 실행

        // 바스티온에서 스폰한 큐브가 분열될 때 좀비 피글린이 스폰되는 것을 막음
        if (type == EntityType.MAGMA_CUBE && entity.getEntitySpawnReason() == CreatureSpawnEvent.SpawnReason.SLIME_SPLIT) return;

        // 스폰을 막을 엔티티가 스폰 되었을 시
        if (isBannedEntity(entity)) {

            // 이벤트 취소 후 같은 자리에 좀비 생성
            e.setCancelled(true);

            Location loc = e.getLocation();
            World world = loc.getWorld();
            EntityType replaceType = null;

            // 스폰 위치가 오버월드일 시 사막 바이옴이면 허스크, 아니면 좀비 타입으로 설정
            if (world.getEnvironment() == World.Environment.NORMAL) {
                replaceType = (world.getBiome(loc) == Biome.DESERT) ? EntityType.HUSK : EntityType.ZOMBIE;

            // 스폰 위치가 네더월드일 시 좀비 피글린 타입으로 설정
            } else if (world.getEnvironment() == World.Environment.NETHER) {
                replaceType = EntityType.ZOMBIFIED_PIGLIN;
            }

            // 설정된 타입이 null 이 아닐 시 엔티티 스폰 후 플레이어와 멀어지면 정상 디스폰 되도록 설정
            if (replaceType != null) {
                Entity replacedEntity = world.spawnEntity(loc, replaceType, CreatureSpawnEvent.SpawnReason.NATURAL);

                if (replacedEntity instanceof LivingEntity le) le.setRemoveWhenFarAway(true);
            }

            return;
        }

        // 변이를 부여할 몬스터가 아니면 리턴
        if (!(type ==  EntityType.ZOMBIE || type == EntityType.HUSK || type == EntityType.ZOMBIFIED_PIGLIN || type == EntityType.DROWNED)) return;

        // 변이를 부여할 몬스터 타입만 아래 코드 실행

        // 자연 스폰된 좀비, 스포너에서 스폰된 좀비, 강제 스폰된 좀비만 아래 코드 실행
        Zombie zombie = (Zombie) entity;

        MutationHandler mh = new MutationHandler(zombie);
        // 이미 타입이 정해져 있으면 리턴
        if (mh.getMutation() != MUTATION_TYPE.NORMAL) return;

        MUTATION_TYPE mutation = MUTATION_TYPE.getRandom();
        mh.setMutation(mutation);

        if (mutation == MUTATION_TYPE.JOCKEY && !zombie.isAdult()) {  // 애기좀비가 JOCKEY 면 히트 박스가 작아서 못 때리는 버그 해결
            zombie.setAdult();
        } else if (mutation == MUTATION_TYPE.TANKER) {
            EntityEquipment equipment = zombie.getEquipment();
            equipment.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));

            equipment.setChestplateDropChance(0.1f);
        } else if (mutation == MUTATION_TYPE.PARASITE && zombie.getEntitySpawnReason() != CreatureSpawnEvent.SpawnReason.SLIME_SPLIT) {
            mh.setSplitTier(3);
        }
    }

    @EventHandler
    public void onZombifiedPiglin(CreatureSpawnEvent e) {
        if (!(e.getEntity() instanceof PigZombie zombifiedPiglin)) return;

        zombifiedPiglin.setAnger(99999);
        zombifiedPiglin.setAngry(true);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!zombifiedPiglin.isValid() || zombifiedPiglin.isDead()) {
                    cancel();
                    return;
                }

                LivingEntity target = zombifiedPiglin.getTarget();
                if (target != null && !target.isDead()) return;

                Player nearestPlayer = null;
                double minDistanceSq = 48 * 48;

                for (Player player : zombifiedPiglin.getWorld().getPlayers()) {
                    if (player.getGameMode() != GameMode.SURVIVAL && player.getGameMode() != GameMode.ADVENTURE) continue;

                    double distanceSq = player.getLocation().distanceSquared(zombifiedPiglin.getLocation());
                    if (distanceSq < minDistanceSq) nearestPlayer = player;
                }

                if (nearestPlayer != null) zombifiedPiglin.setTarget(nearestPlayer);
            }
        }.runTaskTimer(ZombieAC.getInstance(), 5L, 20L);
    }

    private boolean isBannedEntity(Entity entity) {
        return entity.getType() == EntityType.BOGGED
                || entity.getType() == EntityType.SKELETON
                || entity.getType() == EntityType.CAVE_SPIDER
                || entity.getType() == EntityType.SPIDER
                || entity.getType() == EntityType.CREEPER
                || entity.getType() == EntityType.PIGLIN
                || entity.getType() == EntityType.PIGLIN_BRUTE
                || entity.getType() == EntityType.STRAY
                || entity.getType() == EntityType.WITCH
                || entity.getType() == EntityType.HOGLIN
                || entity.getType() == EntityType.SLIME
                || entity.getType() == EntityType.MAGMA_CUBE;
    }

}
