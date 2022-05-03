package emu.grasscutter;

public final class CNLanguage {
    public String An_error_occurred_during_game_update = "游戏更新期间出错。";
    public String Starting_Grasscutter = "启动 Grasscutter 中...";
    public String Invalid_server_run_mode = "无效的服务器运行模式。";
    public String Server_run_mode = "服务器运行模式必须是 'HYBRID', 'DISPATCH_ONLY', 或者 'GAME_ONLY'。 无法启动 Grasscutter...";
    public String Shutting_down = "服务器关闭...";
    public String Start_done = "完成! 获取帮助, 输入 \"help\"";
    public String Dispatch_mode_not_support_command = "该命令不支持在仅dispatch模式下使用。";
    public String Command_error = "命令错误:";
    public String Error = "发生了一个错误。";
    public String Grasscutter_is_free = "Grasscutter 是一个免费软件。 如果你为此付出了金钱，你可能已经被骗了。 项目主页: https://github。com/Grasscutters/Grasscutter";
    public String Game_start_port = "服务器运行在端口 %s 上";
    public String Client_connect = "客户端 %s 已连接";
    public String Client_disconnect = "客户端 %s 已断开连接";
    public String Client_request = "[Dispatch] 客户端 %s %s 请求: %s";
    public String Not_load_keystore = "[Dispatch] 无法加载 keystore。 请尝试输入 keystore password...";
    public String Use_default_keystore = "[Dispatch] 默认的 keystore password 加载成功。 请考虑在config。json中将密码设置为123456。";
    public String Load_keystore_error = "[Dispatch] 加载keystore时发生错误!";
    public String Not_find_ssl_cert = "[Dispatch] 没有找到SSL证书! 开始尝试以HTTP协议运行。";
    public String Welcome = "欢迎使用 Grasscutter";
    public String Potential_unhandled_request = "[Dispatch] 潜在的未经处理的 %s 请求: %s";
    public String Client_login_token = "[Dispatch] 客户端 %s 正在尝试使用token登陆";
    public String Client_token_login_failed = "[Dispatch] 客户端 %s 使用token登陆失败";
    public String Client_login_in_token = "[Dispatch] 客户端 %s 通过token登陆 %s";
    public String Game_account_cache_error = "游戏帐号缓存信息错误";
    public String Wrong_session_key = "错误的会话密钥。";
    public String Client_exchange_combo_token = "[Dispatch] 客户端 %s 交换token令牌成功";
    public String Client_failed_exchange_combo_token = "[Dispatch] 客户端 %s 交换token令牌失败";
    public String Dispatch_start_server_port = "[Dispatch] Dispatch 服务器正运行在端口 %s 上";
    public String Client_failed_login_account_create = "[Dispatch] 客户端 %s 登陆失败: 账户 %s 已创建";
    public String Client_failed_login_account_create_failed = "[Dispatch] 客户端 %s 登陆失败: 账户创建失败";
    public String Client_failed_login_account_no_found = "[Dispatch] 客户端 %s 登陆失败: 账户不存在";
    public String Client_login = "[Dispatch] 客户端 %s 登陆账户 %s";
    public String Username_not_found = "用户名未找到。";
    public String Username_not_found_create_failed = "用户名未找到，创建失败。";

    // Command
    public String No_command_specified = "没有指定命令。";
    public String Unknown_command = "未知命令: ";
    public String You_not_permission_run_command = "你没有权限去执行此命令";
    public String This_command_can_only_run_from_console = "此命令只能从控制台执行。";
    public String Run_this_command_in_game = "请在游戏内执行此命令";
    public String Invalid_playerId = "错误的玩家ID。";
    public String Player_not_found = "玩家不存在。";
    public String Player_is_offline = "玩家已离线";
    public String Invalid_item_id = "错误的物品ID。";
    public String Invalid_item_or_player_id = "错误的物品ID或玩家ID。";
    public String Enabled = "已开启";
    public String Disabled = "已关闭";
    public String No_command_found = "找不到命令。";
    public String Help = "帮助";
    public String Player_not_found_or_offline = "玩家不存在或玩家已离线。";
    public String Invalid_arguments = "错误的参数。";
    public String Success = "成功";
    public String Invalid_entity_id = "错误的实体ID。";

    // Help
    public String Help_usage = "   用法: ";
    public String Help_aliases = "   别名: ";
    public String Help_available_command = "支持的命令:";

