package com.sylvia.createbuttercat.ponder;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.kinetics.gauge.StressGaugeBlockEntity;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import com.sylvia.createbuttercat.block.ButterCatEngineBlockEntity;
import com.sylvia.createbuttercat.register.ModBlocks;
import com.sylvia.createbuttercat.register.ModItems;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.EntityElement;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class ModPonderScenes {
    public static void butterCatEngine(SceneBuilder sceneBuilder, SceneBuildingUtil util){
        CreateSceneBuilder scene = new CreateSceneBuilder(sceneBuilder);
        scene.title("butter_cat_engine", "Let the Butter Cat spin around");
        scene.configureBasePlate(0, 0, 5 );
        scene.world().showSection(util.select().layer(0), Direction.UP);
        scene.idle(5);
        scene.world().showSection(util.select().fromTo(0, 1, 1, 4, 2, 4), Direction.DOWN);
        scene.world().setKineticSpeed(util.select().everywhere(), 0);
        scene.idle(10);

        BlockPos catPos = util.grid().at(2, 1, 1);
        BlockPos enginePos = util.grid().at(2, 2, 2);
        BlockPos catDropPos = util.grid().at(2, 1, 2);
        BlockPos stressPos = util.grid().at(4, 2, 2);
        BlockPos speedPos = util.grid().at(0, 2, 2);

        scene.world().createEntity(world->{
               Cat c = EntityType.CAT.create(world);
               c.setPos(catPos.getBottomCenter());
               c.setYRot(180);
               c.setInSittingPose(true);
               c.setVariant(BuiltInRegistries.CAT_VARIANT.getHolder(CatVariant.TABBY).get());
               return c;
        });

        scene.overlay().showText(50)
                .text("Secure the cat to the Shafts with a lead.")
                .placeNearTarget()
                .pointAt(util.vector().topOf(catPos));
        scene.idle(60);

        scene.overlay().showControls(util.vector().topOf(catPos), Pointing.LEFT, 20).rightClick().withItem(Items.LEAD.getDefaultInstance());
        scene.idle(45);

        scene.overlay().showControls(util.vector().topOf(enginePos), Pointing.LEFT, 20).rightClick();
        scene.idle(5);
        scene.world().modifyEntities(Cat.class, Entity::discard);
        scene.world().setBlock(enginePos,
                ModBlocks.BUTTER_CAT_ENGINE
                .getDefaultState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST),
                true);
        scene.idle(60);

        scene.overlay().showText(50)
                .text("This is the Butter Cat engine, let`s put bread and butter on it.")
                .placeNearTarget()
                .pointAt(util.vector().topOf(enginePos));
        scene.idle(90);

        scene.addKeyframe();

        scene.overlay().showControls(util.vector().topOf(enginePos), Pointing.DOWN, 20).rightClick().withItem(Items.BREAD.getDefaultInstance());
        scene.idle(5);
        scene.world().modifyBlockEntity(enginePos,ButterCatEngineBlockEntity.class, ButterCatEngineBlockEntity::addBread);
        scene.idle(40);

        scene.overlay().showControls(util.vector().topOf(enginePos), Pointing.DOWN, 20).rightClick().withItem(ModItems.BUTTER.asStack());
        scene.idle(5);
        scene.world().modifyBlockEntity(enginePos,ButterCatEngineBlockEntity.class,(be)->be.addButterCount(1));
        scene.world().setKineticSpeed(util.select().everywhere(), 32);
        scene.world().modifyBlockEntityNBT(util.select().position(stressPos), StressGaugeBlockEntity.class,
                nbt -> nbt.putFloat("Value", .1f));
        scene.effects().indicateSuccess(speedPos);
        scene.effects().indicateRedstone(stressPos);
        scene.idle(60);
        scene.overlay().showText(50)
                .text("The more butter you add, the greater its kinetic output.")
                .placeNearTarget()
                .pointAt(util.vector().topOf(enginePos));

        scene.idle(90);
        ItemStack[] butters = new ItemStack[]{ModItems.BUTTER.asStack(),ModItems.HONEY_BUTTER.asStack()};
        for (int i = 0;i<5 ;i ++){
            scene.overlay().showControls(util.vector().topOf(enginePos), Pointing.DOWN, 10).rightClick().withItem(butters[i%2]);
            scene.idle(5);
            scene.world().modifyBlockEntity(enginePos,ButterCatEngineBlockEntity.class,(be)->be.addButterCount(1));

            int i1 = i+1;
            scene.world().setKineticSpeed(util.select().everywhere(), Math.min(256,i1*64));
            scene.world().modifyBlockEntityNBT(util.select().position(stressPos), StressGaugeBlockEntity.class,
                    nbt -> nbt.putFloat("Value", (i1+1)*0.1f));
            scene.effects().indicateSuccess(speedPos);
            scene.effects().indicateRedstone(stressPos);
            scene.idle(15);
        }
        scene.idle(40);

        scene.addKeyframe();

        scene.overlay().showText(50)
                .text("And with super butter? Perpetual motion machine.")
                .placeNearTarget()
                .pointAt(util.vector().topOf(enginePos));
        scene.idle(90);
        scene.overlay().showControls(util.vector().topOf(enginePos), Pointing.DOWN, 20).rightClick().withItem(ModItems.SUPER_BUTTER.asStack());
        scene.idle(5);
        scene.world().modifyBlockEntity(enginePos,ButterCatEngineBlockEntity.class,(be)->be.setInfinite(true));
        scene.world().setKineticSpeed(util.select().everywhere(), 256);
        scene.world().modifyBlockEntityNBT(util.select().position(stressPos), StressGaugeBlockEntity.class,
                nbt -> nbt.putFloat("Value",.9f));
        scene.effects().indicateSuccess(speedPos);
        scene.effects().indicateRedstone(stressPos);
        scene.idle(60);

        scene.addKeyframe();

        scene.overlay().showControls(util.vector().topOf(enginePos), Pointing.DOWN, 20).rightClick().withItem(AllItems.WRENCH.asStack());
        scene.idle(5);

        scene.world().setKineticSpeed(util.select().everywhere(), 0);
        scene.world().modifyBlockEntityNBT(util.select().position(stressPos), StressGaugeBlockEntity.class,
                nbt -> nbt.putFloat("Value",0));
        scene.world().setBlock(enginePos, Blocks.AIR.defaultBlockState(), true);
        ElementLink<EntityElement> catElementLink = scene.world().createEntity(world->{
            Cat c = EntityType.CAT.create(world);
            c.setPos(catDropPos.getBottomCenter());
            c.setYRot(180);
            c.setVariant(BuiltInRegistries.CAT_VARIANT.getHolder(CatVariant.TABBY).get());
            c.setLying(true);
            return c;
        });

        scene.idle(20);
        scene.overlay().showText(50)
                .text("Don't worry, the cat is still here,it just doesn't recognize its owner anymore...")
                .placeNearTarget()
                .pointAt(util.vector().topOf(catDropPos));
        scene.idle(90);

        scene.addKeyframe();

        scene.overlay().showControls(util.vector().topOf(enginePos), Pointing.DOWN, 20).rightClick().withItem( AllBlocks.SHAFT.asStack());
        scene.world().setBlock(enginePos, AllBlocks.SHAFT.getDefaultState().setValue(BlockStateProperties.AXIS,Direction.EAST.getAxis()), true);
        scene.idle(40);

        scene.overlay().showControls(util.vector().topOf(catPos), Pointing.DOWN, 20).rightClick().withItem( Items.LEAD.getDefaultInstance());
        scene.idle(40);

        scene.overlay().showControls(util.vector().topOf(enginePos), Pointing.DOWN, 20).rightClick().withItem( Items.LEAD.getDefaultInstance());
        scene.world().setBlock(enginePos, ModBlocks.BUTTER_CAT_ENGINE.getDefaultState().setValue(BlockStateProperties.HORIZONTAL_FACING,Direction.EAST), true);
        scene.world().modifyEntity(catElementLink, Entity::discard);
        scene.idle(40);

        scene.overlay().showControls(util.vector().topOf(enginePos), Pointing.DOWN, 20).rightClick().withItem( Items.BREAD.getDefaultInstance());
        scene.world().modifyBlockEntity(enginePos,ButterCatEngineBlockEntity.class, ButterCatEngineBlockEntity::addBread);
        scene.idle(40);

        scene.overlay().showControls(util.vector().topOf(enginePos), Pointing.DOWN, 20).rightClick().withItem( ModItems.INCOMPLETE_SUPER_BUTTER.asStack());
        scene.world().modifyBlockEntity(enginePos,ButterCatEngineBlockEntity.class,(be)->be.setInfinite(true));
        scene.effects().indicateRedstone(enginePos);
        scene.world().setKineticSpeed(util.select().everywhere(), 8);
        scene.effects().indicateSuccess(speedPos);
        scene.idle(40);

        for(int i = 0;i<3;i++){
            scene.overlay().showControls(util.vector().topOf(enginePos), Pointing.DOWN, 20).rightClick().withItem( Items.LEAD.getDefaultInstance());
            scene.idle(10);

            int catIndex = i + 3;
            scene.world().modifyBlockEntity(enginePos,ButterCatEngineBlockEntity.class,(be)->be.addCat(ButterCatEngineBlockEntity.getCatVariantFromIndex(catIndex )));

            scene.world().setKineticSpeed(util.select().everywhere(), 8 + i * 8);
            scene.effects().indicateSuccess(speedPos);
            scene.effects().indicateRedstone(stressPos);

            scene.idle(20);
        }

        scene.overlay().showText(50)
                .text("Four cats, four times the power!")
                .placeNearTarget()
                .pointAt(util.vector().topOf(enginePos));
        scene.idle(60);

        scene.markAsFinished();
    }
}
