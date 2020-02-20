package growthcraft.trapper.shared.handler;

import growthcraft.trapper.GrowthcraftTrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class TrapItemStackHandler extends ItemStackHandler {
    private boolean insertingLootItem = false;

    public TrapItemStackHandler(int size) {
        super(size);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        // TODO: Handle slot 0 as the bait slot only.
        //  User should not be able to insert into slot 1 through 5
        return super.isItemValid(slot, stack);
    }

    /**
     * Override of insertItem. The trap tile entities should only allow a user/pipe to insert into the bait slot.
     * the item trap or tile entity trap should call insertLootedItem to try and insert loot into the inventory.
     * @param slot The slot id to insert into
     * @param stack The itemStack to be inserted
     * @param simulate Should we test this insert
     * @return The remaining item stack if the insert failed
     */
    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        GrowthcraftTrapper.LOGGER.warn(String.format("Trying to insert %s into slot %d ... ", stack.getItem().getRegistryName(), slot));
        // If we are adding to the looted item slots, iterate over the slots and try and add the item.
        if ( insertingLootItem && slot > 0) {
            for ( int i = 1; i < this.getSlots(); i++) {
                try {
                    this.insertLootedItem(i, stack, simulate, true);
                    stack.shrink(-1);
                } catch (Exception e) {
                    // Do nothing, just swallow the exception.
                }
            }
            insertingLootItem = false;
            return  super.insertItem(slot, stack, simulate);
        }

        // If the slot is the bait slot, then try and insert.
        if ( slot == 0 ) {
            return super.insertItem(slot, stack, simulate);
        } else {
            return stack;
        }

    }

    public ItemStack insertLootedItem(int slot, @Nonnull ItemStack stack, boolean simulate, boolean insertLoot) {
        insertingLootItem = insertLoot;
        return insertItem(slot, stack, simulate);
    }

}
