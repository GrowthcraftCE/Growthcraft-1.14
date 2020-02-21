package growthcraft.trapper.common.tileentity;

import growthcraft.trapper.shared.init.GrowthcraftTrapperBlocks;
import growthcraft.trapper.shared.tileentity.TileEntityTrap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class TileEntityFishtrap extends TileEntityTrap {

    public TileEntityFishtrap() {
        super(GrowthcraftTrapperBlocks.fishtrapTileEntityType, 256, 1024);
        setLootTable(ServerLifecycleHooks.getCurrentServer().getLootTableManager().getLootTableFromLocation(new ResourceLocation("minecraft:fishing")));
    }

    @Override
    public void tick() {
        // Do nothing by default. The specific trap should override this?

    }

}
