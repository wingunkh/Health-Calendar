package com.example.khg

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.edit
import com.example.khg.databinding.DiaryBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import java.util.*
import kotlin.system.exitProcess

class Diary : AppCompatActivity() {
    private val binding by lazy { DiaryBinding.inflate(layoutInflater) }
    //lazy 키워드를 이용하여 최초 호출 시 초기화
    private val pref by lazy { getSharedPreferences("MY-SETTINGS", 0) }
    //shared-preferences 객체 생성
    private var mythis=this
    //this : diary.kt
    private lateinit var today:CalendarDay
    //setOnDateChangedListener 외부에서도 사용할 수 있게 date 를 저장할 변수
    private var nowyear=CalendarDay.today().year
    //현재 날짜의 연도
    private var nowmonth=CalendarDay.today().month
    //현재 날짜의 월
    private var nowday=CalendarDay.today().day
    //현재 날짜의 일

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        setContentView(binding.root) //diary.xml 파일 화면 출력

        val cal = binding.calendar
        cal.addDecorator(TodayDecorator(this))
        cal.addDecorator(MondayDecorator())
        cal.addDecorator(SaturdayDecorator())
        cal.addDecorator(SundayDecorator())

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

        show()

        fun invisible() { //스크롤 뷰 내부의 모든 뷰를 숨기는 함수
            binding.empty.visibility=View.INVISIBLE

            binding.ex1.visibility = View.INVISIBLE
            binding.ex2.visibility = View.INVISIBLE
            binding.ex3.visibility = View.INVISIBLE
            binding.ex4.visibility = View.INVISIBLE
            binding.ex5.visibility = View.INVISIBLE

            binding.ex1Name.visibility = View.INVISIBLE
            binding.ex2Name.visibility = View.INVISIBLE
            binding.ex3Name.visibility = View.INVISIBLE
            binding.ex4Name.visibility = View.INVISIBLE
            binding.ex5Name.visibility = View.INVISIBLE

            binding.ex1Weight.visibility = View.INVISIBLE
            binding.ex2Weight.visibility = View.INVISIBLE
            binding.ex3Weight.visibility = View.INVISIBLE
            binding.ex4Weight.visibility = View.INVISIBLE
            binding.ex5Weight.visibility = View.INVISIBLE

            binding.ex1Repeat.visibility = View.INVISIBLE
            binding.ex2Repeat.visibility = View.INVISIBLE
            binding.ex3Repeat.visibility = View.INVISIBLE
            binding.ex4Repeat.visibility = View.INVISIBLE
            binding.ex5Repeat.visibility = View.INVISIBLE

            binding.ex1Kg.visibility = View.INVISIBLE
            binding.ex2Kg.visibility = View.INVISIBLE
            binding.ex3Kg.visibility = View.INVISIBLE
            binding.ex4Kg.visibility = View.INVISIBLE
            binding.ex5Kg.visibility = View.INVISIBLE

            binding.ex1Set.visibility = View.INVISIBLE
            binding.ex2Set.visibility = View.INVISIBLE
            binding.ex3Set.visibility = View.INVISIBLE
            binding.ex4Set.visibility = View.INVISIBLE
            binding.ex5Set.visibility = View.INVISIBLE

            binding.addEx2.visibility = View.INVISIBLE
            binding.addEx3.visibility = View.INVISIBLE
            binding.addEx4.visibility = View.INVISIBLE
            binding.addEx5.visibility = View.INVISIBLE
        }

        fun readPref() { //selectedDate(선택된 날짜)를 포함하는 이름을 가진 키의 값을 가져오는 함수
            binding.ex1Name.setText(pref.getString("name1" + cal.selectedDate, ""))
            binding.ex1Weight.setText(pref.getString("weight1" + cal.selectedDate, ""))
            binding.ex1Repeat.setText(pref.getString("repeat1" + cal.selectedDate, ""))

            binding.ex2Name.setText(pref.getString("name2" + cal.selectedDate, ""))
            binding.ex2Weight.setText(pref.getString("weight2" + cal.selectedDate, ""))
            binding.ex2Repeat.setText(pref.getString("repeat2" + cal.selectedDate, ""))

            binding.ex3Name.setText(pref.getString("name3" + cal.selectedDate, ""))
            binding.ex3Weight.setText(pref.getString("weight3" + cal.selectedDate, ""))
            binding.ex3Repeat.setText(pref.getString("repeat3" + cal.selectedDate, ""))

            binding.ex4Name.setText(pref.getString("name4" + cal.selectedDate, ""))
            binding.ex4Weight.setText(pref.getString("weight4" + cal.selectedDate, ""))
            binding.ex4Repeat.setText(pref.getString("repeat4" + cal.selectedDate, ""))

            binding.ex5Name.setText(pref.getString("name5" + cal.selectedDate, ""))
            binding.ex5Weight.setText(pref.getString("weight5" + cal.selectedDate, ""))
            binding.ex5Repeat.setText(pref.getString("repeat5" + cal.selectedDate, ""))
        }

