package growthcraft.core.shared.init;

import growthcraft.core.common.blocks.RockSaltOreBlock;
import growthcraft.core.common.blocks.SaltBlock;
import growthcraft.core.shared.Reference;
import net.minecraftforge.registries.ObjectHolder;

public class GrowthcraftCoreBlocks {

    private GrowthcraftCoreBlocks() {
        // Private constructor, do nothing.
    }

    @ObjectHolder(Reference.MODID + ":" + RockSaltOreBlock.unlocalizedName)
    public static RockSaltOreBlock rockSaltOreBlock;

    @ObjectHolder(SaltBlock.modReferenceName)
    public static SaltBlock saltBlock;

}
