package emu.grasscutter;

public final class Language {
    public String An_error_occurred_during_game_update = "An error occurred during game update.";
    public String Starting_Grasscutter = "Starting Grasscutter...";
    public String Invalid_server_run_mode = "Invalid server run mode.";
    public String Server_run_mode = "Server run mode must be 'HYBRID', 'DISPATCH_ONLY', or 'GAME_ONLY'. Unable to start Grasscutter...";
    public String Shutting_down = "Shutting down...";
    public String Start_done = "Done! For help, type \"help\"";
    public String Dispatch_mode_not_support_command = "Commands are not supported in dispatch only mode.";
    public String Command_error = "Command error:";
    public String error = "An error occurred.";
    public String grasscutter_is_free = "Grasscutter is FREE software. If you have paid for this, you may have been scammed. Homepage: https://github.com/Grasscutters/Grasscutter";
    public String Game_start_port = "Game Server started on port %s";
    public String Client_connect = "Client connected from %s";
    public String Client_disconnect = "Client disconnected from %s";
    public String Client_request = "[Dispatch] Client %s %s request: %s";
    public String Not_load_keystore = "[Dispatch] Unable to load keystore. Trying default keystore password...";
    public String Use_default_keystore = "[Dispatch] The default keystore password was loaded successfully. Please consider setting the password to 123456 in config.json.";
    public String Load_keystore_error = "[Dispatch] Error while loading keystore!";
    public String Not_find_ssl_cert = "[Dispatch] No SSL cert found! Falling back to HTTP server.";
    public String Welcome = "Welcome to Grasscutter";
    public String Potential_unhandled_request = "[Dispatch] Potential unhandled %s request: %s";
    public String Client_login_token = "[Dispatch] Client %s is trying to log in via token";
    public String Client_token_login_failed = "[Dispatch] Client %s failed to log in via token";
    public String Client_login_in_token = "[Dispatch] Client %s logged in via token as %s";
    public String Game_account_cache_error = "Game account cache information error";
    public String Wrong_session_key = "Wrong session key.";
    public String Client_exchange_combo_token = "[Dispatch] Client %s succeed to exchange combo token";
    public String Client_failed_exchange_combo_token = "[Dispatch] Client %s failed to exchange combo token";
    public String Dispatch_start_server_port = "[Dispatch] Dispatch server started on port %s";
    public String Client_failed_login_account_create = "[Dispatch] Client %s failed to log in: Account %s created";
    public String Client_failed_login_account_create_failed = "[Dispatch] Client %s failed to log in: Account create failed";
    public String Client_failed_login_account_no_found = "[Dispatch] Client %s failed to log in: Account no found";
    public String Client_login = "[Dispatch] Client %s logged in as %s";
    public String Username_not_found = "Username not found.";
    public String Username_not_found_create_failed = "Username not found, create failed.";

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
    public String Account_create_UID = "Account created with UID %s.";
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
    public String Clear_weapons = "Cleared weapons for %s .";
    public String Clear_artifacts = "Cleared artifacts for %s .";
    public String Clear_materials = "Cleared materials for %s .";
    public String Clear_furniture = "Cleared furniture for %s .";
    public String Clear_displays = "Cleared displays for %s .";
    public String Clear_virtuals = "Cleared virtuals for %s .";
    public String Clear_everything = "Cleared everything for %s .";

    // Coop
    public String Coop_usage = "Usage: coop <playerId> <target playerId>";

    // Drop
    public String Drop_usage = "Usage: drop <itemId|itemName> [amount]";
    public String Drop_dropped_of = "Dropped %s of %s.";

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
    public String GiveArtifact_given = "Given %s to %s.";

    // GiveChar
    public String GiveChar_usage = "Usage: givechar <player> <itemId|itemName> [amount]";
    public String GiveChar_given = "Given %s with level %s to %s.";
    public String GiveChar_invalid_avatar_id = "Invalid avatar id.";
    public String GiveChar_invalid_avatar_level = "Invalid avatar level.";
    public String GiveChar_invalid_avatar_or_player_id = "Invalid avatar or player ID.";

    // Give
    public String Give_usage = "Usage: give <player> <itemId|itemName> [amount] [level]";
    public String Give_refinement_only_applicable_weapons = "Refinement is only applicable to weapons.";
    public String Give_refinement_must_between_1_and_5 = "Refinement must be between 1 and 5.";
    public String Give_given = "Given %s of %s to %s.";
    public String Give_given_with_level_and_refinement = "Given %s with level %s, refinement %s %s times to %s";
    public String Give_given_level = "Given %s with level %s %s times to %s";

