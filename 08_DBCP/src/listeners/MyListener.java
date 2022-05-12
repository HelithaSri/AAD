package listeners;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

/**
 * @author Helitha Sri
 * @created 5/10/2022 - 2:59 PM
 * @project JavaEE
 */

@WebListener
public class MyListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        bds.setUrl("jdbc:mysql://127.0.0.1:3306/company");
        bds.setUsername("root");
        bds.setPassword("1234");
        bds.setMaxTotal(5);
        bds.setInitialSize(5);

        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("bds",bds);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");
        try {
            bds.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}