package dev.leap.frog.Util.Entity;

import dev.leap.frog.Manager.UtilManager;
import dev.leap.frog.Util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class Playerutil extends UtilManager {

    public static float getSpeedInKM() {
        final double deltaX = Minecraft.getMinecraft().player.posX - Minecraft.getMinecraft().player.prevPosX;
        final double deltaZ = Minecraft.getMinecraft().player.posZ - Minecraft.getMinecraft().player.prevPosZ;

        float distance = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ);

        double floor = Math.floor(( distance/1000.0f ) / ( 0.05f/3600.0f ));

        String formatter = String.valueOf(floor);

        if (!formatter.contains("."))
            formatter += ".0";

        return Float.valueOf(formatter);
    }

    public static boolean isMoving(EntityLivingBase entity) {
        return entity.moveForward != 0 || entity.moveStrafing != 0;
    }

    public static float getPlayerHealth() {
        return Wrapper.getMC().player.getHealth() + Wrapper.getMC().player.getAbsorptionAmount();
    }

    public static BlockPos GetLocalPlayerPosFloored() {
        return new BlockPos(Math.floor(Wrapper.getMC().player.posX), Math.floor(Wrapper.getMC().player.posY), Math.floor(Wrapper.getMC().player.posZ));
    }


    public static Vec3d[] offsetsDefault = new Vec3d[] {
                    new Vec3d(0.0, 0.0, -1.0),
                    new Vec3d(1.0, 0.0, 0.0),
                    new Vec3d(0.0, 0.0, 1.0),
                    new Vec3d(-1.0, 0.0, 0.0),
                    new Vec3d(0.0, 1.0, -1.0),
                    new Vec3d(1.0, 1.0, 0.0),
                    new Vec3d(0.0, 1.0, 1.0),
                    new Vec3d(-1.0, 1.0, 0.0),
                    new Vec3d(0.0, 2.0, -1.0),
                    new Vec3d(1.0, 2.0, 0.0),
                    new Vec3d(0.0, 2.0, 1.0),
                    new Vec3d(-1.0, 2.0, 0.0),
                    new Vec3d(0.0, 3.0, -1.0),
                    new Vec3d(0.0, 3.0, 0.0)
            };
}
