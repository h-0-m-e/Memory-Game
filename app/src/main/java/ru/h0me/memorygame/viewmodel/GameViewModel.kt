package ru.h0me.memorygame.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.h0me.memorygame.R

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val context = this.getApplication<Application>()

    val currentBalance = MutableLiveData(0)
    val currentBestTimeInSec = MutableLiveData(0L)

    val lastReward = MutableLiveData(0)

    val lastRoundTimeInSec = MutableLiveData(0L)

    private val catList = listOf(
        R.drawable.cat_1,
        R.drawable.cat_1,
        R.drawable.cat_2,
        R.drawable.cat_2,
        R.drawable.cat_3,
        R.drawable.cat_3,
        R.drawable.cat_4,
        R.drawable.cat_4,
        R.drawable.cat_5,
        R.drawable.cat_5,
        R.drawable.cat_6,
        R.drawable.cat_6,
        R.drawable.cat_7,
        R.drawable.cat_7,
        R.drawable.cat_8,
        R.drawable.cat_8,
    )

    private val shapeList = listOf(
        R.drawable.shape_1,
        R.drawable.shape_1,
        R.drawable.shape_2,
        R.drawable.shape_2,
        R.drawable.shape_3,
        R.drawable.shape_3,
        R.drawable.shape_4,
        R.drawable.shape_4,
        R.drawable.shape_5,
        R.drawable.shape_5,
        R.drawable.shape_6,
        R.drawable.shape_6,
        R.drawable.shape_7,
        R.drawable.shape_7,
        R.drawable.shape_8,
        R.drawable.shape_8,
    )

    private val _catMode = MutableLiveData(false)
    val catMode = _catMode

    var currentModeMap = shapeList

    init {
        load()
    }

    private fun load() {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            context.getString(R.string.app_shared_prefs), Context.MODE_PRIVATE
        )
        currentBalance.value = sharedPreferences.getInt("balance_key", 0)
        currentBestTimeInSec.value = sharedPreferences.getLong("best_time_key", 0)
    }

    fun increaseBalance() {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            context.getString(R.string.app_shared_prefs), Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putInt("balance_key", currentBalance.value?.plus(lastReward.value!!)!!)
            .apply()
        load()
        lastReward.value = 0
    }

    private fun changeBestTime() {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            context.getString(R.string.app_shared_prefs), Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        if ((currentBestTimeInSec.value!! > lastRoundTimeInSec.value!!)
            || (currentBestTimeInSec.value!! == 0L)
        ) {
            editor.putLong("best_time_key", lastRoundTimeInSec.value!!)
                .apply()
            load()
        }
    }

    fun calcReward(timeInMillis: Long) {
        lastRoundTimeInSec.value = timeInMillis / 1000L
        lastReward.value = if (lastRoundTimeInSec.value!! <= 20) {
            100
        } else {
            val i = lastRoundTimeInSec.value!! - 20
            if (i >= 18) {
                10
            } else {
                100 - (5 * i.toInt())
            }
        }
        changeBestTime()
    }

    fun doubleBalance() {
        lastReward.value = lastReward.value?.times(2)
    }

    fun catModeOn(){
        _catMode.value = true
        currentModeMap = catList
    }

    fun catModeOff(){
        _catMode.value = false
        currentModeMap = shapeList
    }

}


