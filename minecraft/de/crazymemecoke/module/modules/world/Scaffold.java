package de.crazymemecoke.module.modules.world;

import de.crazymemecoke.module.Category;
import de.crazymemecoke.module.Module;
import de.crazymemecoke.utils.Wrapper;
import de.crazymemecoke.utils.render.Rainbow;
import de.crazymemecoke.utils.time.TimeHelper;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.function.Consumer;

public class Scaffold extends Module {

    private final Queue<Consumer<EntityPlayerSP>> postponeActions = new ArrayDeque<Consumer<EntityPlayerSP>>();
    private final BlockData blockData = null;
    private final boolean tower = true;
    public TimeHelper time = new TimeHelper();
    boolean kek;

    public Scaffold() {
        super("Scaffold", Keyboard.KEY_NONE, Category.WORLD, Rainbow.rainbow(1, 1).hashCode());
    }

    public Queue<Consumer<EntityPlayerSP>> getPostponeActions() {
        return this.postponeActions;
    }

    public void onUpdate() {
        if (!this.getState()) {
            return;
        }

        if (time.hasReached(500) && mc.gameSettings.keyBindForward.pressed == true) {
            mc.gameSettings.keyBindBack.pressed = true;
        }
        if (time.hasReached(630)) {
            if (mc.gameSettings.keyBindBack.pressed == true) {
                mc.gameSettings.keyBindBack.pressed = false;
                time.reset();
            }
        }
        EntityPlayerSP p = mc.thePlayer;
        ArrayList<BlockPos> blockStandOn = getSurroundingBlocks();
        if (!mc.thePlayer.onGround) {
            mc.rightClickDelayTimer = 0;
            mc.rightClickMouse();

        }
        ArrayList<BlockPos> collisionBlocks = getCollisionBlocks();
        for (BlockPos posCollision : collisionBlocks) {
            IBlockState blockOnCollision = p.worldObj.getBlockState(posCollision);
            if ((blockOnCollision.getBlock() instanceof BlockAir)) {
                for (BlockPos posStandOn : blockStandOn) {
                    IBlockState blockInReach = p.worldObj.getBlockState(posStandOn);
                    if (!(blockInReach.getBlock() instanceof BlockAir)) {
                        if (!posStandOn.equals(posCollision)) {
                            if (posStandOn.offset(p.getHorizontalFacing()).equals(posCollision)) {
                                Wrapper.mc.thePlayer.sendQueue.addToSendQueue(
                                        new C03PacketPlayer.C05PacketPlayerLook(mc.thePlayer.rotationYaw, 90.0F, true));
                                if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld,
                                        p.inventory.getCurrentItem(), posStandOn.add(0, 0, 0), p.getHorizontalFacing(),
                                        new Vec3(posStandOn.getX(), posStandOn.getY(), posStandOn.getZ()))) {

                                    mc.rightClickDelayTimer = 0;
                                    p.swingItem();

                                    break;

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private ArrayList<BlockPos> getCollisionBlocks() {
        ArrayList<BlockPos> collisionBlocks = new ArrayList();
        EntityPlayer p = mc.thePlayer;

        BlockPos var1 = new BlockPos(p.getEntityBoundingBox().minX + 0.1D, p.getEntityBoundingBox().minY - 0.001D,
                p.getEntityBoundingBox().minZ + 0.1D);
        BlockPos var2 = new BlockPos(p.getEntityBoundingBox().maxX - 0.1D, p.getEntityBoundingBox().maxY + 0.001D,
                p.getEntityBoundingBox().maxZ - 0.1D);
        for (int var3 = var1.getX(); var3 <= var2.getX(); var3++) {
            for (int var4 = var1.getY(); var4 <= var2.getY(); var4++) {
                for (int var5 = var1.getZ(); var5 <= var2.getZ(); var5++) {
                    BlockPos blockPos = new BlockPos(var3, var4, var5);
                    IBlockState var7 = p.worldObj.getBlockState(blockPos);
                    try {
                        if ((var4 > p.posY - 2.0D) && (var4 <= p.posY - 1.0D)) {
                            collisionBlocks.add(blockPos);
                        }
                    } catch (Throwable localThrowable) {
                    }
                }
            }
        }
        return collisionBlocks;
    }

    private ArrayList<BlockPos> getSurroundingBlocks() {
        ArrayList<BlockPos> blocksStandOn = new ArrayList();
        EntityPlayer p = mc.thePlayer;
        for (int var3 = (int) p.posX - 1; var3 <= (int) p.posX + 1; var3++) {
            for (int var4 = (int) p.posY - 1; var4 <= (int) p.posY; var4++) {
                for (int var5 = (int) p.posZ - 1; var5 <= (int) p.posZ + 1; var5++) {
                    if ((var3 == Math.floor(p.posX)) || (var5 == Math.floor(p.posZ))) {
                        BlockPos blockPos = new BlockPos(var3, var4, var5);
                        try {
                            if ((var4 > p.posY - 2.0D) && (var4 <= p.posY - 1.0D)) {
                                blocksStandOn.add(blockPos);
                            }
                        } catch (Throwable localThrowable) {
                        }
                    }
                }
            }
        }
        return blocksStandOn;
    }

    public void onRender() {
        if (!this.getState()) {
            return;
        }
        EntityPlayer p = mc.thePlayer;
        ArrayList<BlockPos> blockStandOn = getSurroundingBlocks();
        IBlockState state;
        for (BlockPos pos : blockStandOn) {
            state = p.worldObj.getBlockState(pos);

        }
        ArrayList<BlockPos> collisionBlocks = getCollisionBlocks();
        for (BlockPos pos : collisionBlocks) {

        }
    }

    private class BlockData {
        public BlockPos position;
        public EnumFacing face;

        public BlockData(BlockPos position, EnumFacing face) {
            this.position = position;
            this.face = face;
        }
    }
}