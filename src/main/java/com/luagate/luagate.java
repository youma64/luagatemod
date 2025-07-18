package com.luagatemod;

import static net.minecraft.server.command.CommandManager.literal;


import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import net.minecraft.entity.LivingEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.entity.player.PlayerEntity;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.command.v2.EntitySelectorOptionRegistry;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class luagate implements ModInitializer 
{

	Globals g = JsePlatform.standardGlobals();
	luagate javaObj;
    	LuaValue luaObj = CoerceJavaToLua.coerce(javaObj);
	
	public static final String MOD_ID = "luagatemod";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() 
	{

		g.loadfile("mods/lua/main.lua").call(); //loading the main lua file
		g.set("mdp", luaObj);  // make it available in Lua, mdp stand for main data parser (even if it isn't really one that's the main job of it)

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			// A command that exists on both types of servers
			dispatcher.register(literal("lua_start").executes(this::executeMainLua));
			dispatcher.register(literal("lua_reload").executes(this::reloadMainLua));
		});
		
		LOGGER.info("Hi I'm loaded !");
	}


	
	private int executeMainLua(CommandContext<ServerCommandSource> context) 
	{
		final ServerCommandSource source = context.getSource();
		Text.literal("executing main lua...");
		g.get("Main").call();

		return 1;
	}


	private int reloadMainLua(CommandContext<ServerCommandSource> context)
	{
		final ServerCommandSource source = context.getSource();
		g.loadfile("mods/lua/main.lua").call();

		source.sendFeedback(() -> Text.literal("reloaded the main lua file"), false);

		return 1;
	}
	
	public float GetPlayerPos() //convenience func to get the player's position to lua
	{
		return 10.0f;
	}

	public void SendChatMessage(LuaValue msg)//convenience func to send a message in lua
	{
		Text.literal("global text test");
		//Text.literal(msg.tojstring());
	}

}

/*public class MainParser extends luagate
{

}*/
