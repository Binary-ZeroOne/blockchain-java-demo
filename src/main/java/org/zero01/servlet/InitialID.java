package org.zero01.servlet;

import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitialID implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		servletContext.setAttribute("uuid", uuid);
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}
}
