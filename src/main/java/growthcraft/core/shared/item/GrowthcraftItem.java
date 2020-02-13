package growthcraft.core.shared.item;

import growthcraft.core.Growthcraft;
import net.minecraft.item.Item;

public class GrowthcraftItem extends Item {

    public GrowthcraftItem(String modId, String unlocalizedName) {
        super(getInitProperties());
        setRegistryName(modId, unlocalizedName);
    }

    /**
     * Setup properties that we want all Growthcraft Items to have, like the
     * creative tab.
     * @return
     */
    private static Properties getInitProperties() {
        Properties properties = new Properties();
        properties.group(Growthcraft.itemGroup);
        return properties;
    }

}
