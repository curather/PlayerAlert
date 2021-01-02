package com.gumaden.playeralert.gui;

import com.gumaden.playeralert.playeralert;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class GuiMainConfig extends GuiConfig
{
    public GuiMainConfig(GuiScreen gui)
    {
        super(gui,
                new ConfigElement(playeralert.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), // What category of config to show in the GUI, can be something else (HAS TO BE IN YOUR CONFIG ALREADY!!!)
                playeralert.MODID,
                false,
                false,
                GuiConfig.getAbridgedConfigPath(playeralert.config.toString()));
    }
}