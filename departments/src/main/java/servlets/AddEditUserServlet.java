package servlets;

import dao.impl.DepartmentsDaoMysqlImpl;
import dao.impl.UserDaoMysqlImpl;
import com.mySampleApplication.client.shared.Department;
import model.User;
import service.DepartmentService;
import service.UserService;
import service.impl.DepartmentServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by dik81 on 23.01.18.
 */
//@WebServlet (value ="/addUserServlet")
public class AddEditUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = Integer.valueOf(req.getParameter("userId"));
        Integer id = Integer.valueOf(req.getParameter("id"));
//        DepartmentDao departmentDao = new DepartmentsDaoMysqlImpl();
//        Department department = null;
//        try {
//            department = departmentDao.getDepartmentByIdOldMethod(id);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        UserService userService = new UserServiceImpl(new UserDaoMysqlImpl());
        User user = null;
        try {
            user = userService.getUserById(userId);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("editedName", user.getName());
        req.setAttribute("editedAge", user.getAge());
        req.setAttribute("id", id);
        req.setAttribute("userId", userId);
        req.getRequestDispatcher("addEditUser.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        Integer age = Integer.valueOf(req.getParameter("age"));
        Integer departmentId = Integer.valueOf(req.getParameter("id"));
        DepartmentService departmentService = new DepartmentServiceImpl(new DepartmentsDaoMysqlImpl());
        Department department = null;
        try {
            department = departmentService.getDepartmentById(departmentId);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        UserService userService = new UserServiceImpl(new UserDaoMysqlImpl());
        String paramUserId = req.getParameter("userId");
        if (paramUserId==null||paramUserId.isEmpty()) {
            User user = new User(name, age, departmentId);
            try {
                userService.addUser(user);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        } else {
            Integer userId = Integer.valueOf(paramUserId);
            try {
                userService.updateUser(userId, name, age);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

        }
        req.setAttribute("departmentName", department.getName());
        req.setAttribute("departmentId", departmentId);
        try {
            req.setAttribute("usersList", userService.getUsersOfDepartment(departmentId));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("users.jsp");
        dispatcher.forward(req, resp);
    }
}
