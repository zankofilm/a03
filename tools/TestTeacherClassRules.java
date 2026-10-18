package ir.scooljavanrood.madreseyar.teacher;
import java.util.Arrays;
import java.util.Collections;

public final class TestTeacherClassRules {
    static int passed=0;
    static void check(boolean condition,String label){if(!condition)throw new AssertionError(label);passed++;}
    public static void main(String[] args) {
        check(TeacherClassRules.valid("class1","ریاضی"),"valid Persian assignment");
        check(!TeacherClassRules.valid("","ریاضی"),"invalid empty class");
        check(!TeacherClassRules.valid("class1", "  "),"invalid empty subject");
        check(TeacherClassRules.key(" class1 "," ریاضی ").equals("class1\u0000ریاضی"),"trim both keys");
        check(!TeacherClassRules.key("c1","ریاضی").equals(TeacherClassRules.key("c1","ادبی")),"two subjects same class are separate assignments");
        check(TeacherClassRules.distinctClasses(Arrays.asList("a","b","b"))==2,"two classes detected");
        check(TeacherClassRules.distinctClasses(Arrays.asList("c1","c1"))==1,"two lessons in one class not two classes");
        check(TeacherClassRules.distinctClasses(Arrays.asList(" ","c1",null))==1,"empty class filtered");
        check(TeacherClassRules.distinctClasses(Collections.emptyList())==0,"empty list");
        check(TeacherClassRules.distinctAssignments(Arrays.asList("a\u0000م", "a\u0000م", "b\u0000م"))==2,"duplicate records deduped");
        System.out.println("Teacher class rules: "+passed+"/"+passed+" PASS");
    }
}
