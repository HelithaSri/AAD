import db.DbConnection;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
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
        resp.setContentType("application/json");
        /*try {
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

            String finalJson = "[" + allRecodes.substring(0, allRecodes.length() - 1) + "]";
            PrintWriter writer = resp.getWriter();
            writer.write(finalJson);

            System.out.println(finalJson);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            ResultSet resultSet = connection.prepareStatement("select * from Customer").executeQuery();

            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            JsonObjectBuilder objBuilder = Json.createObjectBuilder();

            while (resultSet.next()) {

                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);

                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("id", id);
                objectBuilder.add("name", name);
                objectBuilder.add("address", address);

                arrayBuilder.add(objectBuilder.build());
            }

            objBuilder.add("data",arrayBuilder.build());
            objBuilder.add("massage","Done");
            objBuilder.add("status","200");


            PrintWriter writer = resp.getWriter();
            writer.print(objBuilder.build());

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
        System.out.println(customerID + "\t" + customerName + "\t" + customerAddress);
        System.out.println("Post : " + customerID);

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

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusId = req.getParameter("customerID");
//        String cusId = req.getHeader("customerID");
//        String cusId = req.getQueryString().split("=")[1];    // This also works
//        String cusId = queryString.split("=")[1];
//        System.out.println(queryString);
        System.out.println(cusId);

        try {
            boolean b = DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM customer WHERE id='" + cusId + "'").executeUpdate() > 0;
//            boolean b = DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM customer WHERE id='C006'").executeUpdate() > 0;

            if (b) {
                PrintWriter writer = resp.getWriter();
                writer.write(String.valueOf(b));
                writer.write("\nCustomer Deleted");
            } else {
                PrintWriter writer = resp.getWriter();
                writer.write(String.valueOf(b));
                writer.write("\nCustomer not Deleted");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerID = req.getParameter("customerID");
        String customerName = req.getParameter("customerName");
        String customerAddress = req.getParameter("customerAddress");
        System.out.println(customerID);

        try {
            PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE customer SET name=?,address=? WHERE id=?");

            pstm.setObject(1, customerName);
            pstm.setObject(2, customerAddress);
            pstm.setObject(3, customerID);

            boolean b = pstm.executeUpdate() > 0;
            if (b) {
                PrintWriter writer = resp.getWriter();
                writer.write("Customer Updated");
            } else {
                PrintWriter writer = resp.getWriter();
                writer.write("Customer Not Updated");
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
