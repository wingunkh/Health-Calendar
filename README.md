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
<br>
<img src="https://user-images.githubusercontent.com/58140360/185295251-22166101-a76c-4fd8-8596-a76d5ca0fb19.jpg" width="250" height="500"/>

```kotlin
cal.setOnDateChangedListener { widget, date, selected -> //날짜를 선택했을 때 호출되는 이벤트 리스너
            binding.scrollView.visibility = View.VISIBLE
            invisible()
            readPref()
            composer()

            if (date.toString() == CalendarDay.today().toString()) { //selectedDate(선택된 날짜)가 오늘일 때,
                today=date //date 를 변수 today 에 대입
                if (binding.ex1Name.text.toString() != "") { //ex1Name 의 editText 내용이 비어있지 않다면 (적혀있다면)
                    binding.addEx2.visibility = View.INVISIBLE //첫번째 운동 추가 버튼 비활성화
                    binding.save.visibility = View.INVISIBLE //저장 버튼 비활성화
                }
                else { //오늘 저장 버튼을 누른 적이 없다면
                    binding.ex1.visibility = View.VISIBLE
                    binding.ex1Name.visibility = View.VISIBLE
                    binding.ex1Weight.visibility = View.VISIBLE
                    binding.ex1Repeat.visibility = View.VISIBLE
                    binding.ex1Kg.visibility = View.VISIBLE
                    binding.ex1Set.visibility = View.VISIBLE
                    binding.addEx2.visibility = View.VISIBLE //운동 추가 버튼 활성화
                    binding.save.visibility = View.VISIBLE //저장 버튼 활성화
                }
            }
            else { //selectedDate(선택된 날짜)가 오늘이 아닐 때,
                binding.save.visibility = View.GONE //저장 버튼 비활성화
                if (binding.ex1Name.text.toString() == "")  //ex1Name 의 editText 내용이 비어있다면 (적혀있지 않다면)
                    binding.empty.visibility=View.VISIBLE //운동 기록이 없을 때의 문구 출력
            }
        }
```

> ##### 날짜를 선택하면 setOnDateChengedListener 이벤트 리스너가 호출됩니다.
<br>
<img src="https://user-images.githubusercontent.com/58140360/185299219-0d7cd95e-9c0e-4e7e-8280-831cbd6e1463.jpg" width="250" height="500"/>

> ##### if문에 의해서 선택된 날짜가 오늘일 때, 운동 추가 버튼 및 저장 버튼이 활성화 되어 운동 기록을 자유롭게 작성 및 저장할 수 있습니다.
<br>
<img src="https://user-images.githubusercontent.com/58140360/185301390-6ff8c845-7dd5-4a02-9323-2ebb0a9e2586.jpg" width="250" height="500"/>

```kotlin
class SaveDecorator(context: Activity , dates: Collection<CalendarDay>):DayViewDecorator{ //현재 날짜를 매개변수로 받아 도장을 달력에 표시하는 데코레이터
    private val drawable: Drawable = context.getDrawable(R.drawable.pic2)!!
    private var dates: HashSet<CalendarDay> = HashSet(dates)

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade?) {
        view?.setBackgroundDrawable(drawable)
    }
}
```

> ##### 저장 버튼을 누르면 SaveDecorator 클래스에 의해 해당 날짜에 귀여운 아령 도장이 찍히게 됩니다.
<br>
<img src="https://user-images.githubusercontent.com/58140360/185303823-befa7036-dc93-4919-bedc-5ec7e2110c17.jpg" width="250" height="500"/>

> ##### 귀여운 아령 도장을 누르면 언제든지 과거의 운동 기록을 확인할 수 있습니다.
