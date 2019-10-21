package lime.xp_matters;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;


@Mod.EventBusSubscriber
public class EventHandlers {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void entityJoinWorld(EntityJoinWorldEvent event){
        if (event.getEntity() instanceof EntityPlayer) {
            updatePlayerHealth((EntityPlayer)event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawnEvent(PlayerEvent.PlayerRespawnEvent event) { updatePlayerHealth(event.player); }

    @SubscribeEvent
    public static void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) { updatePlayerHealth(event.player); }

    @SubscribeEvent
    public static void onPlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event) { updatePlayerHealth(event.player); }

    @SubscribeEvent
    public static void onPlayerPickupXpEvent(PlayerPickupXpEvent event) { updatePlayerHealth(event.getEntityPlayer()); }

    @SubscribeEvent
    public static void onAdvancementEvent(AdvancementEvent event) { updatePlayerHealth(event.getEntityPlayer()); }

    @SubscribeEvent
    public static void onAnvilRepairEvent(AnvilRepairEvent event) { updatePlayerHealth(event.getEntityPlayer()); }

    @SubscribeEvent
    public static void onPlayerInteractEvent(PlayerInteractEvent event) { updatePlayerHealth(event.getEntityPlayer()); }

    @SubscribeEvent
    public static void onPlayerContainerEvent(PlayerContainerEvent event) { updatePlayerHealth(event.getEntityPlayer()); }

    static void updatePlayerHealth(EntityPlayer player) {
        double new_h = SharedMonsterAttributes.MAX_HEALTH.getDefaultValue() + Math.floor(player.experienceLevel * XpMatters.healthBonusPerLevel);
        if (new_h <= XpMatters.maxHealth && Math.floor(new_h) != Math.floor(player.getMaxHealth())){
            player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(new_h);

            // force client update
            player.setHealth((float)new_h-1);
            player.setHealth((float)new_h);
        }
    }
}
