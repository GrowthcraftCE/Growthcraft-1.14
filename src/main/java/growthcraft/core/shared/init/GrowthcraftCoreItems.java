package growthcraft.core.shared.init;

import growthcraft.core.common.blocks.SaltBlock;
import growthcraft.core.common.items.SaltItem;
import growthcraft.core.shared.Reference;
import net.minecraftforge.registries.ObjectHolder;

public class GrowthcraftCoreItems {

    @ObjectHolder(Reference.MODID + ":" + SaltBlock.unlocalizedName)
    public static SaltItem salt;

}
