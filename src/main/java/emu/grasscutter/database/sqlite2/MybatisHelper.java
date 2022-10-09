package emu.grasscutter.database.sqlite2;

import dev.morphia.mapping.Mapper;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.reflections.Reflections;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.util.Set;

import static org.reflections.scanners.Scanners.SubTypes;
import static org.reflections.scanners.Scanners.TypesAnnotated;

public class MybatisHelper {

    private static SqlSessionFactory sqlSessionFactory;


    public static void query() {
        try (SqlSession session = sqlSessionFactory.openSession()) {

        }
    }

    public static void init() {
        DataSource dataSource = getDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("sql", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        scanAllMappers().forEach(configuration::addMapper);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    private static Set<Class<?>> scanAllMappers() {
        var reflections = new Reflections(MybatisHelper.class.getPackageName());
        return reflections.get(SubTypes.of(TypesAnnotated.with(Mapper.class)).asClass());
    }

    private static DataSource getDataSource() {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:test.db");
        return ds;
    }

}
