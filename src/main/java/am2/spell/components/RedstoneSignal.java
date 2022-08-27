package am2.spell.components;

import am2.AMCore;
import am2.api.ArsMagicaApi;
import am2.api.blocks.IKeystoneLockable;
import am2.api.items.KeystoneAccessType;
import am2.api.spell.component.interfaces.ISpellComponent;
import am2.api.spell.enums.Affinity;
import am2.api.spell.enums.SpellModifiers;
import am2.blocks.BlocksCommonProxy;
import am2.items.ItemsCommonProxy;
import am2.playerextensions.ExtendedProperties;
import am2.spell.SpellUtils;
import am2.utility.DummyEntityPlayer;
import am2.utility.KeystoneUtilities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.tclproject.mysteriumlib.asm.fixes.MysteriumPatchesFixesMagicka;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;

public class RedstoneSignal implements ISpellComponent{

	@Override
	public boolean applyEffectBlock(ItemStack stack, World world, int blockx, int blocky, int blockz, int blockFace, double impactX, double impactY, double impactZ, EntityLivingBase caster){
		Block block = world.getBlock(blockx, blocky, blockz);
		if (block == Blocks.air || block.getMaterial().isLiquid() || block instanceof BlockDoor || block instanceof BlockTrapDoor){
			return false;
		}

		int activeTime = SpellUtils.instance.getModifiedInt_Mul(10, stack, caster, caster, world, 0, SpellModifiers.DURATION);
		activeTime = SpellUtils.instance.modifyDurationBasedOnArmor(caster, activeTime);
		MysteriumPatchesFixesMagicka.providingRedstone.add(new int[]{world.provider.dimensionId, blockx, blocky, blockz, activeTime});
		block.onNeighborBlockChange(world, blockx, blocky, blockz, Blocks.stonebrick);
		return true;
	}

	@Override
	public boolean applyEffectEntity(ItemStack stack, World world, EntityLivingBase caster, Entity target){
		return false;
	}

	@Override
	public float manaCost(EntityLivingBase caster){
		return 15;
	}

	@Override
	public float burnout(EntityLivingBase caster){
		return 12;
	}

	@Override
	public ItemStack[] reagents(EntityLivingBase caster){
		return null;
	}

	@Override
	public void spawnParticles(World world, double x, double y, double z, EntityLivingBase caster, Entity target, Random rand, int colorModifier){
		for (int i = 0; i < 3; i++) world.spawnParticle("reddust", x, y, z, rand.nextDouble() - 0.5, rand.nextDouble() - 0.5 ,rand.nextDouble() - 0.5);
	}

	@Override
	public EnumSet<Affinity> getAffinity(){
		return EnumSet.of(Affinity.EARTH);
	}

	@Override
	public int getID(){
		return 80;
	}

	@Override
	public Object[] getRecipeItems(){
		return new Object[]{
				new ItemStack(ItemsCommonProxy.rune, 1, ItemsCommonProxy.rune.META_RED),
				Items.redstone,
				new ItemStack(Blocks.lever)
		};
	}

	@Override
	public float getAffinityShift(Affinity affinity){
		return 0.001f;
	}
}
