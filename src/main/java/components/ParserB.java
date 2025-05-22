package components;

import file.Parsers;

import java.util.ArrayList;
import java.util.List;

public class ParserB implements Parsers {
    @Override
    public List<List<String>> parse(String row) {
        String[] arr = row.split(" ");
        List<String> values = new ArrayList<>(List.of(arr));
        return List.of(values);
    }
}
