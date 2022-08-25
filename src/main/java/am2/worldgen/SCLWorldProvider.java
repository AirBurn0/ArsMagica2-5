package am2.worldgen;

import am2.AMCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;

public class SCLWorldProvider extends WorldProvider {
   private final float[] colorsSunriseSunset = new float[4];

   public void registerWorldChunkManager() {
      this.worldChunkMgr = new WorldChunkManagerHell(SCLBiome.instance, (float)this.dimensionId);
      this.dimensionId = AMCore.config.getMMFDimensionID();
      this.hasNoSky = false;
   }

   public IChunkProvider createChunkGenerator() {
      return new SCLChunkProvider(this.worldObj, this.worldObj.getSeed());
   }

   public long getWorldTime() {
      return 18500L;
   }

   public int getAverageGroundLevel() {
      return 0;
   }

   @SideOnly(Side.CLIENT)
   public boolean doesXZShowFog(int par1, int par2) {
      return false;
   }

   public String getDimensionName() {
      return "The Moo Moo Farm";
   }

   public boolean shouldMapSpin(String entity, double x, double y, double z) {
      return true;
   }

   @SideOnly(Side.CLIENT)
   public boolean isSkyColored() {
      return true;
   }

   public boolean canRespawnHere() {
      return false;
   }

   public boolean isSurfaceWorld() {
      return true;
   }

   @SideOnly(Side.CLIENT)
   public float getCloudHeight() {
      return 128.0F;
   }

   public boolean canCoordinateBeSpawn(int par1, int par2) {
      return false;
   }

   public ChunkCoordinates getEntrancePortalLocation() {
      return new ChunkCoordinates(50, 5, 0);
   }

   protected void generateLightBrightnessTable() {
      float f = 12.0F;

      for(int i = 0; i <= 15; ++i) {
         float f1 = 12.0F - (float)i / 15.0F;
         this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
      }

   }

   @SideOnly(Side.CLIENT)
   public String getWelcomeMessage() {
      return this instanceof SCLWorldProvider ? "Entering The Moo Moo Farm" : null;
   }

   @SideOnly(Side.CLIENT)
   public float[] calcSunriseSunsetColors(float par1, float par2) {
      float f2 = 0.4F;
      float f3 = MathHelper.cos(par1 * 3.141593F * 2.0F) - 0.0F;
      float f4 = -0.0F;
      if (f3 >= f4 - f2 && f3 <= f4 + f2) {
         float f5 = (f3 - f4) / f2 * 0.5F + 0.5F;
         float f6 = 1.0F - (1.0F - MathHelper.sin(f5 * 3.141593F)) * 0.99F;
         f6 *= f6;
         this.colorsSunriseSunset[0] = f5 * 0.3F + 0.7F;
         this.colorsSunriseSunset[1] = f5 * f5 * 0.7F + 0.2F;
         this.colorsSunriseSunset[2] = f5 * f5 * 0.0F + 0.2F;
         this.colorsSunriseSunset[3] = f6;
         return this.colorsSunriseSunset;
      } else {
         return null;
      }
   }

   public float calculateCelestialAngle(long par1, float par3) {
      int j = (int)(par1 % 24000L);
      float f1 = ((float)j + par3) / 24000.0F - 0.25F;
      if (f1 < 0.0F) {
         ++f1;
      }

      if (f1 > 1.0F) {
         --f1;
      }

      float f2 = f1;
      f1 = 1.0F - (float)((Math.cos((double)f1 * 3.141592653589793D) + 1.0D) / 2.0D);
      f1 = f2 + (f1 - f2) / 3.0F;
      return f1;
   }

   @SideOnly(Side.CLIENT)
   public Vec3 getFogColor(float par1, float par2) {
      int i = 10518688;
      float f2 = MathHelper.cos(par1 * 3.141593F * 2.0F) * 2.0F + 0.5F;
      if (f2 < 0.0F) {
         f2 = 0.0F;
      }

      if (f2 > 1.0F) {
         f2 = 1.0F;
      }

      float f3 = (float)(i >> 16 & 255) / 255.0F;
      float f4 = (float)(i >> 8 & 255) / 255.0F;
      float f5 = (float)(i & 255) / 255.0F;
      f3 *= f2 * 0.0F + 0.15F;
      f4 *= f2 * 0.0F + 0.15F;
      f5 *= f2 * 0.0F + 0.15F;
      return Vec3.createVectorHelper((double)f3, (double)f4, (double)f5);
   }
}
