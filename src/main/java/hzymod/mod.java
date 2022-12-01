package hzymod;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.logging.LogUtils;
import hzymod.Graphics.HudHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(value = "hzymod")
public class mod {
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public mod() {
        // Register the setup method for mod loading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Initialising Config handler here cuz it needs to load before FMLSetup!!!!!!!
        ConfigHandler.Init();
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Starting HzyMOD 2.0 as Client");
        HudHandler.Init(Minecraft.getInstance());
        KeyHandler.Init();
    }

    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent e) {
        LOGGER.info("Registering Commands");
        CommandDispatcher<CommandSourceStack> dis = e.getDispatcher();
        HzyCommands.ChangeHudColor.Register(dis);
        HzyCommands.Calculator.Register(dis);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("Starting HzyMOD 2.0 as Server");
        LOGGER.warn("This mod is meant to be a Client Side mod using it in a Server is useless!");
    }
}
