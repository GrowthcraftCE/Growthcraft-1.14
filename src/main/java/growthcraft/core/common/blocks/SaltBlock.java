package growthcraft.core.common.blocks;

import growthcraft.core.shared.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class SaltBlock extends Block {

    public static final String unlocalizedName = "salt_block";
    public static final String modReferenceName = Reference.MODID + ":" + unlocalizedName;

    public SaltBlock() {
        super(getInitProperties());
        this.setRegistryName(Reference.MODID, unlocalizedName);
    }

    private static Properties getInitProperties() {
        Properties properties = Properties.create(Material.SAND);
        properties.sound(SoundType.SAND);
        return  properties;
    }

}
