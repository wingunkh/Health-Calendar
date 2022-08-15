# :muscle:Health Calendar:muscle:

## :question:Introduce
##### 3학년 1학기 여름방학, 제가 쓰고 싶어서 만들게 된 **운동 기록 저장 앱** 입니다.

##### :calendar: [**Material Calendar View**](https://github.com/prolificinteractive/material-calendarview) 를 이용하였습니다.

## :iphone: ```Intro.kt```
<img src="https://user-images.githubusercontent.com/58140360/184626180-a8a017f5-da66-4cc7-9685-3b18749b30a7.jpg" width="250" height="500"/>

```kotlin
SystemClock.sleep(1000) //1초 대기
val intent=Intent(this,Start::class.java)
startActivity(intent) //인텐트를 이용한 액티비티 교체
```
> ##### 첫 시작화면은 코틀린 언어의 공식 로고이자 제 앱의 로고가 그려진 인트로 화면입니다.

> ##### 1초 대기 후 다음 화면으로 넘어가게 됩니다.

## :iphone: ```Start.kt```
