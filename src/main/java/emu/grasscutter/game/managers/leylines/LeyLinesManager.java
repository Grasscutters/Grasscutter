package emu.grasscutter.game.managers.leylines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.entity.EntityGadget;
import emu.grasscutter.game.entity.gadget.GadgetWorktop;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.net.proto.VisionTypeOuterClass;
import emu.grasscutter.server.packet.send.PacketBlossomBriefInfoNotify;
import emu.grasscutter.utils.Position;
import emu.grasscutter.utils.Utils;

public class LeyLinesManager {
    private Player player;
    private final ArrayList<ActiveLeyLines> activeLeyLines = new ArrayList<>();
    private final ArrayList<ActiveLeyLines> activeChests = new ArrayList<>();
    private final ArrayList<EntityGadget> createdEntity = new ArrayList<>();
    private static final HashMap<Integer,ArrayList<Reward>> REWARDS_GOLDEN = new HashMap<>();
    private static final HashMap<Integer,ArrayList<Reward>> REWARDS_BLUE = new HashMap<>();
    private static final HashMap<Integer,ArrayList<Integer>> monstersDifficulty = new HashMap<>();
    static{
        ArrayList<Reward> rewards;
        rewards = new ArrayList<>();
        rewards.add(new Reward(202,12000));
        rewards.add(new Reward(105,20));
        rewards.add(new Reward(102,100));
        REWARDS_GOLDEN.put(0,rewards);

        rewards = new ArrayList<>();
        rewards.add(new Reward(202,20000));
        rewards.add(new Reward(105,20));
        rewards.add(new Reward(102,100));
        REWARDS_GOLDEN.put(1,rewards);

        rewards = new ArrayList<>();
        rewards.add(new Reward(202,28000));
        rewards.add(new Reward(105,20));
        rewards.add(new Reward(102,100));
        REWARDS_GOLDEN.put(2,rewards);

        rewards = new ArrayList<>();
        rewards.add(new Reward(202,36000));
        rewards.add(new Reward(105,20));
        rewards.add(new Reward(102,100));
        REWARDS_GOLDEN.put(3,rewards);

        rewards = new ArrayList<>();
        rewards.add(new Reward(202,44000));
        rewards.add(new Reward(105,20));
        rewards.add(new Reward(102,100));
        REWARDS_GOLDEN.put(4,rewards);

        rewards = new ArrayList<>();
        rewards.add(new Reward(202,52000));
        rewards.add(new Reward(105,20));
        rewards.add(new Reward(102,100));
        REWARDS_GOLDEN.put(5,rewards);

        rewards = new ArrayList<>();
        rewards.add(new Reward(202,60000));
        rewards.add(new Reward(105,20));
        rewards.add(new Reward(102,100));
        REWARDS_GOLDEN.put(6,rewards);
        REWARDS_GOLDEN.put(7,rewards);
        REWARDS_GOLDEN.put(8,rewards);

        //
        rewards = new ArrayList<>();
        rewards.add(new Reward(104001,7,8));
        rewards.add(new Reward(104002,3,4));
        rewards.add(new Reward(105,20));
        rewards.add(new Reward(102,100));
        REWARDS_BLUE.put(0,rewards);

        rewards = new ArrayList<>();
        rewards.add(new Reward(104001,10,12));
        rewards.add(new Reward(104002,5,6));
        rewards.add(new Reward(105,20));
        rewards.add(new Reward(102,100));
        REWARDS_BLUE.put(1,rewards);

        rewards = new ArrayList<>();
        rewards.add(new Reward(104002,10,11));
        rewards.add(new Reward(105,20));
        rewards.add(new Reward(102,100));
        REWARDS_BLUE.put(2,rewards);

        rewards = new ArrayList<>();
        rewards.add(new Reward(104002,13,14));
        rewards.add(new Reward(105,20));
        rewards.add(new Reward(102,100));
        REWARDS_BLUE.put(3,rewards);

        rewards = new ArrayList<>();
        rewards.add(new Reward(104003,2,3));
        rewards.add(new Reward(104002,6,7));
        rewards.add(new Reward(105,20));
        rewards.add(new Reward(102,100));
        REWARDS_BLUE.put(4,rewards);

        rewards = new ArrayList<>();
        rewards.add(new Reward(104003,3,4));
        rewards.add(new Reward(104002,6,7));
        rewards.add(new Reward(105,20));
        rewards.add(new Reward(102,100));
        REWARDS_BLUE.put(5,rewards);

        rewards = new ArrayList<>();
        rewards.add(new Reward(104003,4,5));
        rewards.add(new Reward(104002,6,7));
        rewards.add(new Reward(105,20));
        rewards.add(new Reward(102,100));
        REWARDS_BLUE.put(6,rewards);
        REWARDS_BLUE.put(7,rewards);
        REWARDS_BLUE.put(8,rewards);
    }
    static {
        monstersDifficulty.put(0,new ArrayList<>());
        monstersDifficulty.put(1,new ArrayList<>());
        monstersDifficulty.put(2,new ArrayList<>());
        monstersDifficulty.put(3,new ArrayList<>());
        registerMonster(21010101,0); // 丘丘人
        registerMonster(21020101,1); // 木盾丘丘暴徒
        registerMonster(21020201,1); // 火斧丘丘暴徒
        registerMonster(21020202,1); // 火斧丘丘暴徒
        registerMonster(21020301,1); // 岩盾丘丘暴徒
        registerMonster(21020401,2); // 丘丘霜铠王
        registerMonster(21020501,2); // 丘丘岩盔王
        registerMonster(21020601,1); // 冰盾丘丘暴徒
        registerMonster(21020701,1); // 雷斧丘丘暴徒
        registerMonster(21020703,1); // 雷斧丘丘暴徒
        registerMonster(21020801,2); // 丘丘雷兜王
        registerMonster(20010101,0); // 草史莱姆
        registerMonster(20010201,0); // 大型草史莱姆
        registerMonster(20010202,0); // 大型草史莱姆
        registerMonster(20010301,0); // 风史莱姆
        registerMonster(20010401,0); // 大型风史莱姆
        registerMonster(20010403,0); // 大型风史莱姆
        registerMonster(20020101,2); // 狂风之核
        registerMonster(20010501,0); // 雷史莱姆
        registerMonster(20010601,0); // 大型雷史莱姆
        registerMonster(20010604,0); // 大型雷史莱姆
        registerMonster(20010701,0); // 变异雷史莱姆
        registerMonster(20010702,0); // 变异雷史莱姆
        registerMonster(20010801,0); // 冰史莱姆
        registerMonster(20010802,0); // 冰史莱姆
        registerMonster(20010901,0); // 大型冰史莱姆
        registerMonster(20010902,0); // 大型冰史莱姆
        registerMonster(20010904,0); // 大型冰史莱姆
        registerMonster(20011001,0); // 水史莱姆
        registerMonster(20011101,0); // 大型水史莱姆
        registerMonster(20011103,0); // 大型水史莱姆
        registerMonster(20011601,0); // 无相之水·召唤物
        registerMonster(20011701,0); // 无相之水·召唤物
        registerMonster(20011801,0); // 无相之水·召唤物
        registerMonster(20011901,0); // 无相之水·召唤物
        registerMonster(20050201,0); // 幻形豕兽·水
        registerMonster(20050202,0); // 幻形豕兽·水
        registerMonster(20050203,0); // 幻形豕兽·水
        registerMonster(20050301,0); // 幻形鹤·水
        registerMonster(20050302,0); // 幻形鹤·水
        registerMonster(20050401,0); // 幻形蟹·水
        registerMonster(20050402,0); // 幻形蟹·水
        registerMonster(20050403,0); // 幻形蟹·水
        registerMonster(20050501,0); // 幻形雀·水
        registerMonster(20050502,0); // 幻形雀·水
        registerMonster(20050601,0); // 幻形游禽·水
        registerMonster(20050602,0); // 幻形游禽·水
        registerMonster(20050603,0); // 幻形游禽·水
        registerMonster(20050701,0); // 幻形花鼠·水
        registerMonster(20050702,0); // 幻形花鼠·水
        registerMonster(20050703,0); // 幻形花鼠·水
        registerMonster(20050801,0); // 幻形蛙·水
        registerMonster(20050802,0); // 幻形蛙·水
        registerMonster(20050901,0); // 幻形飞鸢·水
        registerMonster(20070101,2); // 雷音权现
        registerMonster(21010201,0); // 打手丘丘人
        registerMonster(21010301,0); // 木盾丘丘人
        registerMonster(21010401,0); // 射手丘丘人
        registerMonster(21010402,0); // 射手丘丘人
        registerMonster(21010501,0); // 火箭丘丘人
        registerMonster(21010502,0); // 火箭丘丘人
        registerMonster(21010601,0); // 爆弹丘丘人
        registerMonster(21010603,0); // 爆弹丘丘人
        registerMonster(21010701,0); // 冲锋丘丘人
        registerMonster(21010901,0); // 冰箭丘丘人
        registerMonster(21010902,0); // 冰箭丘丘人
        registerMonster(21011001,0); // 雷箭丘丘人
        registerMonster(21011002,0); // 雷箭丘丘人
        registerMonster(21011201,0); // 岩盾丘丘人
        registerMonster(21011301,0); // 冰弹丘丘人
        registerMonster(21011302,0); // 冰弹丘丘人
        registerMonster(21011401,0); // 冰盾丘丘人
        registerMonster(21011403,0); // 冰盾丘丘人
        registerMonster(21011501,0); // 奇怪的丘丘人
        registerMonster(21011601,0); // 雷弹丘丘人
        registerMonster(21011602,0); // 雷弹丘丘人
        registerMonster(20011201,0); // 火史莱姆
        registerMonster(20011202,0); // 火史莱姆
        registerMonster(20011301,0); // 大型火史莱姆
        registerMonster(20011304,0); // 大型火史莱姆
        registerMonster(20011401,0); // 岩史莱姆
        registerMonster(20011501,0); // 大型岩史莱姆
        registerMonster(20011502,0); // 大型岩史莱姆
        registerMonster(22010101,2); // 火深渊法师
        registerMonster(22010102,2); // 火深渊法师
        registerMonster(22010103,2); // 火深渊法师
        registerMonster(22010104,2); // 火深渊法师
        registerMonster(22010201,2); // 冰深渊法师
        registerMonster(22010202,2); // 冰深渊法师
        registerMonster(22010203,2); // 冰深渊法师
        registerMonster(22010204,2); // 冰深渊法师
        registerMonster(22010301,2); // 水深渊法师
        registerMonster(22010302,2); // 水深渊法师
        registerMonster(22010303,2); // 水深渊法师
        registerMonster(22010304,2); // 水深渊法师
        registerMonster(22010401,2); // 雷深渊法师
        registerMonster(22010403,2); // 雷深渊法师
        registerMonster(22010404,2); // 雷深渊法师


        registerMonster(21030101,0); // 水丘丘萨满
        registerMonster(21030103,0); // 水丘丘萨满
        registerMonster(21030201,0); // 草丘丘萨满
        registerMonster(21030203,0); // 草丘丘萨满
        registerMonster(21030301,0); // 风丘丘萨满
        registerMonster(21030303,0); // 风丘丘萨满
        registerMonster(21030304,0); // 风丘丘萨满
        registerMonster(21030401,0); // 岩丘丘萨满
        registerMonster(21030402,0); // 岩丘丘萨满
        registerMonster(21030501,0); // 冰丘丘萨满
        registerMonster(21030601,0); // 雷丘丘萨满
        registerMonster(21030603,0); // 雷丘丘萨满
        registerMonster(24010101,2); // 遗迹守卫
        registerMonster(24010201,2); // 遗迹猎者
        registerMonster(24010301,2); // 遗迹重机
        registerMonster(24010303,2); // 遗迹重机
        registerMonster(24010401,2); // 遗迹巨蛇
        registerMonster(24020101,2); // 遗迹巡弋者
        registerMonster(24020102,2); // 遗迹巡弋者
        registerMonster(24020103,2); // 遗迹巡弋者
        registerMonster(24020201,2); // 遗迹歼击者
        registerMonster(24020202,2); // 遗迹歼击者
        registerMonster(24020203,2); // 遗迹歼击者
        registerMonster(24020301,2); // 遗迹防卫者
        registerMonster(24020302,2); // 遗迹防卫者
        registerMonster(24020303,2); // 遗迹防卫者
        registerMonster(24020401,2); // 遗迹侦察者
        registerMonster(24020402,2); // 遗迹侦察者
        registerMonster(24020403,2); // 遗迹侦察者
        registerMonster(24021101,2); // 恒常机关阵列
        registerMonster(24021102,2); // 恒常机关阵列
        registerMonster(23010101,2); // 愚人众先遣队·冰铳重卫士
        registerMonster(23010201,2); // 愚人众先遣队·水铳重卫士
        registerMonster(23010301,2); // 愚人众先遣队·雷锤前锋军
        registerMonster(23010401,2); // 愚人众先遣队·岩使游击兵
        registerMonster(23010501,2); // 愚人众先遣队·风拳前锋军
        registerMonster(23010601,2); // 愚人众先遣队·火铳游击兵
        registerMonster(23020101,2); // 愚人众·火之债务处理人
        registerMonster(23020102,2); // 愚人众·火之债务处理人
        registerMonster(23030101,2); // 愚人众·雷萤术士
        registerMonster(23030102,2); // 愚人众·雷萤术士
        registerMonster(23040101,2); // 愚人众·冰萤术士
        registerMonster(23040102,2); // 愚人众·冰萤术士
        registerMonster(23050101,2); // 愚人众·藏镜仕女
        registerMonster(25010101,0); // 盗宝团·「溜溜」
        registerMonster(25010102,0); // 盗宝团·「溜溜」
        registerMonster(25010103,0); // 盗宝团·「溜溜」
        registerMonster(25010104,0); // 盗宝团·「溜溜」
        registerMonster(25010105,0); // 盗宝团·「溜溜」
        registerMonster(25010106,0); // 盗宝团·「溜溜」
        registerMonster(25010201,0); // 盗宝团·斥候
        registerMonster(25010203,0); // 盗宝团·斥候
        registerMonster(25010204,0); // 盗宝团·斥候
        registerMonster(25010205,0); // 盗宝团·斥候
        registerMonster(25010206,0); // 盗宝团·斥候
        registerMonster(25010207,0); // 盗宝团·斥候
        registerMonster(25010208,0); // 盗宝团·斥候
        registerMonster(25010301,0); // 盗宝团·火之药剂师
        registerMonster(25010302,0); // 盗宝团·火之药剂师
        registerMonster(25010401,0); // 盗宝团·水之药剂师
        registerMonster(25010501,0); // 盗宝团·雷之药剂师
        registerMonster(25010601,0); // 盗宝团·冰之药剂师
        registerMonster(25010701,0); // 盗宝团·杂工
        registerMonster(25020101,0); // 盗宝团·「怪鸟」
        registerMonster(25020102,0); // 盗宝团·「怪鸟」
        registerMonster(25020201,0); // 盗宝团·神射手
        registerMonster(25020204,0); // 盗宝团·神射手
        registerMonster(25030101,0); // 盗宝团·「卡门」
        registerMonster(25030102,0); // 盗宝团·「卡门」
        registerMonster(25030103,0); // 盗宝团·「卡门」
        registerMonster(25030201,0); // 盗宝团·掘墓者
        registerMonster(25030301,0); // 盗宝团·海上男儿
        registerMonster(25040101,0); // 盗宝团·「大姐头」
        registerMonster(25040102,0); // 盗宝团·「大姐头」
        registerMonster(25040103,0); // 盗宝团·「大姐头」
        registerMonster(25050101,0); // 千岩军士兵
        registerMonster(25050201,0); // 千岩军教头
        registerMonster(25050301,0); // 幕府足轻
        registerMonster(25050401,0); // 幕府足轻头
        registerMonster(25050402,0); // 幕府足轻头
        registerMonster(25050501,0); // 珊瑚宫众
        registerMonster(25050502,0); // 珊瑚宫众
        registerMonster(25060101,0); // 盗宝团·拳术家
        registerMonster(25060102,0); // 盗宝团·拳术家
        registerMonster(25070101,0); // 盗宝团·粉碎者
        registerMonster(25070201,0); // 盗宝团·「瓦伦斯坦」
        registerMonster(25070202,0); // 盗宝团·「瓦伦斯坦」
        registerMonster(25080101,0); // 野伏·阵刀番
        registerMonster(25080201,0); // 野伏·火付番
        registerMonster(25080202,0); // 野伏·火付番
        registerMonster(25080301,0); // 野伏·机巧番
        registerMonster(25080401,0); // 寄骑武士
        registerMonster(25080402,0); // 寄骑武士
        registerMonster(25100101,2); // 海乱鬼·雷腾
        registerMonster(25100301,0); // 落武者·咒雷
        registerMonster(25100201,2); // 海乱鬼·炎威
        registerMonster(25100401,0); // 落武者·祟炎
        registerMonster(25100102,2); // 海乱鬼·雷腾
        registerMonster(25100302,0); // 落武者·咒雷
        registerMonster(26010101,0); // 冰霜骗骗花
        registerMonster(26010102,0); // 冰霜骗骗花
        registerMonster(26010103,0); // 冰霜骗骗花
        registerMonster(26010104,0); // 冰霜骗骗花
        registerMonster(26010201,0); // 炽热骗骗花
        registerMonster(26010301,0); // 电气骗骗花
        registerMonster(26030101,1); // 幼岩龙蜥
        registerMonster(26040101,1); // 岩龙蜥
        registerMonster(26040102,1); // 岩龙蜥
        registerMonster(26040103,1); // 岩龙蜥
        registerMonster(26040104,1); // 岩龙蜥
        registerMonster(26060101,0); // 雷萤
        registerMonster(26060201,0); // 水萤
        registerMonster(26060301,0); // 冰萤
        registerMonster(26090101,0); // 浮游水蕈兽
        registerMonster(20060101,2); // 水飘浮灵
        registerMonster(20060201,2); // 岩飘浮灵
        registerMonster(20060301,2); // 风飘浮灵
        registerMonster(20060401,2); // 冰飘浮灵
        registerMonster(20060501,2); // 雷飘浮灵
        registerMonster(20060601,2); // 火飘浮灵
        registerMonster(21010102,0); // 丘丘人
        registerMonster(21020102,1); // 木盾丘丘暴徒
        registerMonster(21020203,1); // 火斧丘丘暴徒
        registerMonster(21020702,1); // 雷斧丘丘暴徒
        registerMonster(21020302,1); // 岩盾丘丘暴徒
        registerMonster(21020402,2); // 丘丘霜铠王
        registerMonster(21020502,2); // 丘丘岩盔王
        registerMonster(21020802,2); // 丘丘雷兜王
        registerMonster(21020602,1); // 冰盾丘丘暴徒
        registerMonster(20010302,0); // 风史莱姆
        registerMonster(20010402,0); // 大型风史莱姆
        registerMonster(20010502,0); // 雷史莱姆
        registerMonster(20010602,0); // 大型雷史莱姆
        registerMonster(20010703,0); // 变异雷史莱姆
        registerMonster(20010803,0); // 冰史莱姆
        registerMonster(20010903,0); // 大型冰史莱姆
        registerMonster(20011002,0); // 水史莱姆
        registerMonster(20011102,0); // 大型水史莱姆
        registerMonster(21010302,0); // 木盾丘丘人
        registerMonster(21010702,0); // 冲锋丘丘人
        registerMonster(21011202,0); // 岩盾丘丘人
        registerMonster(21011402,0); // 冰盾丘丘人
        registerMonster(20011203,0); // 火史莱姆
        registerMonster(20011302,0); // 大型火史莱姆
        registerMonster(20011402,0); // 岩史莱姆
        registerMonster(20011503,0); // 大型岩史莱姆
        registerMonster(22010105,2); // 火深渊法师
        registerMonster(22010205,2); // 冰深渊法师
        registerMonster(22010305,2); // 水深渊法师
        registerMonster(22010402,2); // 雷深渊法师
        registerMonster(21030102,0); // 水丘丘萨满
        registerMonster(21030202,0); // 草丘丘萨满
        registerMonster(21030302,0); // 风丘丘萨满
        registerMonster(21030403,0); // 岩丘丘萨满
        registerMonster(21030502,0); // 冰丘丘萨满
        registerMonster(21030602,0); // 雷丘丘萨满
        registerMonster(26040105,1); // 岩龙蜥

        /*

        Super Monsters

        registerMonster(24010102,2); // 遗迹守卫
        registerMonster(24010202,2); // 遗迹猎者
        registerMonster(24010302,2); // 遗迹重机
        registerMonster(26050601,2); // 深海龙蜥·原种
        registerMonster(26050901,2); // 深海龙蜥·原种
        registerMonster(26050701,2); // 深海龙蜥·啮冰
        registerMonster(26051001,2); // 深海龙蜥·啮冰
        registerMonster(26050702,2); // 深海龙蜥·啮冰
        registerMonster(26050801,2); // 深海龙蜥·吞雷
        registerMonster(26051101,2); // 深海龙蜥·吞雷
        registerMonster(26050802,2); // 深海龙蜥·吞雷

        registerMonster(26050101,2); // 古岩龙蜥
        registerMonster(26050201,2); // 古岩龙蜥
        registerMonster(26050301,2); // 古岩龙蜥
        registerMonster(26050401,2); // 古岩龙蜥
        registerMonster(26050501,2); // 古岩龙蜥
        registerMonster(26080101,3); // 跋掣
        registerMonster(25090101,2); // 魔偶剑鬼
        registerMonster(25090102,2); // 魔偶剑鬼
        registerMonster(25090103,2); // 魔偶剑鬼
        registerMonster(25090104,2); // 魔偶剑鬼
        registerMonster(25090201,2); // 魔偶剑鬼·孤风
        registerMonster(25090301,2); // 魔偶剑鬼·霜驰
        registerMonster(25090401,2); // 魔偶剑鬼·凶面
        registerMonster(26020101,3); // 急冻树
        registerMonster(26020102,3); // 急冻树
        registerMonster(26020201,3); // 爆炎树
        registerMonster(29010101,3); // 裂空的魔龙
        registerMonster(29010102,3); // 裂空的魔龙
        registerMonster(29010103,3); // 裂空的魔龙
        registerMonster(29010104,3); // 裂空的魔龙
        registerMonster(29020101,3); // 北风的王狼，奔狼的领主
        registerMonster(29020102,3); // 北风的王狼，奔狼的领主
        registerMonster(29030103,3); // 「公子」
        registerMonster(29030106,3); // 「公子」
        registerMonster(29040101,3); // 若陀龙王
        registerMonster(29040102,3); // 若陀龙王
        registerMonster(29040103,3); // 若陀龙王
        registerMonster(29040104,3); // 若陀龙王
        registerMonster(29040111,3); // 若陀龙王
        registerMonster(29050102,3); // 「女士」
        registerMonster(29050104,3); // 「女士」
        registerMonster(29060202,3); // 祸津御建鸣神命
        registerMonster(29060203,3); // 祸津御建鸣神命
        registerMonster(20040101,2); // 无相之雷
        registerMonster(20040102,2); // 无相之雷
        registerMonster(20040201,2); // 无相之风
        registerMonster(20040202,2); // 无相之风
        registerMonster(20040301,2); // 无相之岩
        registerMonster(20040302,2); // 无相之岩
        registerMonster(20040401,2); // 无相之水
        registerMonster(20040501,2); // 无相之冰
        registerMonster(20040601,2); // 无相之火
        registerMonster(20050101,3); // 纯水精灵
        registerMonster(20050102,3); // 纯水精灵
        registerMonster(22020101,2); // 深渊使徒·激流
        registerMonster(22020102,2); // 深渊使徒·激流
        registerMonster(22030101,2); // 深渊咏者·紫电
        registerMonster(22030102,2); // 深渊咏者·紫电
        registerMonster(22030201,2); // 深渊咏者·渊火
        registerMonster(22030202,2); // 深渊咏者·渊火
        registerMonster(22040101,2); // 嗜岩·兽境幼兽
        registerMonster(22040201,2); // 嗜雷·兽境幼兽
        registerMonster(22050101,2); // 嗜岩·兽境猎犬
        registerMonster(22050201,2); // 嗜雷·兽境猎犬
        registerMonster(22060101,2); // 黄金王兽
        registerMonster(22070101,2); // 黯色空壳·旗令
        registerMonster(22070102,2); // 黯色空壳·旗令
        registerMonster(22070201,2); // 黯色空壳·破阵
        registerMonster(22070202,2); // 黯色空壳·破阵
        registerMonster(22070301,2); // 黯色空壳·近卫
        registerMonster(22070302,2); // 黯色空壳·近卫
        registerMonster(22080101,2); // 黑蛇骑士·斩风之剑

         */
    }
    private static void registerMonster(int monsterId,int difficulty){
        monstersDifficulty.get(difficulty).add(monsterId);
    }
    public void onTick(){
        synchronized (activeLeyLines) {
            Iterator<ActiveLeyLines> it = activeLeyLines.iterator();
            while (it.hasNext()) {
                ActiveLeyLines activeLeyLines = it.next();
                activeLeyLines.onTick();
                if (activeLeyLines.getPass()) {
                    EntityGadget chest = activeLeyLines.getChest();
                    Scene scene = getScene();
                    scene.addEntity(chest);
                    scene.setChallenge(null);
                    activeChests.add(activeLeyLines);
                    it.remove();
                }
            }
        }
    }
    public void recycleLeyLineGadgetEntity(EntityGadget entityGadget){
        createdEntity.remove(entityGadget);
        player.getScene().broadcastPacket(new PacketBlossomBriefInfoNotify(createdEntity));
    }
    public EntityGadget createLeyLineGadgetEntity(Position pos, LeyLinesType leyLinesType){
        int worldLevel = getWorldLevel();
        Scene scene = getScene();
        EntityGadget newGadget = new EntityGadget(scene, leyLinesType.getGadgetId(), pos);
        newGadget.buildContent();
        newGadget.setState(204);
        GadgetWorktop gadgetWorktop = ((GadgetWorktop) newGadget.getContent());
        gadgetWorktop.addWorktopOptions(new int[]{187});
        gadgetWorktop.setOnSelectWorktopOptionEvent((Player player, GadgetWorktop context, int option) -> {
            ActiveLeyLines leyLine;
            EntityGadget entityGadget = context.getGadget();
            synchronized (activeLeyLines) {
                for (ActiveLeyLines activeLeyLines : this.activeLeyLines) {
                    if (activeLeyLines.getGadget() == entityGadget) {
                        return false;
                    }
                }
                ArrayList<Integer> monsters;
                //switch(Utils.randomRange(0,4)) {

                //}
                monsters = getRandomMonstersID(0,4);
                monsters.addAll(getRandomMonstersID(1,2));
                monsters.addAll(getRandomMonstersID(2,1));
                System.out.println("LeyLine Monsters:"+monsters);
                leyLine = new ActiveLeyLines(entityGadget, monsters, -1, worldLevel);
                activeLeyLines.add(leyLine);
            }
            entityGadget.updateState(201);
            scene.setChallenge(leyLine.getChallenge());
            scene.removeEntity(entityGadget, VisionTypeOuterClass.VisionType.VISION_TYPE_REMOVE);
            leyLine.start();
            return true;
        });
        createdEntity.add(newGadget);
        player.getScene().broadcastPacket(new PacketBlossomBriefInfoNotify(createdEntity));
        return newGadget;
        //player.getScene().addEntity(entityGadget);
    }
    public synchronized void setPlayer(Player player) {
        this.player = player;
    }
    private Scene getScene() {
        return player.getScene();
    }

