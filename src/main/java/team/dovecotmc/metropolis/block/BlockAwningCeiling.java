package team.dovecotmc.metropolis.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BlockAwningCeiling extends BlockHorizontalAxis {
    public static final EnumProperty<Type> TYPE = EnumProperty.create("type", Type.class);

    public BlockAwningCeiling(Properties settings) {
        super(settings);
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        return getStateForUpdate(levelAccessor, blockPos, blockState);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return getStateForUpdate(ctx.getLevel(), ctx.getClickedPos(), Objects.requireNonNull(super.getStateForPlacement(ctx)));
    }

    public BlockState getStateForUpdate(LevelAccessor level, BlockPos pos, BlockState state) {
        Direction.Axis axis = state.getValue(AXIS);
        Direction facing = Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE);
        BlockState leftState = level.getBlockState(pos.relative(facing.getClockWise()));
        BlockState rightState = level.getBlockState(pos.relative(facing.getCounterClockWise()));
        boolean left = leftState.getBlock() instanceof BlockAwningCeiling || (leftState.getBlock() instanceof BlockAwningPillar && leftState.getValue(BlockAwningPillar.TYPE).equals(BlockAwningPillar.Type.TOP));
        boolean right = rightState.getBlock() instanceof BlockAwningCeiling || (rightState.getBlock() instanceof BlockAwningPillar && rightState.getValue(BlockAwningPillar.TYPE).equals(BlockAwningPillar.Type.TOP));

        System.out.println("--");
        System.out.println(leftState);
        System.out.println(rightState);

        if (left && right) {
            return state.setValue(TYPE, Type.MIDDLE);
        } else if (right) {
            return state.setValue(TYPE, Type.RIGHT);
        } else if (left) {
            return state.setValue(TYPE, Type.LEFT);
        }
        return state.setValue(TYPE, Type.MIDDLE);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(TYPE);
    }

    public enum Type implements StringRepresentable {
        LEFT("left"),
        MIDDLE("middle"),
        RIGHT("right");

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
