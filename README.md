Strava Estimote (Beacon) starter
=============

What?
Starts Strava when you enter a Estimote beacon.

How?
Starts a service either when you launch the application or when you activate bluetooth.
This listens for the UUID for the device and will start Strava when you enter the zone that you have put your beacon.
When leaving the bike you it will stop and save the activity.


Problems?
This was done in a hurry, so there is not done much to stability, the service might not be started/runned and in that case it will not work.
