package service.impl;

import dao.DepartmentDao;
import com.mySampleApplication.client.shared.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.DepartmentService;

import java.sql.SQLException;
import java.util.List;


@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentDao departmentDao;

    public DepartmentServiceImpl() {
    }

    public DepartmentServiceImpl(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @Override
    public Department getDepartmentById(Integer departmentId) throws SQLException, ClassNotFoundException {
        return departmentDao.getDepartmentById(departmentId);
    }

    @Override
    public List<Department> getDepartments() throws SQLException, ClassNotFoundException {
        return departmentDao.getDepartments();
    }

    @Override
    public void addDepartment(Department department) throws SQLException, ClassNotFoundException {
        departmentDao.addDepartment(department);
    }

    @Override
    public void removeDepartment(Integer id) throws SQLException, ClassNotFoundException {
        departmentDao.removeDepartment(id);
    }

    @Override
    public void updateDepartment(Integer id, String name) throws SQLException, ClassNotFoundException {
        departmentDao.updateDepartment(id, name);
    }
}
