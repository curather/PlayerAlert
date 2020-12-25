package com.gumaden.playeralert;

import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.Minecraft;


@Mod(modid = playeralert.MODID, version = playeralert.VERSION)
public class playeralert
{
    public static final String MODID = "playeralerts";
    public static final String VERSION = "1.0";

    public void ShowTitle(String title, String subtitle, int fadeIn, int time, int fadeOut) {
        GuiIngame gui = Minecraft.getMinecraft().ingameGUI;
        gui.displayTitle(title, null, fadeIn, time, fadeOut);
        gui.displayTitle(null, subtitle, fadeIn, time, fadeOut);
        gui.displayTitle(null, null, fadeIn, time, fadeOut);
    }

    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
    }
    @SubscribeEvent
    public void onChatReceive(ClientChatReceivedEvent event) {
        String unformattedText = event.message.getUnformattedText();
        String TPlayer = unformattedText.replace("[SkyBlock] ", "").replace(" is visiting Your Island!", "");
        if (unformattedText.contains("visiting Your Island")) {
            //msg(TPlayer + " is visiting your island.");
            Minecraft.getMinecraft().thePlayer.playSound("mob.enderdragon.growl", 0.5f, 100);
            Minecraft.getMinecraft().thePlayer.playSound("mob.enderdragon.growl", 0.5f, -10);
            for (int i = 0; i < 3; i++) {
                ShowTitle(TPlayer, "is visiting your island.", 0, 200, 40);
            }
        }
    }

    public static void msg(String key) {
        Minecraft.getMinecraft().thePlayer.addChatMessage((new ChatComponentText(key)));
    }
}
