package hzymod;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;

public class KeyHandler {
	public static KeyMapping TOGGLE_HUD;
	public static final String MOD_ID = "hzymod";

	public static void Init() {
		TOGGLE_HUD = registerKey("toggle_hud", KeyMapping.CATEGORY_GAMEPLAY, InputConstants.KEY_Y);
	}

	public static KeyMapping registerKey(String name, String category, int keycode) {
		KeyMapping k = new KeyMapping("key." + MOD_ID + "." + name, keycode, category);
		ClientRegistry.registerKeyBinding(k);
		return k;
	}
}
