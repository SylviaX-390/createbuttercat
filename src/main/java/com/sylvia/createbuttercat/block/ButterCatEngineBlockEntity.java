package com.sylvia.createbuttercat.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.contraptions.bearing.WindmillBearingBlockEntity;
import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollOptionBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;
import com.sylvia.createbuttercat.register.*;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

import static com.simibubi.create.content.kinetics.base.HorizontalKineticBlock.HORIZONTAL_FACING;


public class  ButterCatEngineBlockEntity  extends GeneratingKineticBlockEntity {
    private static final Logger log = LoggerFactory.getLogger(ButterCatEngineBlockEntity.class);
    protected ScrollOptionBehaviour<WindmillBearingBlockEntity.RotationDirection> movementDirection;
    protected final List<ResourceKey<CatVariant>> catVariants = new ArrayList<>();
    protected boolean bread =false;
    protected boolean infinite =false;
    protected int butterCount = 0;
    protected int overflowCount = 0;
    protected int cd = 0;
    protected float angle = 0;

    public ButterCatEngineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        catVariants.add(CatVariant.TABBY);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        movementDirection = new ScrollOptionBehaviour<>(WindmillBearingBlockEntity.RotationDirection.class,
                CreateLang.translateDirect("contraptions.windmill.rotation_direction"), this,new BCEValueBox());
        movementDirection.withCallback($ -> updateGeneratedRotation());

