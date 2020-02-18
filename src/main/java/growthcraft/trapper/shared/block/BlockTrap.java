package growthcraft.trapper.shared.block;

import growthcraft.trapper.shared.Reference;
import growthcraft.trapper.shared.tileentity.TileEntityTrap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class BlockTrap extends Block {

    public BlockTrap(String unlocalizedName) {
        super(getInitProperties());
        setRegistryName(Reference.MODID, unlocalizedName);
    }

    /**
     * Get the default initialization properties for this block object.
     *
     * @return Block properties for this block.
     */
    private static Properties getInitProperties() {
        Properties properties = Properties.create(Material.WOOD);
        properties.hardnessAndResistance(1.0F, 5.0F);
        properties.harvestTool(ToolType.PICKAXE);
        properties.harvestLevel(1);
        properties.sound(SoundType.WOOD);
        return properties;
    }

    // region TileEntity
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    // TODO: Make subclass implement override of createTileEntity

    // endregion

    // region DirectionalProperties
    public static Direction getFacingFromEntity(BlockPos clickedBlock, LivingEntity placer) {
        return Direction.getFacingFromVector(
                (float) (placer.posX - clickedBlock.getX()),
                (float) (placer.posY - clickedBlock.getY()),
                (float) (placer.posZ - clickedBlock.getZ())
        );
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (placer != null) {
            worldIn.setBlockState(pos, state.with(BlockStateProperties.FACING, getFacingFromEntity(pos, placer)), 2);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
    }
    // endregion
}
