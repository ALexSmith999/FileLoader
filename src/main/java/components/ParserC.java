package components;

import file.Parsers;

import java.util.ArrayList;
import java.util.List;

public class ParserC implements Parsers {
    /*
    Depending on the structure of a file and requirements
    the underlying algorithm can change significantly.
    Primarily serves to demonstrate the overall architecture.
    **/
    @Override
    public List<List<String>> parse(String row) {
        row = row.trim();
        String[] arr = row.split(" ");
        List<String> values = new ArrayList<>(List.of(arr));
        return List.of(values);
    }
}
