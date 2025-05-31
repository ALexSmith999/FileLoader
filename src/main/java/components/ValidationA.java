package components;

import file.Validations;

public class ValidationA implements Validations {
    /*
    Basic technical checks to validate data accuracy.
    Serves to check whether or not a file can be processed further
    based on the technical contract.
    **/
    private final int EXPECTED_NUM_OF_ROWS = 3;
    @Override
    public boolean isValidRow(String row) {
        if (row.isBlank() || row.isEmpty()) {
            return false;
        }
        row = row.trim();
        String[] arr = row.split(" ");

        if (arr.length < EXPECTED_NUM_OF_ROWS) {
            return false;
        }

        for (int i = 0; i < EXPECTED_NUM_OF_ROWS; i++) {
            if (arr[i].isEmpty() || arr[i].isBlank()) {
                return false;
            }
        }

        return true;
    }
}
