package growthcraft.trapper.shared.tileentity;

import growthcraft.trapper.shared.handler.TrapItemStackHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityTrap extends TileEntity implements ITickableTileEntity {

    private LazyOptional<IItemHandler> handler = LazyOptional.of(this::createHandler);


    public TileEntityTrap(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    private IItemHandler createHandler() {
        return new TrapItemStackHandler(6);
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
        handler.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(inventory));
        super.read(compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        handler.ifPresent(
                h -> {
                    CompoundNBT inventory = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
                    compound.put("inventory", inventory);
                }
        );
        return super.write(compound);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }
}
