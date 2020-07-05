package entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tbl_Widget")
class Widget {


    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "widget_id")
    var wId: Int = 0

    @ColumnInfo(name = "noteId")
    var noteId: Int = 0

    @ColumnInfo(name = "isSet")
    var isSet: Boolean = false




}