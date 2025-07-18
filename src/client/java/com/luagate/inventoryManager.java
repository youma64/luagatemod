//Thats where you see that minecraft is a bit spaghetti coded, the id for an item slot is not the same if you are manipulating it with gui or player data;
// So heres the result of my research :

//WARNING : - 1 to 4 : crafting zone
//			- 0 : result of craft
// 			- 5 to 8 : Armor slots
//			- 9 to 35 : Inventory slots
//			- 36 to 45 : hotbar slots

// and sadly, gui seems to be way more easier to use for inventory data, but its impossible to get an itemStack with that.

package com.luagatemod;

import net.minecraft.screen.slot.SlotActionType;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayerInteractionManager;


public class inventoryManager extends luagateClient
{

	public int InventoryQuickSort(int Slot) //
	{
		MinecraftClient client = MinecraftClient.getInstance();

		client.setScreen(new InventoryScreen(client.player));

		if (client.currentScreen instanceof InventoryScreen) 
		{
    		client.interactionManager.clickSlot
    		(
        		client.player.currentScreenHandler.syncId,
        		Slot, 						// 0 to 35 inv
        		0, 							// button (not used for QUICK_MOVE), 0 for r_click, 1 for l_click
        		SlotActionType.QUICK_MOVE, // This is shift-click
        		client.player
        	);

        	client.setScreen(null);
        	return 0;
		}
		else
		{
			return -5; //Gui not opened
		}
	}

	public int InventorySlowSort(int sourceSlot,int targetSlot,boolean DoRightClick)
	{
		MinecraftClient client = MinecraftClient.getInstance();

		client.setScreen(new InventoryScreen(client.player));

		int FuckJavaBoolConv = DoRightClick ? 1 : 0;

		if(client.currentScreen instanceof InventoryScreen)
		{
		    client.interactionManager.clickSlot
    		(
        		client.player.currentScreenHandler.syncId,
        		sourceSlot, 						
        		FuckJavaBoolConv, 						// button (not used for QUICK_MOVE), 0 for r_click, 1 for l_click
        		SlotActionType.PICKUP,
        		client.player
        	);

        	client.interactionManager.clickSlot
    		(
        		client.player.currentScreenHandler.syncId,
        		targetSlot, 						
        		0, 							// button (not used for QUICK_MOVE), 0 for r_click, 1 for l_click
        		SlotActionType.PICKUP,
        		client.player
        	);

    		client.setScreen(null);
        	return 0;
    	}
    	else
    	{
    		return -5;
    	}
	}

	public int ItemPlaceOne(int targetSlot, int BackupSlot)
	{
		MinecraftClient client = MinecraftClient.getInstance();

		client.setScreen(new InventoryScreen(client.player));

		if(client.currentScreen instanceof InventoryScreen)
		{
		    //First take-up the stack
		    client.interactionManager.clickSlot
    		(
        		client.player.currentScreenHandler.syncId,
        		targetSlot, 						
        		0,
        		SlotActionType.PICKUP,
        		client.player
        	);

    		//Then place one at the same slot
        	client.interactionManager.clickSlot
    		(
        		client.player.currentScreenHandler.syncId,
        		targetSlot, 						
        		1,
        		SlotActionType.PICKUP,
        		client.player
        	);

    		// then store the stack in another slot
        	client.interactionManager.clickSlot
    		(
        		client.player.currentScreenHandler.syncId,
        		BackupSlot, 						
        		0,
        		SlotActionType.PICKUP,
        		client.player
        	);

    		client.setScreen(null);
        	return 0;
    	}
    	else
    	{
    		return -5;
    	}
	}

	public int TakeItemAmount(int amount,int targetSlot, int BackupSlot)
	{
		if(amount<=0)
		{
			return -1;
		}


		MinecraftClient client = MinecraftClient.getInstance();

		client.setScreen(new InventoryScreen(client.player));

		if(client.currentScreen instanceof InventoryScreen)
		{
		    //First take-up the stack
		    client.interactionManager.clickSlot
    		(
        		client.player.currentScreenHandler.syncId,
        		targetSlot, 						
        		0,
        		SlotActionType.PICKUP,
        		client.player
        	);

    		for(int i=0;i<amount;i++)
    		{
    			client.interactionManager.clickSlot
    			(
        			client.player.currentScreenHandler.syncId,
        			targetSlot, 						
        			1,
        			SlotActionType.PICKUP,
        			client.player
        		);
    		}

    		// then store the stack in another slot
        	client.interactionManager.clickSlot
    		(
        		client.player.currentScreenHandler.syncId,
        		BackupSlot, 						
        		0,
        		SlotActionType.PICKUP,
        		client.player
        	);

    		client.setScreen(null);
        	return 0;
    	}
    	else
    	{
    		return -5;
    	}
	}
}
