package growthcraft.core.shared.init;

import growthcraft.core.common.block.BlockRockSaltOre;
import growthcraft.core.common.block.BlockSalt;
import growthcraft.core.shared.Reference;
import net.minecraftforge.registries.ObjectHolder;

public class GrowthcraftCoreBlocks {

    private GrowthcraftCoreBlocks() {
        // Private constructor, do nothing.
    }

    @ObjectHolder(Reference.MODID + ":" + BlockRockSaltOre.unlocalizedName)
    public static BlockRockSaltOre rockSaltOreBlock;

    @ObjectHolder(BlockSalt.modReferenceName)
    public static BlockSalt blockSalt;

}
