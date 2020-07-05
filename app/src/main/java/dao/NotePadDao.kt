package dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import entity.NotePad

@Dao
interface NotePadDao {
    @Insert
    fun insert(apps: NotePad)

    @Query("DELETE FROM tbl_NotePad")
    fun deleteAll()

    @Query("DELETE FROM tbl_NotePad where id = :id")
    fun deleteNote(id : Int)


    @Query("SELECT * FROM tbl_NotePad where id = :id")
    fun getNote(id : Int) : NotePad

    @Query("SELECT * FROM tbl_NotePad")
    fun getNotes() : List<NotePad>


    @Query("UPDATE tbl_NotePad SET text = :text, title =:title  WHERE id =:id")
    fun updateNote(id : Int,text : String,title:String)

    @Query("SELECT count(*) FROM tbl_NotePad")
    fun isEmpty() : Boolean

    @Query("SELECT max(id) FROM tbl_NotePad")
    fun getMaxId() : Int


}