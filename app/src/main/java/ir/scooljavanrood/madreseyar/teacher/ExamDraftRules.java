package ir.scooljavanrood.madreseyar.teacher;
/** Shared client-side constraints for the native draft form. Server is authoritative. */
final class ExamDraftRules {
    private ExamDraftRules() {}
    static String asciiDigits(String input) {
        if(input==null)return "";
        StringBuilder b=new StringBuilder();
        for(int i=0;i<input.length();i++){
            char c=input.charAt(i);
            if(c>='۰'&&c<='۹')c=(char)('0'+(c-'۰'));
            else if(c>='٠'&&c<='٩')c=(char)('0'+(c-'٠'));
            b.append(c);
        }
        return b.toString().trim();
    }
    static int parseMinutes(String value) {
        try { return Integer.parseInt(asciiDigits(value)); }
        catch (RuntimeException error) { return -1; }
    }
    static boolean validTitle(String value) {return value!=null&&!value.trim().isEmpty()&&value.trim().length()<=160;}
    static boolean validDuration(int minutes){return minutes>=1&&minutes<=300;}
    static boolean validScore(double value){return Double.isFinite(value)&&value>0&&value<=20;}
    static boolean validQuestionCount(int count){return count>=1&&count<=150;}
}
