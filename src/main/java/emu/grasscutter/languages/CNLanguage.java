package emu.grasscutter.languages;

public final class CNLanguage {
    public String An_error_occurred_during_game_update = "游戏更新时发生了错误.";
    public String Starting_Grasscutter = "正在开启Grasscutter...";
    public String Invalid_server_run_mode = "无效的服务器运行模式. ";
    public String Server_run_mode = "服务器运行模式必须为以下几种之一: 'HYBRID'(混合模式), 'DISPATCH_ONLY'(仅dispatch模式), 或 'GAME_ONLY'(仅游戏模式). 无法启动Grasscutter...";
    public String Shutting_down = "正在停止....";
    public String Start_done = "加载完成！需要指令帮助请输入 \"help\"";
    public String Dispatch_mode_not_support_command = "仅dispatch模式无法使用指令。";
    public String Command_error = "命令错误:";
    public String Error = "出现错误.";
    public String Grasscutter_is_free = "Grasscutter是免费软件，如果你是花钱买到的，你大概被骗了。主页: https://github.com/Grasscutters/Grasscutter";
    public String Game_start_port = "游戏服务器已在端口 {port} 上开启。";
    public String Client_connect = "来自 {address} 的客户端已连接。";
    public String Client_disconnect = "来自 {address} 的客户端已断开。";
    public String Client_request = "[Dispatch] 客户端 {ip} 请求:  {method} {url}";
    public String Not_load_keystore = "[Dispatch] 无法加载证书，正在尝试默认密码...";
    public String Use_default_keystore = "[Dispatch] 成功使用默认密码加载证书. 请考虑将config.json中的KeystorePassword项改为123456.";
    public String Load_keystore_error = "[Dispatch] 加载证书时出现错误!";
    public String Not_find_ssl_cert = "[Dispatch] 未找到SSL证书，正在回滚至HTTP模式。";
    public String Welcome = "欢迎使用Grasscutter";
    public String Potential_unhandled_request = "[Dispatch] 潜在的未处理请求: {method} {url}";
    public String Client_login_token = "[Dispatch] 客户端 {ip} 正在尝试使用token登录...";
    public String Client_token_login_failed = "[Dispatch] 客户端 {ip} 使用token登录失败。";
    public String Client_login_in_token = "[Dispatch] 客户端 {ip} 使用token以 {uid} 的身份登录。";
    public String Game_account_cache_error = "游戏账户缓存出现错误。";
    public String Wrong_session_key = "会话密钥错误。";
    public String Client_exchange_combo_token = "[Dispatch] 客户端 {ip} 成功交换token。";
    public String Client_failed_exchange_combo_token = "[Dispatch] 客户端 {ip} 交换token失败。";
    public String Dispatch_start_server_port = "[Dispatch] Dispatch服务器已在端口 {port} 上开启。";
    public String Client_failed_login_account_create = "[Dispatch] 客户端 {ip} 登录失败: 已创建UID为 {uid} 的账户。";
    public String Client_failed_login_account_create_failed = "[Dispatch] 客户端 {ip} 登录失败: 创建账户失败。";
    public String Client_failed_login_account_no_found = "[Dispatch] 客户端 {ip} 登录失败: 未找到帐户。";
    public String Client_login = "[Dispatch] 客户端 {ip} 以 {uid} 的身份登录。";
    public String Username_not_found = "未找到此用户名.";
    public String Username_not_found_create_failed = "未找到此用户名, 创建失败。.";

    // Command
    public String No_command_specified = "未指定命令.";
    public String Unknown_command = "未知命令: ";
    public String You_not_permission_run_command = "你没有权限运行此命令.";
    public String This_command_can_only_run_from_console = "此命令只能在控制台运行.";
    public String Run_this_command_in_game = "请在游戏内运行此命令.";
    public String Invalid_playerId = "无效的玩家ID.";
    public String Player_not_found = "未找到此玩家.";
    public String Player_is_offline = "此玩家已离线.";
    public String Invalid_amount = "无效的数量.";
    public String Invalid_arguments = "无效的命令参数.";
    public String Invalid_artifact_id = "无效的圣遗物ID.";
    public String Invalid_avatar_id = "无效的角色ID.";
    public String Invalid_avatar_level = "无效的角色等级.";
    public String Invalid_entity_id = "无效的物品ID.";
    public String Invalid_item_id = "无效的物品ID.";
    public String Invalid_item_level = "无效的物品等级.";
    public String Invalid_item_refinement = "无效的精炼等级.";
    public String Invalid_UID = "无效的UID.";
    public String Enabled = "启用";
    public String Disabled = "禁用";
    public String No_command_found = "未找到命令.";
    public String Help = "帮助";
    public String Player_not_found_or_offline = "此玩家不存在或已离线.";
    public String Success = "成功";
    public String Target_cleared = "已清除选择目标";
    public String Target_set = "接下来的命令将默认以 @{uid} 为目标。输入命令时不必继续携带UID参数。";
    public String Target_needed = "此命令需要指定一个目标用户. 输入命令时携带 <@UID> 参数或使用 /target @UID 来指定一个默认目标用户.";

