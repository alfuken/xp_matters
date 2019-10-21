package lime.xp_matters;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

@Mod(modid = XpMatters.MODID, name = XpMatters.NAME, version = XpMatters.VERSION)
public class XpMatters
{
    public static final String MODID = "xp_matters";
    public static final String NAME = "XP Matters";
    public static final String VERSION = "3";

    static Configuration config;
    static double healthBonusPerLevel = 0.5;
    static double maxHealth = 40.0;
    static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        config = new Configuration(event.getSuggestedConfigurationFile());
        try {
            config.load();
            healthBonusPerLevel = config.getFloat("player health per xp level", Configuration.CATEGORY_GENERAL,(float)healthBonusPerLevel,0,100,"Increase player HP by <this> much for every XP level (remember: 1 HP == 1/2 heart)");
            maxHealth = config.getFloat("max health", Configuration.CATEGORY_GENERAL,(float) maxHealth,1,1000,"Max HP player can have (remember: 1 HP == 1/2 heart)");
        } catch (Exception e1) {
            XpMatters.logger.log(Level.ERROR, "Problem loading "+ XpMatters.MODID+" config file.", e1);
        } finally {
            if (config.hasChanged()) {
                config.save();
            }
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new EventHandlers());
    }

    @EventHandler
    public void postInit(FMLInitializationEvent event)
    {
        if (config.hasChanged()) {
            config.save();
        }
    }
}
