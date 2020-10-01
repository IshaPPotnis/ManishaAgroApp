package com.example.manishaagro.utils;

public class Utilities {

    public static String getDesignation(String employeeNameText) {
        switch (employeeNameText.toLowerCase()) {
            case "suresh":
                return EmployeeType.MANAGER.name();
            case "ramesh":
                return EmployeeType.EMPLOYEE.name();
            case "roshan":
                return EmployeeType.EMPLOYEE.name();
        }
        return null;
    }
}
