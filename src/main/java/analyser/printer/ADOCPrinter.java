package analyser.printer;

import java.util.List;
import java.util.Map;

public class ADOCPrinter implements Printer {
    final static String BEGIN = "|===\n";
    final static String DELIM = "|";

    @Override
    public String printHeader(String header) {
        return ".%s\n\n".formatted(header);
    }

    @Override
    public String printNamesOfColumns(List<String> names) {
        return BEGIN + DELIM + String.join(" " + DELIM, names) + '\n';
    }

    @Override
    public String printOneRegularCell(Map.Entry<String, Object> entry) {
        return DELIM + entry.getKey() + "\n"
            + DELIM + entry.getValue().toString() + "\n";
    }

    @Override
    public String printEnding() {
        return BEGIN + "\n";
    }
}
