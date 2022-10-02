package emu.grasscutter.command.commands;

import emu.grasscutter.Grasscutter;
import java.lang.reflect.InvocationTargetException;
import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.server.packet.send.PacketSceneEntityAppearNotify;
import emu.grasscutter.utils.Position;
import java.util.List;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.AvatarSkillDepotData;
import emu.grasscutter.game.player.Player;
import javax.annotation.Nullable;
import java.lang.reflect.Method;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;

import java.util.List;

import static emu.grasscutter.utils.Language.translate;

@Command(
    label = "switchelement", 
    usage = { "<(white|common)|(anemo|wind|air)|(geo|rock)|electro|(dendro|grass|plant)>" }, 
    aliases = { "se" }, 
    permission = "player.se",
    permissionTargeted = "player.se.others",
    threading = true)

    @Nullable


public final class SwitchElementCommand implements CommandHandler
{
    @Nullable
    private static final Method getPositionMethod;
    //private static final String failedSuccessfullyMessage = translate(sender, "commands.switchelement.success_without_switch_scene");

    private enum Element {
    elementless(501, 701),
    pyro(502, 702),
    hydro(503, 703),
    anemo(504, 704),
    cryo(505, 705),
    geo(506, 706),
    electro(507, 707),
    dendro(508, 708);

    private final int boyId;
    private final int girlId;

    private Element(final int boyId, final int girlId) {
        this.boyId = boyId;
        this.girlId = girlId;
    }

    public int getSkillRepoId(final int avatarId) {
        return (avatarId == 10000005) ? this.boyId : this.girlId;
    }

    private static /* synthetic */ Element[] $values() {
        return new Element[] { Element.elementless, Element.pyro, Element.hydro, Element.anemo, Element.cryo, Element.geo, Element.electro, Element.dendro };
    }


    }
    private static Method getPositionMethod() {
        try {
            return Player.class.getMethod("getPos", (Class<?>[])new Class[0]);
        }
        catch (NoSuchMethodException ex) {
            try {
                return Player.class.getMethod("getPosition", (Class<?>[])new Class[0]);
            }
            catch (NoSuchMethodException ex2) {
                return null;
            }
        }
    }


   private static SwitchElementCommand instance;
   private static SwitchElementCommand getInstance() {
       return SwitchElementCommand.instance;
   }

    private Element getElementFromString(final String elementString) {
        final String lowerCase = elementString.toLowerCase();
        Element element = null;
        switch (lowerCase) {
            case "white":
            case "common": {
                element = Element.elementless;
                break;
            }
            case "fire":
            case "pyro": {
                element = Element.pyro;
                break;
            }
            case "water":
            case "hydro": {
                element = Element.hydro;
                break;
            }
            case "wind":
            case "anemo":
            case "air": {
                element = Element.anemo;
                break;
            }
            case "ice":
            case "cryo": {
                element = Element.cryo;
                break;
            }
            case "rock":
            case "geo": {
                element = Element.geo;
                break;
            }
            case "electro": {
                element = Element.electro;
                break;
            }
            case "grass":
            case "dendro":
            case "plant": {
                element = Element.dendro;
                break;
            }
            default: {
                element = null;
                break;
            }
        }
        return element;
    }

    private boolean changeAvatarElement(final Player sender, final int avatarId, final Element element) {
        final Avatar avatar = sender.getAvatars().getAvatarById(avatarId);
        final AvatarSkillDepotData skillDepot = (AvatarSkillDepotData)GameData.getAvatarSkillDepotDataMap().get(element.getSkillRepoId(avatarId));
        if (avatar == null || skillDepot == null) {
            return false;
        }
        avatar.setSkillDepotData(skillDepot);
        avatar.setCurrentEnergy(1000.0f);
        avatar.save();
        return true;
    }

    public void execute(final Player sender, final Player targetPlayer, final List<String> args) {
        if (args.size() != 1) {
            CommandHandler.sendTranslatedMessage(sender, "commands.switchelement.usage");
            return;
        }
        if (sender == null) {
            Grasscutter.getLogger().info(translate(sender, "commands.switchelement.failed_console"));
            return;
        }
        final Element element = this.getElementFromString(args.get(0));
        if (element == null) {
            CommandHandler.sendTranslatedMessage(sender, "commands.switchelement.failed_no_element");
            return;
        }
        if (element == Element.cryo || element == Element.pyro || element == Element.hydro ) {
            CommandHandler.sendTranslatedMessage(sender, "commands.switchelement.failed_unable_element");
            return;
        }
        final boolean maleSuccess = this.changeAvatarElement(sender, 10000005, element);
        final boolean femaleSuccess = this.changeAvatarElement(sender, 10000007, element);
        if (maleSuccess || femaleSuccess) {
            if (SwitchElementCommand.getPositionMethod == null) {
                final String message = translate(sender, "commands.switchelement.success_without_switch_scene", element.name());
                CommandHandler.sendMessage(sender, message);
                return;
            }
            final int scene = sender.getSceneId();
            String message2;
            try {
                final Position senderPos = (Position)SwitchElementCommand.getPositionMethod.invoke(sender, new Object[0]);
                sender.getWorld().transferPlayerToScene(sender, 1, senderPos);
                sender.getWorld().transferPlayerToScene(sender, scene, senderPos);
                sender.getScene().broadcastPacket((BasePacket)new PacketSceneEntityAppearNotify(sender));
                message2 = translate(sender, "commands.switchelement.success", element.name());
            }
            catch (IllegalAccessException | InvocationTargetException ex2) {
                final ReflectiveOperationException ex = null;
                final ReflectiveOperationException e = ex;
                message2 = translate(sender, "commands.switchelement.success_without_switch_scene", element.name());
            }
            CommandHandler.sendMessage(sender, message2);
        }
        else {
            CommandHandler.sendTranslatedMessage(sender, "commands.switchelement.failed");
        }
    }

    static {
        getPositionMethod = SwitchElementCommand.getPositionMethod();
    }
}
