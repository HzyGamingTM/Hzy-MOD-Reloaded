package hzymod;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import hzymod.Graphics.HudHandler;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.io.ObjectInputFilter;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class HzyCommands {
	public static void SendWarn(Player p, String msg) {
		p.sendMessage(new TextComponent(Utils.TextColor.yellow + "[HzyMOD]: Warning! " + msg), Util.NIL_UUID);
	}

	public static void SendErr(Player p, String msg) {
		p.sendMessage(new TextComponent(Utils.TextColor.red + "[HzyMOD]: Error! " + msg), Util.NIL_UUID);
	}

	public static class Calculator {
		public static String name = "calc";
		public static void Register(CommandDispatcher<CommandSourceStack> dis) {
			LiteralArgumentBuilder<CommandSourceStack> CalcCommand = literal(name)
				.then(argument("arg1", StringArgumentType.string())
				.then(argument("operator", StringArgumentType.string())
				.then(argument("arg2", StringArgumentType.string())
					.executes(cmd -> Run(cmd))
				)));
			dis.register(CalcCommand);
		}

		static int Run(CommandContext<CommandSourceStack> cmd) {
			Entity source = cmd.getSource().getEntity();
			if (source instanceof Player) {
				Player player = (Player) source;
				String arg1 = StringArgumentType.getString(cmd, "arg1");
				String operator = StringArgumentType.getString(cmd, "operator");
				String arg2 = StringArgumentType.getString(cmd, "arg2");

				double double1 = 0, double2 = 0;
				try {
					double1 = Double.parseDouble(arg1);
					double2 = Double.parseDouble(arg2);
				} catch (Exception e) {
					SendErr(player, "Error parsing value!");
					return 0;
				}
				double value = 0;
				switch (operator) {
					case "+": value = double1 + double2; break;
					case "-": value = double1 - double2; break;
					case "*": value = double1 * double2; break;
					case "/": value = double1 / double2; break;
					default: SendWarn(player, "Invalid Operator!");
				}
				player.sendMessage(new TextComponent(Utils.TextColor.green + "[Calc]: Result: " + value), Util.NIL_UUID);
				return 1;
			}
			return 0;
		}
	}

	public static class ChangeHudColor {
		public static String name = "hudcolor";
		public static void Register(CommandDispatcher<CommandSourceStack> dis) {
			LiteralArgumentBuilder<CommandSourceStack> HudColorCommand =
				literal(name)
					.then(literal("red").executes((cmd) -> run(cmd, Utils.ChatColor.RED)))
					.then(literal("dark_red").executes((cmd) -> run(cmd, Utils.ChatColor.DARK_RED)))
					.then(literal("yellow").executes((cmd) -> run(cmd, Utils.ChatColor.YELLOW)))
					.then(literal("green").executes((cmd) -> run(cmd, Utils.ChatColor.GREEN)))
					.then(literal("dark_green").executes((cmd) -> run(cmd, Utils.ChatColor.DARK_GREEN)))
					.then(literal("aqua").executes((cmd) -> run(cmd, Utils.ChatColor.AQUA)))
					.then(literal("dark_aqua").executes((cmd) -> run(cmd, Utils.ChatColor.DARK_AQUA)))
					.then(literal("blue").executes((cmd) -> run(cmd, Utils.ChatColor.BLUE)))
					.then(literal("dark_blue").executes((cmd) -> run(cmd, Utils.ChatColor.DARK_BLUE)))
					.then(literal("light_purple").executes((cmd) -> run(cmd, Utils.ChatColor.LIGHT_PURPLE)))
					.then(literal("dark_purple").executes((cmd) -> run(cmd, Utils.ChatColor.DARK_PURPLE)))
					.then(literal("black").executes((cmd) -> run(cmd, Utils.ChatColor.BLACK)))
					.then(literal("white").executes((cmd) -> run(cmd, Utils.ChatColor.WHITE)))
					.then(literal("gray").executes((cmd) -> run(cmd, Utils.ChatColor.GRAY)))
					.then(literal("dark_gray").executes((cmd) -> run(cmd, Utils.ChatColor.DARK_GRAY)))
					.then(literal("gold").executes((cmd) -> run(cmd, Utils.ChatColor.GOLD)))
					.then(argument("color", StringArgumentType.string())
						.executes(
							cmd -> run(cmd, StringArgumentType.getString(cmd, "color"))
						)
					);
			dis.register(HudColorCommand);
		}

		private static int run(CommandContext<CommandSourceStack> cmd, int color) {
			Entity source = cmd.getSource().getEntity();
			if (source instanceof Player) {
				Player player = (Player)source;
				HudHandler.hudColor = color;
				player.sendMessage(new TextComponent(Utils.TextColor.green + "Changed HUD Color"), Util.NIL_UUID);
				ConfigHandler.HUD_COLOR.set(color);
				return Command.SINGLE_SUCCESS;
			}
			return 0;
		}

		private static int run(CommandContext<CommandSourceStack> cmd, String colorString) throws CommandSyntaxException {
			Entity source = cmd.getSource().getEntity();
			if (source instanceof Player) {
				Player player = (Player)source;
				int color = hex2int(colorString);
				mod.LOGGER.info("color: " + color);
				HudHandler.hudColor = color;
				player.sendMessage(
					new TextComponent(Utils.TextColor.green + "Changed HUD Color <Custom>"), Util.NIL_UUID
				);
				ConfigHandler.HUD_COLOR.set(color);
				return Command.SINGLE_SUCCESS;
			}
			return 0;
		}

		static int hex2int(String hex) {
			int val = 0;
			for (char h : hex.toCharArray()) {
				if (h >= '0' && h <= '9') h = (char)(h - '0');
        		else if (h >= 'a' && h <='f') h = (char)(h - 'a' + 10);
        		else if (h >= 'A' && h <='F') h = (char)(h - 'A' + 10);
				val = (val << 4) | (h & 0xF);
			}
			return val;
		}
	}
}
