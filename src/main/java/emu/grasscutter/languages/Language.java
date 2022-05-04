package emu.grasscutter.languages;

public final class Language {
    public String An_error_occurred_during_game_update = "An error occurred during game update.";
    public String Starting_Grasscutter = "Starting Grasscutter...";
    public String Invalid_server_run_mode = "Invalid server run mode.";
    public String Server_run_mode = "Server run mode must be 'HYBRID', 'DISPATCH_ONLY', or 'GAME_ONLY'. Unable to start Grasscutter...";
    public String Shutting_down = "Shutting down...";
    public String Start_done = "Done! For help, type \"help\"";
    public String Dispatch_mode_not_support_command = "Commands are not supported in dispatch only mode.";
    public String Command_error = "Command error:";
    public String Error = "An error occurred.";
    public String Grasscutter_is_free = "Grasscutter is FREE software. If you have paid for this, you may have been scammed. Homepage: https://github.com/Grasscutters/Grasscutter";
    public String Game_start_port = "Game Server started on port {port}";
    public String Client_connect = "Client connected from {address}";
    public String Client_disconnect = "Client disconnected from {address}";
    public String Client_request = "[Dispatch] Client {ip} {method} request: {url}";
    public String Not_load_keystore = "[Dispatch] Unable to load keystore. Trying default keystore password...";
    public String Use_default_keystore = "[Dispatch] The default keystore password was loaded successfully. Please consider setting the password to 123456 in config.json.";
    public String Load_keystore_error = "[Dispatch] Error while loading keystore!";
    public String Not_find_ssl_cert = "[Dispatch] No SSL cert found! Falling back to HTTP server.";
    public String Welcome = "Welcome to Grasscutter";
    public String Potential_unhandled_request = "[Dispatch] Potential unhandled {method} request: {url}";
    public String Client_try_login = "[Dispatch] Client {ip} is trying to log in";
    public String Client_login_token = "[Dispatch] Client {ip} is trying to log in via token";
    public String Client_token_login_failed = "[Dispatch] Client {ip} failed to log in via token";
    public String Client_login_in_token = "[Dispatch] Client {ip} logged in via token as {uid}";
    public String Game_account_cache_error = "Game account cache information error";
    public String Wrong_session_key = "Wrong session key.";
    public String Client_exchange_combo_token = "[Dispatch] Client {ip} succeed to exchange combo token";
    public String Client_failed_exchange_combo_token = "[Dispatch] Client {ip} failed to exchange combo token";
    public String Dispatch_start_server_port = "[Dispatch] Dispatch server started on port {port}";
    public String Client_failed_login_account_create = "[Dispatch] Client {ip} failed to log in: Account {uid} created";
    public String Client_failed_login_account_create_failed = "[Dispatch] Client {ip} failed to log in: Account create failed";
    public String Client_failed_login_account_no_found = "[Dispatch] Client {ip} failed to log in: Account no found";
    public String Client_login = "[Dispatch] Client {ip} logged in as {uid}";
    public String Username_not_found = "Username not found.";
    public String Username_not_found_create_failed = "Username not found, create failed.";
    public String Create_resources_folder = "Creating resources folder...";
    public String Place_copy = "Place a copy of 'BinOutput' and 'ExcelBinOutput' in the resources folder.";

    // Command
    public String No_command_specified = "No command specified.";
    public String Unknown_command = "Unknown command: ";
    public String You_not_permission_run_command = "You do not have permission to run this command.";
    public String This_command_can_only_run_from_console = "This command can only be run from the console.";
    public String Run_this_command_in_game = "Run this command in-game.";
    public String Invalid_playerId = "Invalid playerId.";
    public String Player_not_found = "Player not found.";
    public String Player_is_offline = "Player is offline.";
    public String Invalid_item_id = "Invalid item id.";
    public String Invalid_item_or_player_id = "Invalid item or player ID.";
    public String Enabled = "enabled";
    public String Disabled = "disabled";
    public String No_command_found = "No command found.";
    public String Help = "Help";
    public String Player_not_found_or_offline = "Player not found or offline.";
    public String Invalid_arguments = "Invalid arguments.";
    public String Success = "Success";
    public String Invalid_entity_id = "Invalid entity id.";

    // Help
    public String Help_usage = "   Usage: ";
    public String Help_aliases = "   Aliases: ";
    public String Help_available_command = "Available commands:";

    // Account
    public String Modify_user_account = "Modify user accounts";
    public String Invalid_UID = "Invalid UID.";
    public String Account_exists = "Account already exists.";
    public String Account_create_UID = "Account created with UID {uid}.";
    public String Account_delete = "Account deleted.";
    public String Account_not_find = "Account not found.";
    public String Account_command_usage = "Usage: account <create|delete> <username> [uid]";

