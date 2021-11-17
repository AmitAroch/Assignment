package com.motorola.interviewAssignment.ui.userDetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    application: Application,
) : AndroidViewModel(application) {

    private var daysTillNextBirthday: Long = 0

    fun daysTillNextBirthdayFlow(birthday: String) = flow {
        setUsersBirthday(birthday)
        emit(daysTillNextBirthday.toString())
        delay(calculateMillisecondsTillEndOfDay())
        while (true) {
            daysTillNextBirthday.minus(1)
            emit(daysTillNextBirthday.toString())
            delay(TimeUnit.DAYS.toMillis(1))
        }
    }

    private fun calculateMillisecondsTillEndOfDay(): Long {
        val now = LocalDateTime.now()
        val endOfDay = Calendar.getInstance()
        endOfDay.set(now.year, now.monthValue - 1, now.dayOfMonth + 1, 0, 0, 0)
        return endOfDay.timeInMillis - System.currentTimeMillis()
    }

    private fun calculateDaysTillNextBirthday(usersBirthday: LocalDateTime): Long {

        val now = LocalDateTime.now()
        var nextBirthdayYear: Int = now.year
        if (usersBirthday.dayOfYear < now.dayOfYear) nextBirthdayYear += 1

        val usersNextBirthday = Calendar.getInstance()
        usersNextBirthday.set(
            nextBirthdayYear,
            usersBirthday.monthValue - 1,
            usersBirthday.dayOfMonth
        )

        val today = Calendar.getInstance()
        today.set(
            now.year,
            now.monthValue - 1,
            now.dayOfMonth
        )
        val millisecondTillNextBirthday =
            usersNextBirthday.timeInMillis - today.timeInMillis
        return TimeUnit.MILLISECONDS.toDays(
            millisecondTillNextBirthday
        )
    }

    private fun setUsersBirthday(birthday: String) {
        //"date": "1993-07-20T09:44:18.674Z"
        val birthdayLDT = LocalDateTime.parse(
            birthday,
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'.'SSS'Z'")
        )
        daysTillNextBirthday = calculateDaysTillNextBirthday(birthdayLDT)
    }


}