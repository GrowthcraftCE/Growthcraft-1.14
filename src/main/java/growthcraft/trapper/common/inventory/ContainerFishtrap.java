package growthcraft.trapper.common.inventory;

import growthcraft.trapper.shared.init.GrowthcraftTrapperBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.HopperTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class ContainerFishtrap extends Container {

    private TileEntity tileEntity;
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;

    public ContainerFishtrap(int id, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        super(GrowthcraftTrapperBlocks.fishtrapContainerType, id);
        this.playerInventory = new InvWrapper(playerInventory);
        this.playerEntity = playerEntity;
        this.tileEntity = world.getTileEntity(pos);

        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(
                h -> {
                    addSlot(new SlotItemHandler(h, 0, 17, 20));
                    addSlot(new SlotItemHandler(h, 1, 44, 20));
                    addSlot(new SlotItemHandler(h, 2, 62, 20));
                    addSlot(new SlotItemHandler(h, 3, 80, 20));
                    addSlot(new SlotItemHandler(h, 4, 98, 20));
                    addSlot(new SlotItemHandler(h, 5, 116, 20));
                }
        );

        playerInventorySlots(8, 51);
    }

    /**
     * Add inventory slots for GUI
     * @param x X coord of first slot
     * @param y Y coord of first slot
     */
    private void playerInventorySlots(int x, int y) {
        // Player Inventory Grid
        addSlotRows(playerInventory, 9, x, y, 9, 18, 3, 18);
        // Player Hotbox
        addSlotRow(playerInventory, 0, x, y + 58, 9, 18);
    }

    private int addSlotRow(IItemHandler itemHandler, int index, int x, int y, int slots, int offsetX) {
        for(int i = 0; i < slots; i++) {
            addSlot(new SlotItemHandler(itemHandler, index, x, y));
            x += offsetX;
            index++;
        }
        return index;
    }

    private int addSlotRows(IItemHandler itemHandler, int index, int x, int y, int cols, int offsetX, int rows, int offsetY) {
        for(int i = 0; i < rows; i++) {
            index = addSlotRow(itemHandler, index, x, y, rows, offsetX);
            y += offsetY;
        }
        return index;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, GrowthcraftTrapperBlocks.fishtrap);
    }
}
