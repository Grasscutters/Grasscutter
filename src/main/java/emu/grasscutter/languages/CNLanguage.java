package emu.grasscutter.languages;

public final class CNLanguage {
    public String An_error_occurred_during_game_update = "游�?更新时�?�生了错误.";
    public String Starting_Grasscutter = "正在开�?�Grasscutter...";
    public String Invalid_server_run_mode = "无效的�?务器�?行模�?. ";
    public String Server_run_mode = "�?务器�?行模�?必须为以下几�?之一: 'HYBRID'(混�?�模�?), 'DISPATCH_ONLY'(仅dispatch模�?), 或 'GAME_ONLY'(仅游�?模�?). 无法�?�动Grasscutter...";
    public String Shutting_down = "正在�?�止....";
    public String Start_done = "加载完�?�?需�?指令帮助请输入 \"help\"";
    public String Dispatch_mode_not_support_command = "仅dispatch模�?无法使用指令。";
    public String Command_error = "命令错误:";
    public String Error = "出现错误.";
    public String Grasscutter_is_free = "Grasscutter是�?费软件，如果你是花钱买到的，你大概被骗了。主页: https://github.com/Grasscutters/Grasscutter";
    public String Game_start_port = "游�?�?务器已在端�?� {port} 上开�?�。";
    public String Client_connect = "�?�自 {address} 的客户端已连接。";
    public String Client_disconnect = "�?�自 {address} 的客户端已断开。";
    public String Client_request = "[Dispatch] 客户端 {ip} 请求:  {method} {url}";
    public String Not_load_keystore = "[Dispatch] 无法加载�?书，正在�?试默认密�?...";
    public String Use_default_keystore = "[Dispatch] �?功使用默认密�?加载�?书. 请考虑将config.json中的KeystorePassword项改为123456.";
    public String Load_keystore_error = "[Dispatch] 加载�?书时出现错误!";
    public String Not_find_ssl_cert = "[Dispatch] 未找到SSL�?书，正在回滚至基本HTTP。";
    public String Welcome = "欢迎使用Grasscutter";
    public String Potential_unhandled_request = "[Dispatch] 潜在的未处�?�请求: {method} {url}";
    public String Client_login_token = "[Dispatch] 客户端 {ip} 正在�?试使用token登录...";
    public String Client_token_login_failed = "[Dispatch] 客户端 {ip} 使用token登录失败。";
    public String Client_login_in_token = "[Dispatch] 客户端 {ip} 使用token以 {uid} 的身份登录。";
    public String Game_account_cache_error = "游�?账户缓存出现错误。";
    public String Wrong_session_key = "会�?密钥错误。";
    public String Client_exchange_combo_token = "[Dispatch] 客户端 {ip} �?功交�?�token。";
    public String Client_failed_exchange_combo_token = "[Dispatch] 客户端 {ip} 交�?�token失败。";
    public String Dispatch_start_server_port = "[Dispatch] Dispatch�?务器已在端�?� {port} 上开�?�。";
    public String Client_failed_login_account_create = "[Dispatch] 客户端 {ip} 登录失败: 已创建UID为 {uid} 的账户。";
    public String Client_failed_login_account_create_failed = "[Dispatch] 客户端 {ip} 登录失败: 创建账户失败。";
    public String Client_failed_login_account_no_found = "[Dispatch] Client {ip} 登录失败: 未找到�?户。";
    public String Client_login = "[Dispatch] 客户端 {ip} 以 {uid} 的身份登录。";
    public String Username_not_found = "未找到此用户�??.";
    public String Username_not_found_create_failed = "未找到此用户�??, 创建失败。.";

    // Command
    public String No_command_specified = "未指定命令.";
    public String Unknown_command = "未知命令: ";
    public String You_not_permission_run_command = "你没有�?��?�?行此命令.";
    public String This_command_can_only_run_from_console = "此命令�?�能在控制�?��?行.";
    public String Run_this_command_in_game = "请在游�?内�?行此命令.";
    public String Invalid_playerId = "无效的玩家ID.";
    public String Player_not_found = "未找到此玩家.";
    public String Player_is_offline = "此玩家已离线.";
    public String Invalid_item_id = "无效的物�?ID.";
    public String Invalid_item_or_player_id = "无效的玩家或物�?ID.";
    public String Enabled = "�?�用";
    public String Disabled = "�?用";
    public String No_command_found = "未找到命令.";
    public String Help = "帮助";
    public String Player_not_found_or_offline = "此玩家�?存在或已离线.";
    public String Invalid_arguments = "无效的�?�数.";
    public String Success = "�?功";
    public String Invalid_entity_id = "无效的实体ID.";