        behaviours.add(movementDirection);
    }
    @Override
    public void initialize() {
        super.initialize();
        if (!hasSource() || getGeneratedSpeed() > getTheoreticalSpeed())
            updateGeneratedRotation();

    }

    public void dropButterCat(Level level){
        for(int i= 0;i<getCatCount();i++){
            level.addFreshEntity(getCat(level, catVariants.get(i)));
        }
        if (isInfinite())
            Block.popResource(level, getBlockPos(), new ItemStack(ModItems.SUPER_BUTTER.get()));
        else if (getButterCount() > 0) Block.popResource(level, getBlockPos(), new ItemStack(ModItems.BUTTER.get(), getButterCount()));

    }
    ///================getter/setter================
    public void addButterCount(int count) {
        this.butterCount += count;
        if (this.butterCount < 0) this.butterCount = 0;
        if (this.butterCount > getMaxButterCount()) {
            this.overflowCount += butterCount - getMaxButterCount();
            this.butterCount = getMaxButterCount();
        }
        
        updateGeneratedRotation();
    }
    public int getButterCount() {return butterCount;}
    public int getTotalCount() {return butterCount + overflowCount;}
    public int getCatCount(){return catVariants.size();}
    public void clearCats(){
        catVariants.clear();}
    public boolean addCat(ResourceKey<CatVariant> catVariant) {
        if (getCatCount() >=4) return false;
        this.catVariants.add(catVariant);
        return true;
    }

    public Cat getCat(Level level,ResourceKey<CatVariant> variant) {
        Cat cat = new Cat(EntityType.CAT, level);
        cat.setVariant(BuiltInRegistries.CAT_VARIANT.getHolder(variant).get());
        cat.setPos(getBlockPos().getCenter().offsetRandom(level.random, 0.2f));
        Player player = level.getNearestPlayer(cat,6);
        if(player!=null)
            cat.setLeashedTo(player, true);
        cat.revive();
        return cat;
    }
    public void addBread(){
        bread = true;
        updateGeneratedRotation();
    }
    public boolean hasBread(){
        return bread;
    }

    public void setInfinite(boolean bool) {
        bread = bool;
        infinite = bool;
        if(bool)
            butterCount = getMaxButterCount();
        else
            butterCount = 0;
        updateGeneratedRotation();

    }
    public boolean isInfinite(){
        return infinite;
    }
    public boolean isFull() {
        return overflowCount > getCatCount() || isInfinite();
    }

    public int getCd(boolean remaining) {
        return remaining ? 200 - cd : cd;
    }

    public void tick(){
        super.tick();

        angle = ( angle +  getAngularSpeed())% 360;
        if(isInfinite())return;
        if(enable()){
            cd++;
        }
        if(cd > 200 ){
            if(butterCount > 0) butterCount -= getCatCount();
            if(overflowCount > 0){
                //从溢出量中补给当前量
                int diffCount = Math.min(getMaxButterCount() - butterCount, overflowCount);
                overflowCount -= diffCount;
                butterCount += diffCount;
            }
            cd = 0;
            updateGeneratedRotation();
        }
    }

   public boolean enable(){
        return getButterCount()>=getCatCount();
   }
    ///================ speed ================
    //应力生产速度，受黄油数量和猫数量影响
    @Override
    public float getGeneratedSpeed() {
        if(!enable()) return 0;
        float speed = isInfinite()
                ? 256
                : Math.min(butterCount * getCatCount() * 2, 256);
        return speed * getAngleSpeedDirection();
    }
    //应力系数
    @Override
    public float calculateAddedStressCapacity() {
        float capacity = isInfinite()
                ? getMaxInfiniteOutput() * getCatCount()
                : (float) this.butterCount * 8 * getCatCount() ;
        this.lastCapacityProvided = capacity;
        return capacity;
    }
    //引擎自己的旋转方向
    protected float getAngleSpeedDirection() {
        WindmillBearingBlockEntity.RotationDirection rotationDirection = WindmillBearingBlockEntity.RotationDirection.values()[movementDirection.getValue()];
        return (rotationDirection == WindmillBearingBlockEntity.RotationDirection.CLOCKWISE ? 1 : -1);
    }
    //将最终速度转换为角度速度:RPM → 度/tick
        public float getAngularSpeed() {return convertToAngular(getSpeed());}
    //客户端渲染使用，每个tick中的gpu使用的角度，用于renderer和visual
    public float getInterpolatedAngle(float partialTicks){return Mth.lerp(partialTicks, angle, angle +getAngularSpeed());}
    ///================serialize================
    @Override
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(compound, registries, clientPacket);

        compound.putBoolean("infinite", infinite);
        compound.putBoolean("bread", bread);
        compound.putInt("cd", cd);
        compound.putInt("butterCount", butterCount);
        compound.putInt("overflowCount", overflowCount);

        compound.putIntArray("catVariants", catVariants.stream().mapToInt(ButterCatEngineBlockEntity::getCatVariantToIndex).toArray());
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(compound, registries, clientPacket);

        infinite = compound.getBoolean("infinite");
        bread = compound.getBoolean("bread");
        cd = compound.getInt("cd");
        butterCount = compound.getInt("butterCount");
        overflowCount = compound.getInt("overflowCount");

        this.clearCats();
        if (compound.contains("catVariants")) {
            for ( int i : compound.getIntArray("catVariants")  ){
                this.addCat(ButterCatEngineBlockEntity.getCatVariantFromIndex(i));
            }
        }
    }
    ///================get models================
    public int getButterLevel(){
        return Math.min((int)Math.floor(getSpeed()/32),3) ;
    }
    public List<PartialModel> getCatModels() {
        return catVariants.stream()
                .map(ModPartialModels::getCatModel)
                .collect(Collectors.toList());
    }
    public PartialModel getButterModel() {
        if(isInfinite()) return ModPartialModels.BCE_SUPER_BUTTER;
        return switch (getButterLevel()) {
            case 0 -> ModPartialModels.BCE_EMPTY;
            case 2 -> ModPartialModels.BCE_BUTTER;
            case 3 -> ModPartialModels.BCE_BUTTER_BIG;
            default -> ModPartialModels.BCE_BUTTER_SMALL;
        };
    }
    public PartialModel getBreadModel() {
        return hasBread()? ModPartialModels.BCE_BREAD:ModPartialModels.BCE_EMPTY;
    }
    ///================collision================
    static class BCEValueBox extends ValueBoxTransform.Sided {
        @Override
        protected Vec3 getSouthLocation() {
            return VecHelper.voxelSpace(8, 8, 12.5);
        }

        @Override
        public Vec3 getLocalOffset(LevelAccessor level, BlockPos pos, BlockState state) {
            Direction facing = state.getValue(HORIZONTAL_FACING);
            return super.getLocalOffset(level, pos, state).add(Vec3.atLowerCornerOf(facing.getNormal())
                    .scale(-1 / 16f));
        }

        @Override
        public void rotate(LevelAccessor level, BlockPos pos, BlockState state, PoseStack ms) {
            super.rotate(level, pos, state, ms);
            Direction facing = state.getValue(HORIZONTAL_FACING);

            if (getSide() != Direction.UP)
                return;
            TransformStack.of(ms)
                    .rotateZDegrees(-AngleHelper.horizontalAngle(facing) + 180);
        }

        @Override
        protected boolean isSideActive(BlockState state, Direction direction) {
            Direction facing = state.getValue(HORIZONTAL_FACING);
            if (facing.getAxis() != Direction.Axis.Y && direction == Direction.DOWN)
                return false;
            return direction.getAxis() != facing.getAxis();
        }

    }

    public static int getMaxButterCount(){
        return ModConfigs.COMMON.maxButterCount.get();
    }
    public static int getMaxInfiniteOutput(){
        return ModConfigs.COMMON.maxInfiniteCapacity.get();
    }

    private static final Map<ResourceKey<CatVariant>, Integer> VARIANT_TO_INDEX = new HashMap<>();

    static {
        VARIANT_TO_INDEX.put(CatVariant.TABBY, 1);
        VARIANT_TO_INDEX.put(CatVariant.BLACK, 2);
        VARIANT_TO_INDEX.put(CatVariant.RED, 3);
        VARIANT_TO_INDEX.put(CatVariant.SIAMESE, 4);
        VARIANT_TO_INDEX.put(CatVariant.BRITISH_SHORTHAIR, 5);
        VARIANT_TO_INDEX.put(CatVariant.CALICO, 6);
        VARIANT_TO_INDEX.put(CatVariant.PERSIAN, 7);
        VARIANT_TO_INDEX.put(CatVariant.RAGDOLL, 8);
        VARIANT_TO_INDEX.put(CatVariant.WHITE, 9);
        VARIANT_TO_INDEX.put(CatVariant.JELLIE, 10);
        VARIANT_TO_INDEX.put(CatVariant.ALL_BLACK, 11);
    }

    public static int getCatVariantToIndex(ResourceKey<CatVariant> catVariant) {
        return VARIANT_TO_INDEX.getOrDefault(catVariant, 1);
    }
    public static ResourceKey<CatVariant> getCatVariantFromIndex(int index) {
        return VARIANT_TO_INDEX.entrySet().stream()
                .filter(entry -> entry.getValue() == index)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(CatVariant.TABBY);
    }

}