    // Broadcast
    public String Broadcast_command_usage = "Usage: broadcast <message>";
    public String Broadcast_message_sent = "Message sent.";

    // ChangeScene
    public String Change_screen_usage = "Usage: changescene <scene id>";
    public String Change_screen_you_in_that_screen = "You are already in that scene";
    public String Change_screen = "Changed to scene ";
    public String Change_screen_not_exist = "Scene does not exist";

    // Clear
    public String Clear_weapons = "Cleared weapons for {name} .";
    public String Clear_artifacts = "Cleared artifacts for {name} .";
    public String Clear_materials = "Cleared materials for {name} .";
    public String Clear_furniture = "Cleared furniture for {name} .";
    public String Clear_displays = "Cleared displays for {name} .";
    public String Clear_virtuals = "Cleared virtuals for {name} .";
    public String Clear_everything = "Cleared everything for {name} .";

    // Coop
    public String Coop_usage = "Usage: coop <playerId> <target playerId>";

    // Drop
    public String Drop_usage = "Usage: drop <itemId|itemName> [amount]";
    public String Drop_dropped_of = "Dropped {amount} of {item}.";

    // EnterDungeon
    public String EnterDungeon_usage = "Usage: enterdungeon <dungeon id>";
    public String EnterDungeon_changed_to_dungeon = "Changed to dungeon ";
    public String EnterDungeon_dungeon_not_found = "Dungeon does not exist";
    public String EnterDungeon_you_in_that_dungeon = "You are already in that dungeon";

    // GiveAll
    public String GiveAll_usage = "Usage: giveall [player] [amount]";
    public String GiveAll_item = "Giving all items...";
    public String GiveAll_done = "Giving all items done";
    public String GiveAll_invalid_amount_or_playerId = "Invalid amount or player ID.";

    // GiveArtifact
    public String GiveArtifact_usage = "Usage: giveart|gart [player] <artifactId> <mainPropId> [<appendPropId>[,<times>]]... [level]";
    public String GiveArtifact_invalid_artifact_id = "Invalid artifact ID.";
    public String GiveArtifact_given = "Given {itemId} to {target}.";

    // GiveChar
    public String GiveChar_usage = "Usage: givechar <player> <itemId|itemName> [amount]";
    public String GiveChar_given = "Given {avatarId} with level {level} to {target}.";
    public String GiveChar_invalid_avatar_id = "Invalid avatar id.";
    public String GiveChar_invalid_avatar_level = "Invalid avatar level.";
    public String GiveChar_invalid_avatar_or_player_id = "Invalid avatar or player ID.";

    // Give
    public String Give_usage = "Usage: give <player> <itemId|itemName> [amount] [level]";
    public String Give_refinement_only_applicable_weapons = "Refinement is only applicable to weapons.";
    public String Give_refinement_must_between_1_and_5 = "Refinement must be between 1 and 5.";
    public String Give_given = "Given {amount} of {item} to {target}.";
    public String Give_given_with_level_and_refinement = "Given {item} with level {lvl}, refinement {refinement} {amount} times to {target}";
    public String Give_given_level = "Given {item} with level {lvl} {amount} times to {target}";

    // GodMode
    public String Godmode_status = "Godmode is now {status} for {name} .";

    // Heal
    public String Heal_message = "All characters have been healed.";

    // Kick
    public String Kick_player_kick_player = "Player [{sendUid}:{sendName}] has kicked player [{kickUid}:{kickName}]";
    public String Kick_server_player = "Kicking player [{kickUid}:{kickName}]";

    // Kill
    public String Kill_usage = "Usage: killall [playerUid] [sceneId]";
    public String Kill_scene_not_found_in_player_world = "Scene not found in player world";
    public String Kill_kill_monsters_in_scene = "Killing {size} monsters in scene {id}";

    // KillCharacter
    public String KillCharacter_usage = "Usage: /killcharacter [playerId]";
    public String KillCharacter_kill_current_character = "Killed {name} current character.";

    // List
    public String List_message = "There are {size} player(s) online:";

    // Permission
    public String Permission_usage = "Usage: permission <add|remove> <username> <permission>";
    public String Permission_add = "Permission added.";
    public String Permission_have_permission = "They already have this permission!";
    public String Permission_remove = "Permission removed.";
    public String Permission_not_have_permission = "They don't have this permission!";

    // Position
    public String Position_message = "Coord: {x}, {y}, {z}\nScene id: {id}";

    // Reload
    public String Reload_reload_start = "Reloading config.";
    public String Reload_reload_done = "Reload complete.";

