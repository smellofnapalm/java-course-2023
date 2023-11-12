package analyser;

import java.io.IOException;
import static analyser.ReadLogs.findMatchingFiles;

public final class Main {

    @SuppressWarnings("RegexpSinglelineJava")
    public static void main(String[] args) throws IOException {
        String pattern = "src/logs/2023**";
        System.out.println(findMatchingFiles(pattern));
    }

    private Main() {
    }
}
