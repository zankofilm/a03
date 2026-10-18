from pathlib import Path
r=Path(__file__).resolve().parents[1]
s=(r/'app/src/main/java/ir/scooljavanrood/madreseyar/teacher/MainActivity.java').read_text()
api=(r/'app/src/main/java/ir/scooljavanrood/madreseyar/teacher/SchoolApi.java').read_text()
checks=['startNativeExamBuilder','showNativeExamCourses','showNativeDraftEditor','nativeDraftQuestions','confirmSaveNativeDraft','sendNativeDraft','/api/staff/teacher-exams/p673','"status","draft"','sourceQuestionId','new AlertDialog.Builder(this).setTitle("تأیید پیش‌نویس "+examType)']
for x in checks:assert x in s,x
assert 'android.webkit.WebView' not in s
assert 'https://scooljavanrood.ir/app' in api
assert 'versionCode 18' in (r/'app/build.gradle').read_text()
assert 'MainActivity.java' in str(next((r/'app/src/main/java').rglob('MainActivity.java')))
print('A10 STATIC',len(checks)+4,'/',len(checks)+4,'PASS')
