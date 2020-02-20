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

    private ItemStackHandler handler;

    public TileEntityTrap(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    private ItemStackHandler getHandler() {
        if (handler == null) {
            handler = new ItemStackHandler(1);
        }
        return handler;
    }

    @Override
    public void tick() {
        if (world.isRemote()) {
            /* Do Something that all trap entities should do */
        }
    }

    @Override
    public void read(CompoundNBT compound) {
        CompoundNBT inventory = compound.getCompound("inventory");
        getHandler().deserializeNBT(inventory);
        super.read(compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        CompoundNBT inventory = getHandler().serializeNBT();
        compound.put("inventory", inventory);
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