    // Help
    public String Help_usage = "   用法: ";
    public String Help_aliases = "   别名: ";
    public String Help_available_command = " 可用命令:";

    // Account
    public String Modify_user_account = "修改用户帐户";
    public String Account_exists = "账户已存在.";
    public String Account_create_UID = "UID为 {uid} 的账户已创建.";
    public String Account_delete = "已删除账户.";
    public String Account_not_find = "账户不存在.";
    public String Account_command_usage = "用法: account <create(创建)|delete(删除)> <用户名> [uid]";

    // Broadcast
    public String Broadcast_command_usage = "用法: broadcast <消息>";
    public String Broadcast_message_sent = "消息已发送.";

    // ChangeScene
    public String Change_screen_usage = "用法: changescene <场景id>";
    public String Change_screen_you_in_that_screen = "你已经在此场景中了";
    public String Change_screen = "切换到场景 ";
    public String Change_screen_not_exist = "此场景不存在。";

    // Cleart_or_playerId
    public String Clear_weapons = "已清除 {name} 的武器.";
    public String Clear_artifacts = "已清除 {name} 的圣遗物 .";
    public String Clear_materials = "已清除 {name} 的材料.";
    public String Clear_furniture = "已清除 {name} 的摆设.";
    public String Clear_displays = "已清除 {name} 的displays.";
    public String Clear_virtuals = "已清除 {name} 的virtuals.";
    public String Clear_everything = "已清除 {name} 的所有物品.";

    // Coop
    public String Coop_usage = "用法: coop <房主的UID>";
    public String Coop_success = "已将{target}拉进{host}的世界.";
    
    // Drop
    public String Drop_usage = "用法: drop <物品ID|物品名> [数量]";
    public String Drop_dropped_of = "已在地上丢弃 {amount} 个 {item}.";

    // EnterDungeon
    public String EnterDungeon_usage = "用法: enterdungeon <副本 id>";
    public String EnterDungeon_changed_to_dungeon = "已进入副本 ";
    public String EnterDungeon_dungeon_not_found = "副本不存在";
    public String EnterDungeon_you_in_that_dungeon = "你已经在此副本中了。";

    // GiveAll
    public String GiveAll_usage = "用法: giveall [数量]";
    public String GiveAll_item = "正在给予所有物品...";
    public String GiveAll_done = "完成。";

    // GiveArtifact
    public String GiveArtifact_usage = "用法: giveart|gart [玩家] <圣遗物Id> <主词条Id> [<副词条Id>[,<被强化次数>]]... [等级]";
    public String GiveArtifact_given = "已将 {itemId} 给予 {target}.";

    // GiveChar
    public String GiveChar_usage = "用法: givechar <p玩家> <角色Id|角色名> [等级]";
    public String GiveChar_given = "将等级为 {level} 的 {avatarId} 给予 {target}.";

    // Give
    public String Give_usage = "用法: give [玩家名] <物品ID|物品名> [数量] [等级] ";
    public String Give_refinement_only_applicable_weapons = "精炼只对武器有效。";
    public String Give_refinement_must_between_1_and_5 = "精炼等级必须在1和5之间。";
    public String Give_given = "已将 {amount} 个 {item} 给与 {target}.";
    public String Give_given_with_level_and_refinement = "已将 {amount} 个等级为 {lvl}, 精炼 {refinement} 的 {item} 给予 {target}.";
    public String Give_given_level = "已将 {amount} 个等级为 {lvl} 的 {item} 给与 {target}.";

    // GodMode
    public String Godmode_usage = "用法: godmode [on|off|toggle]";
    public String Godmode_status = "设置 {name} 的无敌模式为: {status}  ";

    // Heal
    public String Heal_message = "所有角色已被治疗。";

    // Kick
    public String Kick_player_kick_player = "玩家 [{sendUid}:{sendName}] 已踢出 [{kickUid}:{kickName}]";
    public String Kick_server_player = "正在踢出玩家 [{kickUid}:{kickName}]";

    // Kill
    public String Kill_usage = "用法: killall [玩家UID] [场景ID]";
    public String Kill_scene_not_found_in_player_world = "未在玩家世界中找到此场景";
    public String Kill_kill_monsters_in_scene = "已杀死场景 {id} 中的 {size} 只怪物。 ";

