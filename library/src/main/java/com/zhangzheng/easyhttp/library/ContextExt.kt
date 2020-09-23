package com.zhangzheng.easyhttp.library

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import kotlinx.coroutines.*
import java.lang.ref.WeakReference

fun Context.launch(execute:suspend  () -> Unit) {

    val uiScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    uiScope.launch {
        execute()
    }
    (applicationContext as Application).registerActivityLifecycleCallbacks(
        ActivityLifecycleCallbacksProxy(this,uiScope))
}

 internal class ActivityLifecycleCallbacksProxy(context: Context,var uiScope:CoroutineScope) : Application.ActivityLifecycleCallbacks {

     private val weakContext = WeakReference(context)

     override fun onActivityPaused(p0: Activity) =Unit
     override fun onActivityStarted(p0: Activity)  =Unit
     override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle)  =Unit
     override fun onActivityStopped(p0: Activity)  =Unit
     override fun onActivityCreated(p0: Activity, p1: Bundle?)  =Unit
     override fun onActivityResumed(p0: Activity)  =Unit
     override fun onActivityDestroyed(p0: Activity){
         if (weakContext.get()==null||weakContext.get()==p0) {
             uiScope.cancel()
             (p0.applicationContext as Application).unregisterActivityLifecycleCallbacks(this)
         }
     }

 }