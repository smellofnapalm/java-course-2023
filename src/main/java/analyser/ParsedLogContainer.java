package analyser;

import java.nio.file.Path;
import java.time.OffsetDateTime;

public record ParsedLogContainer(String ip, String user, OffsetDateTime dateTime, String request, Path path,
                                 String protocol, String code, long sent, String referer, String agent) {
}