    // KillCharacter
    public String KillCharacter_usage = "用法: /killcharacter [玩家Id]";
    public String KillCharacter_kill_current_character = "已干掉 {name} 当前的场上角色.";

    // List
    public String List_message = "现有 {size} 名玩家在线:";

    // Permission
    public String Permission_usage = "用法: permission <add|remove> <权限名>";
    public String Permission_add = "权限已添加。";
    public String Permission_have_permission = "此玩家已拥有此权限!";
    public String Permission_remove = "权限已移除。";
    public String Permission_not_have_permission = "此玩家未拥有此权限!";

    // Position
    public String Position_message = "坐标: {x},{y},{z}\n场景: {id}";

    // Reload
    public String Reload_reload_start = "正在重新加载配置.";
    public String Reload_reload_done = "完成.";

    // ResetConst
    public String ResetConst_reset_all = "重置你所有角色的命座。";
    public String ResetConst_reset_all_done = "{name} 的命座已被重置。请重新登录。";

    // ResetShopLimit
    public String ResetShopLimit_usage = "用法: /resetshop <玩家id>";

    // SendMail
    public String SendMail_usage = "用法: give [player] <itemId|itemName> [amount]";
    public String SendMail_user_not_exist = "不存在id为 '{id}' 的用户。";
    public String SendMail_start_composition = "开始编辑邮件的组成部分.\n请使用 `/sendmail <标题>` 以继续.\n你可以在任何时候使用`/sendmail stop` 来停止编辑.";
    public String SendMail_templates = "很快就会有邮件模板了.......";
    public String SendMail_invalid_arguments = "无效的参数.\n用法： `/sendmail <用户Id|all|help> [模板Id]``";
    public String SendMail_send_cancel = "已取消发送邮件。";
    public String SendMail_send_done = "已向 {name} 发送邮件!";
    public String SendMail_send_all_done = "已向所有玩家发送邮件!";
    public String SendMail_not_composition_end = "邮件组成部分编辑尚未结束.\n请使用 `/sendmail {args}` 或 `/sendmail stop` 来停止编辑";
    public String SendMail_Please_use = "请使用 `/sendmail {args}`";
    public String SendMail_set_title = "邮件标题已设为 '{title}'.\n使用 '/sendmail <邮件正文>' 以继续.";
    public String SendMail_set_contents = "邮件的正文如下:\n '{contents}'\n使用 '/sendmail <发送者署名>' 以继续.";
    public String SendMail_set_message_sender = "邮件的发送者已设为 '{send}'.\n使用 '/sendmail <物品Id|物品名|finish(结束编辑并发送)> [数量] [等级]";
    public String SendMail_send = "已将 {amount} 个 {item} (等级 {lvl}) 作为邮件附件.\n你可以继续添加附件，也可以使用 `/sendmail finish` 来停止编辑并发送邮件.";
    public String SendMail_invalid_arguments_please_use = "无效的参数 \n 请使用 `/sendmail {args}`";
    public String SendMail_title = "<标题>";
    public String SendMail_message = "<正文>";
    public String SendMail_sender = "<发送者>";
    public String SendMail_arguments = "<物品Id|物品名|finish(结束编辑并发送)> [数量] [等级]";
    public String SendMail_error = "错误:无效的编写阶段 {stage}. 需要stacktrace请看服务器命令行.";

    // SendMessage
    public String SendMessage_usage = "用法: sendmessage <玩家名> <消息>";
    public String SenaMessage_message_sent = "已发送.";

    // SetFetterLevel
    public String SetFetterLevel_usage = "用法: setfetterlevel <等级>";
    public String SetFetterLevel_fetter_level_must_between_0_and_10 = "设置的好感等级必须位于 0 和 10 之间。";
    public String SetFetterLevel_fetter_set_level = "好感等级已设置为 {level}";
    public String SetFetterLevel_invalid_fetter_level = "无效的好感等级。";

