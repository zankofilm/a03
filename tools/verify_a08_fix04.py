#!/usr/bin/env python3
"""Fail closed if the GitHub repo has old APK/source files or missing previous features."""
from pathlib import Path
ROOT=Path(__file__).resolve().parents[1]
s=(ROOT/'app/src/main/java/ir/scooljavanrood/madreseyar/teacher/MainActivity.java').read_text()
g=(ROOT/'app/build.gradle').read_text()
more=s.split('private void renderMore()',1)[1].split('private void weekView()',1)[0]
checks={
 'A08-FIX04 login screen marker':'A08-FIX04 · پایداری ورود و اینترنت' in s,
 'A08-FIX04 visible More menu marker':'نسخه A08-FIX04 · تکالیف فعال · بازیابی امن نشست' in more,
 'Version updated':'versionCode 15' in g and '0.8.4-native-fa-session-recovery-test' in g,
 'Homework entry intact':'this::loadHomework' in more and 'private void loadHomework()' in s,
 'Homework save intact':'/api/staff/homework/p652/save' in s,
 'Homework grading intact':'/api/staff/homework/grade' in s,
 'Class sync intact':'/api/p635/assignments' in s,
 'Biometric vault intact':(ROOT/'app/src/main/java/ir/scooljavanrood/madreseyar/teacher/BiometricVault.java').is_file(),
 'Jalali dates intact':(ROOT/'app/src/main/java/ir/scooljavanrood/madreseyar/teacher/JalaliDates.java').is_file(),
 'No web shell':'WebView' not in s,
}
for k,v in checks.items():print(('PASS' if v else 'FAIL'),k)
if not all(checks.values()):raise SystemExit('Outdated or incomplete repository; refusing to build')
print('A08-FIX04 source gate PASSED')
