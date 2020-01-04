package growthcraft.trapper.setup;

import net.minecraft.client.Minecraft;

public class ClientProxy implements IProxy {

    @Override
    public Minecraft getClientWorld() {
        return Minecraft.getInstance();
    }

}
