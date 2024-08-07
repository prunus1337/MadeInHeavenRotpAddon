package com.weever.rotp_mih.init;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.stand.*;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject;
import com.github.standobyte.jojo.power.impl.stand.StandInstance.StandPart;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;
import com.github.standobyte.jojo.util.mod.StoryPart;
import com.weever.rotp_mih.RotpMadeInHeavenAddon;
import com.weever.rotp_mih.action.stand.*;
import com.weever.rotp_mih.action.stand.gui.TimeSystemReleaseAcceleration;
import com.weever.rotp_mih.action.stand.gui.TimeSystemReleaseClear;
import com.weever.rotp_mih.action.stand.gui.TimeSystemReleaseSlow;
import com.weever.rotp_mih.entity.stand.stands.MihEntity;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class InitStands {
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<Action<?>> ACTIONS = DeferredRegister.create(
            (Class<Action<?>>) ((Class<?>) Action.class), RotpMadeInHeavenAddon.MOD_ID);
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<StandType<?>> STANDS = DeferredRegister.create(
            (Class<StandType<?>>) ((Class<?>) StandType.class), RotpMadeInHeavenAddon.MOD_ID);

    // ======================================== Made In Heaven! ========================================

    public static final RegistryObject<StandEntityAction> MIH_PUNCH = ACTIONS.register("mih_punch",
            () -> new StandEntityLightAttack(new StandEntityLightAttack.Builder()));

    public static final RegistryObject<StandEntityAction> MIH_BARRAGE = ACTIONS.register("mih_barrage",
            () -> new StandEntityMeleeBarrage(new StandEntityMeleeBarrage.Builder()
                    .standSound(InitSounds.MIH_BARRAGE)
                    .shout(InitSounds.MIH_BARRAGE_USER))
    );

    public static final RegistryObject<StandEntityHeavyAttack> MIH_IMPALE = ACTIONS.register("mih_impale",
            () -> new Impale(new StandEntityHeavyAttack.Builder().shout(InitSounds.MIH_IMPALE_USER)
                    .partsRequired(StandPart.ARMS))
    );

    public static final RegistryObject<StandEntityHeavyAttack> MIH_HEAVY_PUNCH = ACTIONS.register("mih_heavy_punch",
            () -> new StandEntityHeavyAttack(new StandEntityHeavyAttack.Builder()
                    .partsRequired(StandPart.ARMS)
                    .setFinisherVariation(MIH_IMPALE)
                    .shout(InitSounds.MIH_HEAVY_PUNCH_USER)
                    .shiftVariationOf(MIH_PUNCH).shiftVariationOf(MIH_BARRAGE))
    );

    public static final RegistryObject<StandEntityAction> MIH_THROAT_SLICE = ACTIONS.register("mih_throat_slice",
            () -> new ThroatSlice(new StandEntityAction.Builder()
                    .partsRequired(StandPart.MAIN_BODY)
                    .staminaCost(150).cooldown(100, 100).resolveLevelToUnlock(1)
                    .holdToFire(10, false)
            )
    );

    public static final RegistryObject<StandEntityAction> MIH_DASH = ACTIONS.register("mih_dash",
            () -> new LightSpeedDash(new StandEntityAction.Builder()
                    .standAutoSummonMode(StandEntityAction.AutoSummonMode.OFF_ARM)
                    //.standSound(InitSounds.MIH_DASH).shout(InitSounds.MIH_DASH_USER)
                    .staminaCost(100).cooldown(50, 50).resolveLevelToUnlock(1)
                    .partsRequired(StandPart.MAIN_BODY))
    );

    public static final RegistryObject<StandEntityAction> MIH_TWO_STEPS = ACTIONS.register("mih_two_steps",
            () -> new TwoStepsBehind(new StandEntityAction.Builder()
                    .partsRequired(StandPart.ARMS)
                    //.standSound(InitSounds.MIH_TWO_STEPS)
                    .staminaCost(50).cooldown(100, 100).resolveLevelToUnlock(1)
                    .holdToFire(15, true))
    );

    public static final RegistryObject<StandEntityAction> MIH_UNIVERSE_RESET = ACTIONS.register("mih_universe_reset",
            () -> new UniverseReset(new StandEntityAction.Builder().standPerformDuration(500).standUserWalkSpeed(1F)
                    .staminaCost(300).cooldown(5550, 5550).resolveLevelToUnlock(4)
                    .standSound(InitSounds.MIH_UNIVERSE_RESET).shout(InitSounds.MIH_UNIVERSE_RESET_USER)
                    .partsRequired(StandPart.MAIN_BODY))
    );

    public static final RegistryObject<StandEntityAction> MIH_TIME_SYSTEM_MENU = ACTIONS.register("mih_time_system",
            () -> new TimeSystemMenuStandEntityAction(new StandEntityAction.Builder()
                    .cooldown(10, 10).resolveLevelToUnlock(1)
                    .holdToFire(10, false)
                    .partsRequired(StandPart.MAIN_BODY)
            )
    );

    public static final RegistryObject<StandEntityAction> MIH_TIME_SYSTEM_CLEAR = ACTIONS.register("mih_time_system_clear",
            () -> new TimeSystemReleaseClear(new StandEntityAction.Builder()
                    .partsRequired(StandPart.MAIN_BODY)
                    .ignoresPerformerStun()
            )
    );

    public static final RegistryObject<StandEntityAction> MIH_TIME_SYSTEM_ACCELERATION = ACTIONS.register("mih_time_system_acceleration",
            () -> new TimeSystemReleaseAcceleration(new StandEntityAction.Builder()
                    .partsRequired(StandPart.MAIN_BODY)
                    .ignoresPerformerStun()
            )
    );

    public static final RegistryObject<StandEntityAction> MIH_TIME_SYSTEM_SLOW = ACTIONS.register("mih_time_system_slow",
            () -> new TimeSystemReleaseSlow(new StandEntityAction.Builder()
                    .partsRequired(StandPart.MAIN_BODY)
                    .ignoresPerformerStun()
            )
    );

    public static final RegistryObject<StandEntityAction> MIH_BLOCK = ACTIONS.register("mih_block",
            () -> new StandEntityBlock());

    public static final EntityStandRegistryObject<EntityStandType<StandStats>, StandEntityType<MihEntity>> MIH =
            new EntityStandRegistryObject<>("madeinheaven",
                    STANDS,
                    () -> new EntityStandType.Builder<>()
                            .color(0xD3D5E1)
                            .storyPartName(StoryPart.STONE_OCEAN.getName())
                            .leftClickHotbar(
                                    MIH_PUNCH.get(),
                                    MIH_BARRAGE.get(),
                                    MIH_THROAT_SLICE.get()
                            )
                            .rightClickHotbar(
                                    MIH_BLOCK.get(),
                                    MIH_DASH.get(),
                                    MIH_TWO_STEPS.get(),
                                    MIH_TIME_SYSTEM_MENU.get()
                            )
                            .defaultStats(StandStats.class, new StandStats.Builder()
                                    .power(13.0)
                                    .speed(20.0)
                                    .range(8, 10)
                                    .durability(14.0)
                                    .precision(10.0)
                                    .randomWeight(0.1)
                            )
                            .addOst(InitSounds.MIH_OST)
                            .addSummonShout(InitSounds.MIH_SUMMON)
                            .build(),

                    InitEntities.ENTITIES,
                    () -> new StandEntityType<>(MihEntity::new, 0.65F, 1.75F)
                            .summonSound(InitSounds.MIH_SUMMON)
                            .unsummonSound(InitSounds.MIH_UNSUMMON)
                    ).withDefaultStandAttributes();
}