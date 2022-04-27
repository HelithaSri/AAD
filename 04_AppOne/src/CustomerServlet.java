import db.DbConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Helitha Sri
 * @created 4/18/2022 - 10:34 AM
 * @project JavaEE
 */

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            ResultSet resultSet = connection.prepareStatement("select * from Customer").executeQuery();
            String allRecodes = "";

            while (resultSet.next()) {

                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);

                String customer = "{\"id\":\"" + id + "\",\"name\":\"" + name + "\",\"address\":\"" + address + "\"},";
                allRecodes = allRecodes + customer;
            }

            String finalJson = "["+allRecodes.substring(0,allRecodes.length()-1)+"]";
            PrintWriter writer = resp.getWriter();
            writer.write(finalJson);

            System.out.println(finalJson);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("hey");
        String customerID = req.getParameter("customerID");
        String customerName = req.getParameter("customerName");
        String customerAddress = req.getParameter("customerAddress");
//        String customerSlary = req.getParameter("customerSlary");
        System.out.println(customerID+"\t"+customerName+"\t"+customerAddress);

        try {
            PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO customer VALUES (?,?,?)");
            pstm.setObject(1, customerID);
            pstm.setObject(2, customerName);
            pstm.setObject(3, customerAddress);
            boolean b = pstm.executeUpdate() > 0;
            PrintWriter writer = resp.getWriter();
            writer.write("Customer Added");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }
}
