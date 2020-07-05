package com.waqar.stickynotes

import Fragments.MainFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.content.Intent
import android.view.View
import kotlinx.android.synthetic.main.fragment_main.*


class MainActivity : AppCompatActivity() {


    var isLongClick = false
    var count = 0

    lateinit var mainFragment : Fragment
    override fun onNewIntent(intent: Intent?) {
        val extras = intent!!.extras

        Log.d("TAG","NEW INTENT")

        super.onNewIntent(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mainFragment = MainFragment()

        val intent = intent
        val extras = intent.extras
        if (extras != null) {
            var appWidgetId = extras.getInt("WID",0)

            var bundle = Bundle()
            bundle.putInt("widgetKey",appWidgetId)
            Log.d("TAG", "MAIN widgetID = $appWidgetId")
            fragmentTransaction(mainFragment,"MainFragment",bundle)
        }else
            fragmentTransaction(mainFragment,"MainFragment",null)
    }





    fun fragmentTransaction(fragment: Fragment, fragmentName: String, bundle: Bundle? = null) {
        val fragmentManager = supportFragmentManager

        count++
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragment.arguments = bundle
        fragmentTransaction.replace(R.id.Container, fragment)
        fragmentTransaction.addToBackStack(fragmentName)
        fragmentTransaction.commit()
    }


    override fun onBackPressed() {

        val intent = intent
        val extras = intent.extras
        if(extras != null)
            finish()

        if(count == 1) {

            if(!isLongClick)
                finish()
            else {
                isLongClick = false
                mainFragment.rv_MainList.adapter!!.notifyDataSetChanged()
            }


        } else {
            count--
            super.onBackPressed()
        }

    }

    fun imageVisible(){
        Iv_empty.visibility = View.VISIBLE
        rv_MainList.visibility = View.GONE
    }
}
