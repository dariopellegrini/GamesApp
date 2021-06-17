package com.dariopellegrini.gamesapp.application

import android.app.Application
import com.dariopellegrini.spike.response.Spike
import com.dariopellegrini.storagedone.StorageDoneDatabase

class GamesAppApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Spike.instance.configure(this)
        StorageDoneDatabase.configure(this)
    }
}