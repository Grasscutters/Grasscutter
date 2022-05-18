package emu.grasscutter.command.commands;

import com.google.gson.Gson;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.utils.ShowInfosUtil;
import emu.grasscutter.utils.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static emu.grasscutter.utils.Language.translate;
import static emu.grasscutter.utils.ShowInfosUtil.InfoCategory.*;

@Command(label = "show", usage = "show <avatars(avt),artifacts(art),talents(tl),weapons(wp)> pageNo(1,+∞) pageSize(10,+∞) [name]",
        permission = "player.show", permissionTargeted = "player.show.others", description = "commands.show.description")
public class ShowCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (targetPlayer == null) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.execution.need_target"));
            return;
        }

        // toTalentsFile(targetPlayer);


        if (!checkArgs(sender, args)) {
            return;
        }


        StringBuilder stringBuilder = new StringBuilder();

        PageInfo pageInfo = new PageInfo(args);
        int pageSize = pageInfo.getPageSize();

        String item = args.get(0);

        Locale locale = targetPlayer.getAccount().getLocale();

        String name = args.size() > 3 ? args.get(3).toLowerCase(locale) : null;

        String langCode = Utils.getLanguageCode(locale);

        if (existAvatars(item)) {
            List<Map.Entry<Integer, String>> avatars = ShowInfosUtil.getShowInfoMap(langCode, AVATARS).entrySet().stream().filter(entry -> {
                if (name == null) {
                    return true;
                }
                if (entry.getValue() != null) {
                    return entry.getValue().toLowerCase(locale).contains(name);
                }
                return true;
            }).toList();
            stringBuilder.append(translate(sender, "commands.show.avatars_title", pageInfo.getPageNo(), pageInfo.getPageSize(), pageInfo.getMaxPageNo(avatars.size()), avatars.size()));
            for (Map.Entry<Integer, String> avatar : avatars.stream().skip(pageInfo.getSkipNum()).toList()) {
                pageSize -= 1;
                stringBuilder.append("%s : %s\n".formatted(avatar.getKey(), avatar.getValue()));
                if (pageSize == 0) break;
            }
            CommandHandler.sendMessage(sender, stringBuilder.substring(0,stringBuilder.length() - 1));
        } else if (existArtifacts(item)) {
            List<Map.Entry<Integer, String>> artifacts = ShowInfosUtil.getShowInfoMap(langCode, ARTIFACTS).entrySet().stream().filter(entry -> {
                if (name == null) {
                    return true;
                }
                if (entry.getValue() != null) {
                    return entry.getValue().toLowerCase(locale).contains(name);
                }
                return true;
            }).toList();
            stringBuilder.append(translate(sender, "commands.show.artifacts_title", pageInfo.getPageNo(), pageInfo.getPageSize(), pageInfo.getMaxPageNo(artifacts.size()), artifacts.size()));
            for (Map.Entry<Integer, String> artifact : artifacts.stream().skip(pageInfo.getSkipNum()).toList()) {
                pageSize -= 1;
                stringBuilder.append("%s : %s\n".formatted(artifact.getKey(), artifact.getValue()));
                if (pageSize == 0) break;
            }
            CommandHandler.sendMessage(sender, stringBuilder.substring(0,stringBuilder.length() - 1));
        } else if (existTalents(item)) {
            List<Map.Entry<Integer, String>> avatarSkillData = ShowInfosUtil.getShowInfoMap(langCode, TALENTS).entrySet().stream().filter(entry -> {
                if (name == null) {
                    return true;
                }
                if (entry.getValue() != null) {
                    return entry.getValue().toLowerCase(locale).contains(name);
                }
                return true;
            }).toList();
            stringBuilder.append(translate(sender, "commands.show.talents_title", pageInfo.getPageNo(), pageInfo.getPageSize(), pageInfo.getMaxPageNo(avatarSkillData.size()), avatarSkillData.size()));
            for (Map.Entry<Integer, String> skillData : avatarSkillData.stream().skip(pageInfo.getSkipNum()).toList()) {
                pageSize -= 1;
                stringBuilder.append("%s : %s\n".formatted(skillData.getKey(), skillData.getValue()));
                if (pageSize == 0) break;
            }
            CommandHandler.sendMessage(sender, stringBuilder.substring(0,stringBuilder.length() - 1));
        } else if (existWeapons(item)) {
            List<Map.Entry<Integer, String>> weapons = ShowInfosUtil.getShowInfoMap(langCode, WEAPONS).entrySet().stream().filter(entry -> {
                if (name == null) {
                    return true;
                }
                if (entry.getValue() != null) {
                    return entry.getValue().toLowerCase(locale).contains(name);
                }
                return true;
            }).toList();
            stringBuilder.append(translate(sender, "commands.show.weapons_title", pageInfo.getPageNo(), pageInfo.getPageSize(), pageInfo.getMaxPageNo(weapons.size()), weapons.size()));
            for (Map.Entry<Integer, String> weapon : weapons.stream().skip(pageInfo.getSkipNum()).toList()) {
                pageSize -= 1;
                stringBuilder.append("%s : %s\n".formatted(weapon.getKey(), weapon.getValue()));
                if (pageSize == 0) break;
            }
            CommandHandler.sendMessage(sender, stringBuilder.substring(0,stringBuilder.length() - 1));
        }
    }

    static class PageInfo {
        int pageNo = 1;
        int pageSize = 10;

        public PageInfo(List<String> args) {
            try {
                pageNo = Integer.parseInt(args.get(1));
                pageSize = Integer.parseInt(args.get(2));
            } catch (Exception ignored) {
            }
        }

        public int getPageNo() {
            return Math.max(pageNo, 1);
        }

        public int getSkipNum() {
            return Math.max(pageNo - 1, 0) * pageSize;
        }

        public int getPageSize() {
            return Math.max(pageSize, 10);
        }

        public int getMaxPageNo(int total) {
            if (total % getPageSize() == 0) {
                return (total / getPageSize());
            }
            return (total / getPageSize() + 1);
        }
    }

    private boolean existAvatars(String arg) {
        return "avatars".equalsIgnoreCase(arg) || "avt".equalsIgnoreCase(arg);
    }

    private boolean existArtifacts(String arg) {
        return "artifacts".equalsIgnoreCase(arg) || "art".equalsIgnoreCase(arg);
    }

    private boolean existTalents(String arg) {
        return "talents".equalsIgnoreCase(arg) || "tl".equalsIgnoreCase(arg);
    }

    private boolean existWeapons(String arg) {
        return "weapons".equalsIgnoreCase(arg) || "wp".equalsIgnoreCase(arg);
    }



    private boolean checkArgs(Player sender, List<String> args) {
        try {
            if (args.size() == 0) {
                throw new NumberFormatException();
            } else if (args.size() == 1) {
                if (!isExist(args.get(0))) throw new NumberFormatException();
            } else if (args.size() == 2) {
                if (!isExist(args.get(0))) throw new NumberFormatException();
                Integer.parseInt(args.get(1));
            } else {
                if (!isExist(args.get(0))) throw new NumberFormatException();
                Integer.parseInt(args.get(1));
                Integer.parseInt(args.get(2));
            }
        } catch (NumberFormatException e) {
            CommandHandler.sendMessage(sender, translate(sender, "commands.show.usage"));
            return false;
        }

        return true;
    }

    private boolean isExist(String item) {
        return existAvatars(item) || existArtifacts(item) || existTalents(item) || existWeapons(item);
    }

    /**
     * 生成测试文件方便对照
     *
     * @param targetPlayer
     */
    private void toTalentsFile(Player targetPlayer) {
        Gson gson = Grasscutter.getGsonFactory();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileWriter talents = new FileWriter("./avatars/Talents.txt");
            Map<Integer, String> map = new HashMap<>();
            targetPlayer.getAvatars().getAvatars().forEach((avatarId, avatar) -> {
                try {
                    String filePath = "./avatars/%s".formatted(avatarId);
                    File file = new File(filePath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    FileWriter writer = new FileWriter(filePath + "/skills.txt");
                    writer.write("// talents\n");
                    writer.write(gson.toJson(avatar.getTalentIdList()) + "\n");
                    writer.write("// avatar.getSkillDepotId()\n");
                    writer.write(avatar.getSkillDepotId() + "\n");
                    writer.write("// avatar.getSkillDepot()\n");
                    writer.write(gson.toJson(avatar.getSkillDepot()) + "\n");
                    writer.write("// avatar.getAvatarData().getCandSkillDepotIds()()\n");
                    writer.write(gson.toJson(avatar.getAvatarData().getCandSkillDepotIds()) + "\n");

                    writer.write("// avatar.getSkillLevelMap()\n");
                    writer.write(gson.toJson(avatar.getSkillLevelMap()) + "\n");

                    writer.write("// avatar.getProudSkillList()\n");
                    writer.write(gson.toJson(avatar.getProudSkillList()) + "\n");
                    writer.write("// avatar.getCoreProudSkillLevel()\n");
                    writer.write(gson.toJson(avatar.getCoreProudSkillLevel()) + "\n");
                    writer.write("// avatar.getProudSkillBonusMap()\n");
                    writer.write(gson.toJson(avatar.getProudSkillBonusMap()) + "\n");

                    stringBuilder.append("// %s - %s\n".formatted(avatarId, avatar.getData().getName()));
                    stringBuilder.append("%s:skillName\n".formatted(avatar.getSkillDepot().getSkills().get(0)));
                    stringBuilder.append("%s:skillName\n".formatted(avatar.getSkillDepot().getSkills().get(1)));
                    stringBuilder.append("%s:skillName\n".formatted(avatar.getSkillDepot().getEnergySkill()));
                    // if (avatar.getSkillDepot().getSkills().size() >)
                    for (Object proudSkillId : avatar.getProudSkillList().toArray()) {
                        stringBuilder.append("%s:skillName\n".formatted(proudSkillId));
                    }

                    String string = stringBuilder.toString();
                    map.put(avatarId, string);
                    stringBuilder.replace(0, string.length(), "");

                    // talents.write("// %s - %s\n".formatted(avatarId, avatar.getData().getName()));
                    // talents.write("%s:skillName\n".formatted(avatar.getSkillDepot().getSkills().get(0)));
                    // talents.write("%s:skillName\n".formatted(avatar.getSkillDepot().getSkills().get(1)));
                    // talents.write("%s:skillName\n".formatted(avatar.getSkillDepot().getEnergySkill()));
                    // for (Object proudSkillId : avatar.getProudSkillList().toArray()) {
                    //     talents.write("%s:skillName\n".formatted(proudSkillId));
                    // }

                    writer.flush();
                    writer.close();

                    // talents.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            ArrayList<Map.Entry<Integer, String>> arrayList = new ArrayList<>(map.entrySet());
            arrayList.sort((Comparator.comparingInt(Map.Entry::getKey)));
            for (Map.Entry<Integer, String> integerStringEntry : arrayList) {
                talents.write(integerStringEntry.getValue());
                talents.flush();
            }
            talents.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
