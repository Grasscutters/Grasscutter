package emu.grasscutter.languages;

public final class PTLanguage {
    public String An_error_occurred_during_game_update = "Ocorreu um erro durante a atualização do jogo.";
    public String Starting_Grasscutter = "Iniciando Grasscutter...";
    public String Invalid_server_run_mode = "Modo de execução do servidor inválido.";
    public String Server_run_mode = "Modo de execução do servidor deve ser  \u0027HYBRID\u0027, \u0027DISPATCH_ONLY\u0027, ou \u0027GAME_ONLY\u0027. Não foi possível iniciar Grasscutter...";
    public String Shutting_down = "Desligando...";
    public String Start_done = "Pronto! Para obter ajuda, digite \"help\"";
    public String Dispatch_mode_not_support_command = "Comandos não são suportados no modo DISPATCH_ONLY.";
    public String Command_error = "Erro de comando:";
    public String Error = "Ocorreu um erro.";
    public String Grasscutter_is_free = "Grasscutter é um software GRATUITO. Se você pagou por isso, você pode ter sido enganado. Pagina inicial: https://github.com/Grasscutters/Grasscutter";
    public String Game_start_port = "Game Server iniciado na porta {port}";
    public String Client_connect = "Cliente conectado de {address}";
    public String Client_disconnect = "Cliente desconectado de {address}";
    public String Client_request = "[Dispatch] Cliente {ip} pedido {method}: {url}";
    public String Not_load_keystore = "[Dispatch] Não foi possível carregar o keystore. Tentando a senha padrão do keystore...";
    public String Use_default_keystore = "[Dispatch] A senha do keystore padrão foi carregada com sucesso. Por favor, considere definir a senha para 123456 em config.json";
    public String Load_keystore_error = "[Dispatch] Erro ao carregar keystore!";
    public String Not_find_ssl_cert = "[Dispatch] Nenhum certificado SSL encontrado! Voltando ao servidor HTTP.";
    public String Welcome = "Bem-vindo ao Grasscutter";
    public String Potential_unhandled_request = "[Dispatch] Possível solicitação {method} não processada: {url}";
    public String Client_try_login = "[Dispatch] Cliente {ip} está tentando fazer login";
    public String Client_login_token = "[Dispatch] Cliente {ip} está tentando fazer login via token";
    public String Client_token_login_failed = "[Dispatch] Cliente {ip} falhou ao fazer login via token";
    public String Client_login_in_token = "[Dispatch] Cliente {ip} logado via token como {uid}";
    public String Game_account_cache_error = "Erro nas informações de cache da conta do jogo";
    public String Wrong_session_key = "Chave de sessão errada.";
    public String Client_exchange_combo_token = "[Dispatch] Cliente {ip} conseguiu trocar o token de combinação";
    public String Client_failed_exchange_combo_token = "[Dispatch] Client {ip} falhou a trocar o token de combinação";
    public String Dispatch_start_server_port = "[Dispatch] Dispatch server iniciado na porta {port}";
    public String Client_failed_login_account_create = "[Dispatch] Cliente {ip} falhou ao fazer login: Conta: {uid} criada";
    public String Client_failed_login_account_create_failed = "[Dispatch] Cliente {ip} falhou ao fazer login: falha na criação da conta";
    public String Client_failed_login_account_no_found = "[Dispatch] Cliente {ip} falhou ao fazer login: Conta não encontrada";
    public String Client_login = "[Dispatch] Cliente {ip} logado como {uid}";
    public String Username_not_found = "Nome de usuário não encontrado.";
    public String Username_not_found_create_failed = "Nome de usuário não encontrado, falha na criação.";
    public String Create_resources_folder = "Criando pasta resources...";
    public String Place_copy = "Coloque uma cópia de \u0027BinOutput\u0027 e \u0027ExcelBinOutput\u0027 na pasta resources.";

