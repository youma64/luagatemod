package com.luagatemod;

import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;



public class inventoryData extends luagateClient
{

	private EquipmentSlot IntToSlot(int select) // ONLY USED FOR ARMOR AND HAND SLOT
	{
		switch(select)
		{
			default:
				LOGGER.info(MOD_ID + ": Invalid slot, it can be from 0 to 5 (from function IntToSlot) !");
				return null;
			case 0:
				return EquipmentSlot.MAINHAND;
			case 1:
				return EquipmentSlot.OFFHAND;
			case 2:
				return EquipmentSlot.FEET;
			case 3:
				return EquipmentSlot.LEGS;
			case 4:
				return EquipmentSlot.CHEST;
			case 5:
				return EquipmentSlot.HEAD;
		}
	}

	public ItemStack InventorySlotCheck(int slot)//Check a specific slot of the player inv, from 0 to 35
	{
		PlayerEntity player = MinecraftClient.getInstance().player;
    	

		return player.getInventory().main.get(slot);
	}

	public ItemStack ArmorSlotCheck(EquipmentSlot slot)
	{
		return MinecraftClient.getInstance().player.getEquippedStack(slot);
	}

	public ItemStack HandSlotCheck(EquipmentSlot slot)
	{
    	return MinecraftClient.getInstance().player.getEquippedStack(slot);
	}

	public void DropItem(boolean DropStack)
	{
		PlayerInventory inv = MinecraftClient.getInstance().player.getInventory();
		inv.dropSelectedItem(DropStack);
	}

	public int GetItemCount(String mainClass,String itemName) //mainClass is in case of using a mod (else its always minecraft:[item]), 0 in that case mean no item found
	{
		PlayerInventory inv = MinecraftClient.getInstance().player.getInventory();
		Identifier id = Identifier.of(mainClass,itemName);
		ItemStack item = new ItemStack(Registries.ITEM.get(id));
		
		if(inv.contains(item))
		{
			
			for(int i=0;i<35;i++)
			{
				if(InventorySlotCheck(i).getItem() == item.getItem())
				{
					ItemStack itemFinal = InventorySlotCheck(i);
					return itemFinal.getCount();
				} 
			}

			for(int i=2;i<=5;i++) //Armor slot check
			{
				if(ArmorSlotCheck(IntToSlot(i)).getItem() == item.getItem())
				{
					ItemStack itemFinal = ArmorSlotCheck(IntToSlot(i));
					return itemFinal.getCount();
				} 
			}

			if(HandSlotCheck(EquipmentSlot.OFFHAND).getItem() == item.getItem())
			{
				ItemStack itemFinal = HandSlotCheck(EquipmentSlot.OFFHAND);
				return itemFinal.getCount();
			} 

			return -101; //logic error : -101 --> item found but not ?, can only happen with custom slot ! (probably)
		}
		else
		{
			return 0;
		}
	}

	public ItemStack GetItemStack(String mainClass,String itemName) //basically the same as above (like many func) but return the itemstack instead
	{
		PlayerInventory inv = MinecraftClient.getInstance().player.getInventory();
		Identifier id = Identifier.of(mainClass,itemName);
		ItemStack item = new ItemStack(Registries.ITEM.get(id));
		
		if(inv.contains(item))
		{
			
			for(int i=0;i<35;i++)
			{
				if(InventorySlotCheck(i).getItem() == item.getItem())
				{
					return InventorySlotCheck(i);
				} 
			}

			for(int i=2;i<=5;i++) //Armor slot check
			{
				if(ArmorSlotCheck(IntToSlot(i)).getItem() == item.getItem())
				{
					return ArmorSlotCheck(IntToSlot(i));
				} 
			}

			if(HandSlotCheck(EquipmentSlot.OFFHAND).getItem() == item.getItem())
			{
				return HandSlotCheck(EquipmentSlot.OFFHAND);
			} 

			return null; //logic error : -101 --> item found but not ?, can only happen with custom slot ! (probably)
		}
		else
		{
			return null;
		}
	}

}