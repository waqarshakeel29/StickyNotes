package dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import entity.NotePad
import entity.Widget

@Dao
interface WidgetDao {

    @Insert
    fun insert(Widget: Widget)

    @Query("DELETE FROM tbl_Widget")
    fun deleteAll()

    @Query("DELETE FROM tbl_Widget where widget_id = :wid")
    fun deleteWidget(wid : Int)

    @Query("DELETE FROM tbl_Widget where noteId = :noteId")
    fun deleteWidgetFromNoteId(noteId : Int)

    @Query("SELECT * FROM tbl_Widget")
    fun getAllWidget() : List<Widget>

    @Query("SELECT * FROM tbl_Widget where id = :id")
    fun getWidget(id : Int) : Widget

    @Query("SELECT widget_id FROM tbl_Widget where noteId = :noteId")
    fun getWidgetFromNote(noteId : Int) : Int

    @Query("SELECT * FROM tbl_Widget where widget_id = :wid")
    fun isWidgetExist(wid : Int) : Boolean

    @Query("SELECT isSet FROM tbl_Widget where widget_id = :wid")
    fun isNotedSelected(wid : Int) : Boolean

    @Query("UPDATE tbl_Widget SET isSet=:bool WHERE widget_id =:wid")
    fun setSelection(bool : Boolean,wid: Int)

    @Query("SELECT noteId FROM tbl_Widget where widget_id = :wid")
    fun getNoteId(wid : Int) : Int


}