package Fragments


import Adapters.ToDoAdapter
import Models.ToDoModel
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.waqar.stickynotes.MainActivity
import com.waqar.stickynotes.MyApplication

import com.waqar.stickynotes.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import utility.DataGenerator

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MainFragment : Fragment() {

    var list : MutableList<ToDoModel>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity!! as MainActivity).main_ActionBar.visibility = View.VISIBLE

            list = mutableListOf<ToDoModel>()
            var NoteList = DataGenerator.with(MyApplication.appDatabase!!)!!.getNotes()


            NoteList.forEach {
                list!!.add(ToDoModel(it.id, it.title, it.text, 0))
            }

        if(list!!.size == 0){
            (activity!! as MainActivity).imageVisible()
        }

//            Log.d("TAG", NoteList.size.toString() + " ____ " + list!!.size)

        fab_addNote.setOnClickListener {
            var dialog = AlertDialog.Builder(activity!!)
                .setView(R.layout.add_title_dialog)
                .setCancelable(false)
                .show()
            var save = dialog.findViewById<Button>(R.id.save)
            var editText = dialog.findViewById<EditText>(R.id.et_dialog)
            var delete = dialog.findViewById<Button>(R.id.delete)

            save!!.setOnClickListener {

                if(editText!!.text.toString() != ""){
                    var id =
                        DataGenerator.with(MyApplication.appDatabase!!)!!.generateNote(editText!!.text.toString(), "")


                    list!!.add(ToDoModel(id, editText!!.text.toString(), "", 0/*Color.parseColor("#FFFFFF")*/))
                    rv_MainList.adapter!!.notifyItemChanged(list!!.size - 1)
                    rv_MainList.scrollToPosition(list!!.size - 1)
                    dialog.dismiss()
                    imageInvisible()
                }else{
                    Toast.makeText(activity!!,"Name must not be empty",Toast.LENGTH_SHORT).show()
                }
            }

            delete!!.setOnClickListener {
                dialog.dismiss()
            }
//
//            list.add(ToDoModel("WAQAR","Waqars List",Color.GRAY))
//            rv_MainList.adapter!!.notifyItemChanged(list.size-1)
//            rv_MainList.scrollToPosition(list.size-1)
        }

        rv_MainList.layoutManager = GridLayoutManager(activity!!, 2) as GridLayoutManager
        if(arguments == null) {


            rv_MainList.adapter = ToDoAdapter(activity!!, list!!)
            Log.d("TAG","id = "  + "is NULL")

        }else{

//            (activity!! as MainActivity).tvTitleText.setText("Select Note For Widget")

            var wid = arguments!!["widgetKey"].toString().toInt()


            if(DataGenerator.with(MyApplication.appDatabase!!)!!.isNoteSelected(wid)) {

                var noteId = DataGenerator.with(MyApplication.appDatabase!!)!!.getNoteId(wid)
                var bundle = Bundle()
                bundle.putInt("ID", noteId)
                bundle.putString("FROM", "WIDGET")
                (activity!! as MainActivity).fragmentTransaction(NoteFragment(), "NoteFragment", bundle)
                Log.d("TAG","TYPE = 0")
                rv_MainList.adapter = ToDoAdapter(activity!!, list!!, 0, wid)
            }else{
                Log.d("TAG","TYPE = 1")
                rv_MainList.adapter = ToDoAdapter(activity!!, list!!, 1, wid)
            }
        }
    }


    private fun imageInvisible(){
        Iv_empty.visibility = View.GONE
        rv_MainList.visibility = View.VISIBLE
    }

}