        fun composer(){ //기록된 운동의 수 만큼 운동 뷰를 노출시키는 함수
            if (binding.ex1Name.text.toString() != "") { //ex1Name 의 editText 내용이 비어있지 않다면 (적혀있다면)
                binding.ex1.visibility = View.VISIBLE
                binding.ex1Name.visibility = View.VISIBLE
                binding.ex1Weight.visibility = View.VISIBLE
                binding.ex1Repeat.visibility = View.VISIBLE
                binding.ex1Kg.visibility = View.VISIBLE
                binding.ex1Set.visibility = View.VISIBLE

                if (binding.ex2Name.text.toString() != "") {
                    binding.ex2.visibility = View.VISIBLE
                    binding.ex2Name.visibility = View.VISIBLE
                    binding.ex2Weight.visibility = View.VISIBLE
                    binding.ex2Repeat.visibility = View.VISIBLE
                    binding.ex2Kg.visibility = View.VISIBLE
                    binding.ex2Set.visibility = View.VISIBLE

                    if (binding.ex3Name.text.toString() != "") {
                        binding.ex3.visibility = View.VISIBLE
                        binding.ex3Name.visibility = View.VISIBLE
                        binding.ex3Weight.visibility = View.VISIBLE
                        binding.ex3Repeat.visibility = View.VISIBLE
                        binding.ex3Kg.visibility = View.VISIBLE
                        binding.ex3Set.visibility = View.VISIBLE

                        if (binding.ex4Name.text.toString() != "") {
                            binding.ex4.visibility = View.VISIBLE
                            binding.ex4Name.visibility = View.VISIBLE
                            binding.ex4Weight.visibility = View.VISIBLE
                            binding.ex4Repeat.visibility = View.VISIBLE
                            binding.ex4Kg.visibility = View.VISIBLE
                            binding.ex4Set.visibility = View.VISIBLE

                            if (binding.ex5Name.text.toString() != "") {
                                binding.ex5.visibility = View.VISIBLE
                                binding.ex5Name.visibility = View.VISIBLE
                                binding.ex5Weight.visibility = View.VISIBLE
                                binding.ex5Repeat.visibility = View.VISIBLE
                                binding.ex5Kg.visibility = View.VISIBLE
                                binding.ex5Set.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }

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
                    binding.addEx2.visibility = View.VISIBLE
                    binding.save.visibility = View.VISIBLE //저장 버튼 활성화
                }
            }
            else { //selectedDate(선택된 날짜)가 오늘이 아닐 때,
                binding.save.visibility = View.GONE //저장 버튼 비활성화
                if (binding.ex1Name.text.toString() == "")  //ex1Name 의 editText 내용이 비어있다면 (적혀있지 않다면)
                    binding.empty.visibility=View.VISIBLE //운동 기록이 없을 때의 문구 출력
            }
        }

        binding.save.setOnClickListener { //저장 버튼을 누를 때 호출되는 이벤트 리스너
            cal.addDecorator(SaveDecorator(mythis, Collections.singleton(today)))
            pref.edit() {
                putString("name1" + cal.selectedDate, binding.ex1Name.text.toString())
                putString("weight1" + cal.selectedDate, binding.ex1Weight.text.toString())
                putString("repeat1" + cal.selectedDate, binding.ex1Repeat.text.toString())

                putString("name2" + cal.selectedDate, binding.ex2Name.text.toString())
                putString("weight2" + cal.selectedDate, binding.ex2Weight.text.toString())
                putString("repeat2" + cal.selectedDate, binding.ex2Repeat.text.toString())

                putString("name3" + cal.selectedDate, binding.ex3Name.text.toString())
                putString("weight3" + cal.selectedDate, binding.ex3Weight.text.toString())
                putString("repeat3" + cal.selectedDate, binding.ex3Repeat.text.toString())

                putString("name4" + cal.selectedDate, binding.ex4Name.text.toString())
                putString("weight4" + cal.selectedDate, binding.ex4Weight.text.toString())
                putString("repeat4" + cal.selectedDate, binding.ex4Repeat.text.toString())

                putString("name5" + cal.selectedDate, binding.ex5Name.text.toString())
                putString("weight5" + cal.selectedDate, binding.ex5Weight.text.toString())
                putString("repeat5" + cal.selectedDate, binding.ex5Repeat.text.toString())

                putString(cal.selectedDate.toString(),cal.selectedDate.toString())
                //현재 날짜를 키로 하는 shared-preferences 를 저장함으로써 show()함수에서 사용한다.

                //shared-preferences 는 키와 값의 쌍으로 데이터를 저장한다.
                //이 때 키의 이름에 selectedDate(선택된 날짜)를 포함시킴으로써 각 날짜별로 쌍을 만들 수 있다.
                apply()
            }
            binding.scrollView.visibility = View.GONE
        }

        binding.cancel.setOnClickListener { //취소 버튼을 누를 때 호출되는 이벤트 리스너
            binding.scrollView.visibility = View.GONE
        }

        binding.addEx2.setOnClickListener { //첫번째 운동 추가 버튼
            binding.ex2.visibility = View.VISIBLE
            binding.ex2Name.visibility = View.VISIBLE
            binding.ex2Weight.visibility = View.VISIBLE
            binding.ex2Set.visibility = View.VISIBLE
            binding.ex2Kg.visibility = View.VISIBLE
            binding.ex2Repeat.visibility = View.VISIBLE
            binding.addEx3.visibility = View.VISIBLE
            binding.addEx2.visibility = View.INVISIBLE
        }

        binding.addEx3.setOnClickListener {
            binding.ex3.visibility = View.VISIBLE
            binding.ex3Name.visibility = View.VISIBLE
            binding.ex3Weight.visibility = View.VISIBLE
            binding.ex3Set.visibility = View.VISIBLE
            binding.ex3Kg.visibility = View.VISIBLE
            binding.ex3Repeat.visibility = View.VISIBLE
            binding.addEx4.visibility = View.VISIBLE
            binding.addEx3.visibility = View.INVISIBLE
        }

        binding.addEx4.setOnClickListener {
            binding.ex4.visibility = View.VISIBLE
            binding.ex4Name.visibility = View.VISIBLE
            binding.ex4Weight.visibility = View.VISIBLE
            binding.ex4Set.visibility = View.VISIBLE
            binding.ex4Kg.visibility = View.VISIBLE
            binding.ex4Repeat.visibility = View.VISIBLE
            binding.addEx5.visibility = View.VISIBLE
            binding.addEx4.visibility = View.INVISIBLE
        }

        binding.addEx5.setOnClickListener {
            binding.ex5.visibility = View.VISIBLE
            binding.ex5Name.visibility = View.VISIBLE
            binding.ex5Weight.visibility = View.VISIBLE
            binding.ex5Set.visibility = View.VISIBLE
            binding.ex5Kg.visibility = View.VISIBLE
            binding.ex5Repeat.visibility = View.VISIBLE
            binding.addEx5.visibility = View.INVISIBLE
        }
    }

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
}

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

class MondayDecorator:DayViewDecorator{ //월요일을 비활성화 시키는 데코레이터
    private val calendar = Calendar.getInstance()
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        day?.copyTo(calendar)
        val weekDay = calendar.get(Calendar.DAY_OF_WEEK)
        return weekDay == Calendar.MONDAY
    }

    override fun decorate(view: DayViewFacade?) {
        view?.setDaysDisabled(true)
    }
}

class SaturdayDecorator:DayViewDecorator{ //토요일을 파란색으로 보이게 하는 데코레이터
    private val calendar = Calendar.getInstance()
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        day?.copyTo(calendar)
        val weekDay = calendar.get(Calendar.DAY_OF_WEEK)
        return weekDay == Calendar.SATURDAY
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(object: ForegroundColorSpan(Color.BLUE){})
    }
}

class SundayDecorator:DayViewDecorator{ //일요일을 빨간색으로 보이게 하는 데코레이터
    private val calendar = Calendar.getInstance()
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        day?.copyTo(calendar)
        val weekDay = calendar.get(Calendar.DAY_OF_WEEK)
        return weekDay == Calendar.SUNDAY
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(object: ForegroundColorSpan(Color.RED){})
    }
}

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