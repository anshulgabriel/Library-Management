package com.webkorps.library.controllers;

import static com.webkorps.library.dao.LoginDao.saveAdmin;
import static com.webkorps.library.dao.LoginDao.saveUser;
import static com.webkorps.library.dao.LoginDao.doesEmailExist;
import com.webkorps.library.util.EmailSender;
import java.io.IOException;
import java.util.Random;
import javax.servlet.RequestDispatcher;
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
        String name = "";
        name = request.getParameter("name");
        String libraryName = request.getParameter("library_name");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String role = "";
        String password = request.getParameter("pass");
        String checkTerms = request.getParameter("agree-term");

        if (doesEmailExist(email)) {
            request.setAttribute("status", "failed");
            request.setAttribute("register_message", "Email already exist, please try with a different email.");
            RequestDispatcher rd = request.getRequestDispatcher("signup.jsp");
            rd.forward(request, response);
            return;
        }

        if (libraryName.length() > 0 && address.length() > 0) {
            String memberId = generateMemberId(name);
            role = "admin";
            int adminSaved = saveAdmin(memberId, name, libraryName, address, email, role, password);
            if (adminSaved > 0) {
                request.setAttribute("status", "success");
                request.setAttribute("register_message", "Account Created Successfully.\nPlease Check Your Email For MemberId and Password");
                boolean sendEmail = EmailSender.sendEmail(email.trim(), name, memberId, password);
                RequestDispatcher rd = request.getRequestDispatcher("signup.jsp");
                rd.forward(request, response);
            } else {
                request.setAttribute("status", "failed");
                request.setAttribute("register_message", "Account Created Successfully.\nPlease Check Your Email For MemberId and Password");
                RequestDispatcher rd = request.getRequestDispatcher("signup.jsp");
                rd.forward(request, response);
            }
        } else if (libraryName.length() <= 0 && address.length() <= 0) {
            String memberId = generateMemberId(name);
            role = "user";
            int userSaved = saveUser(memberId, name, email, role, password);

            if (userSaved > 0) {
                EmailSender.sendEmail(email.trim(), name, memberId, password);
                request.setAttribute("status", "success");
                request.setAttribute("register_message", "Account Created Successfully.\nPlease Check Your Email For MemberId and Password");
                RequestDispatcher rd = request.getRequestDispatcher("signup.jsp");
                rd.forward(request, response);
            } else {
                request.setAttribute("status", "failed");
                request.setAttribute("register_message", "Something went wrong, please try again!");
                RequestDispatcher rd = request.getRequestDispatcher("signup.jsp");
                rd.forward(request, response);
            }
        } else {
            request.setAttribute("status", "failed");
            request.setAttribute("register_message", "Something went wrong, please try again!");
            RequestDispatcher rd = request.getRequestDispatcher("signup.jsp");
            rd.forward(request, response);
        }

    }

    public String generateMemberId(String name) {
        Random random = new Random();
        int threeDigitNumber = 100 + random.nextInt(900);
        String[] memId = name.split(" ");
        String memberId = memId[0] + "" + threeDigitNumber;
        return memberId;
    }

}
