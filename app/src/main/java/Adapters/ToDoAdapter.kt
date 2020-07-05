package Adapters

import Fragments.NoteFragment
import Models.ToDoModel
import android.app.AlertDialog
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.waqar.stickynotes.MainActivity
import com.waqar.stickynotes.MyApplication
import com.waqar.stickynotes.NoteWidget
import com.waqar.stickynotes.R
import kotlinx.android.synthetic.main.main_list_item.view.*
import utility.DataGenerator
import android.util.DisplayMetrics


class ToDoAdapter(var context: Context,var list : MutableList<ToDoModel>,var type  :Int = 0,var wid : Int = 0) : RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {

//    var isLongClick = false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
       return ToDoViewHolder(LayoutInflater.from(context).inflate(R.layout.main_list_item,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.onBind(context,position,list,type,wid)
    }

    inner class ToDoViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun onBind(context: Context,position: Int,list: MutableList<ToDoModel>,type: Int,wid : Int){
            itemView.Tv_mainText.text = list[position].text
            itemView.Tv_title.setText(list[position].name)

            val displayMetrics = context.resources.displayMetrics

            itemView.Tv_mainText.layoutParams.height = (displayMetrics.widthPixels/2).toInt() -
                    convertDpToPixel(40f,context).toInt()


            itemView.Tv_mainText.setBackgroundColor(list[position].color)
            setAnimation(context,itemView)

            if ((context as MainActivity).isLongClick)
                itemView.Iv_close.visibility = View.VISIBLE
            else
                itemView.Iv_close.visibility = View.GONE

            if(type == 0) {

                itemView.Iv_close.setOnClickListener {

                    val appWidgetManager = AppWidgetManager.getInstance(context)
                    val widgetIds = appWidgetManager.getAppWidgetIds(ComponentName(context, NoteWidget::class.java!!))

                    var wid = DataGenerator.with(MyApplication.appDatabase!!)!!.getWidgetFromNote(list[position].id)
                    if (!widgetIds.contains(wid)) {
                        var id = list[position].id
                        list.removeAt(position)
                        DataGenerator.with(MyApplication.appDatabase!!)!!.deleteWidgetFromNoteID(id)
                        DataGenerator.with(MyApplication.appDatabase!!)!!.deleteWhere(id)
                        notifyDataSetChanged()

                        if(list.size == 0){
                            (context as MainActivity).imageVisible()
                            (context as MainActivity).isLongClick = false

                        }

                    } else {
                        AlertDialog.Builder(context)
                            .setTitle("Widget Exists")
                            .setMessage("Please remove widget before removing note.")
                            .setCancelable(false)
                            .setPositiveButton("Ok") { dialogInterface, i ->
                                dialogInterface.dismiss()
                            }.show()
                    }
//                if(list.size != position) {
//                    for(i in position..list.size){
//                        notifyItemChanged(i)
//                    }
//                }

                }

                itemView.setOnLongClickListener {

                    (context as MainActivity).isLongClick = true
                    notifyDataSetChanged()

                    true
                }

                itemView.setOnClickListener {

                    if (type == 0) {

                        if(!(context as MainActivity).isLongClick) {
                            var bundle = Bundle()
                            bundle.putInt("ID", list[position].id)
                            (context as MainActivity).fragmentTransaction(NoteFragment(), "NoteFragment", bundle)
                        }
                    }
                }

            }else{

                itemView.setOnClickListener {


                    var noteId = list[position].id
                    DataGenerator.with(MyApplication.appDatabase!!)!!.generateWidget(wid, noteId)
                    DataGenerator.with(MyApplication.appDatabase!!)!!.setSelection(true,wid)

                    val appWidgetManager = AppWidgetManager.getInstance(context!!)
                    NoteWidget.updateAppWidget(context!!, appWidgetManager, wid)
                    (context as MainActivity).finish()
                }

            }
        }

        fun convertDpToPixel(dp: Float, context: Context): Float {
            return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        }

        private fun setAnimation(context: Context,viewToAnimate: View) {
            val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
        }
    }
}