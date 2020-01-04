package growthcraft.core.common.items;

import growthcraft.core.Growthcraft;
import growthcraft.core.shared.Reference;
import net.minecraft.item.Item;

public class SaltItem extends Item {

    public static final String unlocalizedName = "salt";

    public SaltItem() {
        super(getInitProperties());
        setRegistryName(Reference.MODID, unlocalizedName);
    }

    private static Properties getInitProperties() {
        Properties properties = new Properties();
        properties.group(Growthcraft.itemGroup);
        return properties;
    }
}