    // SetStats
    public String SetStats_usage = "用法: setstats|stats <stat> <value>";
    public String SetStats_setstats_help_message = "用法: /setstats|stats <hp(生命值) | mhp(最大生命值) | def(防御力) | atk(攻击) | em(元素精通) | crate(暴击率) | cdmg(暴击伤害)> <数值> 基本属性(整数)";
    public String SetStats_stats_help_message = "用法: /stats <er(元素充能) | epyro(火伤) | ecryo(冰伤) | ehydro(水伤) | eanemo(风伤) | egeo(岩伤) | edend(草伤) | eelec(雷伤) | ephys(物伤)> <数值> 元素属性(百分比)";
    public String SetStats_set_max_hp = "最大生命值已设为 {int}.";
    public String SetStats_set_max_hp_error = "无效的生命数值.";
    public String SetStats_set_hp = "生命设置为 {int}.";
    public String SetStats_set_hp_error = "无效的生命数值.";
    public String SetStats_set_def = "防御力设置为 {int}.";
    public String SetStats_set_def_error = "无效的防御力数值.";
    public String SetStats_set_atk = "攻击力设置为 {int}.";
    public String SetStats_set_atk_error = "无效的攻击力数值.";
    public String SetStats_set_em = "元素精通设置为 {int}.";
    public String SetStats_set_em_error = "无效的元素精通数值.";
    public String SetStats_set_er = "元素充能设置为 {int}%.";
    public String SetStats_set_er_error = "无效的元素充能数值.";
    public String SetStats_set_cr = "暴击率设置为 {int}%.";
    public String SetStats_set_cr_error = "无效的暴击率数值.";
    public String SetStats_set_cd = "暴击伤害设置为 {int}%.";
    public String SetStats_set_cd_error = "无效的暴击伤害数值.";
    public String SetStats_set_pdb = "火伤设置为 {int}%.";
    public String SetStats_set_pdb_error = "无效的火伤数值.";
    public String SetStats_set_cdb = "冰伤设置为 {int}%.";
    public String SetStats_set_cdb_error = "无效的冰伤数值.";
    public String SetStats_set_hdb = "水伤设置为 {int}%.";
    public String SetStats_set_hdb_error = "无效的水伤数值.";
    public String SetStats_set_adb = "风伤设置为 {int}%.";
    public String SetStats_set_adb_error = "无效的风伤数值.";
    public String SetStats_set_gdb = "岩伤设置为 {int}%.";
    public String SetStats_set_gdb_error = "无效的岩伤数值.";
    public String SetStats_set_edb = "雷伤设置为 {int}%.";
    public String SetStats_set_edb_error = "无效的雷伤数值.";
    public String SetStats_set_physdb = "物伤设置为 {int}%.";
    public String SetStats_set_physdb_error = "无效的物伤数值.";
    public String SetStats_set_ddb = "草伤设置为 {int}%.";
    public String SetStats_set_ddb_error = "无效的草伤数值.";

    // SetWorldLevel
    public String SetWorldLevel_usage = "用法: setworldlevel <level>";
    public String SetWorldLevel_world_level_must_between_0_and_8 = "世界等级必须在0-8之间。";
    public String SetWorldLevel_set_world_level = "世界等级已设置为 {level}.";
    public String SetWorldLevel_invalid_world_level = "无效的世界等级.";

    // Spawn
    public String Spawn_usage = "用法: spawn <实体ID|实体名> [数量] [等级(仅限怪物)]";
    public String Spawn_message = "已生成 {amount} 个 {id}.";

    // Stop
    public String Stop_message = "正在关闭服务器...";

    // Talent
    public String Talent_usage_1 = "设置技能等级: /talent set <技能ID> <数值>";
    public String Talent_usage_2 = "另一种方式: /talent <n 或 e 或 q> <数值>";
    public String Talent_usage_3 = "获取技能ID: /talent getid";
    public String Talent_lower_16 = "技能等级应低于16。";
    public String Talent_set_atk = "设置普通攻击等级为 {level}.";
    public String Talent_set_e = "设置元素战技(e技能)等级为 {level}.";
    public String Talent_set_q = "设置元素爆发(q技能)等级为 {level}.";
    public String Talent_invalid_skill_id = "无效的技能ID。";
    public String Talent_set_this = "技能等级已设为 {level}.";
    public String Talent_set_id = "将技能 {id} 的等级设为 {level}.";
    public String Talent_invalid_talent_level = "无效的技能等级。";
    public String Talent_normal_attack_id = "普通攻击技能ID {id}.";
    public String Talent_e_skill_id = "元素战技(e技能)ID {id}.";
    public String Talent_q_skill_id = "元素爆发(q技能)ID {id}.";

    // TeleportAll
    public String TeleportAll_message = "此命令仅在多人游戏下可用。";

    // Teleport
    public String Teleport_usage_server = "用法: /tp  <x> <y> <z> [场景ID]";
    public String Teleport_invalid_position = "无效的位置。";
    public String Teleport_message = "已将 {name} 传送到场景 {id} ，坐标 {x},{y},{z}";

    // Weather
    public String Weather_usage = "用法: weather <天气ID> [气候ID]";
    public String Weather_message = "已修改天气为 {weatherId} 气候为 {climateId}";
    public String Weather_invalid_id = "无效的ID。";
}
