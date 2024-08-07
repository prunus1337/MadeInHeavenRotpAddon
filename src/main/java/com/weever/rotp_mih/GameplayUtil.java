package com.weever.rotp_mih;

import com.github.standobyte.jojo.capability.world.TimeStopHandler;
import com.github.standobyte.jojo.capability.world.TimeStopInstance;
import com.github.standobyte.jojo.init.power.non_stand.ModPowers;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.impl.nonstand.type.vampirism.VampirismData;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.stats.TimeStopperStandStats;
import com.mojang.brigadier.CommandDispatcher;
import com.weever.rotp_mih.command.CumCommand;
import com.weever.rotp_mih.utils.TimeData;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.github.standobyte.jojo.action.stand.TimeResume.userTimeStopInstance;

@Mod.EventBusSubscriber(modid = RotpMadeInHeavenAddon.MOD_ID)
public class GameplayUtil {
    private static final int TICKS_FIRST_CLICK = TimeStopInstance.TIME_RESUME_SOUND_TICKS + 1;
    public static final Map<PlayerEntity, Integer> playerTickCounters = new HashMap<>();
    private static TimeData globalValue = new TimeData(null, Values.NONE);
    public static void setGlobalValue(UUID player, Values val) {
        globalValue = new TimeData(player, val);
    }
    public static TimeData getGlobalValue() {
        return globalValue;
    }
    public static int timeAccelPhase = 1;

//    private static PlayerEntity universeResetPlayer = null;
//    public static PlayerEntity getUniverseResetPlayer() {
//        return universeResetPlayer;
//    }
//    public static void setUniverseResetPlayer(PlayerEntity player) {
//        universeResetPlayer = player;
//    }

    public enum Values {
        NONE,
        ACCELERATION,
        SLOW
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();
        CumCommand.register(dispatcher);
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (player != null && !player.level.isClientSide) {
            IStandPower.getStandPowerOptional(player).ifPresent(power -> {
                if (power.getType() != null && power.getType().getStats() instanceof TimeStopperStandStats) {
                    if (globalValue.getValue() == Values.ACCELERATION) {
                        AtomicBoolean vampire = new AtomicBoolean(false);
                        INonStandPower.getNonStandPowerOptional(player).ifPresent(ipower -> {
                            Optional<VampirismData> data = ipower.getTypeSpecificData(ModPowers.VAMPIRISM.get());
                            vampire.set(data.isPresent());
                        });
                        int phase = GameplayUtil.timeAccelPhase;
                        int delenie = 1;
                        switch (phase) {
                            case 1: case 2: case 3: case 4: case 5:
                                delenie = 2;
                                break;
                            case 6: case 7: case 8: case 9: case 10:
                                delenie = 3;
                                break;
                            case 11: case 12: case 13: case 14: case 15:
                                delenie = 4;
                                break;
                        }
                        if (TimeStopHandler.isTimeStopped(player.level, player.blockPosition())) {
                            playerTickCounters.putIfAbsent(player, 0);
                            int tick = playerTickCounters.get(player) + 1;
                            playerTickCounters.put(player, tick);

                            if (tick >= 2 * ((TimeStopperStandStats) power.getType().getStats()).getMaxTimeStopTicks(vampire.get()) / delenie) {
                                userTimeStopInstance(player.level, player, instance -> {
                                    if (instance != null) {
                                        instance.setTicksLeft(!instance.wereTicksManuallySet() && instance.getTicksLeft() > TICKS_FIRST_CLICK ? TICKS_FIRST_CLICK : 0);
                                    }
                                });
                                playerTickCounters.put(player, 0);
                            }
                        }
                    }
                }
            });
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onPlayerDeathWithTimeManipulation(LivingDeathEvent event) {
        if (globalValue.getValue() == Values.ACCELERATION) {
            if (event.getEntityLiving() instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) event.getEntityLiving();
                if (globalValue.getPlayer().equals(player.getUUID())) {
                    setGlobalValue(null, Values.NONE);
                }
            }
        }
    }
}