/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author hp
 */

import exceptions.DataFileNotFoundException;
import exceptions.DuplicateUserException;
import exceptions.InvalidPhoneNumberException;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class UserValidationHelper {

    public static void validatePhoneNumber(String phone) throws InvalidPhoneNumberException {
        if (!phone.matches("\\d+")) {
            throw new InvalidPhoneNumberException("Phone number must contain digits only.");
        }
    }

 public static void validateUniqueUsername(String filePath, String username, String excludeUserId) throws DuplicateUserException {
    try {
        List<String> lines = FileHelper.readAllLines(filePath);
        for (String line : lines) {
            String[] p = line.split(",");
            if (p.length > 1 && p[1].equalsIgnoreCase(username) && !p[0].equals(excludeUserId)) {
                throw new DuplicateUserException("Username \"" + username + "\" already exists.");
            }
        }
    } catch (DataFileNotFoundException e) {
        // no file yet
    }
}

    public static String generateNextId(String filePath, String prefix) {
        int maxNumber = 0;
        try {
            List<String> lines = FileHelper.readAllLines(filePath);
            for (String line : lines) {
                String[] p = line.split(",");
                if (p.length > 0 && p[0].startsWith(prefix)) {
                    try {
                        int num = Integer.parseInt(p[0].substring(prefix.length()));
                        if (num > maxNumber) maxNumber = num;
                    } catch (NumberFormatException e) {
                        // skip malformed IDs
                    }
                }
            }
        } catch (DataFileNotFoundException e) {
            // no file yet, start from 0
        }
        return prefix + String.format("%03d", maxNumber + 1);
    }

    public static boolean saveTableToFile(DefaultTableModel model, String filePath) {
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            String[] cols = new String[model.getColumnCount()];
            for (int c = 0; c < cols.length; c++) {
                cols[c] = String.valueOf(model.getValueAt(i, c));
            }
            lines.add(String.join(",", cols));
        }
        return FileHelper.writeAllLines(filePath, lines);
    }
}
