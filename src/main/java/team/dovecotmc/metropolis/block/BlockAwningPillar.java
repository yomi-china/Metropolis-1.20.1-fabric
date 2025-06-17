package team.dovecotmc.metropolis.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.NotNull;

public class BlockAwningPillar extends BlockHorizontalAxis implements IBlockAwningPillar {
    public static final EnumProperty<Type> TYPE = EnumProperty.create("type", Type.class);

    public BlockAwningPillar(Properties settings) {
        super(settings);
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        return getStateForUpdate(levelAccessor, blockPos, blockState);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return getStateForUpdate(ctx.getLevel(), ctx.getClickedPos(), super.getStateForPlacement(ctx));
    }

    public BlockState getStateForUpdate(LevelAccessor level, BlockPos pos, BlockState state) {
        BlockState above = level.getBlockState(pos.above());
        BlockState below = level.getBlockState(pos.below());
        if (above.getBlock() instanceof BlockAwningPillar && below.getBlock() instanceof BlockAwningPillar) {
            return state.setValue(TYPE, Type.MIDDLE);
        } else if (above.getBlock() instanceof BlockAwningPillar) {
            if (above.getValue(AXIS).equals(state.getValue(AXIS))) {
                return state.setValue(TYPE, Type.BOTTOM);
            } else {
                return state.setValue(TYPE, Type.MIDDLE);
            }
        } else if (below.getBlock() instanceof BlockAwningPillar) {
            if (below.getValue(AXIS).equals(state.getValue(AXIS))) {
                return state.setValue(TYPE, Type.TOP);
            } else {
                return state.setValue(TYPE, Type.MIDDLE);
            }
        } else {
            return state.setValue(TYPE, Type.MIDDLE);
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(TYPE);
    }

    public enum Type implements StringRepresentable {
        BOTTOM("bottom"),
        TOP("top"),
        MIDDLE("middle");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        @Override
        public java.lang.@NotNull String getSerializedName() {
            return this.name;
        }
    }
}
