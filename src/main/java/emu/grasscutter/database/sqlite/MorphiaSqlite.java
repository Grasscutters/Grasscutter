package emu.grasscutter.database.sqlite;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.PostLoad;
import dev.morphia.annotations.Transient;
import dev.morphia.mapping.Mapper;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.activity.PlayerActivityData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.battlepass.BattlePassManager;
import emu.grasscutter.game.battlepass.BattlePassMission;
import emu.grasscutter.game.battlepass.BattlePassReward;
import emu.grasscutter.game.expedition.ExpeditionInfo;
import emu.grasscutter.game.home.FurnitureMakeSlotItem;
import emu.grasscutter.game.home.GameHome;
import emu.grasscutter.game.home.HomeSceneItem;
import emu.grasscutter.game.mail.Mail;
import emu.grasscutter.game.managers.forging.ActiveForgeData;
import emu.grasscutter.game.managers.mapmark.MapMark;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.player.TeamInfo;
import emu.grasscutter.game.player.TeamManager;
import emu.grasscutter.game.quest.GameMainQuest;
import emu.grasscutter.game.quest.GameQuest;
import emu.grasscutter.game.quest.QuestGroupSuite;
import emu.grasscutter.utils.Position;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static org.reflections.scanners.Scanners.SubTypes;
import static org.reflections.scanners.Scanners.TypesAnnotated;

class MorphiaSqlite {

