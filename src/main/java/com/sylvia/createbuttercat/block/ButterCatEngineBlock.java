package com.sylvia.createbuttercat.block;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import com.sylvia.createbuttercat.datagen.other.ModTags;
import com.sylvia.createbuttercat.event.ClientEffect;
import com.sylvia.createbuttercat.register.ModBlockEnetities;
import com.sylvia.createbuttercat.register.ModDataComponents;
import com.sylvia.createbuttercat.register.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ButterCatEngineBlock extends HorizontalKineticBlock implements  IBE<ButterCatEngineBlockEntity> {


    public ButterCatEngineBlock(Properties properties) {
        super(properties);
    }
    private static final VoxelShape SHAPE_1 = Shapes.box(0.0, .2, .2, 1.0, .8, .8);
    private static final VoxelShape SHAPE_2 = Shapes.box(.2, .2, 0.0, .8, .8, 1.0);

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(HORIZONTAL_FACING)) {
            case NORTH, SOUTH -> SHAPE_2;
            default -> SHAPE_1;
        };
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }
    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof ButterCatEngineBlockEntity be) {
            int butterCount = be.getButterCount();
            if (butterCount <= 0) return 0;

            int maxButter = ButterCatEngineBlockEntity.getMaxButterCount();

            return (int) Math.ceil((double) butterCount / maxButter * 15);
        }
        return 0;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (hand != InteractionHand.MAIN_HAND || itemStack.is(AllItems.WRENCH)) {
            return ItemInteractionResult.FAIL;
        }

        if (player.isCrouching() || !(level.getBlockEntity(pos) instanceof ButterCatEngineBlockEntity be)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (!be.hasBread()) {
            if (ModTags.matchesIngredient(itemStack, ModTags.getBreads())) {
                be.addBread();
                itemStack.shrink(1);
                if (level.isClientSide) {
                    ClientEffect.create(level, pos, ClientEffect.EffectType.BREAD);
                }
                return ItemInteractionResult.SUCCESS;
            }
            displayMessage(player, "string.createbuttercat.no_bread", 0xFF5555);
            return ItemInteractionResult.SUCCESS;
        }

        if (!be.isInfinite() && itemStack.is(ModItems.SUPER_BUTTER)) {
            be.setInfinite(true);
            itemStack.shrink(1);
            if (level.isClientSide) {
                ClientEffect.create(level, pos, ClientEffect.EffectType.SUPER_BUTTER);
            }
            displayMessage(player, "string.createbuttercat.infinite", 0xFF55FF);
        }

        if (ModTags.matchesIngredient(itemStack, ModTags.getButters())) {
            if (be.isFull()) {
                displayMessage(player, "string.createbuttercat.full", 0xFF5555);
                return ItemInteractionResult.FAIL;
            }
            Integer butterLevel = itemStack.get(ModDataComponents.BUTTER_LEVEL);
            if (butterLevel != null) {
                be.addButterCount(butterLevel.intValue());
                itemStack.shrink(1);
                if (level.isClientSide) {
                    ClientEffect.create(level, pos, ClientEffect.EffectType.BUTTER);
                }
            }
        }

        if (be.getButterCount() == 0) {
            displayMessage(player, "string.createbuttercat.no_butter", 0xFFAA00);
        }

        return ItemInteractionResult.SUCCESS;
    }

    private void displayMessage(Player player, String translationKey, int color) {
        player.displayClientMessage(Component.translatable(translationKey).withColor(color), true);
    }
    public static InteractionResultHolder<ItemStack> armInsert(BlockState state, Level level, BlockPos pos, ItemStack itemStack, boolean simulate) {
        if (!state.hasBlockEntity())
            return InteractionResultHolder.fail(ItemStack.EMPTY);

        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof ButterCatEngineBlockEntity blockEntity))
            return InteractionResultHolder.fail(ItemStack.EMPTY);

        if ( blockEntity.isFull())
            return InteractionResultHolder.fail(ItemStack.EMPTY);

        // 0=bread
        // 1=butter
        // 2=super butter
        // else
        int butterType = -1;

        if(!blockEntity.hasBread()){
            if(ModTags.matchesIngredient(itemStack,ModTags.getBreads()))
                butterType = 0;
        }else {
            if ( ModTags.matchesIngredient(itemStack,ModTags.getButters()))
                butterType = 1;
            else if (itemStack.is(ModItems.SUPER_BUTTER))
                butterType = 2;
        }

        if(butterType == -1 )
            return InteractionResultHolder.fail(ItemStack.EMPTY);

        if(!simulate){
            switch (butterType){
                case 0 -> {
                    blockEntity.addBread();
                }
                case 1 -> {
                    int levelCount = itemStack.get(ModDataComponents.BUTTER_LEVEL).intValue();
                    blockEntity.addButterCount(levelCount);
                }
                case 2 ->  {
                    blockEntity.setInfinite(true);
                }
            }
        }

        ItemStack container = itemStack.hasCraftingRemainingItem() ? itemStack.getCraftingRemainingItem() : ItemStack.EMPTY;
        if(!level.isClientSide) itemStack.shrink(1);
        return InteractionResultHolder.success(container);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(HORIZONTAL_FACING).getAxis();
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face.getAxis() == getRotationAxis(state);
    }

    @Override
    public Class<ButterCatEngineBlockEntity> getBlockEntityClass() {
        return ButterCatEngineBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends ButterCatEngineBlockEntity> getBlockEntityType() {
        return ModBlockEnetities.BUTTER_CAT_ENGINE_BE.get();
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.hasBlockEntity() && (state.getBlock() != newState.getBlock() || !newState.hasBlockEntity())) {
            if (level.getBlockEntity(pos) instanceof ButterCatEngineBlockEntity be) {
                if (!level.isClientSide) {
                    level.addFreshEntity(be.getCat(level));
                }
                if (be.isInfinite()) {
                    Block.popResource(level, pos, new ItemStack(ModItems.SUPER_BUTTER.get()));
                } else {
                    int butterCount = be.getButterCount();
                    if (butterCount > 0) Block.popResource(level, pos, new ItemStack(ModItems.BUTTER.get(), butterCount));
                }
                level.removeBlockEntity(pos);
            }
        }
    }
}