    // Command
    public String No_command_specified = "Nenhum comando especificado.";
    public String Unknown_command = "Comando desconhecido: ";
    public String You_not_permission_run_command = "Você não tem permissão para executar este comando.";
    public String This_command_can_only_run_from_console = "Este comando só pode ser executado a partir do console.";
    public String Run_this_command_in_game = "Execute este comando no jogo.";
    public String Invalid_amount = "Quantia inválida.";
    public String Invalid_arguments = "Argumentos inválidos.";
    public String Invalid_artifact_id = "ID de artefato inválida.";
    public String Invalid_avatar_id = "ID de avatar inválida.";
    public String Invalid_avatar_level = "Nível de avatar inválido.";
    public String Invalid_entity_id = "ID de entidade inválida.";
    public String Invalid_item_id = "ID de item inválida.";
    public String Invalid_item_level = "Nível de item inválido.";
    public String Invalid_item_refinement = "Nível de refinamento de item inválido.";
    public String Invalid_playerId = "PlayerId inválida.";
    public String Invalid_UID = "UID inválida.";
    public String Player_not_found = "Jogador não encontrado.";
    public String Player_is_offline = "Jogador está offline.";
    public String Enabled = "ativado";
    public String Disabled = "desativado";
    public String No_command_found = "Nenhum comando encontrado.";
    public String Help = "Ajuda";
    public String Player_not_found_or_offline = "Jogador não encontrado ou offline.";
    public String Success = "Successo";
    public String Target_cleared = "Alvo limpo.";
    public String Target_set = "Os comandos subsequentes terão como alvo @{uid} por padrão.";
    public String Target_needed = "Este comando requer um UID de destino. Adicione um argumento \u003c@UID\u003e ou defina um destino persistente com /target @UID.";

    // Help
    public String Help_usage = "   Uso: ";
    public String Help_aliases = "   Pseudônimos: ";
    public String Help_available_command = "Comandos disponíveis:";

    // Account
    public String Modify_user_account = "Modificar contas de usuário";
    public String Account_exists = "Essa conta já existe.";
    public String Account_create_UID = "Conta criada com a UID {uid}.";
    public String Account_delete = "Conta excluída.";
    public String Account_not_find = "Conta não encontrada.";
    public String Account_command_usage = "Uso: account \u003ccreate|delete\u003e \u003cnome de usuário\u003e [uid]";

    // Broadcast
    public String Broadcast_command_usage = "Uso: broadcast \u003cmensagem\u003e";
    public String Broadcast_message_sent = "Mensagem enviada.";

    // ChangeScene
    public String Change_screen_usage = "Uso: changescene \u003cscene id\u003e";
    public String Change_screen_you_in_that_screen = "Você já está nessa cena";
    public String Change_screen = "Mudou para cena ";
    public String Change_screen_not_exist = "Cena inexistente";

    // Clear
    public String Clear_usage = "Uso: clear \u003call|wp|art|mat\u003e";
    public String Clear_weapons = "Armas removidas de {name} .";
    public String Clear_artifacts = "Artefatos removidos de {name} .";
    public String Clear_materials = "Materiais removidos de {name} .";
    public String Clear_furniture = "Mobílias removidas de {name} .";
    public String Clear_displays = "Exibições removidas de {name} .";
    public String Clear_virtuals = "Virtuais removidos de {name} .";
    public String Clear_everything = "Removido tudo de {name} .";

    // Coop
    public String Coop_usage = "Uso: coop \u003chost UID\u003e";
    public String Coop_success = "Invocado {target} para o mundo de {host}";

    // Drop
    public String Drop_usage = "Uso: drop \u003citemId|itemName\u003e [quantia]";
    public String Drop_dropped_of = "Dropou {amount} de {item}.";

    // EnterDungeon
    public String EnterDungeon_usage = "Uso: enterdungeon \u003cdungeon id\u003e";
    public String EnterDungeon_changed_to_dungeon = "Mudou para dungeon ";
    public String EnterDungeon_dungeon_not_found = "Dungeon inexistente";
    public String EnterDungeon_you_in_that_dungeon = "Você já está nessa dungeon";

    // GiveAll
    public String GiveAll_usage = "Uso: giveall [quantia]";
    public String GiveAll_item = "Dando todos os itens...";
    public String GiveAll_done = "Dando todos os itens terminado";

    // GiveArtifact
    public String GiveArtifact_usage = "Uso: giveart|gart [player] \u003cartifactId\u003e \u003cmainPropId\u003e [\u003cappendPropId\u003e[,\u003cvezes\u003e]]... [nível]";
    public String GiveArtifact_given = "Dado {itemId} para {target}.";

