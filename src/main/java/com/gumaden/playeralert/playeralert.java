package com.gumaden.playeralert;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import com.gumaden.playeralert.gui.GuiMainConfig;


@Mod(modid = playeralert.MODID, version = playeralert.VERSION, clientSideOnly = true, guiFactory = "com.gumaden.playeralert.gui.ConfigGuiFactory")
public class playeralert
{
    public static final String MODID = "playeralert";
    public static final String VERSION = "1.02";
    public static Configuration config;
    private boolean showConfig = false;
    public static boolean DisableMod = false;
    public static boolean DisableTitle = false;
    public static boolean DisableAudio = false;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (this.showConfig) {
            Minecraft.getMinecraft().displayGuiScreen((GuiMainConfig)new GuiMainConfig(null));
            this.showConfig = false;
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        ClientCommandHandler.instance.registerCommand((ICommand)new ConfigCommand());
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile());
        saveConfig();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        if (config.hasChanged())
            config.save();
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals("playeralert"))
            saveConfig();
    }

    public static void saveConfig() {
        DisableMod=config.getBoolean("DisableMod", Configuration.CATEGORY_GENERAL, false, "Disables the mod.");

        DisableTitle=config.getBoolean("DisableTitle", Configuration.CATEGORY_GENERAL, false, "Disables the title");

        DisableAudio=config.getBoolean("DisableAudio", Configuration.CATEGORY_GENERAL, false, "Disables the audio");

        if(config.hasChanged()){
            config.save();
        }
    }

    public void ShowTitle(String title, String subtitle, int fadeIn, int time, int fadeOut) {
        GuiIngame gui = Minecraft.getMinecraft().ingameGUI;
        gui.displayTitle(title, null, fadeIn, time, fadeOut);
        gui.displayTitle(null, subtitle, fadeIn, time, fadeOut);
        gui.displayTitle(null, null, fadeIn, time, fadeOut);
    }

    
    @SubscribeEvent
    public void onChatReceive(ClientChatReceivedEvent event) {
        if (DisableMod) return;
        String unformattedText = event.message.getUnformattedText();
        String TPlayer = unformattedText.replace("[SkyBlock] ", "").replace(" is visiting Your Island!", "");
        if (unformattedText.contains("visiting Your Island")) {
            //msg(TPlayer + " is visiting your island.");
            if (!DisableAudio) {
                Minecraft.getMinecraft().thePlayer.playSound("mob.enderdragon.growl", 0.5f, 100);
                Minecraft.getMinecraft().thePlayer.playSound("mob.enderdragon.growl", 0.5f, -10);
            }
            for (int i = 0; i < 3; i++) {
                if (!DisableTitle)
                    ShowTitle(TPlayer, "is visiting your island.", 0, 200, 40);
            }
        }
    }

    public static void msg(String key) {
        Minecraft.getMinecraft().thePlayer.addChatMessage((new ChatComponentText(key)));
    }

    public class ConfigCommand extends CommandBase {
        public String getCommandName() {
            return "playeralert";
        }

        public String getCommandUsage(ICommandSender sender) {
            return "/playeralert";
        }

        public void processCommand(ICommandSender sender, String[] args) throws CommandException {
            playeralert.this.showConfig = true;
        }

        public int getRequiredPermissionLevel() {
            return 0;
        }
    }
}
