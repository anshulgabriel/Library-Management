package com.webkorps.library.controllers;

import static com.webkorps.library.dao.LoginDao.saveAdmin;
import static com.webkorps.library.dao.LoginDao.saveUser;
import static com.webkorps.library.dao.LoginDao.doesEmailExist;
import com.webkorps.library.util.EmailSender;
import java.io.IOException;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name;
        name = request.getParameter("name");
        String libraryName = request.getParameter("library_name");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String role;
        String password = request.getParameter("pass");

        if (doesEmailExist(email)) {
            request.setAttribute("status", "failed");
            request.setAttribute("register_message", "Email already exist, please try with a different email.");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        String memberId = generateMemberId(name);
        String registerMessage = "Account Created Successfully.\nPlease Check Your Email For MemberId and Password";
        String errorMessage = "Something went wrong, please try again!";

        boolean isLibraryAdmin = libraryName.length() > 0 && address.length() > 0;
        boolean isUser = !isLibraryAdmin && libraryName.length() <= 0 && address.length() <= 0;

        int saveResult = 0;

        if (isLibraryAdmin) {
            role = "admin";
            saveResult = saveAdmin(memberId, name, libraryName, address, email, role, password);
        } else if (isUser) {
            role = "user";
            saveResult = saveUser(memberId, name, email, role, password);
        }

        if (saveResult > 0) {
            EmailSender.sendEmail(email.trim(), name, memberId, password);
            request.setAttribute("status", "success");
            request.setAttribute("register_message", registerMessage);
        } else {
            request.setAttribute("status", "failed");
            request.setAttribute("register_message", errorMessage);
        }

        request.getRequestDispatcher("signup.jsp").forward(request, response);
    }

    public String generateMemberId(String name) {
        Random random = new Random();
        int threeDigitNumber = 100 + random.nextInt(900);
        String[] memId = name.split(" ");
        String memberId = memId[0] + "" + threeDigitNumber;
        return memberId;
    }
}
