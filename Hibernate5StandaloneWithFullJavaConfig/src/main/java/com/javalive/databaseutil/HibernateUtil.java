package com.javalive.databaseutil;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import com.javalive.entity.Student;

public class HibernateUtil {
	/*ServiceRegistry holds the services that Hibernate will need during bootstrapping and at runtime.
	StandardServiceRegistryBuilder — this is a Builder for standard ServiceRegistry instances.*/
	private static StandardServiceRegistry registry;
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				Configuration configuration = new Configuration();
				// Hibernate settings equivalent to hibernate.cfg.xml's properties
				Properties settings = new Properties();
				settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
				settings.put(Environment.URL, "jdbc:mysql://localhost:3306/test?useSSL=false");
				settings.put(Environment.USER, "root");
				settings.put(Environment.PASS, "root");
				settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
				settings.put(Environment.SHOW_SQL, "true");//This property displays query build and executed by Hibernate to console.
				//http://rbyjava.blogspot.com/2012/05/what-is-currentsessioncontextclass-in.html
				settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");//Read above link for more explanation 
				// settings.put(Environment.HBM2DDL_AUTO, "create-drop");
				configuration.setProperties(settings);
				//Mapping entity classes.
				configuration.addAnnotatedClass(Student.class);
				registry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();
				sessionFactory = configuration.buildSessionFactory(registry);

			} catch (Exception e) {
				e.printStackTrace();
				if (registry != null) {
					StandardServiceRegistryBuilder.destroy(registry);
				}
			}
		}
		return sessionFactory;
	}

	public static void shutdown() {
		if (registry != null) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}

}
