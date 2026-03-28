package com.sylvia.createbuttercat.event;

import com.sylvia.createbuttercat.CreateButterCat;
import com.sylvia.createbuttercat.mob_effect.ButterRotationEffect;
import com.sylvia.createbuttercat.register.ModEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderFrameEvent;

@EventBusSubscriber(modid = CreateButterCat.MODID, value = Dist.CLIENT)
public class RotationHandler {
    static float acceleration = 0;
    static int amplifier = -1;
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre event) {
        Player player = Minecraft.getInstance().player;
        if (player == null ) return;
        MobEffectInstance effect = player.getEffect(ModEffects.BUTTER_ROTATION_EFFECT.getDelegate());

        acceleration = Math.clamp(acceleration+0.1f*(effect!=null?1:-1),0,1);
        if(effect!=null){
            int amplifier0 = player.getEffect(ModEffects.BUTTER_ROTATION_EFFECT.getDelegate()).getAmplifier();
            if(amplifier0 != amplifier)
                amplifier = amplifier0;
        }
    }

    @SubscribeEvent
    public static void onRender(RenderFrameEvent.Post event) {
        Player player = Minecraft.getInstance().player;
        if (player == null || acceleration==0) return;

        float pt = event.getPartialTick().getGameTimeDeltaPartialTick(true);
        float y= player.getYRot()+ getAngle(pt,amplifier);

        player.setYRot(y);
    }
    public static float getAngle(float pt,int a) {
        return (getTickAngleSpeed(a) * pt ) % 360;
    }
    private static float getTickAngleSpeed(int amplifier){
        return (3*amplifier+1)* ButterRotationEffect.ROTATION_ANGULAR_SPEED * acceleration;
    }
}