    // Help
    public String Help_usage = "   用法: ";
    public String Help_aliases = "   别�??: ";
    public String Help_available_command = " �?�用命令:";

    // Account
    public String Modify_user_account = "修改用户�?户";
    public String Invalid_UID = "无效的UID.";
    public String Account_exists = "账户已存在.";
    public String Account_create_UID = "UID为 {uid} 的账户已创建.";
    public String Account_delete = "已删除账户.";
    public String Account_not_find = "账户�?存在.";
    public String Account_command_usage = "用法: account <create(创建)|delete(删除)> <用户�??> [uid]";

    // Broadcast
    public String Broadcast_command_usage = "用法: broadcast <消�?�>";
    public String Broadcast_message_sent = "消�?�已�?��?.";

    // ChangeScene
    public String Change_screen_usage = "用法: changescene <场景id>";
    public String Change_screen_you_in_that_screen = "你已�?在此场景中了";
    public String Change_screen = "切�?�到场景 ";
    public String Change_screen_not_exist = "此场景�?存在。";

    // Clear
    public String Clear_weapons = "已清除 {name} 的武器.";
    public String Clear_artifacts = "已清除 {name} 的圣�?�物 .";
    public String Clear_materials = "已清除 {name} 的�??料.";
    public String Clear_furniture = "已清除 {name} 的摆设.";
    public String Clear_displays = "已清除 {name} 的displays.";
    public String Clear_virtuals = "已清除 {name} 的virtuals.";
    public String Clear_everything = "已清除 {name} 的所有物�?.";

    // Coop
    public String Coop_usage = "用法: coop <玩家ID> <房主的玩家ID>";

    // Drop
    public String Drop_usage = "用法: drop <物�?ID|物�?�??> [数�?]";
    public String Drop_dropped_of = "已投掷 {amount} 个 {item}.";

    // EnterDungeon
    public String EnterDungeon_usage = "用法: enterdungeon <副本 id>";
    public String EnterDungeon_changed_to_dungeon = "已进入副本 ";
    public String EnterDungeon_dungeon_not_found = "副本�?存在";
    public String EnterDungeon_you_in_that_dungeon = "你已�?在此副本中了。";

    // GiveAll
    public String GiveAll_usage = "用法: giveall [player] [amount]";
    public String GiveAll_item = "正在给予所有物�?...";
    public String GiveAll_done = "完�?。";
    public String GiveAll_invalid_amount_or_playerId = "无效的数�?或玩家ID";

    // GiveArtifact
    public String GiveArtifact_usage = "用法: giveart|gart [玩家] <圣�?�物Id> <主�?�?�Id> [<副�?�?�Id>[,<被强化次数>]]... [等级]";
    public String GiveArtifact_invalid_artifact_id = "无效的圣�?�物Id.";
    public String GiveArtifact_given = "已将 {itemId} 给予 {target}.";

    // GiveChar
    public String GiveChar_usage = "用法: givechar <p玩家> <角色Id|角色�??> [等级]";
    public String GiveChar_given = "将等级为 {level} 的 {avatarId} 给予 {target}.";
    public String GiveChar_invalid_avatar_id = "无效的角色ID";
    public String GiveChar_invalid_avatar_level = "无效的角色等级.";
    public String GiveChar_invalid_avatar_or_player_id = "无效的角色ID或玩家ID.";

    // Give
    public String Give_usage = "用法: give [玩家�??] <物�?ID|物�?�??> [数�?] [等级] ";
    public String Give_refinement_only_applicable_weapons = "精炼�?�对武器有效。";
    public String Give_refinement_must_between_1_and_5 = "精炼等级必须在1和5之间。";
    public String Give_given = "已将 {amount} 个 {item} 给与 {target}.";
    public String Give_given_with_level_and_refinement = "已将 {amount} 个等级为 {lvl}, 精炼 {refinement} 的 {item} 给予 {target}.";
    public String Give_given_level = "已将 {amount} 个等级为 {lvl} 的 {item} 给与 {target}.";