    // GodMode
    public String Godmode_status = "Godmode is now %s for %s .";

    // Heal
    public String Heal_message = "All characters have been healed.";

    // Kick
    public String Kick_player_kick_player = "Player [%s:%s] has kicked player [%s:%s]";
    public String Kick_server_player = "Kicking player [%s:%s]";

    // Kill
    public String Kill_usage = "Usage: killall [playerUid] [sceneId]";
    public String Kill_scene_not_found_in_player_world = "Scene not found in player world";
    public String Kill_kill_monsters_in_scene = "Killing %s monsters in scene %s";

    // KillCharacter
    public String KillCharacter_usage = "Usage: /killcharacter [playerId]";
    public String KillCharacter_kill_current_character = "Killed %s current character.";

    // List
    public String List_message = "There are %s player(s) online:";

    // Permission
    public String Permission_usage = "Usage: permission <add|remove> <username> <permission>";
    public String Permission_add = "Permission added.";
    public String Permission_have_permission = "They already have this permission!";
    public String Permission_remove = "Permission removed.";
    public String Permission_not_have_permission = "They don't have this permission!";

    // Position
    public String Position_message = "Coord: %.3f, %.3f, %.3f\nScene id: %d";

    // Reload
    public String Reload_reload_start = "Reloading config.";
    public String Reload_reload_done = "Reload complete.";

    // ResetConst
    public String ResetConst_reset_all = "Reset all avatars' constellations.";
    public String ResetConst_reset_all_done = "Constellations for %s have been reset. Please relog to see changes.";

    // ResetShopLimit
    public String ResetShopLimit_usage = "Usage: /resetshop <player id>";

    // SendMail
    public String SendMail_usage = "Usage: give [player] <itemId|itemName> [amount]";
    public String SendMail_user_not_exist = "The user with an id of '%s' does not exist";
    public String SendMail_start_composition = "Starting composition of message.\nPlease use `/sendmail <title>` to continue.\nYou can use `/sendmail stop` at any time";
    public String SendMail_templates = "Mail templates coming soon implemented...";
    public String SendMail_invalid_arguments = "Invalid arguments.\nUsage `/sendmail <userId|all|help> [templateId]`";
    public String SendMail_send_cancel = "Message sending cancelled";
    public String SendMail_send_done = "Message sent to user %s!";
    public String SendMail_send_all_done = "Message sent to all users!";
    public String SendMail_not_composition_end = "Message composition not at final stage.\nPlease use `/sendmail %s` or `/sendmail stop` to cancel";
    public String SendMail_Please_use = "Please use `/sendmail %s`";
    public String SendMail_set_title = "Message title set as '%s'.\nUse '/sendmail <content>' to continue.";
    public String SendMail_set_contents = "Message contents set as '%s'.\nUse '/sendmail <sender>' to continue.";
    public String SendMail_set_message_sender = "Message sender set as '%s'.\nUse '/sendmail <itemId|itemName|finish> [amount] [level]' to continue.";
    public String SendMail_send = "Attached %s of %s (level %s) to the message.\nContinue adding more items or use `/sendmail finish` to send the message.";
    public String SendMail_invalid_arguments_please_use = "Invalid arguments \n Please use `/sendmail %s`";
    public String SendMail_title = "<title>";
    public String SendMail_message = "<message>";
    public String SendMail_sender = "<sender>";
    public String SendMail_arguments = "<itemId|itemName|finish> [amount] [level]";
    public String SendMail_error = "ERROR: invalid construction stage %s. Check console for stacktrace.";

    // SendMessage
    public String SendMessage_usage = "Usage: sendmessage <player> <message>";
    public String SenaMessage_message_sent = "Message sent.";

    // SetFetterLevel
    public String SetFetterLevel_usage = "Usage: setfetterlevel <level>";
    public String SetFetterLevel_fetter_level_must_between_0_and_10 = "Fetter level must be between 0 and 10.";
    public String SetFetterLevel_fetter_set_level = "Fetter level set to %s";
    public String SetFetterLevel_invalid_fetter_level = "Invalid fetter level.";

