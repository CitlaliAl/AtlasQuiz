package com.upchiapas.atlasquiz

import android.app.Application
import com.upchiapas.atlasquiz.di.AtlasQuizModule

class AtlasQuizApplication : Application() {

    val module: AtlasQuizModule by lazy { AtlasQuizModule() }
}