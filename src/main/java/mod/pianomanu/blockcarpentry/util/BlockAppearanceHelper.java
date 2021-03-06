package mod.pianomanu.blockcarpentry.util;

import mod.pianomanu.blockcarpentry.BlockCarpentryMain;
import mod.pianomanu.blockcarpentry.setup.Registration;
import mod.pianomanu.blockcarpentry.tileentity.FrameBlockTile;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

import static mod.pianomanu.blockcarpentry.util.BCBlockStateProperties.*;

/**
 * Util class for certain frame block things like light level and textures
 *
 * @author PianoManu
 * @version 1.0
 */
public class BlockAppearanceHelper {
    public static int setLightLevel(ItemStack item, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand) {
        int lightLevel=0;
        if (item.getItem()== Items.GLOWSTONE_DUST && state.get(LIGHT_LEVEL)<13) {
            int count = player.getHeldItem(hand).getCount();
            lightLevel=lightLevel+3;
            world.setBlockState(pos,state.with(LIGHT_LEVEL, state.getLightValue()+3));
            player.getHeldItem(hand).setCount(count-1);
            Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent("Light Level: "+(state.get(LIGHT_LEVEL)+3)), true);
        }
        if ((item.getItem() == Items.COAL || item.getItem() == Items.CHARCOAL) && state.get(LIGHT_LEVEL)<15) {
            int count = player.getHeldItem(hand).getCount();
            lightLevel=lightLevel+1;
            world.setBlockState(pos,state.with(LIGHT_LEVEL, state.getLightValue()+1));
            player.getHeldItem(hand).setCount(count-1);
            Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent("Light Level: " + (state.get(LIGHT_LEVEL)+1)), true);
        }
        if (item.getItem()== Items.GLOWSTONE_DUST && state.get(LIGHT_LEVEL) >= 13) {
            Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent("Light Level: " + state.get(LIGHT_LEVEL)), true);
        }
        if ((item.getItem() == Items.COAL || item.getItem() == Items.CHARCOAL) && state.get(LIGHT_LEVEL) == 15) {
            Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent("Light Level: " + state.get(LIGHT_LEVEL)), true);
        }
        return lightLevel;
    }

    public static void setTexture(ItemStack item, BlockState state, World world, PlayerEntity player, BlockPos pos) {

        if (item.getItem() == Registration.TEXTURE_WRENCH.get() && !player.isSneaking() && state.get(CONTAINS_BLOCK)) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof FrameBlockTile) {
                FrameBlockTile fte = (FrameBlockTile) tileEntity;
                List<TextureAtlasSprite> texture = TextureHelper.getTextureListFromBlock(fte.getMimic().getBlock());
                if (fte.getTexture() < texture.size()-1 && fte.getTexture() < texture.size()-1) {
                    fte.setTexture(fte.getTexture()+1);
                } else {
                    fte.setTexture(0);
                }
                Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent("Texture: "+fte.getTexture()), true);
            }
        }
    }

    public static void setDesign(World world, BlockPos pos, PlayerEntity player, ItemStack item) {
        if (item.getItem() == Registration.CHISEL.get() && !player.isSneaking()) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof FrameBlockTile) {
                FrameBlockTile fte = (FrameBlockTile) tileEntity;
                if (fte.getDesign() < fte.maxDesigns) {
                    fte.setDesign(fte.getDesign() + 1);
                } else {
                    fte.setDesign(0);
                }
                Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent("Design: "+fte.getDesign()), true);
            }
        }
    }

    public static void setDesignTexture(World world, BlockPos pos, PlayerEntity player, ItemStack item) {
        if (item.getItem() == Registration.PAINTBRUSH.get() && !player.isSneaking()) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof FrameBlockTile) {
                FrameBlockTile fte = (FrameBlockTile) tileEntity;
                if (fte.getDesignTexture() < fte.maxDesignTextures) {
                    fte.setDesignTexture(fte.getDesignTexture() + 1);
                } else {
                    fte.setDesignTexture(0);
                }
                //player.sendMessage(new TranslationTextComponent("message.frame.design_texture"));
                Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent("Design Texture: "+fte.getDesignTexture()), true);
            }
        }
    }

    public static void clearContent(World world, BlockState state, BlockPos pos, PlayerEntity player, Hand hand) {
        if (player.getHeldItem(hand).getItem() == Registration.HAMMER.get()) {
            //this.dropContainedBlock(world, pos);
            state = state.with(CONTAINS_BLOCK, Boolean.FALSE);
            world.setBlockState(pos,state,2);
        }
    }
}