    // GiveChar
    public String GiveChar_usage = "Uso: givechar \u003cplayer\u003e \u003citemId|itemName\u003e [quantia]";
    public String GiveChar_given = "Dado {avatarId} com nível {level} para {target}.";

    // Give
    public String Give_usage = "Uso: give \u003cplayer\u003e \u003citemId|itemName\u003e [quantia] [nível]";
    public String Give_refinement_only_applicable_weapons = "Refinamento somente é aplicável a armas.";
    public String Give_refinement_must_between_1_and_5 = "Refinamento deve estar entre 1 e 5.";
    public String Give_given = "Dado {amount} de {item} para {target}.";
    public String Give_given_with_level_and_refinement = "Dado {amount} {item} com nível {lvl}, refinamento {refinement} para {target}";
    public String Give_given_level = "Dado {amount} {item} com nível {lvl} para {target}";

    // GodMode
    public String Godmode_usage = "Uso: godmode [on|off|toggle]";
    public String Godmode_status = "Godmode agora está {status} para {name}.";

    // Heal
    public String Heal_message = "Todos os personagens foram curados.";

    // Kick
    public String Kick_player_kick_player = "Jogador [{sendUid}:{sendName}] expulsou o jogador [{kickUid}:{kickName}]";
    public String Kick_server_player = "Expulsando jogador [{kickUid}:{kickName}]";

    // Kill
    public String Kill_usage = "Uso: killall [playerUid] [sceneId]";
    public String Kill_scene_not_found_in_player_world = "Cena não encontrada no mundo do jogador";
    public String Kill_kill_monsters_in_scene = "Matando {size} monstros na cena {id}";

    // KillCharacter
    public String KillCharacter_usage = "Uso: /killcharacter [playerId]";
    public String KillCharacter_kill_current_character = "Matou o personagem atual de {name}.";

    // List
    public String List_message = "Há {size} jogador(es) online:";

    // Permission
    public String Permission_usage = "Uso: permission \u003cadd|remove\u003e \u003cpermission\u003e";
    public String Permission_add = "Permissão adicionada.";
    public String Permission_have_permission = "Eles já têm essa permissão!";
    public String Permission_remove = "Permissão removida.";
    public String Permission_not_have_permission = "Eles não têm essa permissão!";

    // Position
    public String Position_message = "Coord: {x}, {y}, {z}\nScene id: {id}";

    // Reload
    public String Reload_reload_start = "Recarregando config.";
    public String Reload_reload_done = "Recarregamento concluído.";

    // ResetConst
    public String ResetConst_reset_all = "Reseta todas as constelações de avatares.";
    public String ResetConst_reset_all_done = "Constelações para {name} foram redefinidas. Por favor, relogue para ver as alterações.";

    // ResetShopLimit
    public String ResetShopLimit_usage = "Uso: /resetshop \u003cplayer id\u003e";

    // SendMail
    public String SendMail_usage = "Uso: give [player] \u003citemId|itemName\u003e [quantia]";
    public String SendMail_user_not_exist = "O usuário com o ID \u0027{id}\u0027 não existe";
    public String SendMail_start_composition = "Composição inicial da mensagem.\nPor favor, use `/sendmail \u003ctitle\u003e` para continuar.\nVocê pode usar `/sendmail stop` a qualquer momento";
    public String SendMail_templates = "Modelos de e-mail serão implementados em breve...";
    public String SendMail_invalid_arguments = "Argumentos inválidos.\nUsage `/sendmail \u003cuserId|all|help\u003e [templateId]`";
    public String SendMail_send_cancel = "Envio de mensagem cancelado";
    public String SendMail_send_done = "Mensagem enviada ao usuário {name}!";
    public String SendMail_send_all_done = "Mensagem enviada a todos os usuários!";
    public String SendMail_not_composition_end = "A composição da mensagem não está na fase final.\nPor favor, use `/sendmail {args}` ou `/sendmail stop` para cancelar";
    public String SendMail_please_use = "Por favor, use `/sendmail {args}`";
    public String SendMail_set_title = "Título da mensagem definido como \u0027{title}\u0027.\nUse \u0027/sendmail \u003ccontent\u003e\u0027 para continuar.";
    public String SendMail_set_contents = "Conteúdo da mensagem definido como \u0027{contents}\u0027.\nUse \u0027/sendmail \u003csender\u003e\u0027 para continuar.";
    public String SendMail_set_message_sender = "Remetente da mensagem definido como \u0027{send}\u0027.\nUse \u0027/sendmail \u003citemId|itemName|finish\u003e [quantia] [nível]\u0027 para continuar.";
    public String SendMail_send = "Anexado {amount} de {item} (nível {lvl}) para a mensagem.\nContinue adicionando mais itens ou use `/sendmail finish` para enviar a mensagem..";
    public String SendMail_invalid_arguments_please_use = "Argumentos inválidos \n Por favor, use `/sendmail {args}`";
    public String SendMail_title = "\u003ctítulo\u003e";
    public String SendMail_message = "\u003cmensagem\u003e";
    public String SendMail_sender = "\u003cremetente\u003e";
    public String SendMail_arguments = "\u003citemId|itemName|finish\u003e [quantia] [nível]";
    public String SendMail_error = "ERRO: estágio de construção inválido {stage}. Verifique o console pelo stacktrace.";

