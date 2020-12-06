package com.masterof13fps.features.modules.impl.player;

import com.masterof13fps.features.modules.Category;
import com.masterof13fps.features.modules.Module;
import com.masterof13fps.features.modules.ModuleInfo;
import com.masterof13fps.manager.eventmanager.Event;
import com.masterof13fps.manager.eventmanager.impl.EventUpdate;
import com.masterof13fps.manager.settingsmanager.Setting;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

@ModuleInfo(name = "AntiVoid", category = Category.PLAYER, description = "You can't die in the void anymore")
public class AntiVoid extends Module {

    Setting fallDist = new Setting("Fall Distance", this, 15, 1, 30, true);

    @Override
    public void onToggle() {

    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onEvent(Event event) {
        if(event instanceof EventUpdate){
            for (int i = 0; i < getY(); ++i) {
                if (getBlock(new BlockPos(getX(), i, getZ())).getBlock() != Blocks.air) {
                    return;
                }
            }
            if (getPlayer().fallDistance > fallDist.getCurrentValue()) {
                sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(getX(), getY() + 7.0, getZ(), false));
            }
        }
    }
}