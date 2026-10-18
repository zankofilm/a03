#!/usr/bin/env python3
"""Static targeted safety regression checks, NOT a substitute for Android instrumentation tests."""
from pathlib import Path
s=(Path(__file__).resolve().parents[1]/'app/src/main/java/ir/scooljavanrood/madreseyar/teacher/MainActivity.java').read_text()
error=s.split('private void background(Work work, Consumer<JSONObject> onSuccess)',1)[1].split('private String nameOfClass(',1)[0]
login=s.split('private void renderLogin()',1)[1].split('private void render() {',1)[0]
biom=s.split('private void enableBio()',1)[1].split('// A08 — Native Persian homework center.',1)[0]
checks={
    'network connectivity errors classified separately':'isConnectivityFailure(error)' in error,
    'network outage does not erase vault':'vault.clear()' not in error,
    '401 records server reauthentication requirement':'setServerReloginRequired(true)' in error,
    '401 invalidates token, not biometric enrollment':'token=""; unlocked=false;' in error,
    'login does not offer known-expired biometric token':'requiresServerRelogin()' in login and 'else{' in login,
    'successful login schedules resealing':'resealBiometricAfterLogin=vault.isEnabled();' in login,
    'new token resealed after biometric approval':'vault.saveAfterBiometric(verified,token);setServerReloginRequired(false)' in biom,
    'retry retains decrypted token in memory':'if(!token.isEmpty())fetchData();else renderLogin();' in s,
    'true logout erases vault':'final String old=token;token="";unlocked=false;vault.clear();' in s,
    'keystore invalidation erases vault':'catch(Exception e){vault.clear();setServerReloginRequired(false);' in biom,
    'application is still native':'WebView' not in s,
    'new release marker':'A08-FIX04' in s,
}
for name,good in checks.items():print(('PASS' if good else 'FAIL'),name)
if not all(checks.values()):raise SystemExit('FAIL: A08-FIX04 biometric/session safety invariants')
print('PASS',len(checks),'A08-FIX04 auth recovery static checks (no APK/device runtime claim).')
