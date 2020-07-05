package com.waqar.stickynotes

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.util.Log
import utility.DataGenerator
import android.content.ComponentName


/**
 * Implementation of App Widget functionality.
 */
class NoteWidget : AppWidgetProvider() {
    val EXTRA_ITEM = "com.sticky.notes.EXTRA_ITEM"


    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {

        // Perform this loop procedure for each App Widget that belongs to this provider

        appWidgetIds.forEach {appWidgetId ->
            // Create an Intent to launch Required Activity

            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("WID", appWidgetId)

            val pendingIntent = PendingIntent.getActivity(context, appWidgetId, intent, 0)

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            val views = RemoteViews(context.packageName, R.layout.note_widget)
            views.setOnClickPendingIntent(R.id.textViewWidget, pendingIntent)
            views.setOnClickPendingIntent(R.id.widgetTitle, pendingIntent)

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled

        var list = DataGenerator.with(MyApplication.appDatabase!!)!!.getAllWidgets()

        val appWidgetManager = AppWidgetManager.getInstance(context!!)
        val widgetIds = appWidgetManager.getAppWidgetIds(ComponentName(context!!, NoteWidget::class.java!!))
        for (appWidgetId in widgetIds) {
            list.forEachIndexed { index, widget ->
                if(widget.wId == appWidgetId){
                    list.removeAt(index)
                }
            }
        }
        list.forEach {
            var noteId = DataGenerator.with(MyApplication.appDatabase!!)!!.getNoteId(it.wId)
            DataGenerator.with(MyApplication.appDatabase!!)!!.deleteWidgetFromNoteID(noteId)
//            DataGenerator.with(MyApplication.appDatabase!!)!!.deleteWhere(noteId)
            Log.d("TAG","DELETED")
        }
    }

    companion object {
        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {

            val manager = AppWidgetManager.getInstance(context)
//            val infoList = manager.installedProviders
//            for (info in infoList) {
//                if(info.label == "Sticky Notes") {
//                    Log.d("TAG", "Name: " + info.label)
//                    Log.d("TAG", "Provider Name: " + info.provider)
//                    Log.d("TAG", "Configure Name: " + info.configure)
//                }
//            }

//            val intent = Intent(context, MainActivity::class.java)
//            intent.putExtra("WID",appWidgetId)


            var noteId = DataGenerator.with(MyApplication.appDatabase!!)!!.getNoteId(appWidgetId)
            var note = DataGenerator.with(MyApplication.appDatabase!!)!!.getNote(noteId)

            val remoteViews = RemoteViews(context.packageName, R.layout.note_widget)

            if(note == null){
                remoteViews.setTextViewText(R.id.textViewWidget, "Title"!!)
                remoteViews.setTextViewText(R.id.widgetTitle, "Text"!!)
            }else{
                remoteViews.setTextViewText(R.id.textViewWidget, note.text!!)
                remoteViews.setTextViewText(R.id.widgetTitle, note.title!!)
            }

            Log.d("TAG", "widgetID = $appWidgetId")
//            context.startActivity(intent)


//            Log.d("TAG","WID")
//            intent.action = ClickIntentService.ACTION_CLICK

//            var newIntent = Intent(context, MainActivity::class.java)
//            newIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
//            val pendingIntent = PendingIntent.getActivity(context, 0, newIntent, 0)

//            remoteViews.setOnClickPendingIntent(R.id.textViewWidget, pendingIntent)
//            remoteViews.setOnClickPendingIntent(R.id.widgetTitle, pendingIntent)

//            var noteId = DataGenerator.with(MyApplication.appDatabase!!)!!.getNoteId(appWidgetId)
//            var note = DataGenerator.with(MyApplication.appDatabase!!)!!.getNote(noteId)
//
//            if(note == null){
//                remoteViews.setTextViewText(R.id.textViewWidget, "Title"!!)
//                remoteViews.setTextViewText(R.id.widgetTitle, "Text"!!)
//            }else{
//                remoteViews.setTextViewText(R.id.textViewWidget, note.text!!)
//                remoteViews.setTextViewText(R.id.widgetTitle, note.title!!)
//            }
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
        }
    }
}

