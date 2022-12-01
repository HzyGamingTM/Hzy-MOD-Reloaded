package hzymod.Events;

import hzymod.Graphics.HudHandler;
import hzymod.KeyHandler;
import hzymod.mod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "hzymod", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEvents {
	public static void Register() {
		MinecraftForge.EVENT_BUS.register(new ClientEvents());
	}

	@SubscribeEvent
	public void ClientTick(TickEvent.ClientTickEvent e) {
		if (KeyHandler.TOGGLE_HUD.isDown()) {
			HudHandler.enabled = !HudHandler.enabled;
			KeyHandler.TOGGLE_HUD.setDown(false);
		}
	}
}
