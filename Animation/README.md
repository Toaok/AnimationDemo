# Android Animation

## 分类

<table bgcolor="#000000">
<tr>
<th colspan="2">Android 动画类型</th><th>引入时间</th><th>所在包名</th><th>动画类的命名</th><th>动画所改变的对象</th>
</tr>
<tr><td rowspan="2">View Animation</td>
<td>Tween Animation</td>
<td rowspan="2">API Level 1 </td>
<td rowspan="2">android.view.animation</td>
<td rowspan="2">XXXXAnimation</td>
<td rowspan="2">仅仅转变的是控件的显示而已，并没有改变控件本身的值（如位置、大小、方向等）。</td>
</tr>
<tr>
<td>Frame Animation</td>
</tr>
<tr>
<td rowspan="2" >Property Animation</td>
<td>ValueAnimator</td>
<td rowspan="2">API Level 11 </td>
<td rowspan="2">android.animation</td>
<td rowspan="2">XXXXAnimator</td>
<td rowspan="2">作用于控件的属性，直接更改属性的值（如位置、大小、方向等）。</td>
</tr>
<tr>
<td>ObjectAnimator</td>
</tr>

</table>

## View Animation
    
#### Tween Animation:只能对派生自View的控件实例起作用;仅仅转变的是控件的显示而已，并没有改变控件本身的值（属性）。

###### 移动:TranslateAnimation
```Java
Animation translateAnimation;
translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, 0.8f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
translateAnimation.setDuration(3000);
```  
或
```xml
<?xml version="1.0" encoding="utf-8"?>
<translate xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="2000"
    android:fromXDelta="-100%p"
    android:fromYDelta="0%p"
    android:toXDelta="100%p"
    android:toYDelta="0%p"
    android:fillBefore="true"
    />
```

###### 旋转:RotateAnimation
```Java
Animation rotateAnimation;
rotateAnimation = new RotateAnimation(0.0f, 720.0f, Animation.RELATIVE_TO_SELF, 0.50f, Animation.RELATIVE_TO_SELF, 0.50f);
rotateAnimation.setDuration(3000);
```  
或

```xml
<?xml version="1.0" encoding="utf-8"?>
<rotate xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="3000"
    android:fillAfter="true"
    android:fromDegrees="0"
    android:pivotX="50%"
    android:pivotY="50%"
    android:toDegrees="360" />
```
###### 透明度:AlphaAnimation
```Java
Animation alphaAnimation;
alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
alphaAnimation.setDuration(3000);
```  
或
```xml
<?xml version="1.0" encoding="utf-8"?>
<alpha xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="1000"
    android:fillBefore="true"
    android:fromAlpha="1.0"
    android:toAlpha="0.1" />
```

###### 缩放:ScaleAnimation
```Java
Animation scaleAnimation;
scaleAnimation = new ScaleAnimation(0.0f, 1.8f, 0.0f, 1.8f, Animation.RELATIVE_TO_SELF, 0.50f, Animation.RELATIVE_TO_SELF, 0.50f);
scaleAnimation.setDuration(3000);
```  
或
```xml
<?xml version="1.0" encoding="utf-8"?>
<scale xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="1000"
    android:fromXScale="0.0"
    android:fromYScale="0.0"
    android:pivotX="50%p"
    android:pivotY="50%p"
    android:toXScale="1.4"
    android:toYScale="1.4" />
```
###### 组合动画：AnimationSet
```Java
AnimationSet setAnimation;
setAnimation = new AnimationSet(true);
setAnimation.addAnimation(alphaAnimation);
setAnimation.addAnimation(scaleAnimation);
setAnimation.addAnimation(rotateAnimation);
setAnimation.addAnimation(translateAnimation);
setAnimation.setDuration(3000);
```  
或
```xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="3000"
    android:fillAfter="false">
    <alpha
        android:fromAlpha="0.0"
        android:toAlpha="1.0" />
    <scale
        android:fromXScale="0.0"
        android:fromYScale="0.0"
        android:pivotX="50%"
        android:pivotY="50%"
        android:toXScale="1.8"
        android:toYScale="1.8" />

    <rotate
        android:fromDegrees="0"
        android:pivotX="50%"
        android:pivotY="50%"
        android:toDegrees="720" />

    <translate
        android:fromXDelta="-100%"
        android:fromYDelta="0"
        android:toXDelta="50%"
        android:toYDelta="0" />
</set>
```
    
#### Frame Animation:一帧帧的播放图片，利用人眼视觉残留原理，生成动画效果
```xml
<?xml version="1.0" encoding="utf-8"?>
<animation-list xmlns:android="http://schemas.android.com/apk/res/android"
    android:oneshot="false">
    <item
        android:drawable="@drawable/image_1"
        android:duration="300" />
    <item
        android:drawable="@drawable/image_2"
        android:duration="300" />
    <item
        android:drawable="@drawable/image_3"
        android:duration="300" />
    <item
        android:drawable="@drawable/image_4"
        android:duration="300" />
    <item
        android:drawable="@drawable/image_5"
        android:duration="300" />
    <item
        android:drawable="@drawable/image_6"
        android:duration="300" />
</animation-list>
```
### View Animation的使用方式
```java
 mView.startAnimation(mAnimation);
``` 

## Property Animation:

#### ValueAnimator: 



#### ObjectAnimator:

### Property Animation的使用方式