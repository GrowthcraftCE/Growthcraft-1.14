package growthcraft.trapper.common.tileentity;

import growthcraft.trapper.shared.init.GrowthcraftTrapperBlocks;
import growthcraft.trapper.shared.tileentity.TileEntityTrap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTables;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class TileEntityFishtrap extends TileEntityTrap {

    public TileEntityFishtrap() {
        super(GrowthcraftTrapperBlocks.fishtrapTileEntityType, 256, 1024);
        setLootTable(ServerLifecycleHooks.getCurrentServer().getLootTableManager().getLootTableFromLocation(LootTables.GAMEPLAY_FISHING));
    }

}
