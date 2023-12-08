package analyser;

import java.time.OffsetDateTime;

public record ArgsContainer(String path, OffsetDateTime from, OffsetDateTime to, String format) {
}
