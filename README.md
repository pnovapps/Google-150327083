# Accessibility Service does not function after upgrade on Android 10

[View Google Ticket...](https://issuetracker.google.com/issues/150327083)

## TL;DR

An application containing an active AccessibilityService fails to update consistently on Android 10. The behaviour is
O.S. and device dependent (e.g. a Galaxy S10e running Android 10 will update consistently whereas a Galaxy S9 running Android 10 will fail approx 1 time in 3; no issues have been reported on earlier android versions).  This issue is blocking us from pushing any updates to any android 10 devices.

The problem only seems to happen when updates are pushed by the MDM, updating with android studio or adb does not show this problem.
### Actors

* BES12 MDM used to push the APK and updates
* Android 10 running on a Samsung Galaxy S10 (not S10e) or S9
* This repo's APK sample code

## Severity / Impact

High commercial impact: We currently have our APK deployed to over 10,000 devices globally; if the service fails to upgrade 1-in-3-times this means we need to support around a 3rd of our customer base! The application itself will cease functioning and potential loss of earning is significant.

## Steps to Reproduce

1. Create 2x signed APKs, 1 with version code of `1`, the 2nd with a version code of `2`.
1. Upload and publish the 1st using the MDM
1. Once pushed (installed) to a test device, activate the Accessibility Service from the Android settings menu
1. Running `logcat` against the test device will show the accessibility service events are functioning correctly
1. Now deploy the 2nd APK (version `2`) to the device

EXPECTED OUTCOME:
1. Our service updates as expected
1. We intercept a `android.intent.action.MY_PACKAGE_REPLACED` intent and display an overlay UI for 5-seconds

ACTUAL OUTCOME:
1. The service receives the `onServiceConnected` but `onAccessibilityEvent` is never called
1. When attempting to show an overlay, the following error occurs:

```bash
W WindowManager: Attempted to add Accessibility overlay window with unknown token null.  Aborting.
E MainActivity: android.view.WindowManager$BadTokenException: Unable to add window -- token null is not valid; is your activity running?
```
1. Toggling the service in accessibility settings has no effect however the service starts normally if the device is rebooted and then re-activated manually

Strangely this doesn't happen everytime, looks to be some kind of timing issue during the upgrade process?

