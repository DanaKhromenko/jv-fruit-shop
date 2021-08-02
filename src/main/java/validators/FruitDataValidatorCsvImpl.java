package validators;

import fom.FruitOperationManager;
import fom.FruitOperationManagerCsvImpl;
import java.util.List;

public class FruitDataValidatorCsvImpl implements FruitDataValidator {
    private final String csvFilePath;

    public FruitDataValidatorCsvImpl(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    @Override
    public boolean validate() {
        FruitOperationManager operationManager = new FruitOperationManagerCsvImpl(csvFilePath);
        List<String> operations = operationManager.getAllOperations();
        return operations.stream()
                .allMatch(this::rowIsValid);
    }

    private boolean rowIsValid(String row) {
        // checking for null string
        if (row == null) {
            return false;
        }
        // checking values quantity
        String[] data = row.split(",");
        if (data.length != 3) {
            return false;
        }
        // checking operation name
        if (!data[0].equals("b")
                && !data[0].equals("s")
                && !data[0].equals("p")
                && !data[0].equals("r")) {
            return false;
        }
        // checking fruit name
        if (data[1].isEmpty()) {
            return false;
        }
        // checking operation value
        try {
            int value = Integer.parseInt(data[2]);
            if (value <= 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}