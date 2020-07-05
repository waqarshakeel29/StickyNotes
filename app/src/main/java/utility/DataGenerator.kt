package utility

import database.AppDatabase
import entity.NotePad
import entity.Widget

class DataGenerator {

    companion object {
        var instance: DataGenerator? = null
        var database: AppDatabase? = null

        fun with(appDatabase: AppDatabase): DataGenerator? {
            if (database == null)
                database = appDatabase

            if (instance == null)
                instance = DataGenerator()
            return instance
        }
    }

        fun generateNote(title: String,text: String) : Int {
            if (database == null)
                return -1
            var notepad: NotePad

            notepad = noteInstance(title,text)


            database!!.NotePadDao().insert(notepad)
            return database!!.NotePadDao().getMaxId()
        }

        private fun noteInstance(title: String,text : String): NotePad {
            var notepad: NotePad = NotePad()
            notepad.title = title
            notepad.text = text
            return notepad
        }


        fun updateNote(id : Int,text : String,title :String){
            database!!.NotePadDao().updateNote(id,text,title)
        }

        fun deleteWhere(id:Int){
            database!!.NotePadDao().deleteNote(id)
        }

        fun isEmpty() : Boolean{
            return database!!.NotePadDao().isEmpty()
        }

        fun getNotes() : List<NotePad>{
            return database!!.NotePadDao().getNotes()
        }

        fun getNote(id : Int) : NotePad{
            return database!!.NotePadDao().getNote(id)
        }


    //////////////////////////////////////
    //WIDGET

        fun generateWidget(widgetId : Int,noteId : Int){
            var widget : Widget = widgetInstance(widgetId,noteId)


            database!!.WidgetDao().insert(widget)
        }

        private fun widgetInstance(widgetId : Int,noteId : Int): Widget {
            var widget: Widget = Widget()
            widget.wId = widgetId
            widget.noteId = noteId
            widget.isSet = false
            return widget
        }

        fun getAllWidgets() : ArrayList<Widget>{
            var list = database!!.WidgetDao().getAllWidget()
            var arrayList = ArrayList<Widget>()
            list.forEach {
                arrayList.add(it)
            }
            return arrayList
        }

        fun deleteWhereWidgetId(wid:Int){
            database!!.WidgetDao().deleteWidget(wid)
        }

        fun deleteWidgetFromNoteID(noteId : Int){
            database!!.WidgetDao().deleteWidgetFromNoteId(noteId)
        }

        fun isWidgetExists(wid : Int) : Boolean{
            return database!!.WidgetDao().isWidgetExist(wid)
        }

        fun isNoteSelected(wid : Int) : Boolean{
            return database!!.WidgetDao().isNotedSelected(wid)
        }

        fun getNoteId(wid : Int) : Int{
            return database!!.WidgetDao().getNoteId(wid)
        }

        fun getWidgetFromNote(noteId : Int) : Int {
            return database!!.WidgetDao().getWidgetFromNote(noteId)
        }

        fun setSelection(bool : Boolean,wid: Int){
            database!!.WidgetDao().setSelection(bool,wid)
        }

    }