/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package buttercup.actividadintegradora.resources.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Lisaa
 */
@WebServlet(name = "registrarUsuario", urlPatterns = {"/registrarUsuario"})
public class registrarUsuario extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usuario = request.getParameter("usuario");
        String correo = request.getParameter("correo");
        String password = request.getParameter ("contrasena");
        String nombre = request.getParameter("nombre");
        int edad = Integer.parseInt(request.getParameter("edad"));
        double estatura = Double.parseDouble(request.getParameter ("estatura"));

        if (edad < 15 || estatura < 1.0 || estatura > 2.5) {
            response.getWriter().println("Edad o estatura inválida.");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:1527/user[root on ROOT]/Users", "root", "")) {
            String sql = "INSERT INTO usuarios (nombre, edad, estatura, usuario, correo, contrasena) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(4, nombre);
            stmt.setInt(2, edad);
            stmt.setDouble(6, estatura);
            stmt.setString(5, usuario);
            stmt.setString(1, correo);
            stmt.setString(3, password);
            stmt.executeUpdate();

            response.sendRedirect("login.jsp");
        } 
        catch (SQLException e) {
            response.getWriter().println("Error al registrar.");
        }
    }
}
