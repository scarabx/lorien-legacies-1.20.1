package net.scarab.lorienlegacies.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.scarab.lorienlegacies.LorienLegaciesMod;

import static net.scarab.lorienlegacies.LorienLegaciesMod.MOD_ID;

public class ModEntities {

    public static final EntityType<ChimaeraParrotEntity> CHIMAERA_PARROT = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "chimaera_parrot"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChimaeraParrotEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.9f)).build());

    public static final EntityType<IceballProjectileEntity> THROWN_ICEBALL_PROJECTILE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "iceball_projectile"),
            FabricEntityTypeBuilder.<IceballProjectileEntity>create(SpawnGroup.MISC, IceballProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build());

    public static final EntityType<KineticProjectileEntity> THROWN_KINETIC_PROJECTILE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "thrown_kinetic_projectile"),
            FabricEntityTypeBuilder.<KineticProjectileEntity>create(SpawnGroup.MISC, KineticProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build());

    public static final EntityType<ShockCollarProjectileEntity> THROWN_SHOCK_COLLAR_PROJECTILE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "shock_collar_projectile"),
            FabricEntityTypeBuilder.<ShockCollarProjectileEntity>create(SpawnGroup.MISC, ShockCollarProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build());

    public static final EntityType<SpikyYellowBallEntity> SPIKY_YELLOW_BALL = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "spiky_yellow_ball"),
            FabricEntityTypeBuilder.<SpikyYellowBallEntity>create(SpawnGroup.MISC, SpikyYellowBallEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).trackRangeBlocks(4).trackedUpdateRate(10).build());

    public static final EntityType<SpikyBlackBallEntity> SPIKY_BLACK_BALL = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "spiky_black_ball"),
            FabricEntityTypeBuilder.<SpikyBlackBallEntity>create(SpawnGroup.MISC, SpikyBlackBallEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());

    public static final EntityType<JoustStaffEntity> JOUST_STAFF = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "joust_staff"),
            FabricEntityTypeBuilder.<JoustStaffEntity>create(SpawnGroup.MISC, JoustStaffEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).trackRangeBlocks(4).trackedUpdateRate(10).build());

    public static final EntityType<IciclesEntity> ICICLES = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "icicles"),
            FabricEntityTypeBuilder.<IciclesEntity>create(SpawnGroup.MISC, IciclesEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.8f)).build());

    public static void registerModEntities() {
        LorienLegaciesMod.LOGGER.info("Registering Mod Entities for " + MOD_ID);
    }
}
