package de.crazymemecoke.features.modules.misc;

import de.crazymemecoke.Client;
import de.crazymemecoke.manager.eventmanager.Event;
import de.crazymemecoke.manager.eventmanager.impl.EventTick;
import de.crazymemecoke.manager.modulemanager.Category;
import de.crazymemecoke.manager.modulemanager.Module;
import de.crazymemecoke.manager.notificationmanager.Notification;
import de.crazymemecoke.manager.notificationmanager.NotificationManager;
import de.crazymemecoke.manager.notificationmanager.NotificationType;
import de.crazymemecoke.utils.time.TimeHelper;
import org.lwjgl.input.Keyboard;

public class MemoryCleaner extends Module {
    public MemoryCleaner() {
        super("MemoryCleaner", Keyboard.KEY_NONE, Category.MISC, -1);
    }

    TimeHelper timeHelper = new TimeHelper();

    @Override
    public void onDisable() {
        timeHelper.reset();
    }

    @Override
    public void onEvent(Event event) {
        if(event instanceof EventTick){
            if(timeHelper.hasReached(1000L)){
                System.gc();
                NotificationManager.show(new Notification(NotificationType.INFO, "MemoryCleaner", "Cleaned your Memory", 5));
                timeHelper.reset();
            }
        }
    }
}