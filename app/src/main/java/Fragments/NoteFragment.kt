package Fragments


import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.waqar.stickynotes.MainActivity
import com.waqar.stickynotes.MyApplication
import com.waqar.stickynotes.NoteWidget

import com.waqar.stickynotes.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_note.*
import utility.DataGenerator
import android.app.Activity
import android.view.inputmethod.InputMethodManager
import android.widget.Toast


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class NoteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity!! as MainActivity).main_ActionBar.visibility = View.GONE
        var id = this!!.arguments!!["ID"].toString().toInt()
        var FROM = this!!.arguments!!["FROM"].toString()

        var note = DataGenerator.with(MyApplication.appDatabase!!)!!.getNote(id)
        if(note != null) {
            et_title.setText(note.title)
            et_text.setText(note.text)
        }else{
            et_title.setText("")
            et_text.setText("")
        }
        Iv_Save.setOnClickListener {
            DataGenerator.with(MyApplication.appDatabase!!)!!.updateNote(id,et_text.text.toString(),et_title.text.toString())
            val appWidgetManager = AppWidgetManager.getInstance(activity!!)
            val widgetIds = appWidgetManager.getAppWidgetIds(ComponentName(activity!!, NoteWidget::class.java!!))
            for (appWidgetId in widgetIds) {
                NoteWidget.updateAppWidget(activity!!, appWidgetManager, appWidgetId)
            }
            hideSoftKeyboard()
            Toast.makeText(activity!!,"Saved",Toast.LENGTH_SHORT).show()
        }

        Iv_Back.setOnClickListener {
            if(FROM.equals("WIDGET"))
                activity!!.finish()
            else
                activity!!.onBackPressed()
        }
    }
    fun hideSoftKeyboard() {
        if(et_text.hasFocus()) {
            val inputMethodManager = activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager!!.hideSoftInputFromWindow(activity!!.currentFocus!!.windowToken, 0)
        }
    }
    override fun onPause() {
//        (activity!! as MainActivity).finish()
        super.onPause()
    }
}