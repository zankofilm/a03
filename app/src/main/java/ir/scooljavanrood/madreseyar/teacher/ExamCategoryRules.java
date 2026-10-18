package ir.scooljavanrood.madreseyar.teacher;
/** Client side exam-mode and score guard. The server is authoritative. */
final class ExamCategoryRules {
    private ExamCategoryRules() {}
    static boolean validCategory(String value){return "ongoing".equals(value)||"final".equals(value);}
    static boolean validPeriod(String value){return "semester1".equals(value)||"semester2".equals(value);}
    static String categoryLabel(String category){return "final".equals(category)?"پایان‌ترم":"مستمر";}
    static String periodLabel(String period){return "semester2".equals(period)?"نیم‌سال دوم":"نیم‌سال اول";}
    static double finalCeiling(double continuous){return Math.round((20.0-continuous)*100.0)/100.0;}
    static boolean matchesDifficulty(String raw,int selection){
        if(selection==0)return true;
        String value=raw==null?"":raw.trim().toLowerCase(java.util.Locale.ROOT);
        switch(selection){
            case 1:return value.equals("easy")||value.equals("آسان")||value.equals("simple");
            case 2:return value.equals("medium")||value.equals("متوسط")||value.equals("normal");
            case 3:return value.equals("hard")||value.equals("advanced")||value.equals("سخت")||value.equals("دشوار");
            default:return false;
        }
    }
    /** Distribute the official final score in 0.25 increments, keeping every question >0.
      * When the source sum matches the official ceiling, preserve original scores exactly.
      * No hidden change to total marks; unsupported fractions fail safely. */
    static double[] planFinalScores(double[] sources,double target){
        if(sources==null||sources.length==0||sources.length>150||!Double.isFinite(target)||target<=0||target>20)return null;
        double sum=0;for(double v:sources){if(!Double.isFinite(v)||v<=0||v>20)return null;sum+=v;}
        if(Math.abs(sum-target)<0.000001)return sources.clone();
        double scaled=target*4.0;int units=(int)Math.round(scaled);
        if(Math.abs(scaled-units)>0.000001||units<sources.length)return null;
        int[] n=new int[sources.length];double[] residues=new double[n.length];int used=0;
        for(int i=0;i<n.length;i++){
            double ideal=(units-n.length)*sources[i]/sum;
            n[i]=1+(int)Math.floor(ideal);residues[i]=ideal-Math.floor(ideal);used+=n[i];
        }
        int left=units-used;
        for(int k=0;k<left;k++){
            int best=0;for(int i=1;i<n.length;i++)if(residues[i]>residues[best])best=i;
            n[best]++;residues[best]=-1;
        }
        double[] out=new double[n.length];double total=0;
        for(int i=0;i<n.length;i++){out[i]=n[i]/4.0;if(out[i]<=0||out[i]>20)return null;total+=out[i];}
        return Math.abs(total-target)<0.000001?out:null;
    }
}
