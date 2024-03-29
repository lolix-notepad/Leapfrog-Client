package dev.leap.frog.Manager;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import club.minnced.discord.rpc.DiscordUser;
import dev.leap.frog.LeapFrog;
import dev.leap.frog.Util.Entity.Playerutil;
import dev.leap.frog.Util.Render.Chatutil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreenServerList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

import javax.annotation.Nullable;
import java.util.Objects;

public class DiscordManager {

    private  DiscordRichPresence discordRichPresence = new DiscordRichPresence();
    private  Minecraft mc = Minecraft.getMinecraft();

    private DiscordRichPresence presence = new DiscordRichPresence();
    private Thread thread;
    private  DiscordRPC lib = DiscordRPC.INSTANCE;

    public DiscordManager() {
        Start();
    }

    public void Start() {
        String applicationId = "830252222372773908";
        String steamId = "";
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> System.out.println("Ready!");
        lib.Discord_Initialize(applicationId, handlers, true, steamId);

        lib.Discord_UpdatePresence(presence);
        presence.largeImageKey = "leapfrog512";
        this.thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                lib.Discord_RunCallbacks();
                presence.details = setDetails();
                presence.state = setState();


                lib.Discord_UpdatePresence(presence);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {
                }
            }
        }, "RPC-Callback-Handler");
        thread.start();
    }

    public void Stop() {
        if(!this.thread.isInterrupted() || thread.isAlive()) {
            this.thread.interrupt();
            this.lib.Discord_Shutdown();
            this.lib.Discord_ClearPresence();
            return;
        }
    }

    private String setState() { // mc.player != null will run first
        String back = discordRichPresence.state;

        if(mc.player == null)
            return "Main menu";


        if(mc.world != null && LeapFrog.getModuleManager().getModuleName("ClickGUI").isToggled()) {
            return "Configuring Client";
        }

        if(mc.player.moveStrafing != 0 && mc.player.moveForward != 0) {
            return "Moving " + Playerutil.getSpeedInKM() + " KM/H";
        }

        if(mc.player.isElytraFlying()) {
            return "Zooming";
        }

        return back;
    }

    private String setDetails() {
        String detail = discordRichPresence.details;

        if(mc.player == null)
            return "Leapfrog on top!";

        if(mc.isSingleplayer()) {
            return mc.player.getName() + " | " + "Singleplayer";
        }

        if(mc.player != null && !mc.isSingleplayer() && !(Objects.requireNonNull(mc.getCurrentServerData()).serverIP == null)) {
            return mc.player.getName() + " | " + "Multiplayer";
        }

        return detail;
    }

}
