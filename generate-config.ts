import { writeFileSync } from "fs";

const configToSave = {
    folderStructure: {
        resources: getStringFromEnv("FOLDER_STRUCTURE_RESOURCES", "./resources/"),
        data: getStringFromEnv("FOLDER_STRUCTURE_DATA", "./data/"),
        packets: getStringFromEnv("FOLDER_STRUCTURE_PACKETS", "./packets/"),
        scripts: getStringFromEnv("FOLDER_STRUCTURE_SCRIPTS", "./resources/Scripts/"),
        plugins: getStringFromEnv("FOLDER_STRUCTURE_PLUGINS", "./plugins/"),
    },
    databaseInfo: {
        server: {
            connectionUri: getStringFromEnv("DATABASE_INFO_SERVER_CONNECTION_URI", "mongodb://localhost:27017"),
            collection: getStringFromEnv("DATABASE_INFO_SERVER_COLLECTION", "grasscutter"),
        },
        game: {
            connectionUri: getStringFromEnv("DATABASE_INFO_GAME_CONNECTION_URI", "mongodb://localhost:27017"),
            collection: getStringFromEnv("DATABASE_INFO_GAME_COLLECTION", "grasscutter"),
        },
    },
    language: {
        language: getStringFromEnv("LANGUAGE_LANGUAGE", "en_US"),
        fallback: getStringFromEnv("LANGUAGE_FALLBACK", "en_US"),
        document: getStringFromEnv("LANGUAGE_DOCUMENT", "EN"),
    },
    account: {
        autoCreate: getBoolFromEnv("ACCOUNT_AUTO_CREATE", false),
        EXPERIMENTAL_RealPassword: getBoolFromEnv("ACCOUNT_EXPERIMENTAL_REAL_PASSWORD", false),
        defaultPermissions: getStringArrayFromEnv("ACCOUNT_DEFAULT_PERMISSIONS", []),
        maxPlayer: getIntFromEnv("ACCOUNT_MAX_PLAYER", -1),
    },
    server: {
        debugWhitelist: getStringArrayFromEnv("SERVER_DEBUG_WHITELIST", []),
        debugBlacklist: getStringArrayFromEnv("SERVER_DEBUG_BLACKLIST", []),
        runMode: getStringFromEnv("SERVER_RUN_MODE", "HYBRID"),
        logCommands: getBoolFromEnv("SERVER_LOG_COMMANDS", false),
        http: {
            bindAddress: getStringFromEnv("SERVER_HTTP_BIND_ADDRESS", "0.0.0.0"),
            bindPort: getIntFromEnv("SERVER_HTTP_BIND_PORT", 443),
            accessAddress: getStringFromEnv("SERVER_HTTP_ACCESS_ADDRESS", "127.0.0.1"),
            accessPort: getIntFromEnv("SERVER_HTTP_ACCESS_PORT", 0),
            encryption: {
                useEncryption: getBoolFromEnv("SERVER_HTTP_ENCRYPTION_USE_ENCRYPTION", true),
                useInRouting: getBoolFromEnv("SERVER_HTTP_ENCRYPTION_USE_IN_ROUTING", true),
                keystore: getStringFromEnv("SERVER_HTTP_ENCRYPTION_KEYSTORE", "./keystore.p12"),
                keystorePassword: getStringFromEnv("SERVER_HTTP_ENCRYPTION_KEYSTORE_PASSWORD", "123456"),
            },
            policies: {
                cors: {
                    enabled: getBoolFromEnv("SERVER_HTTP_POLICIES_CORS_ENABLED", false),
                    allowedOrigins: getStringArrayFromEnv("SERVER_HTTP_POLICIES_CORS_ALLOWED_ORIGINS", ["*"]),
                },
            },
            files: {
                indexFile: getStringFromEnv("SERVER_HTTP_FILES_INDEX_FILE", "./index.html"),
                errorFile: getStringFromEnv("SERVER_HTTP_FILES_ERROR_FILE", "./404.html"),
            },
        },
        game: {
            bindAddress: getStringFromEnv("SERVER_GAME_BIND_ADDRESS", "0.0.0.0"),
            bindPort: getIntFromEnv("SERVER_GAME_BIND_PORT", 22102),
            accessAddress: getStringFromEnv("SERVER_GAME_ACCESS_ADDRESS", "127.0.0.1"),
            accessPort: getIntFromEnv("SERVER_GAME_ACCESS_PORT", 0),
            loadEntitiesForPlayerRange: getIntFromEnv("SERVER_GAME_LOAD_ENTITIES_FOR_PLAYER_RANGE", 100),
            enableScriptInBigWorld: getBoolFromEnv("SERVER_GAME_ENABLE_SCRIPT_IN_BIG_WORLD", false),
            enableConsole: getBoolFromEnv("SERVER_GAME_ENABLE_CONSOLE", true),
            kcpInterval: getIntFromEnv("SERVER_GAME_KCP_INTERVAL", 20),
            logPackets: getStringFromEnv("SERVER_GAME_LOG_PACKETS", "NONE"),
            gameOptions: {
                inventoryLimits: {
                    weapons: getIntFromEnv("SERVER_GAME_GAME_OPTIONS_INVENTORY_LIMITS_WEAPONS", 2000),
                    relics: getIntFromEnv("SERVER_GAME_GAME_OPTIONS_INVENTORY_LIMITS_RELICS", 2000),
                    materials: getIntFromEnv("SERVER_GAME_GAME_OPTIONS_INVENTORY_LIMITS_MATERIALS", 2000),
                    furniture: getIntFromEnv("SERVER_GAME_GAME_OPTIONS_INVENTORY_LIMITS_FURNITURE", 2000),
                    all: getIntFromEnv("SERVER_GAME_GAME_OPTIONS_INVENTORY_LIMITS_ALL", 30000),
                },
                avatarLimits: {
                    singlePlayerTeam: getIntFromEnv("SERVER_GAME_GAME_OPTIONS_AVATAR_LIMITS_SINGLE_PLAYER_TEAM", 4),
                    multiplayerTeam: getIntFromEnv("SERVER_GAME_GAME_OPTIONS_AVATAR_LIMITS_MULTIPLAYER_TEAM", 4),
                },
                sceneEntityLimit: getIntFromEnv("SERVER_GAME_GAME_OPTIONS_SCENE_ENTITY_LIMIT", 1000),
                watchGachaConfig: getBoolFromEnv("SERVER_GAME_GAME_OPTIONS_WATCH_GACHA_CONFIG", false),
                enableShopItems: getBoolFromEnv("SERVER_GAME_GAME_OPTIONS_ENABLE_SHOP_ITEMS", true),
                staminaUsage: getBoolFromEnv("SERVER_GAME_GAME_OPTIONS_STAMINA_USAGE", true),
                energyUsage: getBoolFromEnv("SERVER_GAME_GAME_OPTIONS_ENERGY_USAGE", true),
                fishhookTeleport: getBoolFromEnv("SERVER_GAME_GAME_OPTIONS_FISHHOOK_TELEPORT", true),
                resinOptions: {
                    resinUsage: getBoolFromEnv("SERVER_GAME_GAME_OPTIONS_RESIN_OPTIONS_RESIN_USAGE", false),
                    cap: getIntFromEnv("SERVER_GAME_GAME_OPTIONS_RESIN_OPTIONS_CAP", 160),
                    rechargeTime: getIntFromEnv("SERVER_GAME_GAME_OPTIONS_RESIN_OPTIONS_RECHARGE_TIME", 480),
                },
                rates: {
                    adventureExp: getFloatFromEnv("SERVER_GAME_GAME_OPTIONS_RATES_ADVENTURE_EXP", 1.0),
                    mora: getFloatFromEnv("SERVER_GAME_GAME_OPTIONS_RATES_MORA", 1.0),
                    leyLines: getFloatFromEnv("SERVER_GAME_GAME_OPTIONS_RATES_LEY_LINES", 1.0),
                },
            },
            joinOptions: {
                welcomeEmotes: [2007, 1002, 4010],
                welcomeMessage: getStringFromEnv(
                    "SERVER_GAME_JOIN_OPTIONS_WELCOME_MESSAGE",
                    "Welcome to a Grasscutter server."
                ),
                welcomeMail: {
                    title: getStringFromEnv("SERVER_GAME_JOIN_OPTIONS_WELCOME_MAIL_TITLE", "Welcome to Grasscutter!"),
                    content: getStringFromEnv(
                        "SERVER_GAME_JOIN_OPTIONS_WELCOME_MAIL_CONTENT",
                        'Hi there!\r\nFirst of all, welcome to Grasscutter. If you have any issues, please let us know so that Lawnmower can help you! \r\n\r\nCheck out our:\r\n\u003ctype\u003d"browser" text\u003d"Discord" href\u003d"https://discord.gg/T5vZU6UyeG"/\u003e\n'
                    ),
                    sender: getStringFromEnv("SERVER_GAME_JOIN_OPTIONS_WELCOME_MAIL_SENDER", "Lawnmower"),
                    items: getItemsFromEnv("SERVER_GAME_JOIN_OPTIONS_WELCOME_MAIL_ITEMS", [
                        {
                            itemId: 13509,
                            itemCount: 1,
                            itemLevel: 1,
                        },
                        {
                            itemId: 201,
                            itemCount: 99999,
                            itemLevel: 1,
                        },
                    ]),
                },
            },
            serverAccount: {
                avatarId: getIntFromEnv("SERVER_GAME_SERVER_ACCOUNT_AVATAR_ID", 10000007),
                nameCardId: getIntFromEnv("SERVER_GAME_SERVER_ACCOUNT_NAME_CARD_ID", 210001),
                adventureRank: getIntFromEnv("SERVER_GAME_SERVER_ACCOUNT_ADVENTURE_RANK", 1),
                worldLevel: getIntFromEnv("SERVER_GAME_SERVER_ACCOUNT_WORLD_LEVEL", 0),
                nickName: getStringFromEnv("SERVER_GAME_SERVER_ACCOUNT_NICK_NAME", "Server"),
                signature: getStringFromEnv("SERVER_GAME_SERVER_ACCOUNT_SIGNATURE", "Welcome to Grasscutter!"),
            },
        },
        dispatch: {
            regions: getStringArrayFromEnv("SERVER_DISPATCH_REGIONS", []),
            defaultName: getStringFromEnv("SERVER_DISPATCH_DEFAULT_NAME", "Grasscutter"),
            logRequests: getStringFromEnv("SERVER_DISPATCH_LOG_REQUESTS", "NONE"),
        },
    },
    version: 4,
};

