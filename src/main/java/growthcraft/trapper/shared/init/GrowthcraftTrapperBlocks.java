package growthcraft.trapper.shared.init;

import growthcraft.trapper.common.block.BlockFishtrap;
import growthcraft.trapper.common.inventory.ContainerFishtrap;
import growthcraft.trapper.common.tileentity.TileEntityFishtrap;
import growthcraft.trapper.shared.Reference;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class GrowthcraftTrapperBlocks {

    private GrowthcraftTrapperBlocks() { /* Do Nothing */ }

    @ObjectHolder(Reference.MODID + ":" + BlockFishtrap.unlocalizedName)
    public static BlockFishtrap fishtrap;

    @ObjectHolder(Reference.MODID + ":" + BlockFishtrap.unlocalizedName)
    public static TileEntityType<TileEntityFishtrap> fishtrapTileEntityType;

    @ObjectHolder(Reference.MODID + ":" + BlockFishtrap.unlocalizedName)
    public static ContainerType<ContainerFishtrap> fishtrapContainerType;

}
