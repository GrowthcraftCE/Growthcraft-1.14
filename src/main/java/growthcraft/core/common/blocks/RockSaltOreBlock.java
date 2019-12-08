package growthcraft.core.common.blocks;

import growthcraft.core.shared.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class RockSaltOreBlock extends Block {

    public static final String unlocalizedName = "rocksalt_ore";

    public RockSaltOreBlock() {
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
        properties.hardnessAndResistance(3.0F, 5.0F);
        properties.harvestTool(ToolType.PICKAXE);
        properties.harvestLevel(1);
        properties.sound(SoundType.STONE);
        return properties;
    }

}
