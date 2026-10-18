from pathlib import Path
import re
root=Path(__file__).resolve().parents[1]
a=(root/'app/src/main/java/ir/scooljavanrood/madreseyar/teacher/MainActivity.java').read_text(encoding='utf-8')
source=(root/'app/src/main/java/ir/scooljavanrood/madreseyar/teacher/SchoolApi.java').read_text(encoding='utf-8')
g=(root/'app/build.gradle').read_text(encoding='utf-8')
checks={
 'version10_with_a09_compat': "versionName '0.10.1-continuous-final-bank-drafts-test'" in g and 'versionCode 18' in g,
 'api_endpoint': '/api/staff/teacher-approved-questions/p67' in a,
 'only_get_approved': 'SchoolApi.request("/api/staff/teacher-approved-questions/p67","GET",token,null)' in a,
 'no_webview': 'WebView' not in a,
 'nav_button': 'moreAction("▣","کتابخانه سؤال‌های تأییدشده"' in a,
 'book_filter': 'approvedBookFilter' in a and 'getSelectedItemPosition()' in a,
 'text_filter': 'approvedTextFilter' in a,
 'pagination': 'final int size=30' in a,
 'question_detail': 'showApprovedQuestionDetail' in a and 'referenceAnswer' in a and 'explanation' in a,
 'approved_server_guard': 'data.optBoolean("ok",false)' in a,
 'gradebook_unchanged': 'GradebookRules' in a and 'showStudentGradebook' in a,
 'homework_unchanged': 'loadHomework' in a and 'HomeworkRules' in a,
 'bio_unchanged': 'BiometricVault' in a,
 'persian_locale': 'attachBaseContext' in a and 'فارسی' in a,
 'sdk': 'https://' in source,
 'no_server_source': not (root/'server.mjs').exists(),
 'no_data_dir': not (root/'data').exists(),
}
for key,v in checks.items():
 print(('PASS' if v else 'FAIL'),key)
if not all(checks.values()): raise SystemExit('A09 source preflight FAILED')
print('A09 feature compatibility under A10:',len(checks),'checks passed')
