package com.luagatemod;


import net.minecraft.client.MinecraftClient;


public class inputAutomation extends luagateClient
{
	private static int InputDelay=10;

	public static void MainClientTick()
	{
		MinecraftClient client = MinecraftClient.getInstance();
		if (MinecraftClient.getInstance().player == null) return;

   	 	if (InputDelay == 0) 
   	 	{
        	InputDelay = 10; // reset
    	} 
    	else 
    	{
        	InputDelay--;
    	}
	}

}