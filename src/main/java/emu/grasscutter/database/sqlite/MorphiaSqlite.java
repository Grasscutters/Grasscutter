package emu.grasscutter.database.sqlite;

import com.google.gson.Gson;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Transient;
import dev.morphia.mapping.Mapper;
import emu.grasscutter.Grasscutter;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.reflections.scanners.Scanners.SubTypes;
import static org.reflections.scanners.Scanners.TypesAnnotated;

class MorphiaSqlite {

    private static final Gson gson = new Gson();

    //test only
    public static void main(String[] args) throws Exception {
//        Set<Class<?>> x = MorphiaSqlite.scanPackageEntity("emu.grasscutter");
//        x.forEach((c)->{
//            String value = c.getAnnotation(Entity.class).value();
//            System.out.println(c);
//            System.out.println(value);
//        });

        // exec fxxk static code blocks
        Class.forName(Grasscutter.class.getName());

        SqliteDatabase db = new SqliteDatabase();
        db.initialize();

//        var account = new Account();
//        account.setUsername("ua");
//        account.setPassword("pwd");
//        account.setEmail("email");
//        account.setBanReason("banReason");
//        saveEntity(account);
//        System.out.println(gson.toJson(loadEntity(Account.class, "id = 3")));

        connection.close();
    }


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
        Set<Field> fieldSet = Arrays.stream(cls.getDeclaredFields()).filter((it) -> {
            int modifiers = it.getModifiers();
            return !Modifier.isTransient(modifiers) &&
                !Modifier.isStatic(modifiers) &&
                !it.isAnnotationPresent(Transient.class);
        }).collect(Collectors.toSet());
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
        Set<Field> fieldSet = Arrays.stream(cls.getDeclaredFields()).filter((it) -> {
            int modifiers = it.getModifiers();
            return !Modifier.isTransient(modifiers) &&
                !Modifier.isStatic(modifiers) &&
                !it.isAnnotationPresent(Transient.class);
        }).collect(Collectors.toSet());
        var sb = new StringBuilder("UPDATE ");
        sb.append(cls.getAnnotation(Entity.class).value());
        sb.append(" SET (\n");
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
        sb.append("WHERE ").append(condition);
        return sb.toString();
    }

    private static <T> String insertSQL(T entity) {
        Class<?> cls = entity.getClass();
        Set<Field> fieldSet = Arrays.stream(cls.getDeclaredFields()).filter((it) -> {
            int modifiers = it.getModifiers();
            return !Modifier.isTransient(modifiers) &&
                !Modifier.isStatic(modifiers) &&
                !it.isAnnotationPresent(Transient.class);
        }).collect(Collectors.toSet());
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
                    } else {
                        String json = gson.toJson(src);
                        realData.add(wrapString(json));
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
            Object query = queryFirstOrNull(cls, "id = " + idField.get(entity));
            if (query == null) {
                return exec(insertSQL(entity));
            } else {
                return exec(updateSQL(entity, "id = " + idField.get(entity)));
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
        List<T> rList = new ArrayList<>();
        try {
            String sql = querySQL(cls, condition);
            System.out.println(sql);
            Set<Field> fieldSet = Arrays.stream(cls.getDeclaredFields()).filter((it) -> {
                int modifiers = it.getModifiers();
                it.setAccessible(true);
                return !Modifier.isTransient(modifiers) &&
                    !Modifier.isStatic(modifiers) &&
                    !it.isAnnotationPresent(Transient.class);
            }).collect(Collectors.toSet());
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                T instance = cls.getDeclaredConstructor().newInstance();
                rList.add(instance);
                fieldSet.forEach((it) -> {
                    try {
                        Object data = resultSet.getObject(it.getName());
                        var type = it.getType();
                        if (data == null ||
                            type == String.class ||
                            type == Integer.class ||
                            type == Long.class ||
                            type == Float.class ||
                            type == Double.class ||
                            type == int.class ||
                            type == long.class ||
                            type == float.class ||
                            type == double.class
                        ) {
                            it.set(instance, data);
                        } else if (type == Boolean.class ||
                            type == boolean.class) {
                            it.set(instance, ((Integer) data) == 1);
                        } else {
                            it.set(instance, gson.fromJson((String) data, type));
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            resultSet.close();
            stmt.close();
        } catch (Exception e) {
            Grasscutter.getLogger().error("sqlite exec query sql failed: ", e);
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

    public static <T> boolean delete(T entity, String condition) {
        var cls = entity.getClass();
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

    public static String wrapString(String str) {
        return "'" + str.replace("'", "\\'") + "'";
    }
}