    // GodMode
    public String Godmode_status = "现已为 {name} {status} 无敌模�? ";

    // Heal
    public String Heal_message = "所有角色已被治疗。";

    // Kick
    public String Kick_player_kick_player = "玩家 [{sendUid}:{sendName}] 已踢出 [{kickUid}:{kickName}]";
    public String Kick_server_player = "正在踢出玩家 [{kickUid}:{kickName}]";

    // Kill
    public String Kill_usage = "用法: killall [玩家UID] [场景ID]";
    public String Kill_scene_not_found_in_player_world = "未在玩家世界中找到此场景";
    public String Kill_kill_monsters_in_scene = "已�?�死场景 {id} 中的 {size} �?�怪物。 ";

    // KillCharacter
    public String KillCharacter_usage = "用法: /killcharacter [玩家Id]";
    public String KillCharacter_kill_current_character = "已干掉 {name} 当�?的场上角色.";

    // List
    public String List_message = "现有 {size} �??玩家在线:";

    // Permission
    public String Permission_usage = "用法: permission <add|remove> <用户�??> <�?��?�??>";
    public String Permission_add = "�?��?已添加。";
    public String Permission_have_permission = "此玩家已拥有此�?��?!";
    public String Permission_remove = "�?��?已移除。";
    public String Permission_not_have_permission = "此玩家未拥有此�?��?!";

    // Position
    public String Position_message = "�??标: %.3f, %.3f, %.3f\n场景: %d";

    // Reload
    public String Reload_reload_start = "正在�?新加载�?置.";
    public String Reload_reload_done = "完�?.";

    // ResetConst
    public String ResetConst_reset_all = "�?置你所有角色的命座。";
    public String ResetConst_reset_all_done = "{name} 的命座已被�?置。请�?新登录。";

    // ResetShopLimit
    public String ResetShopLimit_usage = "用法: /resetshop <玩家id>";

    // SendMail
    public String SendMail_usage = "用法: give [player] <itemId|itemName> [amount]";
    public String SendMail_user_not_exist = "�?存在id为 '{id}' 的用户。";
    public String SendMail_start_composition = "开始编辑邮件的组�?部分.\n请使用 `/sendmail <标题>` 以继续.\n你�?�以在任何时候使用`/sendmail stop` �?��?�止编辑.";
    public String SendMail_templates = "很快就会有邮件模�?�了.......";
    public String SendMail_invalid_arguments = "无效的�?�数.\n用法： `/sendmail <用户Id|all|help> [模�?�Id]``";
    public String SendMail_send_cancel = "已�?�消�?��?邮件。";
    public String SendMail_send_done = "已�?� {name} �?��?邮件!";
    public String SendMail_send_all_done = "已�?�所有玩家�?��?邮件!";
    public String SendMail_not_composition_end = "邮件组�?部分编辑尚未结�?�.\n请使用 `/sendmail {args}` 或 `/sendmail stop` �?��?�止编辑";
    public String SendMail_Please_use = "请使用 `/sendmail {args}`";
    public String SendMail_set_title = "邮件标题已设为 '{title}'.\n使用 '/sendmail <邮件正文>' 以继续.";
    public String SendMail_set_contents = "邮件的正文如下:\n '{contents}'\n使用 '/sendmail <�?��?者署�??>' 以继续.";
	public String SendMail_set_message_sender = "邮件的�?��?者已设为 '{send}'.\n使用 '/sendmail <物�?Id|物�?�??|finish(结�?�编辑并�?��?)> [数�?] [等级]";
    public String SendMail_send = "已将 {amount} 个 {item} (等级 {lvl}) 作为邮件附件.\n你�?�以继续添加附件，也�?�以使用 `/sendmail finish` �?��?�止编辑并�?��?邮件.";
    public String SendMail_invalid_arguments_please_use = "无效的�?�数 \n 请使用 `/sendmail {args}`";
    public String SendMail_title = "<title>";
    public String SendMail_message = "<message>";
    public String SendMail_sender = "<sender>";
    public String SendMail_arguments = "<物�?Id|物�?�??|finish(结�?�编辑并�?��?)> [数�?] [等级]";
	public String SendMail_error = "错误:无效的编写阶段 {stage}. 需�?stacktrace请看�?务器命令行.";

