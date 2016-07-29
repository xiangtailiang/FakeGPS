#FakeGPS

[中文Readme](https://github.com/xiangtailiang/FakeGPS/README_CN.md)

FakeGPS is a GPS device simulator. GPS location signal keep output according to the given coordinates. By the direction keys on the joystick, the user can simulate walking on the map.

#Features
- Simulate the real GPS devices, output GPS location per second.
- Have two modes to set the new location: **Jump Mode** and **Flight Mode**. **Jump Mode**: jump to the new location in second. **Flight Mode**: fly to the new location according to the given time by linear interpolation.
- With a global floating joystick, the direction button will do a certain offset at the current position (via Move Step, degrees unit). Click to move a step, long press will move continuously.
- Bookmarks support. In the Bookmarks List, tap to use it, long press to Delete.  Long Press Bookmark button on the joystick to copy the current coordinates to the clipboard, make it easy to share it with other people.

#Screenshots.

! [Screenshot_1](./ screenshot / Screenshot_1.png)

! [Screenshot_2](./ screenshot / Screenshot_2.png)

! [Screenshot_3](./ screenshot / Screenshot_3.png)

# Download
[FakeGPS V1.0](https://github.com/xiangtailiang/FakeGPS/releases/tag/1.0)


#Installation
Since the FakeGPS need the `INSTALL_LOCATION_PROVIDER` permissions, so it need to installed as system app.

- 1, Root your phone.
- 2, Download [Lucky Patcher](https: //lucky-patcher.netbew.com/) and install it.  Launch Lucky Patcher and find "Rebuild & Install" at the bottom bar. Locate FakeGPS using the built-in exploerer in Lucky Patcher. Click the apk file and select "Install as a system app"
- 3, Uncheck "Allow mock location" in Settings > Developer Options. Enable "Location Services" on your device. It is suggested that you choose "Locate by GPS" instead of "Locate by network and GPS".
- 4, Launch FakeGPS, click Start button, the joystick will be displayed, if not pop up, turn on the applications floating window display authority (especially MIUI and Flyme, permission is off by default), and then Launch the Maps apps (Like Google Map)and check if it work.

# Contribute
If you would like to contribute code to FakeGPS, you can do so through GitHub by forking the repository and sending a pull request.

# License
[The MIT License (MIT)](http://opensource.org/licenses/MIT)
