package ir.scooljavanrood.madreseyar.teacher;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/** Read-only presentation helpers: never invent class access or bypass server permissions. */
final class TeacherClassRules {
    private TeacherClassRules() {}

    static String key(String classId, String subject) {
        String cls = classId == null ? "" : classId.trim();
        String lesson = subject == null ? "" : subject.trim();
        return cls + "\u0000" + lesson;
    }

    static boolean valid(String classId, String subject) {
        return classId != null && !classId.trim().isEmpty()
                && subject != null && !subject.trim().isEmpty();
    }

    static int distinctClasses(Collection<String> classIds) {
        Set<String> distinct = new HashSet<>();
        if (classIds == null) return 0;
        for (String id : classIds) if (id != null && !id.trim().isEmpty()) distinct.add(id.trim());
        return distinct.size();
    }

    static int distinctAssignments(Collection<String> pairs) {
        if (pairs == null) return 0;
        return new HashSet<>(pairs).size();
    }
}
