package ir.scooljavanrood.madreseyar.teacher;
public final class TestExamDraftRules {
    static int n;
    static void check(boolean value){n++;if(!value)throw new AssertionError("test "+n);}
    public static void main(String[] args){
        check(ExamDraftRules.parseMinutes("۳۰")==30);
        check(ExamDraftRules.parseMinutes("١٥")==15);
        check(ExamDraftRules.parseMinutes(" 42 ")==42);
        check(ExamDraftRules.parseMinutes("x")==-1);
        check(ExamDraftRules.validDuration(1));check(ExamDraftRules.validDuration(300));
        check(!ExamDraftRules.validDuration(0));check(!ExamDraftRules.validDuration(301));
        check(ExamDraftRules.validTitle("آزمون فصل ۱"));check(!ExamDraftRules.validTitle(" "));
        check(!ExamDraftRules.validTitle("a".repeat(161)));
        check(ExamDraftRules.validScore(1));check(ExamDraftRules.validScore(20));
        check(!ExamDraftRules.validScore(21));check(!ExamDraftRules.validScore(Double.NaN));
        check(!ExamDraftRules.validScore(0));
        check(ExamDraftRules.validQuestionCount(1));check(ExamDraftRules.validQuestionCount(150));
        check(!ExamDraftRules.validQuestionCount(151));check(!ExamDraftRules.validQuestionCount(0));
        System.out.println("A10 DRAFT RULES "+n+"/"+n+" PASS");
    }
}
