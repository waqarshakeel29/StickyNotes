package database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dao.NotePadDao
import dao.WidgetDao
import entity.NotePad
import entity.Widget

@Database(entities = arrayOf(NotePad::class,Widget::class), version = 1)
open abstract class AppDatabase  : RoomDatabase() {


    abstract fun NotePadDao() : NotePadDao
    abstract fun WidgetDao() : WidgetDao

    companion object {
        var DATABASE_NAME:String = "NoteDb"
        var instance: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase?
        {
            instance= Room.databaseBuilder(context.applicationContext,AppDatabase::class.java,DATABASE_NAME).allowMainThreadQueries().build()
            return instance
        }
    }

}