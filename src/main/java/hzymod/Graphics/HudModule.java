package hzymod.Graphics;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;

public class HudModule {
	float pos_x;
	float pos_y;

	public HudModule(float pos_x, float pos_y) {
		this.pos_x = pos_x;
		this.pos_y = pos_y;
	}

	public void Draw(PoseStack p, Font f, String text) {
		f.drawShadow(p, text, this.pos_x, this. pos_y, HudHandler.hudColor);
 	}
}