    // SendMessage
    public String SendMessage_usage = "用法: sendmessage <玩家�??> <消�?�>";
    public String SenaMessage_message_sent = "已�?��?.";

    // SetFetterLevel
    public String SetFetterLevel_usage = "用法: setfetterlevel <等级>";
    public String SetFetterLevel_fetter_level_must_between_0_and_10 = "设置的好感等级必须�?于 0 和 10 之间。";
    public String SetFetterLevel_fetter_set_level = "好感等级已设置为 {level}";
    public String SetFetterLevel_invalid_fetter_level = "无效的好感等级。";

    // SetStats
    public String SetStats_usage = "用法: setstats|stats <stat> <value>";
    public String SetStats_setstats_help_message = "用法: /setstats|stats <hp(生命值) | mhp(最大生命值) | def(防御力) | atk(攻击) | em(元素精通) | crate(暴击率) | cdmg(暴击伤害)> <数值> 基本属性(整数)";
    public String SetStats_stats_help_message = "用法: /stats <er(元素充能) | epyro(�?�伤) | ecryo(冰伤) | ehydro(水伤) | eanemo(风伤) | egeo(岩伤) | edend(�?�伤) | eelec(雷伤) | ephys(物伤)> <数值> 元素属性(百分比)";
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
    public String SetStats_set_pdb = "�?�伤设置为 {int}%.";
    public String SetStats_set_pdb_error = "无效的�?�伤数值.";
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
    public String SetStats_set_ddb = "�?�伤设置为 {int}%.";
    public String SetStats_set_ddb_error = "无效的�?�伤数值.";

    // SetWorldLevel
    public String SetWorldLevel_usage = "用法: setworldlevel <level>";
    public String SetWorldLevel_world_level_must_between_0_and_8 = "世界等级必须在0-8之间。";
    public String SetWorldLevel_set_world_level = "世界等级已设置为 {level}.";
    public String SetWorldLevel_invalid_world_level = "无效的世界等级.";

    // Spawn
    public String Spawn_usage = "用法: spawn <实体ID|实体�??> [数�?] [等级(仅�?怪物)]";
    public String Spawn_message = "已生�? {amount} 个 {id}.";

    // Stop
    public String Stop_message = "正在关闭�?务器...";

    // Talent
    public String Talent_usage_1 = "设置技能等级: /talent set <技能ID> <数值>";
    public String Talent_usage_2 = "�?�一�?方�?: /talent <n 或 e 或 q> <数值>";
    public String Talent_usage_3 = "获�?�技能ID: /talent getid";
    public String Talent_lower_16 = "技能等级应低于16。";
    public String Talent_set_atk = "设置普通攻击等级为 {level}.";
    public String Talent_set_e = "设置元素战技(e技能)等级为 {level}.";
    public String Talent_set_q = "设置元素爆�?�(q技能)等级为 {level}.";
    public String Talent_invalid_skill_id = "无效的技能ID。";
    public String Talent_set_this = "技能等级已设为 {level}.";
    public String Talent_invalid_talent_level = "无效的技能等级。";
    public String Talent_normal_attack_id = "普通攻击技能ID {id}.";
    public String Talent_e_skill_id = "元素战技(e技能)ID {id}.";
    public String Talent_q_skill_id = "元素爆�?�(q技能)ID {id}.";

    // TeleportAll
    public String TeleportAll_message = "此命令仅在多人游�?下�?�用。";

    // Teleport
    public String Teleport_usage_server = "用法: /tp @<玩家ID>  <x> <y> <z> [场景ID]";
    public String Teleport_usage = "用法: /tp @<玩家ID，�?指定则为你自己>  <x> <y> <z> [场景ID]";
    public String Teleport_specify_player_id = "你必须指定一个玩家。";
    public String Teleport_invalid_position = "无效的�?置。";
    public String Teleport_message = "已将 {name} 传�?到场景 {id} ，�??标 {x},{y},{z}";

    // Weather
    public String Weather_usage = "用法: weather <天气ID> [气候ID]";
    public String Weather_message = "已修改天气为 {weatherId} 气候为 {climateId}";
    public String Weather_invalid_id = "无效的ID。";
}