    // Account
    public String Modify_user_account = "修改用户帐户";
    public String Invalid_UID = "错误的UID。";
    public String Account_exists = "账户已存在。";
    public String Account_create_UID = "已创建账户 UID %s。";
    public String Account_delete = "账户已删除。";
    public String Account_not_find = "账户未找到";
    public String Account_command_usage = "用法: account <create|delete> <用户名> [uid]";

    // Broadcast
    public String Broadcast_command_usage = "用法: broadcast <message>";
    public String Broadcast_message_sent = "信息已发送";

    // ChangeScene
    public String Change_screen_usage = "用法: changescene <场景 id>";
    public String Change_screen_you_in_that_screen = "你已经在这个场景了。";
    public String Change_screen = "转移到场景 ";
    public String Change_screen_not_exist = "场景不存在";

    // Clear
    public String Clear_weapons = "已为 %s 清理武器 。";
    public String Clear_artifacts = "已为 %s 清理圣遗物 。";
    public String Clear_materials = "已为 %s 清理材料。";
    public String Clear_furniture = "已为 %s 清理摆设。";
    public String Clear_displays = "Cleared displays for %s 。";
    public String Clear_virtuals = "已为 %s 清理货币。";
    public String Clear_everything = "已为 %s 清理所有物品。";

    // Coop
    public String Coop_usage = "用法: coop <玩家ID> <目标玩家ID>";

    // Drop
    public String Drop_usage = "用法: drop <物品ID> [数量]";
    public String Drop_dropped_of = "已掉落 %s 个 %s。";

    // EnterDungeon
    public String EnterDungeon_usage = "用法: enterdungeon <dungeon id>";
    public String EnterDungeon_changed_to_dungeon = "Changed to dungeon ";
    public String EnterDungeon_dungeon_not_found = "Dungeon does not exist";
    public String EnterDungeon_you_in_that_dungeon = "You are already in that dungeon";

    // GiveAll
    public String GiveAll_usage = "用法: giveall [用户ID] [数量]";
    public String GiveAll_item = "正在给所有物品...";
    public String GiveAll_done = "给所有物品完成。";
    public String GiveAll_invalid_amount_or_playerId = "错误的数量或者用户ID";

    // GiveArtifact
    public String GiveArtifact_usage = "用法: giveart|gart [用户ID] <圣遗物ID> <主属性词条ID> [<副词条ID>[,<强化次数>]]... [等级]";
    public String GiveArtifact_invalid_artifact_id = "错误的圣遗物ID。";
    public String GiveArtifact_given = "已给 %s 到 %s。";

    // GiveChar
    public String GiveChar_usage = "用法: givechar <用户ID> <角色ID> [等级]";
    public String GiveChar_given = "给 %s 等级为 %s 到 %s。";
    public String GiveChar_invalid_avatar_id = "错误的用户ID";
    public String GiveChar_invalid_avatar_level = "错误的角色等级。";
    public String GiveChar_invalid_avatar_or_player_id = "错误的角色ID或用户ID。";

    // Give
    public String Give_usage = "用法: give <用户ID> <物品ID> [数量] [等级] [精炼等级]";
    public String Give_refinement_only_applicable_weapons = "精炼等级只适用于武器。";
    public String Give_refinement_must_between_1_and_5 = "精炼等级必须为1-5。";
    public String Give_given = "已给 %s 个 %s 到 %s。";
    public String Give_given_with_level_and_refinement = "Given %s with level %s, refinement %s %s times to %s";
    public String Give_given_level = "Given %s with level %s %s times to %s";

    // GodMode
    public String Godmode_status = "上帝模式已经 %s 给 %s 。";

    // Heal
    public String Heal_message = "所有角色已经被治愈。";

    // Kick
    public String Kick_player_kick_player = "玩家 [%s:%s] 踢出了玩家 [%s:%s]";
    public String Kick_server_player = "正在踢出玩家 [%s:%s]";

    // Kill
    public String Kill_usage = "用法: killall [玩家ID] [场景ID]";
    public String Kill_scene_not_found_in_player_world = "在玩家世界中找不到该场景。";
    public String Kill_kill_monsters_in_scene = "正在杀死 %s 野怪 在场景 %s";

    // KillCharacter
    public String KillCharacter_usage = "用法: /killcharacter [玩家ID]";
    public String KillCharacter_kill_current_character = "已杀死玩家 %s 当前的角色。";

