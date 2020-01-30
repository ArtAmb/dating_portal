package psk.projects.dating_portal.tags;

public enum TagPriority {
    LOVE,
    LIKE,
    NORMAL,
    NOT_LIKE,
    HATE;

    public static double getValue(TagPriority priority) {
        switch (priority) {
            case LOVE:
                return 2.0;
            case LIKE:
                return 1.5;
            case NORMAL:
                return 1;
            case NOT_LIKE:
                return -1.5;
            case HATE:
                return -2.0;
        }

        throw new IllegalStateException("No value for " + priority);
    }
}
