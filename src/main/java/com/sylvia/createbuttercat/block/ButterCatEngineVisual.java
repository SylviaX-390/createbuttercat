package com.sylvia.createbuttercat.block;

import com.mojang.math.Axis;
import com.simibubi.create.content.kinetics.base.ShaftVisual;
import com.sylvia.createbuttercat.register.ModPartialModels;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visual.DynamicVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.OrientedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import net.createmod.catnip.math.AngleHelper;
import net.minecraft.core.Direction;
import org.joml.Quaternionf;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ButterCatEngineVisual extends ShaftVisual<ButterCatEngineBlockEntity> implements SimpleDynamicVisual {
    private final List<ButterCatVisualInstanceData> instanceDatas = new ArrayList<>();
    private final Quaternionf blockOrientation;
    private final Axis rotationAxis;
    private int currentButterLevel;
    private int lastSize = 0;

    public ButterCatEngineVisual(VisualizationContext context, ButterCatEngineBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);
        Direction facing = blockState.getValue(ButterCatEngineBlock.HORIZONTAL_FACING);
        blockOrientation = Axis.YP.rotationDegrees(AngleHelper.horizontalAngle(facing));
        rotationAxis = Axis.of(Direction.get(Direction.AxisDirection.POSITIVE, rotationAxis()).step());
    }

    @Override
    public void beginFrame(DynamicVisual.Context ctx) {
        List<PartialModel> catModels = blockEntity.getCatModels();

        if(lastSize != catModels.size()){
            lastSize = catModels.size();
            for (ButterCatVisualInstanceData instanceData : instanceDatas) instanceData.delete();
            instanceDatas.clear();
            for (PartialModel catModel : catModels) instanceDatas.add(new ButterCatVisualInstanceData(catModel));
        }

        for (int i = 0; i < catModels.size() ; i ++){
            instanceDatas.get(i).update(catModels.get(i));
            if (!isVisible(ctx.frustum()) || doDistanceLimitThisFrame(ctx)) continue;
            instanceDatas.get(i).rotate(ctx.partialTick(),i);
        }
    }

    @Override
    public void updateLight(float partialTick) {
        super.updateLight(partialTick);
        for (ButterCatVisualInstanceData instanceData : instanceDatas) instanceData.updateLight();
    }
    @Override
    protected void _delete() {
        super._delete();
        for (ButterCatVisualInstanceData instanceData : instanceDatas) instanceData.delete();
    }
    @Override
    public void collectCrumblingInstances(Consumer<Instance> consumer) {
        super.collectCrumblingInstances(consumer);
        for (ButterCatVisualInstanceData instanceData : instanceDatas) instanceData.collectCrumblingInstances(consumer);
    }

    private class ButterCatVisualInstanceData{
        OrientedInstance cat;
        OrientedInstance bread;
        OrientedInstance butter;
        int catModelCode;
        public ButterCatVisualInstanceData(PartialModel catModel){
            catModelCode = catModel.hashCode();
            cat = instancerProvider().instancer(InstanceTypes.ORIENTED, Models.partial(catModel)).createInstance();
            cat.position(getVisualPosition()).rotation(blockOrientation).light(computePackedLight()).setChanged();

            bread = instancerProvider().instancer(InstanceTypes.ORIENTED, Models.partial(ModPartialModels.BCE_EMPTY)).createInstance();
            bread.position(getVisualPosition()).rotation(blockOrientation).light(computePackedLight()).setChanged();

            butter = instancerProvider().instancer(InstanceTypes.ORIENTED, Models.partial(ModPartialModels.BCE_EMPTY)).createInstance();
            butter.position(getVisualPosition()).rotation(blockOrientation).light(computePackedLight()).setChanged();
        }
        public void rotate(float pt, int k){
            float interpolatedAngle = blockEntity.getInterpolatedAngle(pt - 1) +  ButterCatEngineRenderer.getCatAdditionalAngle(k);

            Quaternionf dynamicRotation = rotationAxis.rotationDegrees(interpolatedAngle);

            dynamicRotation.mul(blockOrientation);

            cat.rotation(dynamicRotation).setChanged();
            bread.rotation(dynamicRotation).setChanged();
            butter.rotation(dynamicRotation).setChanged();
        }
        public void update(PartialModel newCatModel) {
            if (newCatModel.hashCode() != catModelCode) {
                catModelCode = newCatModel.hashCode();
                instancerProvider().instancer(InstanceTypes.ORIENTED, Models.partial(newCatModel)).stealInstance(cat);
                cat.position(getVisualPosition()).rotation(blockOrientation).setChanged();
            }

            if(blockEntity.hasBread()){
                instancerProvider().instancer(InstanceTypes.ORIENTED, Models.partial(blockEntity.getBreadModel())).stealInstance(bread);
                bread.position(getVisualPosition()).rotation(blockOrientation).setChanged();

            }
            if(currentButterLevel != blockEntity.getButterLevel()){
                currentButterLevel = blockEntity.getButterLevel();
                instancerProvider().instancer(InstanceTypes.ORIENTED, Models.partial(blockEntity.getButterModel())).stealInstance(butter);
                butter.position(getVisualPosition()).rotation(blockOrientation).setChanged();
            }
        }
        public void updateLight() {
            relight(cat,bread,butter);
        }
        public void delete(){
            cat.delete();
            bread.delete();
            butter.delete();
        }
        public void collectCrumblingInstances(Consumer<Instance> consumer) {
            consumer.accept(bread);
            consumer.accept(butter);
        }
    }
}