    // List
    public String List_message = "这里有 %s 个玩家在线:";

    // Permission
    public String Permission_usage = "用法: permission <add|remove> <用户登陆名> <权限节点>";
    public String Permission_add = "权限已添加。";
    public String Permission_have_permission = "他已经有这个权限了！";
    public String Permission_remove = "权限已移除。";
    public String Permission_not_have_permission = "他没有这个权限！";

    // Position
    public String Position_message = "坐标: %。3f, %。3f, %。3f\n场景ID: %d";

    // Reload
    public String Reload_reload_start = "正在重新读取配置文件。";
    public String Reload_reload_done = "重新读取配置文件成功。";

    // ResetConst
    public String ResetConst_reset_all = "重置所有角色的命星。";
    public String ResetConst_reset_all_done = "%s 的命星已经重置。 请重新登陆查看变化。";

    // ResetShopLimit
    public String ResetShopLimit_usage = "用法: /resetshop <玩家ID>";

    // SendMail
    public String SendMail_usage = "用法: give [用户ID] <物品ID> [数量]";
    public String SendMail_user_not_exist = "ID为 '%s' 的用户不存在";
    public String SendMail_start_composition = "正在开始生成邮件模版。\n请使用 `/sendmail <标题>` 以继续。\n你可以在任何时间使用 `/sendmail stop` 来停止。";
    public String SendMail_templates = "正在生成邮件模板...";
    public String SendMail_invalid_arguments = "错误的参数。\n用法 `/sendmail <用户ID|all|help> [邮件模版ID]`";
    public String SendMail_send_cancel = "邮件发送已取消。";
    public String SendMail_send_done = "邮件已发送给用户 %s!";
    public String SendMail_send_all_done = "邮件已发送给所有用户!";
    public String SendMail_not_composition_end = "邮件生成不在最终阶段。\n请使用 `/sendmail %s` 或者 `/sendmail stop` 来取消。";
    public String SendMail_Please_use = "请使用 `/sendmail %s`";
    public String SendMail_set_title = "邮件标题设置为 '%s'。\n使用 '/sendmail <内容>' 来继续。";
    public String SendMail_set_contents = "邮件内容设置为 '%s'。\n使用 '/sendmail <发送人>' 来继续。";
    public String SendMail_set_message_sender = "邮件签名设置为 '%s'。\n使用 '/sendmail <物品ID|itemName|finish> [数量] [等级]' 来继续。";
    public String SendMail_send = "已添加 %s 个 %s (等级为 %s) 到邮件。\n继续添加更多物品 或 使用 `/sendmail finish` 来发送邮件。";
    public String SendMail_invalid_arguments_please_use = "参数错误 \n 请使用 `/sendmail %s`";
    public String SendMail_title = "<标题>";
    public String SendMail_message = "<内容>";
    public String SendMail_sender = "<签名>";
    public String SendMail_arguments = "<物品ID|itemName|finish> [数量] [等级]";
    public String SendMail_error = "错误: 无效到创建阶段 %s。 检查控制台是否有stacktrace。";

    // SendMessage
    public String SendMessage_usage = "用法: sendmessage <用户ID> <信息内容>";
    public String SenaMessage_message_sent = "信息已发送。";

    // SetFetterLevel
    public String SetFetterLevel_usage = "用法: setfetterlevel <等级>";
    public String SetFetterLevel_fetter_level_must_between_0_and_10 = "好感等级必须是 0-10。";
    public String SetFetterLevel_fetter_set_level = "好感等级已设置为 %s。";
    public String SetFetterLevel_invalid_fetter_level = "错误的好感等级。";