    // SendMessage
    public String SendMessage_usage = "Uso: sendmessage \u003cplayer\u003e \u003cmensagem\u003e";
    public String SenaMessage_message_sent = "Mensagem enviada.";

    // SetFetterLevel
    public String SetFetterLevel_usage = "Uso: setfetterlevel \u003clevel\u003e";
    public String SetFetterLevel_fetter_level_must_between_0_and_10 = "O nível de amizade deve estar entre 0 e 10.";

    // SetStats
    public String SetFetterLevel_fetter_set_level = "Nível de amizade definido para {level}";
    public String SetFetterLevel_invalid_fetter_level = "Nível de amizade inválido.";
    public String SetStats_usage_console = "Uso: setstats|stats @\u003cUID\u003e \u003cstat\u003e \u003cvalue\u003e";
    public String SetStats_usage_ingame = "Uso: setstats|stats [@UID] \u003cstat\u003e \u003cvalue\u003e";
    public String SetStats_help_message = "\n\tValores para \u003cstat\u003e: hp | maxhp | def | atk | em | er | crate | cdmg | cdr | heal | heali | shield | defi\n\t(cont.) Bônus de Dano Elemental: epyro | ecryo | ehydro | egeo | edendro | eelectro | ephys\n\t(cont.) Elemental RES: respyro | rescryo | reshydro | resgeo | resdendro | reselectro | resphys\n";
    public String SetStats_value_error = "Valor de status inválido.";
    public String SetStats_set_self = "{name} definido como {value}.";
    public String SetStats_set_for_uid = "{name} para {uid} definido como {value}.";
    public String Stats_FIGHT_PROP_MAX_HP = "HP Máximo";
    public String Stats_FIGHT_PROP_CUR_HP = "HP Atual";
    public String Stats_FIGHT_PROP_CUR_ATTACK = "ATK";
    public String Stats_FIGHT_PROP_BASE_ATTACK = "ATK Base";
    public String Stats_FIGHT_PROP_DEFENSE = "DEF";
    public String Stats_FIGHT_PROP_ELEMENT_MASTERY = "Maestria Elemental";
    public String Stats_FIGHT_PROP_CHARGE_EFFICIENCY = "Recarga de Energia";
    public String Stats_FIGHT_PROP_CRITICAL = "Taxa Crítica";
    public String Stats_FIGHT_PROP_CRITICAL_HURT = "Dano CRIT";
    public String Stats_FIGHT_PROP_ADD_HURT = "Bônus de Dano";
    public String Stats_FIGHT_PROP_WIND_ADD_HURT = "Bônus de Dano Anemo";
    public String Stats_FIGHT_PROP_ICE_ADD_HURT = "Bônus de Dano Cryo";
    public String Stats_FIGHT_PROP_GRASS_ADD_HURT = "Bônus de Dano Dendros";
    public String Stats_FIGHT_PROP_ELEC_ADD_HURT = "Bônus de Dano Electro";
    public String Stats_FIGHT_PROP_ROCK_ADD_HURT = "Bônus de Dano Geo DMG";
    public String Stats_FIGHT_PROP_WATER_ADD_HURT = "Bônus de Dano Hydro";
    public String Stats_FIGHT_PROP_FIRE_ADD_HURT = "Bônus de Dano Pyro";
    public String Stats_FIGHT_PROP_PHYSICAL_ADD_HURT = "Bônus de Dano Físico";
    public String Stats_FIGHT_PROP_SUB_HURT = "Redução de Dano";
    public String Stats_FIGHT_PROP_WIND_SUB_HURT = "RES Anemo";
    public String Stats_FIGHT_PROP_ICE_SUB_HURT = "RES Cryo";
    public String Stats_FIGHT_PROP_GRASS_SUB_HURT = "RES Dendro";
    public String Stats_FIGHT_PROP_ELEC_SUB_HURT = "RES Electro";
    public String Stats_FIGHT_PROP_ROCK_SUB_HURT = "RES Geo";
    public String Stats_FIGHT_PROP_WATER_SUB_HURT = "RES Hydro";
    public String Stats_FIGHT_PROP_FIRE_SUB_HURT = "RES Pyro";
    public String Stats_FIGHT_PROP_PHYSICAL_SUB_HURT = "RES Física";
    public String Stats_FIGHT_PROP_SKILL_CD_MINUS_RATIO = "Redução de Recarga";
    public String Stats_FIGHT_PROP_HEAL_ADD = "Bônus de Cura";
    public String Stats_FIGHT_PROP_HEALED_ADD = "Bônus de Cura Recebida";
    public String Stats_FIGHT_PROP_SHIELD_COST_MINUS_RATIO = "Força do Escudo";
    public String Stats_FIGHT_PROP_DEFENCE_IGNORE_RATIO = "Ignorar DEF";