writeFileSync("./config.json", JSON.stringify(configToSave, null, 4));

function getStringFromEnv(key: string, defaultValue: string): string {
    return process.env[key] || defaultValue;
}

function getBoolFromEnv(key: string, defaultValue: boolean): boolean {
    switch (process.env[key]) {
        case "true":
        case "on":
        case "1":
            return true;

        case "false":
        case "off":
        case "0":
            return false;

        default:
            return defaultValue;
    }
}

function getIntFromEnv(key: string, defaultValue: number): number {
    const currentValue = process.env[key];

    if (currentValue === undefined || currentValue === null) {
        return defaultValue;
    }

    try {
        return parseInt(currentValue, 10);
    } catch (error) {
        return defaultValue;
    }
}

function getFloatFromEnv(key: string, defaultValue: number): number {
    const currentValue = process.env[key];

    if (currentValue === undefined || currentValue === null) {
        return defaultValue;
    }

    try {
        return parseFloat(currentValue);
    } catch (error) {
        return defaultValue;
    }
}

function getStringArrayFromEnv(key: string, defaultValue: string[], separator: string = ","): string[] {
    const currentValue = process.env[key];

    if (currentValue === undefined || currentValue === null) {
        return defaultValue;
    }

    return currentValue.split(separator);
}

type ItemInfo = {
    itemId: number;
    itemCount: number;
    itemLevel: number;
};

function getItemsFromEnv(key: string, defaultValue: ItemInfo[]): ItemInfo[] {
    const currentValue = process.env[key];

    if (currentValue === undefined || currentValue === null) {
        return defaultValue;
    }

    const parts = currentValue.split("|");

    return parts.map((part: string) => {
        const [rawItemId, rawItemCount, rawItemLevel] = part.split(",");

        return {
            itemId: parseInt(rawItemId, 10),
            itemCount: parseInt(rawItemCount, 10),
            itemLevel: parseInt(rawItemLevel, 10),
        };
    });
}
