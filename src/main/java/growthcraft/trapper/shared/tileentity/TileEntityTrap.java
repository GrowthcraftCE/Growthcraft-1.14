package growthcraft.trapper.shared.tileentity;

import growthcraft.trapper.GrowthcraftTrapper;
import growthcraft.trapper.common.inventory.ContainerFishtrap;
import growthcraft.trapper.shared.handler.TrapItemStackHandler;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class TileEntityTrap extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    private int cooldown = 0;
    private int randomMaxCooldown = 1024;
    private int minCoolDown = 256;
    private int maxCoolDown = 1024;
    private Random rand = new Random();

    private LazyOptional<TrapItemStackHandler> handler = LazyOptional.of(this::createHandler);
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

    private TrapItemStackHandler createHandler() {
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
        if (world.isRemote()) {
            return;
        }

        cooldown++;
        if (cooldown >= randomMaxCooldown) {
            List<ItemStack> list = getLootItemStack();
            for ( ItemStack itemStack : list) {

                handler.ifPresent(
                        h -> {
                            // Iterate over the slots to find matching item
                            GrowthcraftTrapper.LOGGER.warn("Handler is present ... ");
                            for (int i = 1; i < h.getSlots(); i++) {

                                if (itemStack.getCount() > 0 && itemStack.getItem() == h.getStackInSlot(i).getItem()) {
                                    GrowthcraftTrapper.LOGGER.warn("About to insert into inventory ... ");
                                    h.insertLootedItem(i, itemStack, false, true);
                                } else if (itemStack.getCount() > 0 && h.getStackInSlot(i).getItem() == Items.AIR) {
                                    h.insertLootedItem(i, itemStack, false, true);
                                }
                                markDirty();
                            }
                        }
                );

                ItemEntity itemEntity = new ItemEntity(world, this.pos.getX(), this.pos.getY(), this.pos.getZ(), itemStack);
                world.addEntity(itemEntity);
            }
            randomMaxCooldown = getRandomCooldown();
            cooldown = 0;
        }

    }

    private boolean inWater() {
        return true;
    }

    private List<ItemStack> getLootItemStack() {
        LootContext.Builder builder = new LootContext.Builder((ServerWorld) world).withRandom(rand);
        return lootTable.generate(builder.build(LootParameterSets.EMPTY));
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new ContainerFishtrap(i, world, pos, playerInventory, playerEntity);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }
}
