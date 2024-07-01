package am2.api.flickers;

import net.minecraft.tileentity.TileEntity;

public interface IFlickerController<T extends TileEntity> {
	byte[] getMetadata(IFlickerFunctionality operator);

	void setMetadata(IFlickerFunctionality operator, byte[] meta);

	void removeMetadata(IFlickerFunctionality operator);
}
