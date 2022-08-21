# :muscle:Health Calendar:muscle:
* ##### 2022-08-19 README.md 파일 최종 업로드
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
> ##### 인트로 화면이 지나고 난 후에 보여지는 기본 화면입니다.

> ##### 애니메이션에 의해 "화면을 터치해서 시작" 문구가 깜빡이게 됩니다.
## :iphone: ```Diary.kt```
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
> ##### TodayDecorator 클래스에 의해 현재 날짜에 하늘색 테두리가 표시되며 날짜가 진하게 표시됩니다.

> ##### 그 외 MondayDecorator 클래스에 의해 월요일이 비활성화되며 (월요일은 제가 다니는 헬스장의 휴뮤일입니다.)

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

> ##### 선택된 날짜가 오늘일 때, 운동 추가 버튼 및 저장 버튼이 활성화 되어 운동 기록을 자유롭게 작성 및 저장할 수 있습니다.

> ##### 이미 오늘 저장 버튼을 눌렀다면 운동 기록을 수정할 수 없으므로 신중히 기록해야 합니다.
<br>
<img src="https://user-images.githubusercontent.com/58140360/185601763-3734008e-2d66-4e0b-826f-8c4217527704.jpg" width="250" height="500"/>

```kotlin
class SaveDecorator(context: Activity , dates: Collection<CalendarDay>):DayViewDecorator{ //현재 날짜를 매개변수로 받아 도장을 달력에 찍는 데코레이터
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
<img src="https://user-images.githubusercontent.com/58140360/185603446-b3ae20b8-1308-4a9c-8df9-c9db5a30441c.jpg" width="250" height="500"/>

```kotlin
class PastDecorator(context: Activity, p1:Int, p2:Int, p3:Int):DayViewDecorator{ //날짜를 매개변수로 받아 해당 날짜의 도장을 달력에 표시하는 데코레이터
    private val drawable: Drawable = context.getDrawable(R.drawable.pic2)!!
    private var dates: ArrayList<CalendarDay> = ArrayList()
    private var nowyear=p1
    private var nowmonth=p2
    private var nowday=p3

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        var date = CalendarDay.from(nowyear, nowmonth, nowday)
        dates.add(date)
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade?) {
        view?.setBackgroundDrawable(drawable)
    }
}
```
> ##### 아령 도장이 저장되게 하는 기능을 구현하는 것이 가장 어려웠던 작업이었습니다.

> ##### PastDecorator 클래스에 의해 저장된 아령 도장들이 달력에 표시되게 됩니다.
<br>

```kotlin
binding.save.setOnClickListener { //저장 버튼을 누를 때 호출되는 이벤트 리스너
            // 생략 //
            pref.edit() {
                // 생략 //
                putString(cal.selectedDate.toString(),cal.selectedDate.toString())
                apply()
            }
            // 생략 //
}
```
> ##### 현재 날짜를 키로 하는 shared-preferences를 저장함으로써 각 날짜별로 shared-preferences 쌍을 만들 수 있었습니다.
<br>

```kotlin
fun show() { //지금까지 찍은 도장을 달력에 표시하는 함수
            for (i in nowday downTo 1) { //nowday 가 1까지 감소하면서 반복
                if (pref.getString("CalendarDay{$nowyear-$nowmonth-$i}", "") != "") {
                    cal.addDecorator(PastDecorator(mythis, nowyear, nowmonth, i))
                }

                if(i==1){
                    nowmonth--
                    if(nowmonth==1) {
                        if (nowyear == 2022) //2022년 1월 1일일 때
                            return //반복문 종료
                        else {
                            nowyear--
                            nowmonth = 12
                        }
                    }
                    nowday=31
                    show() //재귀 호출
                }
            }
}
```
> ##### show()함수는 현재 날짜를 기준으로 2022년 1월 1일까지 1일씩 감소하며 재귀호출을 하는 재귀함수입니다.

> ##### 특정 날짜에 해당 날짜를 키로 가지는 shared-preferences가 저장되어 있다면, PastDecorator 클래스 객체를 생성합니다.
<br>

```kotlin
class PastDecorator(context: Activity, p1:Int, p2:Int, p3:Int):DayViewDecorator{ //날짜를 매개변수로 받아 도장을 달력에 표시하는 데코레이터
    private val drawable: Drawable = context.getDrawable(R.drawable.pic2)!!
    private var dates: ArrayList<CalendarDay> = ArrayList()
    private var nowyear=p1
    private var nowmonth=p2
    private var nowday=p3

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        var date = CalendarDay.from(nowyear, nowmonth, nowday)
        dates.add(date)
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade?) {
        view?.setBackgroundDrawable(drawable)
    }
}
```
> ##### 이렇게 PastDecorator 클래스 객체를 생성하여 저장된 아령 도장들이 달력에 표시됩니다.
<br>
<img src="https://user-images.githubusercontent.com/58140360/185303823-befa7036-dc93-4919-bedc-5ec7e2110c17.jpg" width="250" height="500"/>

> ##### 귀여운 아령 도장을 누르면 언제든지 해당 날짜의 운동 기록을 확인할 수 있습니다.
<br>
<img src="https://user-images.githubusercontent.com/58140360/185313163-d28ad09b-82d1-41de-92b6-7117a2ab79ec.jpg" width="250" height="500"/>

> ##### 해당 날짜에 운동하지 않았다면, 운동 기록이 없다는 문구를 확인할 수 있습니다.
<br>
<img src="https://user-images.githubusercontent.com/58140360/185622865-5e7c2341-85a7-44b4-92a7-2297fe49b260.jpg" width="250" height="500"/>

```kotlin
override fun onBackPressed() { //뒤로가기 버튼 오버라이딩
        if(binding.scrollView.visibility==View.VISIBLE) { //스크롤뷰가 visible 일 때
            binding.scrollView.visibility=View.GONE //스크롤뷰를 gone 으로 설정
            return
        }

        val handler=DialogInterface.OnClickListener{ p0, p1 ->
            if(p1==DialogInterface.BUTTON_POSITIVE){
                ActivityCompat.finishAffinity(this@Diary) //액티비티 종료
                exitProcess(0) //프로세스 종료
            }
        }

        AlertDialog.Builder(this).run{ //AlterDialog 클래스의 빌더를 이용해 알림 창을 구현
            setTitle("Health Calendar")
            setIcon(android.R.drawable.ic_dialog_info)
            setMessage("정말 종료하시겠습니까?")
            setPositiveButton("예",handler)
            setNegativeButton("아니오",null)
            show()
        }
}
```
> ##### 뒤로가기 버튼을 적절하게 오버라이딩하여 편의성을 증가시켰습니다.

> ##### 운동 기록을 보여주는 뷰가 활성화된 상태에서 뒤로가기 버튼을 누르면 뷰를 숨깁니다.

> ##### 그 외의 상태에서 뒤로가기 버튼을 누르면 앱을 종료할 것인지를 물어보는 커스텀 다이얼로그 창이 나타나며 "예" 버튼을 눌러서 앱을 종료할 수 있습니다.