    // SetWorldLevel
    public String SetWorldLevel_usage = "Uso: setworldlevel \u003clevel\u003e";
    public String SetWorldLevel_world_level_must_between_0_and_8 = "O nível do mundo deve estar entre 0-8";
    public String SetWorldLevel_set_world_level = "Nível do mundo definido para {level}. Por favor, relogue para ver as alterações.";
    public String SetWorldLevel_invalid_world_level = "Nível de mundo inválido.";

    // Spawn
    public String Spawn_usage = "Uso: spawn \u003centityId\u003e [quantia] [nível(monstro apenas)]";
    public String Spawn_message = "Gerado {amount} de {id}.";

    // Stop
    public String Stop_message = "Desligando o servidor...";

    // Talent
    public String Talent_usage_1 = "Para definir o nível de talento: /talent set \u003ctalentID\u003e \u003cvalue\u003e";
    public String Talent_usage_2 = "Outra maneira de definir o nível de talento: /talent \u003cn or e or q\u003e \u003cvalue\u003e";
    public String Talent_usage_3 = "Para obter o ID do talento: /talent getid";
    public String Talent_lower_16 = "Nível de talento inválido. O nível deve ser inferior a 16";
    public String Talent_set_id = "Talento {id} definido para {level}.";
    public String Talent_set_atk = "Talento do Ataque Normal definido para {level}.";
    public String Talent_set_e = "Talento do E definido para {level}.";
    public String Talent_set_q = "Talento do Q definido para {level}.";
    public String Talent_invalid_skill_id = "ID de habilidade inválida.";
    public String Talent_set_this = "Definido este talento para {level}.";
    public String Talent_invalid_talent_level = "Nível de talento inválido.";
    public String Talent_normal_attack_id = "ID do Ataque Normal {id}.";
    public String Talent_e_skill_id = "ID da habilidade E {id}.";
    public String Talent_q_skill_id = "ID da habilidade Q {id}.";

    // TeleportAll
    public String TeleportAll_message = "Você só pode usar este comando no modo multijogador.";

    // Teleport
    public String Teleport_usage = "Uso: /tp \u003cx\u003e \u003cy\u003e \u003cz\u003e [scene id]";
    public String Teleport_invalid_position = "Posição inválida.";
    public String Teleport_message = "Teleportado {name} para {x},{y},{z} na cena {id}";

    // Weather
    public String Weather_usage = "Uso: weather \u003cweatherId\u003e [climateId]";
    public String Weather_message = "Clima alterado para {weatherId} com clima {climateId}";
    public String Weather_invalid_id = "ID Inválida.";
}
