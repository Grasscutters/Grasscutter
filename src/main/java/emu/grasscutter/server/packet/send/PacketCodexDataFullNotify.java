package emu.grasscutter.server.packet.send;

import emu.grasscutter.data.GameData;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.net.proto.CodexDataFullNotifyOuterClass.CodexDataFullNotify;
import emu.grasscutter.net.proto.CodexTypeDataOuterClass.CodexTypeData;
import java.util.Collections;

public class PacketCodexDataFullNotify extends BasePacket {
    public PacketCodexDataFullNotify(Player player) {
        super(PacketOpcodes.CodexDataFullNotify, true);

        // Quests
        CodexTypeData.Builder questTypeData = CodexTypeData.newBuilder().setTypeValue(1);

        // Weapons
        CodexTypeData.Builder weaponTypeData = CodexTypeData.newBuilder().setTypeValue(2);

        // Animals
        CodexTypeData.Builder animalTypeData = CodexTypeData.newBuilder().setTypeValue(3);

        // Materials
        CodexTypeData.Builder materialTypeData = CodexTypeData.newBuilder().setTypeValue(4);

        // Books
        CodexTypeData.Builder bookTypeData = CodexTypeData.newBuilder().setTypeValue(5);

        // Tips
        CodexTypeData.Builder pushTipsTypeData = CodexTypeData.newBuilder().setTypeValue(6);

        // Views
        CodexTypeData.Builder viewTypeData = CodexTypeData.newBuilder().setTypeValue(7);

        // Reliquary
        CodexTypeData.Builder reliquaryData = CodexTypeData.newBuilder().setTypeValue(8);

        player
                .getQuestManager()
                .forEachMainQuest(
                        mainQuest -> {
                            if (mainQuest.isFinished()) {
                                var codexQuest =
                                        GameData.getCodexQuestDataIdMap().get(mainQuest.getParentQuestId());
                                if (codexQuest != null) {
                                    questTypeData
                                            .addCodexIdList(codexQuest.getId())
                                            .addAllHaveViewedList(Collections.singleton(true));
                                }
                            }
                        });

        player
                .getCodex()
                .getUnlockedWeapon()
                .forEach(
                        weapon -> {
                            var codexWeapon = GameData.getCodexWeaponDataIdMap().get((int) weapon);
                            if (codexWeapon != null) {
                                weaponTypeData
                                        .addCodexIdList(codexWeapon.getId())
                                        .addAllHaveViewedList(Collections.singleton(true));
                            }
                        });

        player
                .getCodex()
                .getUnlockedAnimal()
                .forEach(
                        (animal, amount) -> {
                            var codexAnimal = GameData.getCodexAnimalDataMap().get((int) animal);
                            if (codexAnimal != null) {
                                animalTypeData
                                        .addCodexIdList(codexAnimal.getId())
                                        .addAllHaveViewedList(Collections.singleton(true));
                            }
                        });

        player
                .getCodex()
                .getUnlockedMaterial()
                .forEach(
                        material -> {
                            var codexMaterial = GameData.getCodexMaterialDataIdMap().get((int) material);
                            if (codexMaterial != null) {
                                materialTypeData
                                        .addCodexIdList(codexMaterial.getId())
                                        .addAllHaveViewedList(Collections.singleton(true));
                            }
                        });

        player
                .getCodex()
                .getUnlockedReliquarySuitCodex()
                .forEach(
                        reliquarySuit -> {
                            reliquaryData
                                    .addCodexIdList(reliquarySuit)
                                    .addAllHaveViewedList(Collections.singleton(true));
                        });

        CodexDataFullNotify.Builder proto =
                CodexDataFullNotify.newBuilder()
                        .addTypeDataList(questTypeData.build())
                        .addTypeDataList(weaponTypeData)
                        .addTypeDataList(animalTypeData)
                        .addTypeDataList(materialTypeData)
                        .addTypeDataList(bookTypeData)
                        .addTypeDataList(pushTipsTypeData.build())
                        .addTypeDataList(viewTypeData.build())
                        .addTypeDataList(reliquaryData);

        this.setData(proto);
    }
}
