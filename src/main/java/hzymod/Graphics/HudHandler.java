package hzymod.Graphics;

import hzymod.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HudHandler {
    public static boolean enabled = true;
    public static int hudColor = 0xffffff;
    public static Minecraft mc;
    public static HudModule fpsModule;
    public static HudModule memoryModule;

    public static void Init(Minecraft m) {
        mc = m;
        hudColor = ConfigHandler.HUD_COLOR.get();

        fpsModule = new HudModule(0, 10);
        memoryModule = new HudModule(0, 20);
        MinecraftForge.EVENT_BUS.register(new HudHandler());
    }

    @SubscribeEvent
    public void render(RenderGameOverlayEvent e) {
        if (!enabled || !mc.isWindowActive() || mc.isPaused() || mc.options.renderDebug)
            return;

        mc.font.drawShadow(e.getMatrixStack(),
            "Performance:", 1, 0, hudColor
        );

        fpsModule.Draw(e.getMatrixStack(), mc.font, HudHandler.GetFps());
        memoryModule.Draw(e.getMatrixStack(), mc.font, HudHandler.GetMemory());
    }

    public static String GetFps() {
        int index = 0;
        char[] fpsChar = mc.fpsString.toCharArray();
        for (char i : fpsChar) {
            index++;
            if (i == " ".toCharArray()[0])
                return mc.fpsString.substring(0, index) + "FPS";
        }
        return null;
    }

    static final int MEGABYTE = 1000000;
    public static String GetMemory() {
        long max = Runtime.getRuntime().maxMemory() / MEGABYTE;
        long used = Runtime.getRuntime().freeMemory() / MEGABYTE;
        float percent = (float) used / (float) max * 100;
        return String.format("Memory Usage: %dMB / %dMB | ", used, max) + Math.round(percent) + "%";
    }
}