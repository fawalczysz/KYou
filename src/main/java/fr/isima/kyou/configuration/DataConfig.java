package fr.isima.kyou.configuration;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.sqlite.SQLiteDataSource;

@Configuration
@MapperScan("fr.isima.kyou.dbaccess.mybatis.dao")
public class DataConfig {

	@Bean
	public DataSource dataSource() {
		final SQLiteDataSource dataSource = new SQLiteDataSource();
		String url = "jdbc:sqlite:" + ClassLoader.getSystemClassLoader().getResource("kUser.db").getFile();
		url = url.replace("/", "\\");
		dataSource.setUrl(url);
		return dataSource;
	}

	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean
	public SqlSessionFactoryBean sqlSessionFactory() throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setTypeAliasesPackage("fr.isima.kyou.beans.dao");
		return sessionFactory;
	}
}