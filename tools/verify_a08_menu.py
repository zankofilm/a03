#!/usr/bin/env python3
from pathlib import Path
import re
ROOT=Path(__file__).resolve().parents[1]
s=(ROOT/'app/src/main/java/ir/scooljavanrood/madreseyar/teacher/MainActivity.java').read_text(encoding='utf-8')
b=(ROOT/'app/build.gradle').read_text(encoding='utf-8')
more=s.split('private void renderMore()',1)[1].split('private void weekView()',1)[0]
home=s.split('private void renderHome()',1)[1].split('private void renderClasses()',1)[0]
checks={
 'A08 native homework entry appears once':more.count('"مرکز تکالیف"') == 1,
 'menu entry opens REAL homework API': 'this::loadHomework' in more and 'private void loadHomework()' in s,
 'home shortcut leads to homework': 'homeworkShortcut.setOnClickListener(v->loadHomework())' in home,
 'visible menu release marker': 'نسخه A08-FIX03 · تکالیف فعال · همگام‌سازی کلاس‌ها' in more,
 'release version bumped': 'versionCode 14' in b and '0.8.3-native-fa-class-sync-test' in b,
 'homework save API preserved': 'SchoolApi.request("/api/staff/homework/p652/save","POST",token,body)' in s,
 'homework grade API preserved': 'SchoolApi.request("/api/staff/homework/grade","POST",token,body)' in s,
 'active class crosscheck preserved': 'SchoolApi.request("/api/p635/assignments","GET",token,null)' in s,
 'class-count helper preserved': (ROOT/'app/src/main/java/ir/scooljavanrood/madreseyar/teacher/TeacherClassRules.java').exists(),
 'Jalali module preserved': (ROOT/'app/src/main/java/ir/scooljavanrood/madreseyar/teacher/JalaliDates.java').exists(),
 'No WebView reintroduced': 'WebView' not in s,
}
for k,v in checks.items(): print(('PASS' if v else 'FAIL'),k)
if not all(checks.values()): raise SystemExit('This repo is NOT the A08-FIX03 source; refusing to build an outdated APK.')
print('A08-FIX03: source and menu markers verified; safe to compile.')