    // SetStats
    public String SetStats_usage = "Usage: setstats|stats <stat> <value>";
    public String SetStats_setstats_help_message = "Usage: /setstats|stats <hp | mhp | def | atk | em | er | crate | cdmg> <value> for basic stats";
    public String SetStats_stats_help_message = "Usage: /stats <epyro | ecryo | ehydro | egeo | edend | eelec | ephys> <amount> for elemental bonus";
    public String SetStats_set_max_hp = "MAX HP set to %s.";
    public String SetStats_set_max_hp_error = "Invalid Max HP value.";
    public String SetStats_set_hp = "HP set to %s.";
    public String SetStats_set_hp_error = "Invalid HP value.";
    public String SetStats_set_def = "DEF set to %s.";
    public String SetStats_set_def_error = "Invalid DEF value.";
    public String SetStats_set_atk = "ATK set to %s.";
    public String SetStats_set_atk_error = "Invalid ATK value.";
    public String SetStats_set_em = "Elemental Mastery set to %s.";
    public String SetStats_set_em_error = "Invalid EM value.";
    public String SetStats_set_er = "Energy recharge set to %s%.";
    public String SetStats_set_er_error = "Invalid ER value.";
    public String SetStats_set_cr = "Crit Rate set to %s%.";
    public String SetStats_set_cr_error = "Invalid Crit Rate value.";
    public String SetStats_set_cd = "Crit DMG set to %s%.";
    public String SetStats_set_cd_error = "Invalid Crit DMG value.";
    public String SetStats_set_pdb = "Pyro DMG Bonus set to %s%.";
    public String SetStats_set_pdb_error = "Invalid Pyro DMG Bonus value.";
    public String SetStats_set_cdb = "Cyro DMG Bonus set to %s%.";
    public String SetStats_set_cdb_error = "Invalid Cyro DMG Bonus value.";
    public String SetStats_set_hdb = "Hydro DMG Bonus set to %s%.";
    public String SetStats_set_hdb_error = "Invalid Hydro DMG Bonus value.";
    public String SetStats_set_adb = "Anemo DMG Bonus set to %s%.";
    public String SetStats_set_adb_error = "Invalid Anemo DMG Bonus value.";
    public String SetStats_set_gdb = "Geo DMG Bonus set to %s%.";
    public String SetStats_set_gdb_error = "Invalid Geo DMG Bonus value.";
    public String SetStats_set_edb = "Electro DMG Bonus set to %s%.";
    public String SetStats_set_edb_error = "Invalid Electro DMG Bonus value.";
    public String SetStats_set_physdb = "Physical DMG Bonus set to %s%.";
    public String SetStats_set_physdb_error = "Invalid Physical DMG Bonus value.";
    public String SetStats_set_ddb = "Dendro DMG Bonus set to %s%.";
    public String SetStats_set_ddb_error = "Invalid Dendro DMG Bonus value.";

    // SetWorldLevel
    public String SetWorldLevel_usage = "Usage: setworldlevel <level>";
    public String SetWorldLevel_world_level_must_between_0_and_8 = "World level must be between 0-8";
    public String SetWorldLevel_set_world_level = "World level set to %s.";
    public String SetWorldLevel_invalid_world_level = "Invalid world level.";

    // Spawn
    public String Spawn_usage = "Usage: spawn <entityId> [amount] [level(monster only)]";
    public String Spawn_message = "Spawned %s of %s.";

    // Stop
    public String Stop_message = "Server shutting down...";

    // Talent
    public String Talent_usage_1 = "To set talent level: /talent set <talentID> <value>";
    public String Talent_usage_2 = "Another way to set talent level: /talent <n or e or q> <value>";
    public String Talent_usage_3 = "To get talent ID: /talent getid";
    public String Talent_lower_16 = "Invalid talent level. Level should be lower than 16";
    public String Talent_set_atk = "Set talent Normal ATK to %s.";
    public String Talent_set_e = "Set talent E to %s.";
    public String Talent_set_q = "Set talent Q to %s.";
    public String Talent_invalid_skill_id = "Invalid skill ID.";
    public String Talent_set_this = "Set this talent to %s.";
    public String Talent_invalid_talent_level = "Invalid talent level.";
    public String Talent_normal_attack_id = "Normal Attack ID %s.";
    public String Talent_e_skill_id = "E skill ID %s.";
    public String Talent_q_skill_id = "Q skill ID %s.";

    // TeleportAll
    public String TeleportAll_message = "You only can use this command in MP mode.";

    // Teleport
    public String Teleport_usage_server = "Usage: /tp @<player id> <x> <y> <z> [scene id]";
    public String Teleport_usage = "Usage: /tp [@<player id>] <x> <y> <z> [scene id]";
    public String Teleport_specify_player_id = "You must specify a player id.";
    public String Teleport_invalid_position = "Invalid position.";
    public String Teleport_message = "Teleported %s to %s,%s,%s in scene %s";

    // Weather
    public String Weather_usage = "Usage: weather <weatherId> [climateId]";
    public String Weather_message = "Changed weather to %s with climate %s";
    public String Weather_invalid_id = "Invalid ID.";
}
