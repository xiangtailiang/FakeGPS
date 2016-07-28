#FakeGPS

FakeGPS是一个GPS设备模拟器，能够根据给定的坐标，输出GPS定位信号，并且带有全局悬浮窗的手柄，通过手柄上的方向按键，能够模拟用户在地图上行走。

FakeGPS另外一个很有意思的用途就是Pokemon Go，在中国大陆地区，如果不伪造GPS位置，Pokemon Go的地图上是没有任何数据的。所以，借助FakeGPS后，就可以实现坐在室内却可以让游戏认为你在美国街头游走的效果。
不过，作者还是建议文明游戏，反对cheating。(*^__^*)

按照惯例，先放截图。

![Screenshot_1](./screenshot/Screenshot_1.png)

![Screenshot_2](./screenshot/Screenshot_2.png)

![Screenshot_3](./screenshot/Screenshot_3.png)

[下载地址](https://github.com/xiangtailiang/FakeGPS/blob/master/release/FakeGPS.apk)  `release/FakeGPS.apk`

#功能描述
- 模拟真实的GPS设备，每秒输出GPS定位信号。
- 具备两种运行模式：跳变模式和飞行模式。**跳变模式**：直接跳转到新的位置。**飞行模式**：根据给定的时间，以线性插值的方式慢慢飞行到新的位置。
- 带有全局浮窗手柄，点击手柄上的方向按键会在当前位置上做一定的偏移（通过Move Step设置，单位为度数）。手柄单击一次移动一格，长按会连续移动。
- 带有书签功能，点击使用，长按删除。
- 长按书签按钮可以复制当前坐标到剪切板，方便分享给其他小伙伴。

#安装说明
由于模拟位置需要借助系统的`ACCESS_MOCK_LOCATION`权限，因此FakeGPS 需要以system的权限，安装到system分区中。

- 1、Root手机，获取system分区的读写权限。
- 2、借助[Lucky Patcher](htt
ps://lucky-patcher.netbew.com/) 把FakeGPS以系统应用的方式安装。
- 3、打开Android系统设置开发者选项，取消“允许模拟位置”勾选。设置位置服务仅使用GPS设备。
- 4、打开FakeGPS，点击Start，这个时候手柄会展示出来，如果没有弹出，请打开应用的悬浮窗显示权限（特别是MIUI和Flyme，权限默认是关闭的），然后打开地图应用（谷歌地图和高德地图测试可用）。查看是否能够定位到设置的坐标，点击手柄上的按键检查是否能够正常移动。

#最后
时间比较匆忙，应用的体验还不是细致，有空再来完善。

欢迎各位同学发pull request来帮忙改进，谢谢大家。