    // ResetConst
    public String ResetConst_reset_all = "Reset all avatars' constellations.";
    public String ResetConst_reset_all_done = "Constellations for {name} have been reset. Please relog to see changes.";

    // ResetShopLimit
    public String ResetShopLimit_usage = "Usage: /resetshop <player id>";

    // SendMail
    public String SendMail_usage = "Usage: give [player] <itemId|itemName> [amount]";
    public String SendMail_user_not_exist = "The user with an id of '{id}' does not exist";
    public String SendMail_start_composition = "Starting composition of message.\nPlease use `/sendmail <title>` to continue.\nYou can use `/sendmail stop` at any time";
    public String SendMail_templates = "Mail templates coming soon implemented...";
    public String SendMail_invalid_arguments = "Invalid arguments.\nUsage `/sendmail <userId|all|help> [templateId]`";
    public String SendMail_send_cancel = "Message sending cancelled";
    public String SendMail_send_done = "Message sent to user {name}!";
    public String SendMail_send_all_done = "Message sent to all users!";
    public String SendMail_not_composition_end = "Message composition not at final stage.\nPlease use `/sendmail {args}` or `/sendmail stop` to cancel";
    public String SendMail_please_use = "Please use `/sendmail {args}`";
    public String SendMail_set_title = "Message title set as '{title}'.\nUse '/sendmail <content>' to continue.";
    public String SendMail_set_contents = "Message contents set as '{contents}'.\nUse '/sendmail <sender>' to continue.";
    public String SendMail_set_message_sender = "Message sender set as '{send}'.\nUse '/sendmail <itemId|itemName|finish> [amount] [level]' to continue.";
    public String SendMail_send = "Attached {amount} of {item} (level {lvl}) to the message.\nContinue adding more items or use `/sendmail finish` to send the message.";
    public String SendMail_invalid_arguments_please_use = "Invalid arguments \n Please use `/sendmail {args}`";
    public String SendMail_title = "<title>";
    public String SendMail_message = "<message>";
    public String SendMail_sender = "<sender>";
    public String SendMail_arguments = "<itemId|itemName|finish> [amount] [level]";
    public String SendMail_error = "ERROR: invalid construction stage {stage}. Check console for stacktrace.";

    // SendMessage
    public String SendMessage_usage = "Usage: sendmessage <player> <message>";
    public String SenaMessage_message_sent = "Message sent.";

    // SetFetterLevel
    public String SetFetterLevel_usage = "Usage: setfetterlevel <level>";
    public String SetFetterLevel_fetter_level_must_between_0_and_10 = "Fetter level must be between 0 and 10.";
    public String SetFetterLevel_fetter_set_level = "Fetter level set to {level}";
    public String SetFetterLevel_invalid_fetter_level = "Invalid fetter level.";

