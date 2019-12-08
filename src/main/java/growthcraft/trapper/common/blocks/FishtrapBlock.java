package growthcraft.trapper.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class FishtrapBlock extends Block {
    public FishtrapBlock(String unlocalizedName) {
        super(Properties.create(Material.WOOD)
                .hardnessAndResistance(1.0F, 10F)
                .harvestTool(ToolType.AXE)
                .harvestLevel(1)
                .sound(SoundType.WOOD));
    }
}