    // SetStats
    public String SetStats_usage = "用法: setstats|stats <属性> <值>";
    public String SetStats_setstats_help_message = "用法: /setstats|stats <hp | mhp | def | atk | em | er | crate | cdmg> <值> 来修改基础属性";
    public String SetStats_stats_help_message = "用法: /stats <epyro | ecryo | ehydro | egeo | edend | eelec | ephys> <amount> for elemental bonus";
    public String SetStats_set_max_hp = "最大血量已设置为 %s。";
    public String SetStats_set_max_hp_error = "错误的最大血量值。";
    public String SetStats_set_hp = "血量已设置为 %s。";
    public String SetStats_set_hp_error = "错误的血量值。";
    public String SetStats_set_def = "防御力已设置为 %s。";
    public String SetStats_set_def_error = "错误的防御力值。";
    public String SetStats_set_atk = "攻击力已设置为 %s。";
    public String SetStats_set_atk_error = "错误的攻击力值。";
    public String SetStats_set_em = "元素精通已设置为 %s。";
    public String SetStats_set_em_error = "错误的元素精通值。";
    public String SetStats_set_er = "元素充能已设置为s%。";
    public String SetStats_set_er_error = "错误的元素充能值。";
    public String SetStats_set_cr = "暴击率已设置为%s%。";
    public String SetStats_set_cr_error = "错误的暴击率值。";
    public String SetStats_set_cd = "暴击伤害已设置为%s%。";
    public String SetStats_set_cd_error = "错误的暴击伤害值。";
    public String SetStats_set_pdb = "火伤已设置为%s%。";
    public String SetStats_set_pdb_error = "错误的火伤值。";
    public String SetStats_set_cdb = "冰伤已设置为%s%。";
    public String SetStats_set_cdb_error = "错误的冰伤值。";
    public String SetStats_set_hdb = "水伤已设置为%s%。";
    public String SetStats_set_hdb_error = "错误的水伤值。";
    public String SetStats_set_adb = "风伤已设置为%s%。";
    public String SetStats_set_adb_error = "错误的风伤值。";
    public String SetStats_set_gdb = "岩伤已设置为%s%。";
    public String SetStats_set_gdb_error = "错误的岩伤值。";
    public String SetStats_set_edb = "电伤已设置为%s%。";
    public String SetStats_set_edb_error = "错误的电伤值。";
    public String SetStats_set_physdb = "物伤已设置为%s%。";
    public String SetStats_set_physdb_error = "错误的物伤值。";
    public String SetStats_set_ddb = "重击伤害设置为%s%。";
    public String SetStats_set_ddb_error = "错误的重击伤害值。";

    // SetWorldLevel
    public String SetWorldLevel_usage = "用法: setworldlevel <世界等级>";
    public String SetWorldLevel_world_level_must_between_0_and_8 = "世界等级必须是 0-8";
    public String SetWorldLevel_set_world_level = "世界等级必须是 %s。";
    public String SetWorldLevel_invalid_world_level = "错误的世界等级。";

    // Spawn
    public String Spawn_usage = "用法: spawn <实体ID> [数量] [等级(仅野怪)]";
    public String Spawn_message = "已生成 %s 个 %s。";

    // Stop
    public String Stop_message = "服务器正在关闭...";

    // Talent
    public String Talent_usage_1 = "设置当前角色的技能等级: /talent set <技能ID> <等级>";
    public String Talent_usage_2 = "另一个设置当前角色的技能等级的方法: /talent <n 或 e 或 q> <值>";
    public String Talent_usage_3 = "获取技能ID: /talent getid";
    public String Talent_lower_16 = "错误的技能等级。 等级必须比16低";
    public String Talent_set_atk = "设置 普攻 等级为 %s。";
    public String Talent_set_e = "设置 E技能 为 %s。";
    public String Talent_set_q = "设置 Q技能 为 %s。";
    public String Talent_invalid_skill_id = "错误的技能ID。";
    public String Talent_set_this = "设置这个技能等级为 %s。";
    public String Talent_invalid_talent_level = "错误的技能等级。";
    public String Talent_normal_attack_id = "普攻 ID为 %s。";
    public String Talent_e_skill_id = "E 技能ID为 %s。";
    public String Talent_q_skill_id = "Q 技能ID为 %s。";

    // TeleportAll
    public String TeleportAll_message = "你只能在 MP 模式中使用此功能。";

    // Teleport
    public String Teleport_usage_server = "用法: /tp @<玩家ID> <x> <y> <z> [场景ID]";
    public String Teleport_usage = "用法: /tp [@<玩家ID>] <x> <y> <z> [场景ID]";
    public String Teleport_specify_player_id = "必须指定玩家id";
    public String Teleport_invalid_position = "错误的坐标。";
    public String Teleport_message = "远程传送 %s 到 %s,%s,%s 在场景 %s 中";

    // Weather
    public String Weather_usage = "用法: weather <天气ID> [气候ID]";
    public String Weather_message = "改变天气为 %s 气候为 %s";
    public String Weather_invalid_id = "错误的ID。";
}