    private static final Gson gson = new GsonBuilder()
        .setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getAnnotation(Transient.class) != null;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return clazz == ObjectId.class;
            }
        })
        .registerTypeAdapter(TeamManager.class, (JsonDeserializer) (json, typeOfT, context) -> {
            var team = new TeamManager();
            try {
                Field teams = TeamManager.class.getDeclaredField("teams");
                teams.setAccessible(true);
                String teams1 = json.getAsJsonObject().get("teams").toString();
                Type type = new TypeToken<LinkedHashMap<Integer, TeamInfo>>() {
                }.getType();
                teams.set(team, new Gson().fromJson(teams1, type));
                Field currentTeamIndex = TeamManager.class.getDeclaredField("currentTeamIndex");
                currentTeamIndex.setAccessible(true);
                currentTeamIndex.set(team, json.getAsJsonObject().get("currentTeamIndex").getAsInt());
                team.setCurrentCharacterIndex(json.getAsJsonObject().get("currentCharacterIndex").getAsInt());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return team;
        })
        .create();

    private static Connection connection = null;

    public static void connect(String url) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection(url);
    }

    public static Set<Class<?>> scanPackageEntity(String root) {
        var reflections = new Reflections(root);
        Set<Class<?>> classes = reflections.get(SubTypes.of(TypesAnnotated.with(Entity.class)).asClass());
        return classes.stream().filter((cls) -> {
            Entity annotation = cls.getAnnotation(Entity.class);
            return !annotation.value().equals(Mapper.IGNORED_FIELDNAME);
        }).collect(Collectors.toSet());
    }

    private static String createTableSQL(Class<?> cls) {
        Set<Field> fieldSet = filterFields(cls.getDeclaredFields());
        var sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        sb.append(cls.getAnnotation(Entity.class).value());
        sb.append("(\n");
        fieldSet.forEach((it) -> {
            try {
                it.setAccessible(true);
                Class<?> type = it.getType();
                String sqlType;
                if (type == int.class ||
                    type == Integer.class ||
                    type == long.class ||
                    type == Long.class
                ) {
                    sqlType = "INTEGER";
                } else if (type == float.class ||
                    type == Float.class ||
                    type == double.class ||
                    type == Double.class
                ) {
                    sqlType = "REAL";
                } else if (type == boolean.class ||
                    type == Boolean.class) {
                    sqlType = "BOOLEAN";
                } else {
                    sqlType = "TEXT";
                }
                if (it.isAnnotationPresent(Id.class)) {
                    if (it.isAnnotationPresent(AutoIncrease.class)) {
                        sb.append("id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n");
                    } else {
                        sb.append("id ").append(sqlType).append(" PRIMARY KEY NOT NULL,\n");
                    }
                } else {
                    sb.append(it.getName()).append(" ").append(sqlType).append(",\n");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        sb.deleteCharAt(sb.length() - 2); // delete last ,
        sb.append(")");
        return sb.toString();
    }

    public static boolean createTable(Class<?> cls) throws SQLException {
        return exec(createTableSQL(cls));
    }

    private static <T> String updateSQL(T entity, String condition) {
        Class<?> cls = entity.getClass();
        Set<Field> fieldSet = filterFields(cls.getDeclaredFields());
        var sb = new StringBuilder("UPDATE ");
        sb.append(cls.getAnnotation(Entity.class).value());
        sb.append(" SET \n");
        fieldSet.forEach((it) -> {
            try {
                it.setAccessible(true);
                if (!it.isAnnotationPresent(AutoIncrease.class)) {
                    sb.append(it.getName()).append("=");
                    Class<?> type = it.getType();
                    Object src = it.get(entity);
                    if (src == null) {
                        sb.append("null");
                    } else if (type == int.class ||
                        type == Integer.class ||
                        type == long.class ||
                        type == Long.class ||
                        type == float.class ||
                        type == Float.class ||
                        type == double.class ||
                        type == Double.class ||
                        type == boolean.class ||
                        type == Boolean.class) {
                        sb.append(src);
                    } else if (type == String.class) {
                        var str = (String) src;
                        sb.append(wrapString(str));
                    } else if (type == ObjectId.class) {
                        sb.append(wrapString(src.toString()));
                    } else {
                        String json = gson.toJson(src);
                        sb.append(wrapString(json));
                    }
                    sb.append(", ");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        sb.deleteCharAt(sb.length() - 2); // delete last ,
        sb.append(" WHERE ").append(condition);
        return sb.toString();
    }

    private static <T> String insertSQL(T entity) {
        Class<?> cls = entity.getClass();
        Set<Field> fieldSet = filterFields(cls.getDeclaredFields());
        var sb = new StringBuilder("INSERT INTO ");
        sb.append(cls.getAnnotation(Entity.class).value());
        sb.append("(\n");
        var realData = new ArrayList<String>();
        fieldSet.forEach((it) -> {
            try {
                it.setAccessible(true);
                if (!it.isAnnotationPresent(AutoIncrease.class)) {
                    sb.append(it.getName()).append(", ");
                    Class<?> type = it.getType();
                    Object src = it.get(entity);
                    if (src == null) {
                        realData.add(null);
                    } else if (type == int.class ||
                        type == Integer.class ||
                        type == long.class ||
                        type == Long.class ||
                        type == float.class ||
                        type == Float.class ||
                        type == double.class ||
                        type == Double.class ||
                        type == boolean.class ||
                        type == Boolean.class) {
                        realData.add(String.valueOf(src));
                    } else if (type == String.class) {
                        var str = (String) src;
                        realData.add(wrapString(str));
                    } else if (type == ObjectId.class) {
                        realData.add(wrapString(src.toString()));
                    } else {
                        try {
                            String json = gson.toJson(src);
                            realData.add(wrapString(json));
                        } catch (Exception e) {
//                            e.printStackTrace();
                            System.out.println("gson: " + src.getClass().toString() + src);
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        sb.deleteCharAt(sb.length() - 2); // delete last ,
        sb.append(") VALUES (");
        realData.forEach((it) -> {
            sb.append(it).append(", ");
        });
        sb.deleteCharAt(sb.length() - 2); // delete last ,
        sb.append(")");
        return sb.toString();
    }

    public static <T> boolean insertOrUpdate(T entity) {
        try {
            var cls = entity.getClass();
            Field idField = cls.getDeclaredField("id");
            idField.setAccessible(true);
            Object id = idField.get(entity);
            if (id == null || "null".equals(id) || query(cls, "id = " + wrapString(id.toString()), true).isEmpty()) {
                if (id == null && idField.getType() == ObjectId.class) {
                    idField.set(entity, new ObjectId());
                }
                return exec(insertSQL(entity));
            } else {
                return exec(updateSQL(entity, "id = " + wrapString(id.toString())));
            }
        } catch (Exception e) {
            Grasscutter.getLogger().error(" sqlite insertOrUpdate failed: ", e);
        }
        return false;
    }

    private static <T> String querySQL(Class<T> cls, String condition) {
        String tName = cls.getAnnotation(Entity.class).value();
        return "SELECT * FROM " + tName + " WHERE " + condition;
    }

    public static <T> List<T> query(Class<T> cls, String condition) {
        return query(cls, condition, false);
    }

    public static <T> List<T> query(Class<T> cls, String condition, boolean ignorePostLoad) {
        List<T> rList = new ArrayList<>();
        String sql = querySQL(cls, condition);
        try {
            Set<Field> fieldSet = filterFields(cls.getDeclaredFields());
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                T instance;
                if (cls == PlayerActivityData.class) {
                    instance = (T) PlayerActivityData.of().build();
                } else {
                    instance = cls.getDeclaredConstructor().newInstance();
                }
                // mock a Document to invoke @PreLoad is useless.
                // gson can handle this automatic
//                if (cls == Account.class) {
//                    Document document = new Document();
//                    ((Account) instance).onLoad(document);
//                }
//                Arrays.stream(cls.getDeclaredMethods())
//                    .filter((it) -> it.isAnnotationPresent(PreLoad.class))
//                    .forEach((it) -> {
//                        try {
//                            it.setAccessible(true);
//                            it.invoke(instance);
//                        } catch (IllegalAccessException | InvocationTargetException e) {
//                            throw new RuntimeException(e);
//                        }
//                    });
                rList.add(instance);
                fieldSet.forEach((it) -> {
                    try {
                        String name = it.getName();
                        Object data = resultSet.getObject(name);
                        var type = it.getType();
                        if (data == null) {
                            // do not modify init data.
                        } else if (type == String.class ||
                            type == Integer.class ||
                            type == Long.class ||
                            type == int.class ||
                            type == long.class
                        ) {
                            // Account.id only
                            if (it.isAnnotationPresent(AutoIncrease.class)) {
                                it.set(instance, String.valueOf(data));
                            } else {
                                it.set(instance, data);
                            }
                        } else if (type == double.class ||
                            type == Double.class) {
                            it.set(instance, ((Number) data).doubleValue());
                        } else if (type == float.class ||
                            type == Float.class) {
                            it.set(instance, ((Number) data).floatValue());
                        } else if (type == Boolean.class ||
                            type == boolean.class) {
                            it.set(instance, ((Integer) data) == 1);
                        } else {
                            Type typeToken = null;
                            if (cls == GameMainQuest.class) {
                                if (name.equals("childQuests")) {
                                    typeToken = new TypeToken<Map<Integer, GameQuest>>() {
                                    }.getType();
                                } else if (name.equals("questGroupSuites")) {
                                    typeToken = new TypeToken<List<QuestGroupSuite>>() {
                                    }.getType();
                                } else if (name.equals("rewindPositions") || name.equals("rewindRotations")) {
                                    typeToken = new TypeToken<Map<Integer, Position>>() {
                                    }.getType();
                                }
                            } else if (cls == BattlePassManager.class) {
                                if (name.equals("missions")) {
                                    typeToken = new TypeToken<Map<Integer, BattlePassMission>>() {
                                    }.getType();
                                } else if (name.equals("takenRewards")) {
                                    typeToken = new TypeToken<Map<Integer, BattlePassReward>>() {
                                    }.getType();
                                }
                            } else if (cls == Mail.class) {
                                if (name.equals("itemList")) {
                                    typeToken = new TypeToken<List<Mail.MailItem>>() {
                                    }.getType();
                                }
                            } else if (cls == PlayerActivityData.class) {
                                if (name.equals("watcherInfoMap")) {
                                    typeToken = new TypeToken<Map<Integer, PlayerActivityData.WatcherInfo>>() {
                                    }.getType();
                                }
                            } else if (cls == GameHome.class) {
                                if (name.equals("furnitureMakeSlotItemList")) {
                                    typeToken = new TypeToken<List<FurnitureMakeSlotItem>>() {
                                    }.getType();
                                } else if (name.equals("sceneMap")) {
                                    typeToken = new TypeToken<ConcurrentHashMap<Integer, HomeSceneItem>>() {
                                    }.getType();
                                }
                            } else if (cls == Avatar.class) {
                                if (name.equals("proudSkillBonusMap") ||
                                    name.equals("skillExtraChargeMap") ||
                                    name.equals("skillLevelMap")) {
                                    typeToken = new TypeToken<Map<Integer, Integer>>() {
                                    }.getType();
                                }
                            } else if (cls == Player.class) {
                                typeToken = switch (name) {
                                    case "expeditionInfo" -> new TypeToken<Map<Long, ExpeditionInfo>>() {
                                    }.getType();
                                    case "activeForges" -> new TypeToken<List<ActiveForgeData>>() {
                                    }.getType();
                                    case "mapMarks" -> new TypeToken<HashMap<String, MapMark>>() {
                                    }.getType();
                                    default -> typeToken;
                                };
                                if (name.equals("unlockedRecipies") ||
                                    name.equals("openStates") ||
                                    name.equals("properties") ||
                                    name.equals("questGlobalVariables")) {
                                    typeToken = new TypeToken<Map<Integer, Integer>>() {
                                    }.getType();
                                }
                                if (name.equals("unlockedSceneAreas") ||
                                    name.equals("unlockedScenePoints")) {
                                    typeToken = new TypeToken<Map<Integer, Set<Integer>>>() {
                                    }.getType();
                                }
                            }
                            if (typeToken != null) {
                                it.set(instance, gson.fromJson((String) data, typeToken));
                            } else {
                                it.set(instance, gson.fromJson((String) data, type));
                            }
                        }
                    } catch (Exception e) {
                        Grasscutter.getLogger().error("sqlite serialization failed: ", e);
                    }
                });
                if (!ignorePostLoad) {
                    Arrays.stream(cls.getDeclaredMethods())
                        .filter((it) -> it.isAnnotationPresent(PostLoad.class))
                        .forEach((it) -> {
                            try {
                                it.setAccessible(true);
                                it.invoke(instance);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }
                        });
                }
            }
            resultSet.close();
            stmt.close();
        } catch (Exception e) {
            Grasscutter.getLogger().error(sql + " sqlite exec query sql failed: ", e);
        }
        return rList;
    }

    public static <T> T queryFirstOrNull(Class<T> cls, String condition) {
        List<T> ts = query(cls, condition);
        return ts.isEmpty() ? null : ts.get(0);
    }

    public static <T> List<T> query(Class<T> cls) {
        return query(cls, "1 = 1");
    }

    public static <T> boolean delete(Class<T> cls, String condition) {
        var tName = cls.getAnnotation(Entity.class).value();
        var sql = "DELETE FROM " + tName + " WHERE " + condition;
        return exec(sql);
    }

    public static <T> boolean delete(T entity) {
        try {
            var cls = entity.getClass();
            var tName = cls.getAnnotation(Entity.class).value();
            Field idField = cls.getDeclaredField("id");
            idField.setAccessible(true);
            var sql = "DELETE FROM " + tName + " WHERE id = " + idField.get(entity);
            return exec(sql);
        } catch (Exception e) {
            Grasscutter.getLogger().error(" sqlite delete failed: ", e);
        }
        return false;
    }

    private static boolean exec(String sql) {
        boolean result;
        try {
            Statement stmt = connection.createStatement();
            result = stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            Grasscutter.getLogger().error(sql + " sqlite exec sql failed: ", e);
            return false;
        }
        return result;
    }

    @NotNull
    private static Set<Field> filterFields(Field[] cls) {
        return Arrays.stream(cls).filter((it) -> {
            int modifiers = it.getModifiers();
            it.setAccessible(true);
            return !Modifier.isTransient(modifiers) &&
                !Modifier.isStatic(modifiers) &&
                !Modifier.isVolatile(modifiers) &&
                !it.isAnnotationPresent(Transient.class);
        }).collect(Collectors.toSet());
    }

    public static String wrapString(String str) {
        return "'" + str.replace("'", "\\'") + "'";
    }
}
