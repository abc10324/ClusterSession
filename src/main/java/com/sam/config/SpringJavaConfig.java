package com.sam.config;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.BeanNameViewResolver;

import com.sam.config.websocket.MessagePublisher;
import com.sam.config.websocket.RedisMessagePublisher;
import com.sam.config.websocket.RedisMessageSubscriber;
import com.sam.config.websocket.RedisUserListSubscriber;

@Configuration
@EnableWebMvc
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=60)
@EnableJpaRepositories(basePackages="com.sam.model.dao")
@EnableRedisRepositories(basePackages="com.sam.model.dao")
@ComponentScan(basePackages="com.sam")
public class SpringJavaConfig extends AbstractHttpSessionApplicationInitializer{

	@Bean
    public DataSource dataSource() {
		JndiObjectFactoryBean factory = new JndiObjectFactoryBean();
		factory.setJndiName("java:comp/env/jdbc/UserDB");
		factory.setProxyInterface(DataSource.class);
		
		try {
			
			// look up
			factory.afterPropertiesSet();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        return (DataSource) factory.getObject();
    }
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	
	  HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	  vendorAdapter.setGenerateDdl(true);
	  vendorAdapter.setShowSql(true);
	  vendorAdapter.setDatabase(Database.MYSQL);
	    
	  LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	  factory.setJpaVendorAdapter(vendorAdapter);
	  factory.setPackagesToScan("com.sam.model");
	  factory.setDataSource(dataSource());
	    
	  return factory;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
	  JpaTransactionManager txManager = new JpaTransactionManager();
	  txManager.setEntityManagerFactory(entityManagerFactory().getObject());
	  return txManager;
	}
	
	@Bean
	public RedisConnectionFactory connectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setHostName("172.30.64.33");
		factory.setPort(6379);
		factory.setDatabase(0); // default is DB0
		
		return factory;
	}
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(connectionFactory());
		return template;
	}
	
	@Bean
	public ChannelTopic topic() {
	    return new ChannelTopic("messageQueue");
	}
	
	@Bean
	public ChannelTopic onlineUserListTopic() {
	    return new ChannelTopic("userList");
	}
	
	@Bean
	public MessagePublisher messagePublisher() { 
	    return new RedisMessagePublisher(redisTemplate(), topic(), onlineUserListTopic());
	}
	
	@Bean
	public RedisMessageListenerContainer redisContainer(@Autowired RedisMessageSubscriber redisMessageSubscriber,
														@Autowired RedisUserListSubscriber redisUserListSubscriber) {
	    RedisMessageListenerContainer container = new RedisMessageListenerContainer(); 
	    container.setConnectionFactory(connectionFactory()); 
	    container.addMessageListener(new MessageListenerAdapter(redisMessageSubscriber), topic()); 
	    container.addMessageListener(new MessageListenerAdapter(redisUserListSubscriber), onlineUserListTopic()); 
	    
	    return container; 
	}
	
	@Bean
	public ViewResolver viewResolver() {
		return new BeanNameViewResolver();
	}
}
