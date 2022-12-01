package hzymod;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class ConfigHandler {
	public static ForgeConfigSpec.Builder CLIENT_BUILDER;
	public static ForgeConfigSpec SPEC;
	public static ForgeConfigSpec.ConfigValue<Integer> HUD_COLOR;
	public static ForgeConfigSpec.ConfigValue<String> test_str;

	public static void Init() {
		CLIENT_BUILDER = new ForgeConfigSpec.Builder();
		CLIENT_BUILDER.push("configs");

		HUD_COLOR = CLIENT_BUILDER.comment("Colour for the HUD in Integer format")
			.defineInRange("HUD Color", 0xFFFFFF, 0x000000, 0xFFFFFF);

		CLIENT_BUILDER.pop();
		SPEC = CLIENT_BUILDER.build();
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigHandler.SPEC, "hzymod-client.toml");
	}
}