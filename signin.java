package avinash;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class signin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/avinash", "root", "avinash7223");

            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // Check if email exists in the database
            PreparedStatement ps = con.prepareStatement("SELECT email FROM register WHERE email=?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Email exists in the database, now check the password
                PreparedStatement ps2 = con.prepareStatement("SELECT email FROM register WHERE email=? AND pwd=?");
                ps2.setString(1, email);
                ps2.setString(2, password);
                ResultSet rs2 = ps2.executeQuery();

                if (rs2.next()) {
                    // Password is correct, redirect to welcome page
                    RequestDispatcher rd = request.getRequestDispatcher("welcome.html");
                    rd.forward(request, response);
                } else {
                    // Wrong password
                    String errorMessage = "Incorrect password! Please try again.";
                    errorMessage += "Password should be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character.";
                    response.getWriter().println("<script>alert('" + errorMessage + "');</script>");
                    RequestDispatcher rd = request.getRequestDispatcher("signin.html");
                    rd.include(request, response);
                }
            } else {
                // Email does not exist in the database
                String errorMessage = "Email does not exist!";
                response.getWriter().println("<script>alert('" + errorMessage + "');</script>");
                RequestDispatcher rd = request.getRequestDispatcher("signin.html");
                rd.include(request, response);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
