package com.leusoft.taskcore.datatypes

import android.util.Log
import java.time.LocalDate
import java.time.LocalTime


class EventStorage(var name: String,var description: String, var date: LocalDate, var time: LocalTime, var invitedName:String, var invitedDescription:String) {


    companion object {
        var eventsList: ArrayList<EventStorage> = ArrayList()
        fun eventsForDate(date: LocalDate): ArrayList<EventStorage> {
            val eventStorages: ArrayList<EventStorage> = ArrayList()
            for (event in eventsList) {
                if (event.date == date) eventStorages.add(event)
            }
            return eventStorages
        }
        fun eventsForDateAndTime(date: LocalDate?, time: LocalTime): ArrayList<EventStorage>? {
            val eventStorages: ArrayList<EventStorage> = ArrayList()
            for (event in eventsList) {
                val eventHour = event.time.hour
                val cellHour = time.hour
                Log.i("COMDAS",event.date.toString() + " " + date)
                if (event.date.equals(date) && eventHour == cellHour) eventStorages.add(event)
            }
            return eventStorages
        }

        fun wipeEventStorage()
        {
            eventsList = ArrayList()
        }

    }
}