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
<img src="https://user-images.githubusercontent.com/58140360/184855625-9a1b4f33-4b0d-408a-8646-dbba70e09ff6.jpg" width="250" height="500"/>

```kotlin
val anim=AnimationUtils.loadAnimation(this,R.anim.blink)
binding.textView2.startAnimation(anim)
```
> ##### 인트로 화면이 지나고 난 후 보여지는 기본 화면 입니다.

> ##### 애니메이션에 의해서 "화면을 터치해서 시작" 문구가 깜빡이게 됩니다.
<br>
<img src="https://user-images.githubusercontent.com/58140360/184857753-1c660dfe-81f5-4d97-b2b8-791d0b754727.jpg" width="250" height="500"/>

```kotlin
class TodayDecorator(context: Activity):DayViewDecorator{ //현재 날짜를 강조시키는 데코레이터
    private val drawable: Drawable = context.getDrawable(R.drawable.border)!!
    private var calendar = Calendar.getInstance()

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        day?.copyTo(calendar)
        return day == CalendarDay.today()
    }

    override fun decorate(view: DayViewFacade?) {
        view?.setSelectionDrawable(drawable)
        view?.addSpan(object: StyleSpan(Typeface.BOLD){})
    }
}
```
> ##### TodayDecorator 클래스에 의해 현재 날짜에 하늘색 태두리가 표시되며 날짜가 진하게 표시됩니다.

> ##### 그 외 MondayDecorator 클래스에 의해 월요일이 비활성화 되며 (월요일은 제가 다니는 헬스장의 휴뮤일 입니다.)

> ##### SaturdayDecorator 클래스와 SundayDecorator 클래스에 의해 토요일과 일요일이 각각 파란색과 빨간색으로 표시됩니다.
