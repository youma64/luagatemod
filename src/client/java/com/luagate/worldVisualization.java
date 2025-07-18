package com.luagatemod;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.MinecraftClient;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import net.minecraft.util.math.BlockPos;

import net.minecraft.util.math.Direction; //not used for now but need it later for Neighbours blocks checks


public class worldVisualization extends luagateClient
{
	public Vec3i FindBlockAround(Block bl, int radius)
	{
		ClientWorld world = MinecraftClient.getInstance().world;
		Vec3d plPos = MinecraftClient.getInstance().player.getPos(); //Can't do vector operation on Blockpos, that's why i'm getting the pos instead

    	for(int y=-radius/2;y<radius/2;y++)
    	{
    		for(int x=-radius/2;x<radius/2;x++) 
    		{
    			for(int z=-radius/2;z<radius/2;z++)
    			{
    				BlockPos radPos = new BlockPos((int)plPos.x+x,(int)plPos.y+y,(int)plPos.z+z);  
        			if (world.getBlockState(radPos).getBlock() == bl)
        			{
            			return new Vec3i((int)plPos.x+x,(int)plPos.y+y,(int)plPos.z+z);
       				}
    			}
    		}
    	}

    	return null; //null : block not found
	}

	public int HowManyBlocksAround(Block bl, int radius)
	{
		ClientWorld world = MinecraftClient.getInstance().world;
		Vec3d plPos = MinecraftClient.getInstance().player.getPos(); //Can't do vector operation on Blockpos, that's why i'm getting the pos instead
		int count=0;

    	for(int y=-radius/2;y<radius/2;y++)
    	{
    		for(int x=-radius/2;x<radius/2;x++) 
    		{
    			for(int z=-radius/2;z<radius/2;z++)
    			{
    				BlockPos radPos = new BlockPos((int)plPos.x+x,(int)plPos.y+y,(int)plPos.z+z);  
        			if (world.getBlockState(radPos).getBlock() == bl)
        			{
            			count++;
       				}
    			}
    		}
    	}

    	return count;

	}
}