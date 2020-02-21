package growthcraft.trapper.shared.tileentity;

import growthcraft.trapper.shared.handler.TrapItemStackHandler;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class TileEntityTrap extends TileEntity implements ITickableTileEntity {

    private int cooldown = 0;
    private int randomMaxCooldown = 1024;
    private int minCoolDown = 256;
    private int maxCoolDown = 1024;
    private Random rand = new Random();;

    private LazyOptional<IItemHandler> handler = LazyOptional.of(this::createHandler);
    private LootTable lootTable;

    public TileEntityTrap(TileEntityType<?> tileEntityTypeIn, int minCoolDown, int maxCoolDown) {
        super(tileEntityTypeIn);
        this.cooldown = 0;
        this.minCoolDown = minCoolDown;
        this.maxCoolDown = maxCoolDown;
        this.randomMaxCooldown = getRandomCooldown();
    }

    public void setLootTable(LootTable lootTable) {
        this.lootTable = lootTable;
    }

    private int getRandomCooldown() {
        return rand.nextInt((maxCoolDown - minCoolDown) + 1) + minCoolDown;
    }

    private IItemHandler createHandler() {
        return new TrapItemStackHandler(6);
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

    @Override
    public void tick() {
        // Do nothing by default. The specific trap should override this?
        cooldown++;
        if (cooldown == randomMaxCooldown) {
            List<ItemStack> list = getLootItemStack();
            for(ItemStack itemStack : list) {
                ItemEntity itemEntity = new ItemEntity(world, this.pos.getX(), this.pos.getY(), this.pos.getZ(), itemStack);
                world.addEntity(itemEntity);
            }
        }
    }

    private boolean inWater() {
        return true;
    }

    private List<ItemStack> getLootItemStack() {
        LootContext.Builder builder = new LootContext.Builder((ServerWorld) world).withRandom(rand);
        List<ItemStack> itemStackList = lootTable.generate(builder.build(LootParameterSets.FISHING));
        return itemStackList;
    }


}
