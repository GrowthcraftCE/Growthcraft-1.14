package growthcraft.trapper.common.blocks;

import growthcraft.trapper.shared.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.common.ToolType;

public class FishtrapBlock extends Block {

    public static final String unlocalizedName = "fishtrap";

    public FishtrapBlock() {
        super(getInitProperties());
        this.setRegistryName(Reference.MODID, unlocalizedName);
    }

    /**
     * Get the default initialization properties for this block object.
     *
     * @return Block properties for this block.
     */
    private static Properties getInitProperties() {
        Properties properties = Properties.create(Material.WOOD);
        properties.hardnessAndResistance(1.0F, 10F);
        properties.harvestTool(ToolType.AXE);
        properties.harvestLevel(1);
        properties.sound(SoundType.WOOD);
        return properties;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

}
