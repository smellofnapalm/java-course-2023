package analyser.printer;

import java.util.List;
import java.util.Map;

public class MarkdownPrinter implements Printer {
    final static String DELIM = "|";
    final static String OVERLINE = "-";
    final static String HEADING = "#### ";

    @Override
    public String printHeader(String header) {
        return HEADING + header + "\n\n";
    }

    @Override
    public String printNamesOfColumns(List<String> names) {
        String head = DELIM + " " + String.join(" " + DELIM + " ", names) + " " + DELIM + "\n";
        StringBuilder line = new StringBuilder();
        for (String name : names) {
            line.append(DELIM).append(OVERLINE.repeat(name.length() + 2));
        }
        line.append(DELIM).append("\n");
        return head + line;
    }

    @Override
    public String printOneRegularCell(Map.Entry<String, Object> entry) {
        return "%s %s %s %s %s\n".formatted(DELIM, entry.getKey(), DELIM, entry.getValue().toString(), DELIM);
    }

    @Override
    public String printEnding() {
        return "\n";
    }
}
