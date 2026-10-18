package ir.scooljavanrood.madreseyar.teacher;
import java.util.Random;
public class TestExamCategoryRules {
    static int checks=0;
    static void check(boolean v){checks++;if(!v)throw new AssertionError("case "+checks);}
    public static void main(String[] args){
        check(ExamCategoryRules.validCategory("ongoing"));check(ExamCategoryRules.validCategory("final"));
        check(!ExamCategoryRules.validCategory("mixed"));check(ExamCategoryRules.validPeriod("semester1"));
        check(ExamCategoryRules.validPeriod("semester2"));check(!ExamCategoryRules.validPeriod("annual"));
        check(ExamCategoryRules.finalCeiling(5)==15.0);check(ExamCategoryRules.finalCeiling(4.75)==15.25);
        check(ExamCategoryRules.matchesDifficulty("easy",1));check(ExamCategoryRules.matchesDifficulty("medium",2));
        check(ExamCategoryRules.matchesDifficulty("hard",3));check(!ExamCategoryRules.matchesDifficulty("medium",3));
        check(ExamCategoryRules.planFinalScores(new double[]{5,5,5},15)[0]==5);
        check(ExamCategoryRules.planFinalScores(new double[]{2,1,1},15).length==3);
        check(ExamCategoryRules.planFinalScores(new double[]{1,1,1},.5)==null);
        check(ExamCategoryRules.planFinalScores(new double[]{1,1},15.1)==null);
        check(ExamCategoryRules.planFinalScores(new double[]{},15)==null);
        Random random=new Random(1889);
        for(int n=1;n<=70;n++)for(int k=0;k<30;k++){
            double[] original=new double[n];for(int i=0;i<n;i++)original[i]=.25+random.nextInt(40)*.25;
            double ceiling=(n+random.nextInt(81-n))/4.0;
            double[] planned=ExamCategoryRules.planFinalScores(original,ceiling);
            check(planned!=null);
            double total=0;for(double v:planned){check(v>=.25&&v<=20);check(Math.abs(v*4-Math.rint(v*4))<1e-8);total+=v;}
            check(Math.abs(total-ceiling)<1e-8);
        }
        System.out.println("A10.1 CATEGORY, PERIOD AND SCORING CHECKS "+checks+" PASS");
    }
}
