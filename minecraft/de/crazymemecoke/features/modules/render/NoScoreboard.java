package de.crazymemecoke.features.modules.render;

import de.crazymemecoke.manager.modulemanager.Category;
import de.crazymemecoke.manager.modulemanager.Module;
import org.lwjgl.input.Keyboard;

public class NoScoreboard extends Module {
    public NoScoreboard() {
        super("NoScoreboard", Keyboard.KEY_NONE, Category.RENDER, -1);
    }
}