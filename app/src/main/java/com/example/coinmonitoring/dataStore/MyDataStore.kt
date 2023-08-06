package com.example.coinmonitoring.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.coinmonitoring.App

class MyDataStore {

    private val context = App.context()
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_pref")
    }

    private val mDataStore: DataStore<Preferences> = context.dataStore
    //접속 여부니 boolean의 preference 생성
    private val FIRST_FLAG = booleanPreferencesKey("FIRST_FLAG")

    //메인 액티비티리로 갈 때 TRUE로 변경

    //처음 접속이 아니면 true
    //처음 접속이라면 false
    suspend fun setupFirstData() {
        mDataStore.edit { preferences ->
            preferences[FIRST_FLAG] = true
        }
    }

    // 처음 접속 여부 판열을 확인하기 위한 get함수 생성
    suspend fun getFirstData(): Boolean {
        var currentValue = false
        mDataStore.edit { preferences ->
            currentValue = preferences[FIRST_FLAG] ?: true
        }
        return currentValue
    }
}