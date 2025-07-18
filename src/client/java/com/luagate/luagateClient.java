// I KNOW THAT I COULD HAVE USED THE COERCE TO DIRECTLY USE MC LIBS, possibly. But I wanted to make modding easier because HO MY [F] THE AMOUNT OF DOCS I READ

package com.luagatemod;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.StringReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import net.minecraft.command.CommandRegistryAccess;

//import net.minecraft.client.option.KeyBinding; For InputAutomation

import net.minecraft.text.Text;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class luagateClient implements ClientModInitializer {
	
	public static final String MOD_ID = "luagatemod_client";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	Globals gb = JsePlatform.standardGlobals();

	LuaValue nil = LuaValue.NIL;

	private boolean DebugOnly=true; //use for compiling and Init(ing) only

	@Override
	public void onInitializeClient() //You may see that I wrote every func AFTER the main Init, which is normally bad but java don't give a crap sooooooooo...
	{									// From a C/lua dev

	 	//"translate" the JavaObjects to a lua readable one
		gb.set("inventoryC", CoerceJavaToLua.coerce(new inventoryData()));
		gb.set("usefulC", CoerceJavaToLua.coerce(new usefulFunc()));
		gb.set("visualizerC", CoerceJavaToLua.coerce(new worldVisualization()));
		gb.set("inventoryManagerC", CoerceJavaToLua.coerce(new inventoryManager()));
		gb.set("inputC", CoerceJavaToLua.coerce(new inventoryManager()));
		
		try
		{
			gb.loadfile("mods/lua/mainClient.lua").call();
			gb.get("InitClient").call();
		}
		catch(Exception e)
		{
			LOGGER.info(e.toString());
		}
		finally //Load the rest because we don't care about lua errors during Init
		{


		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("lua_start_client").executes(context -> executeMainLuaC(context.getSource())));
			dispatcher.register(ClientCommandManager.literal("lua_reload_client").executes(context -> reloadMainLuaC(context.getSource())));
			
			dispatcher.register(ClientCommandManager.literal("lua_loadfile_client")
				.then(ClientCommandManager.argument("lua_file", StringArgumentType.string())
					.executes(context -> LoadSpecificFile(context.getSource(),StringArgumentType.getString(context,"lua_file")))));

			dispatcher.register(ClientCommandManager.literal("lua_func_client")
				.then(ClientCommandManager.argument("lua_func", StringArgumentType.string())
					.executes(context -> ExecLuaFunc(context.getSource(),StringArgumentType.getString(context,"lua_func")))));
		});

		ClientTickEvents.END_CLIENT_TICK.register(client -> inputAutomation.MainClientTick());

		LOGGER.info("Hi I'm client side loaded !");

		if(DebugOnly)
		{
			System.exit(0);
		}

		}
	}

	private int executeMainLuaC(FabricClientCommandSource source) 
	{
		source.sendFeedback(Text.literal("executing main lua... (CLIENT SIDE)"));
		gb.get("LuaStartClient").call();

		return 1;
	}


	private int reloadMainLuaC(FabricClientCommandSource source)
	{
		gb.loadfile("mods/lua/mainClient.lua").call();

		source.sendFeedback(Text.literal("reloaded the main lua file (CLIENT SIDE)"));

		return 1;
	}

	private int LoadSpecificFile(FabricClientCommandSource source, String file)
	{
		try //No other choice than try/catch because loadfile return the name of the file + the error instead of just returning a null/NIL
		{
			gb.loadfile("mods/lua/" + file);
		}
		catch(Exception error)
		{
			source.sendFeedback(Text.literal("Please provide a file name (that exist)"));
			return 0;
		}

		gb.loadfile("mods/lua/" + file).call(); //always call to refresh the main table (from what I saw so im not sure of what im talking about)
		source.sendFeedback(Text.literal("reloaded the "+ file +" file (CLIENT SIDE)"));

		return 1;
	}

	private int ExecLuaFunc(FabricClientCommandSource source, String func) // For now, no args can be passed to the lua func, but who needs it anyway ?
	{
		if(gb.get(func) == nil)
		{
			source.sendFeedback(Text.literal("Please provide a function name (which need to be registered ! (do a reload if not !))"));
			return 0;
		}
		gb.get(func).call();
		source.sendFeedback(Text.literal("Lua function "+ func +" executed"));

		return 1;
	}

}