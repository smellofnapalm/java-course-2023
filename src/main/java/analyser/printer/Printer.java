package analyser.printer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface Printer {
    String printHeader(String header);

    String printNamesOfColumns(List<String> names);

    String printOneRegularCell(Map.Entry<String, Object> entry);

    String printEnding();

    default String printAllListOfEntries(List<Map.Entry<String, Object>> list) {
        return list.stream().map(this::printOneRegularCell).collect(Collectors.joining());
    }

    default String print(String header, List<String> names, List<Map.Entry<String, Object>> list) {
        return printHeader(header) + printNamesOfColumns(names) + printAllListOfEntries(list) + printEnding();
    }
}
