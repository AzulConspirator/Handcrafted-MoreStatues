package com.azulc.morestatues.block.statue;

import com.azulc.morestatues.morestatues;
import com.azulc.morestatues.block.base.baseblock;
import com.azulc.morestatues.block.entity.MoreStatueEntityBlock;
import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class Wallblock extends baseblock {
    public static final MapCodec<Wallblock> CODEC = simpleCodec(Wallblock::new);
    private static final VoxelShape[] AABBS =  {
        Block.box(0.0, 4.5, 14.0, 16.0, 12.5, 16.0), // north
        Block.box(0.0, 4.5, 0.0, 16.0, 12.5, 2.0), // south
        Block.box(14.0, 4.5, 0.0, 16.0, 12.5, 16.0), //west
        Block.box(0.0, 4.5, 0.0, 2.0, 12.5, 16.0)}; // east

    public Wallblock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        String id = BuiltInRegistries.BLOCK.getKey(state.getBlock()).getPath();
        VoxelShape[] registeredShape = morestatues.STATUE_SHAPES.getOrDefault(id, AABBS);
        Direction facing = state.getValue(FACING);
        int index = switch (facing) {
            case NORTH -> 0;
            case SOUTH -> 1;
            case WEST  -> 2;
            case EAST  -> 3;
            default    -> 0;
        };

        // Safety Check: If the array doesn't have 4 shapes, fall back to index 0
        if (index < registeredShape.length && registeredShape[index] != null) {
            return registeredShape[index];
        }
        else if (registeredShape[0] != null)
        {
            return registeredShape[0];
        }
        else
        {
            return AABBS[index];
        }
    }
    @Override
    public @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        String id = BuiltInRegistries.BLOCK.getKey(state.getBlock()).getPath();
        VoxelShape[] registeredShape = morestatues.STATUE_SHAPES.getOrDefault(id, AABBS);
        Direction facing = state.getValue(FACING);
        int index = switch (facing) {
            case NORTH -> 0;
            case SOUTH -> 1;
            case WEST  -> 2;
            case EAST  -> 3;
            default    -> 0;
        };

        // Safety Check: If the array doesn't have 4 shapes, fall back to index 0
        if (index < registeredShape.length && registeredShape[index] != null) {
            return registeredShape[index];
        }
        else if (registeredShape[0] != null)
        {
            return registeredShape[0];
        }
        else
        {
            return AABBS[index];
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, VARIANT,POSE);
    }
    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

        @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MoreStatueEntityBlock(pos, state);
    }

}