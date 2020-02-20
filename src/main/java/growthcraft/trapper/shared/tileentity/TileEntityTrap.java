package growthcraft.trapper.shared.tileentity;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityTrap extends TileEntity implements ITickableTileEntity {

    private ItemStackHandler inputHandler;
    private ItemStackHandler outputHandler;

    public TileEntityTrap(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    private ItemStackHandler getHandler() {
        if (inputHandler == null) {
            inputHandler = new ItemStackHandler(1);
        }
        return inputHandler;
    }

    @Override
    public void tick() {
        if (world.isRemote()) {
            /* Do Something that all trap entities should do */
        }
    }

    @Override
    public void read(CompoundNBT compound) {
        CompoundNBT input = compound.getCompound("input");
        getHandler().deserializeNBT(input);
        super.read(compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        CompoundNBT input = getHandler().serializeNBT();
        compound.put("input", input);
        return super.write(compound);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return LazyOptional.of(() -> (T) getHandler());
        }
        return super.getCapability(cap, side);
    }
}
