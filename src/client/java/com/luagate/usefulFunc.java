package com.luagatemod;

import net.minecraft.util.math.Vec3d;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

public class usefulFunc extends luagateClient
{

	public Vec3d GetPlayerPosC() //convenience func to get the player's position to lua
	{
		if (MinecraftClient.getInstance().player == null){return null;}

		PlayerEntity player = MinecraftClient.getInstance().player;
		return new Vec3d(player.getX(),player.getY(),player.getZ());
	}

	public int SendChatMessageC(String msg)//convenience func to send a message in lua, ONLY SEND VIA LOCAL PLAYER SINCE THIS IS THE CLIENT INSTANCE
	{
		MinecraftClient.getInstance().player.networkHandler.sendChatMessage(msg);

		return 0; // (I) prefer to return 0 for lua
	}
}
