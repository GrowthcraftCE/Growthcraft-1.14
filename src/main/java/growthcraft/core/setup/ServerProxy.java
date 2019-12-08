package growthcraft.core.setup;

import net.minecraft.client.Minecraft;

public class ServerProxy implements IProxy {

    @Override
    public Minecraft getClientWorld() {
        throw new IllegalStateException("This should only be called on client.");
    }

}
