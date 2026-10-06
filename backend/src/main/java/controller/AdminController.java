package controller;

import dao.AdminDAO;

public class AdminController {

    private AdminDAO adminDAO;

    // ==============================
    // CONSTRUCTOR
    // ==============================

    public AdminController() {

        adminDAO = new AdminDAO();
    }


    // ==============================
    // ADMIN LOGIN
    // ==============================

    public boolean loginAdmin(
            String email,
            String password
    ) {

        return adminDAO.validateAdmin(
                email,
                password
        );
    }
}