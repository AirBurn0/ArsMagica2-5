package am2.spell.components;

import am2.AMCore;
import am2.RitualShapeHelper;
import am2.api.ArsMagicaApi;
import am2.api.blocks.MultiblockStructureDefinition;
import am2.api.spell.component.interfaces.IRitualInteraction;
import am2.api.spell.component.interfaces.ISpellComponent;
import am2.api.spell.enums.Affinity;
import am2.api.spell.enums.SpellModifiers;
import am2.blocks.BlocksCommonProxy;
import am2.buffs.BuffList;
import am2.entities.EntitySpecificHallucinations;
import am2.items.ItemsCommonProxy;
import am2.particles.AMParticle;
import am2.particles.ParticleArcToEntity;
import am2.spell.SpellHelper;
import am2.spell.SpellUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Random;

import static am2.AMEventHandler.summonCreature;
import static am2.AMEventHandler.tempCurseMap;

public class Curse implements ISpellComponent, IRitualInteraction{
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, int blockx, int blocky, int blockz, int blockFace, double impactX, double impactY, double impactZ, EntityLivingBase caster){
        return false;
    }

    @Override
    public ItemStack[] getReagents(){
        return new ItemStack[]{
                new ItemStack(Items.nether_wart),
                new ItemStack(Items.spider_eye)
        };
    }

    @Override
    public int getReagentSearchRadius(){
        return 3;
    }

    @Override
    public MultiblockStructureDefinition getRitualShape(){
        return RitualShapeHelper.instance.hourglass;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, EntityLivingBase caster, Entity target){
        if (world.isRemote || !(target instanceof EntityPlayer)){
            return true;
        }

        EntityPlayer player = (EntityPlayer)target;

        int x = MathHelper.floor_double(player.posX);
        int y = MathHelper.floor_double(player.posY);
        int z = MathHelper.floor_double(player.posZ);
        Class halclass = null;
        switch (player.worldObj.rand.nextInt(5)) {
            case 0:
            default:
                halclass = EntitySpecificHallucinations.EntityHallucinationCreeper.class;
                break;
            case 1:
                halclass = EntitySpecificHallucinations.EntityHallucinationZombie.class;
                break;
            case 2:
                halclass = EntitySpecificHallucinations.EntityHallucinationSpider.class;
                break;
            case 3:
                halclass = EntitySpecificHallucinations.EntityHallucinationWitherSkeleton.class;
                break;
        }

        int duration = SpellUtils.instance.getModifiedInt_Mul(BuffList.default_buff_duration, stack, caster, target, world, 0, SpellModifiers.DURATION);
        duration = SpellUtils.instance.modifyDurationBasedOnArmor(caster, duration);
        if (RitualShapeHelper.instance.checkForRitual(this, world, (int)Math.floor(target.posX), (int)Math.floor(target.posY), (int)Math.floor(target.posZ)) != null){
            duration += (3600 * (SpellUtils.instance.countModifiers(SpellModifiers.BUFF_POWER, stack, 0) + 1));
            RitualShapeHelper.instance.consumeRitualReagents(this, world, (int)Math.floor(target.posX), (int)Math.floor(target.posY), (int)Math.floor(target.posZ));
        }

        tempCurseMap.put(summonCreature(player.worldObj, halclass, x, y, z, player, 4, 9), duration);
        return true;
    }

    @Override
    public float manaCost(EntityLivingBase caster){
        return 500;
    }

    @Override
    public float burnout(EntityLivingBase caster){
        return ArsMagicaApi.getBurnoutFromMana(manaCost(caster));
    }

    @Override
    public ItemStack[] reagents(EntityLivingBase caster){
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, EntityLivingBase caster, Entity target, Random rand, int colorModifier){
        for (int i = 0; i < 15; ++i){
            AMParticle particle = (AMParticle) AMCore.proxy.particleManager.spawn(world, "ember", x, y, z);
            if (particle != null){
                particle.addRandomOffset(1, 1, 1);
                particle.setIgnoreMaxAge(true);
                particle.AddParticleController(new ParticleArcToEntity(particle, 1, caster, false).SetSpeed(0.03f).generateControlPoints());
                particle.setRGBColorF(1, 0.2f, 0.2f);
                particle.SetParticleAlpha(0.5f);
                if (colorModifier > -1){
                    particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
                }
            }
        }
    }

    @Override
    public EnumSet<Affinity> getAffinity(){
        return EnumSet.of(Affinity.LIFE);
    }

    @Override
    public int getID(){
        return 94;
    }

    @Override
    public Object[] getRecipeItems(){
        return new Object[]{
                new ItemStack(ItemsCommonProxy.rune, 1, ItemsCommonProxy.rune.META_RED),
                new ItemStack(ItemsCommonProxy.itemOre, 1, ItemsCommonProxy.itemOre.META_NIGHTMAREESSENCE),
                new ItemStack(ItemsCommonProxy.itemOre, 1, ItemsCommonProxy.itemOre.META_SUNSTONE),
                BlocksCommonProxy.sanguineAmaryllis
        };
    }

    @Override
    public float getAffinityShift(Affinity affinity){
        return 0.2f;
    }
}
