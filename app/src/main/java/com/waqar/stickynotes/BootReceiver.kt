package com.waqar.stickynotes

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context


class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val widgetIds = appWidgetManager.getAppWidgetIds(ComponentName(context, NoteWidget::class.java!!))
        for (appWidgetId in widgetIds) {
            NoteWidget.updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

}