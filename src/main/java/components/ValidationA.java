package components;

import file.Validations;

public class ValidationA implements Validations {

    @Override
    public boolean isValidRow(String row) {
        if (row.isBlank() || row.isEmpty()) {
            return false;
        }

        String[] arr = row.split(" ");

        if (arr.length < 3) {
            return false;
        }

        for (String curr : arr) {
            if (curr.isEmpty() || curr.isBlank()) {
                return false;
            }
        }

        return true;
    }
}