    public int getWorldLevel(){
        return getScene().getWorld().getWorldLevel();
    }

    public List<GameItem> onReward(EntityGadget chest) {
        synchronized (activeChests) {
            var it = activeChests.iterator();
            while (it.hasNext()) {
                var activeChest = it.next();
                if (activeChest.getChest() == chest) {
                    if (player.getInventory().payItem(106, 20)) {
                        int worldLevel = getWorldLevel();
                        ArrayList<GameItem> items = new ArrayList<>();
                        ArrayList<Reward> rewards;
                        if (activeChest.getGadget().getGadgetId() == LeyLinesType.LEY_LINES_BLUE_GADGET_ID.getGadgetId()) {
                            rewards = REWARDS_BLUE.get(worldLevel);
                        } else {
                            rewards = REWARDS_GOLDEN.get(worldLevel);
                        }
                        if(rewards==null){
                            Grasscutter.getLogger().error("Leyline could not support world level : "+worldLevel);
                        }
                        for (Reward reward : rewards) {
                            items.add(new GameItem(reward.itemId, Utils.randomRange(reward.minCount, reward.maxCount)));
                        }
                        it.remove();
                        recycleLeyLineGadgetEntity(activeChest.getGadget());
                        return items;
                    }
                    return null;
                }
            }
        }
        return null;
    }

    public static ArrayList<Integer> getRandomMonstersID(int difficulty,int count){
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<Integer> monsters = monstersDifficulty.get(difficulty);
        for(int i=0;i<count;i++){
            result.add(monsters.get(Utils.randomRange(0,monsters.size()-1)));
        }
        return result;
    }
}