    // SetStats
    public String SetStats_usage_console = "Usage: setstats|stats @<UID> <stat> <value>";
    public String SetStats_usage_ingame = "Usage: setstats|stats [@UID] <stat> <value>";
    public String SetStats_help_message = """
        \n\tValues for <stat>: hp | maxhp | def | atk | em | er | crate | cdmg | cdr | heal | heali | shield | defi
        \t(cont.) Elemental DMG Bonus: epyro | ecryo | ehydro | egeo | edendro | eelectro | ephys
        \t(cont.) Elemental RES: respyro | rescryo | reshydro | resgeo | resdendro | reselectro | resphys
        """;
    public String SetStats_value_error = "Invalid stat value.";
    public String SetStats_uid_error = "Invalid UID.";
    public String SetStats_player_error = "Player not found or offline.";
    public String SetStats_set_self = "{name} set to {value}.";
    public String SetStats_set_for_uid = "{name} for {uid} set to {value}.";
    public String Stats_FIGHT_PROP_MAX_HP = "Max HP";
    public String Stats_FIGHT_PROP_CUR_HP = "Current HP";
    public String Stats_FIGHT_PROP_CUR_ATTACK = "ATK";
    public String Stats_FIGHT_PROP_BASE_ATTACK = "Base ATK";
    public String Stats_FIGHT_PROP_DEFENSE = "DEF";
    public String Stats_FIGHT_PROP_ELEMENT_MASTERY = "Elemental Mastery";
    public String Stats_FIGHT_PROP_CHARGE_EFFICIENCY = "Energy Recharge";
    public String Stats_FIGHT_PROP_CRITICAL = "Crit Rate";
    public String Stats_FIGHT_PROP_CRITICAL_HURT = "Crit DMG";
    public String Stats_FIGHT_PROP_ADD_HURT = "DMG Bonus";
    public String Stats_FIGHT_PROP_WIND_ADD_HURT = "Anemo DMG Bonus";
    public String Stats_FIGHT_PROP_ICE_ADD_HURT = "Cryo DMG Bonus";
    public String Stats_FIGHT_PROP_GRASS_ADD_HURT = "Dendro DMG Bonus";
    public String Stats_FIGHT_PROP_ELEC_ADD_HURT = "Electro DMG Bonus";
    public String Stats_FIGHT_PROP_ROCK_ADD_HURT = "Geo DMG Bonus";
    public String Stats_FIGHT_PROP_WATER_ADD_HURT = "Hydro DMG Bonus";
    public String Stats_FIGHT_PROP_FIRE_ADD_HURT = "Pyro DMG Bonus";
    public String Stats_FIGHT_PROP_PHYSICAL_ADD_HURT = "Physical DMG Bonus";
    public String Stats_FIGHT_PROP_SUB_HURT = "DMG Reduction";
    public String Stats_FIGHT_PROP_WIND_SUB_HURT = "Anemo RES";
    public String Stats_FIGHT_PROP_ICE_SUB_HURT = "Cryo RES";
    public String Stats_FIGHT_PROP_GRASS_SUB_HURT = "Dendro RES";
    public String Stats_FIGHT_PROP_ELEC_SUB_HURT = "Electro RES";
    public String Stats_FIGHT_PROP_ROCK_SUB_HURT = "Geo RES";
    public String Stats_FIGHT_PROP_WATER_SUB_HURT = "Hydro RES";
    public String Stats_FIGHT_PROP_FIRE_SUB_HURT = "Pyro RES";
    public String Stats_FIGHT_PROP_PHYSICAL_SUB_HURT = "Physical RES";
    public String Stats_FIGHT_PROP_SKILL_CD_MINUS_RATIO = "Cooldown Reduction";
    public String Stats_FIGHT_PROP_HEAL_ADD = "Healing Bonus";
    public String Stats_FIGHT_PROP_HEALED_ADD = "Incoming Healing Bonus";
    public String Stats_FIGHT_PROP_SHIELD_COST_MINUS_RATIO = "Shield Strength";
    public String Stats_FIGHT_PROP_DEFENCE_IGNORE_RATIO = "DEF Ignore";

    // SetWorldLevel
    public String SetWorldLevel_usage = "Usage: setworldlevel <level>";
    public String SetWorldLevel_world_level_must_between_0_and_8 = "World level must be between 0-8";
    public String SetWorldLevel_set_world_level = "World level set to {level}.";
    public String SetWorldLevel_invalid_world_level = "Invalid world level.";

    // Spawn
    public String Spawn_usage = "Usage: spawn <entityId> [amount] [level(monster only)]";
    public String Spawn_message = "Spawned {amount} of {id}.";

    // Stop
    public String Stop_message = "Server shutting down...";

    // Talent
    public String Talent_usage_1 = "To set talent level: /talent set <talentID> <value>";
    public String Talent_usage_2 = "Another way to set talent level: /talent <n or e or q> <value>";
    public String Talent_usage_3 = "To get talent ID: /talent getid";
    public String Talent_lower_16 = "Invalid talent level. Level should be lower than 16";
    public String Talent_set_atk = "Set talent Normal ATK to {level}.";
    public String Talent_set_e = "Set talent E to {level}.";
    public String Talent_set_q = "Set talent Q to {level}.";
    public String Talent_invalid_skill_id = "Invalid skill ID.";
    public String Talent_set_this = "Set this talent to {level}.";
    public String Talent_invalid_talent_level = "Invalid talent level.";
    public String Talent_normal_attack_id = "Normal Attack ID {id}.";
    public String Talent_e_skill_id = "E skill ID {id}.";
    public String Talent_q_skill_id = "Q skill ID {id}.";

    // TeleportAll
    public String TeleportAll_message = "You only can use this command in MP mode.";

    // Teleport
    public String Teleport_usage_server = "Usage: /tp @<player id> <x> <y> <z> [scene id]";
    public String Teleport_usage = "Usage: /tp [@<player id>] <x> <y> <z> [scene id]";
    public String Teleport_specify_player_id = "You must specify a player id.";
    public String Teleport_invalid_position = "Invalid position.";
    public String Teleport_message = "Teleported {name} to {x},{y},{z} in scene {id}";

    // Weather
    public String Weather_usage = "Usage: weather <weatherId> [climateId]";
    public String Weather_message = "Changed weather to {weatherId} with climate {climateId}";
    public String Weather_invalid_id = "Invalid ID.";
}
