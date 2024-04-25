package avinash;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Registration extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.getWriter();

        String fnam = request.getParameter("fname");
        String lnam = request.getParameter("lname");
        String email = request.getParameter("email");
        String pwd = request.getParameter("pwd");
        String epwd = request.getParameter("epwd");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");
        String dob = request.getParameter("dob");
        String addr = request.getParameter("addr");

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/avinash", "root",
                    "avinash7223");

            PreparedStatement ps = con.prepareStatement("insert into register values(?,?,?,?,?,?,?,?,?)");
            ps.setString(1, fnam);
            ps.setString(2, lnam);
            ps.setString(3, email);
            ps.setString(4, pwd);
            ps.setString(5, epwd);
            ps.setString(6, phone);
            ps.setString(7, gender);
            ps.setString(8, dob);
            ps.setString(9, addr);

            int count = ps.executeUpdate();

            if (count > 0) {
                String successMessage = "Registered Successfully";
                response.getWriter().println("<script>alert('" + successMessage + "');</script>");
                RequestDispatcher rd = request.getRequestDispatcher("signin.html");
                rd.include(request, response);
            } else {
                String errorMessage = "Registration Failed";
                response.getWriter().println("<script>alert('" + errorMessage + "');</script>");
                RequestDispatcher rd = request.getRequestDispatcher("Registration.html");
                rd.include(request, response);
            }

            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            String errorMessage = "Registration Failed. Please try again later.";
            response.getWriter().println("<script>alert('" + errorMessage + "');</script>");
            RequestDispatcher rd = request.getRequestDispatcher("Registration.html");
            rd.include(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            String errorMessage = "Registration Failed. ";
            errorMessage += "The provided email is already registered. ";
            errorMessage += "Please try with a different email.";
            response.getWriter().println("<script>alert('" + errorMessage + "');</script>");
            RequestDispatcher rd = request.getRequestDispatcher("Registration.html");
            rd.include(request, response);
        }
    }
}
