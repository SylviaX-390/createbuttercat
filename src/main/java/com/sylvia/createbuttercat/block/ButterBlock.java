package com.sylvia.createbuttercat.block;

import com.sylvia.createbuttercat.register.ModEffects;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.io.IOException;
import java.util.Random;

public class ButterBlock extends Block {

    private boolean canEffect = false;
    public ButterBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(level, pos, state, entity);
        if(canEffect() && entity instanceof LivingEntity livingEntity){
            if(entity instanceof Player && !canEffectPlayer()) return;
            canEffect = false;
            livingEntity.addEffect(new MobEffectInstance(ModEffects.BUTTER_ROTATION_EFFECT, getDuration(), getAmplifier()));
        }
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);
        canEffect = true;
    }

    public boolean canEffect(){
        return canEffect;
    }

    public int getAmplifier(){return 2;}
    public int getDuration(){return 60;}
    public boolean canEffectPlayer(){return false;}
}
