package am2.items;

import am2.buffs.BuffEffectPsychedelic;
import am2.buffs.BuffList;
import am2.buffs.BuffMaxManaIncrease;
import am2.texture.ResourceManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;


public class ItemAstrocalis extends ArsMagicaItem{

	public ItemAstrocalis(){
		super();
		this.setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer){
		if (!par3EntityPlayer.isPotionActive(BuffList.psychedelic))
			par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}

	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer){
		par1ItemStack = new ItemStack(Items.glass_bottle);
		par3EntityPlayer.addPotionEffect(new BuffEffectPsychedelic(5000, 2));
		return par1ItemStack;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack){
		return 32;
	}

	public ItemAstrocalis setUnlocalizedAndTextureName(String name){
		this.setUnlocalizedName(name);
		setTextureName(name);
		return this;
	}

	/**
	 * Return an item rarity from EnumRarity
	 */
	public EnumRarity getRarity(ItemStack p_77613_1_)
	{
		return EnumRarity.epic;
	}

	public EnumAction getItemUseAction(ItemStack p_77661_1_){
		return EnumAction.drink;
	}

	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack, int pass) {
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List lines, boolean iHaveNoIdea){
		super.addInformation(stack, player, lines, iHaveNoIdea);
		lines.add(StatCollector.translateToLocal("am2.tooltip.sideeffects"));
	}
}
