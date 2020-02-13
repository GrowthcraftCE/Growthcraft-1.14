package growthcraft.core.common.items;

import growthcraft.core.Growthcraft;
import growthcraft.core.shared.Reference;
import net.minecraft.item.Item;

public class CrowbarItem extends Item {

    public static final String unlocalizedName = "crowbar";

    public CrowbarItem(String colour) {
        super(getInitProperties());
        setRegistryName(Reference.MODID, unlocalizedName + "_" + colour);
    }

    private static Properties getInitProperties() {
        Properties properties = new Properties();
        properties.group(Growthcraft.itemGroup);
        properties.maxStackSize(1);
        return properties;
    }

}
