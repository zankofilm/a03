from pathlib import Path
r=Path(__file__).resolve().parents[1]
s=(r/'app/src/main/java/ir/scooljavanrood/madreseyar/teacher/MainActivity.java').read_text()
helper=(r/'app/src/main/java/ir/scooljavanrood/madreseyar/teacher/ExamCategoryRules.java').read_text()
checks=['"آزمون مستمر","آزمون پایان‌ترم"','"نیم‌سال اول","نیم‌سال دوم"','"examCategory",category','"academicPeriod",period','/api/staff/gradebook','/api/staff/teacher-exams/p673','ExamCategoryRules.planFinalScores','ExamCategoryRules.matchesDifficulty','"status","draft"','"publicationState","draft"','chapterFilter','sourceQuestionId','new AlertDialog.Builder(this).setTitle("تأیید پیش‌نویس "']
for ch in checks:assert ch in s, ch
assert 'exam.put("examCategory","ongoing")' not in s
assert 'exam.put("academicPeriod","semester1")' not in s
assert 'android.webkit.WebView' not in s
assert 'versionCode 18' in (r/'app/build.gradle').read_text()
assert '"final".equals(category)' in s and 'return null' in helper
print('A10.1 STATIC VALIDATION',len(checks)+5,'PASS')
