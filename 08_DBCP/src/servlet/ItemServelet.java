package servlet;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Helitha Sri
 * @created 5/10/2022 - 1:35 PM
 * @project JavaEE
 */

@WebServlet(urlPatterns = "/item")
public class ItemServelet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = req.getServletContext();
        BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");
        try {
            Connection connection = bds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Item");
            ResultSet rst = pstm.executeQuery();
            while (rst.next()){
                String id = rst.getString(1);
                System.out.println(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
