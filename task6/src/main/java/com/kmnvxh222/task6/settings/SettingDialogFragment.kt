package com.kmnvxh222.task6.settings

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.kmnvxh222.task6.MainActivity
import com.kmnvxh222.task6.R

class SettingsDialogFragment : DialogFragment() {

    private val typeStorage = arrayOf("ThreadPoolExecutor + Handler", "CompletableFuture + ThreadPoolExecutor", "RxJava")

    private val settingsSharedPreferences = SharedPreferencesSettings()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var selectType = ""
        val saveItem = when(settingsSharedPreferences.getSetting(context!!.applicationContext)){
            getString(R.string.threadHandlerValue) -> getString(R.string.threadHandlerTitle)
            getString(R.string.threadCompletableValue) -> getString(R.string.threadCompletableTitle)
            getString(R.string.rxJavaValue) -> getString(R.string.rxJavaTitle)
            else -> ""
        }
        var selectItem: Int = -1

        typeStorage.forEachIndexed{ index, element ->
            println("$index : $element")
            if (element == saveItem){
                selectItem = index
            }
        }
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.settingsTitle)
                    .setSingleChoiceItems(typeStorage, selectItem
                    ) { _, item ->
                        Toast.makeText(activity, "${getString(R.string.settingsMessage)} ${typeStorage[item]}", Toast.LENGTH_SHORT).show()

                       when(typeStorage[item]){
                           getString(R.string.threadHandlerTitle) -> selectType=getString(R.string.threadHandlerValue)
                           getString(R.string.threadCompletableTitle) -> selectType=getString(R.string.threadCompletableValue)
                           getString(R.string.rxJavaTitle) -> selectType=getString(R.string.rxJavaValue)
                       }
                    }
                    .setPositiveButton(R.string.settingsOK) { _, _ ->
                        settingsSharedPreferences.saveSetting(selectType, context!!.applicationContext)
                        refreshActivity()
                        dialog?.dismiss()
                    }
                    .setNegativeButton(R.string.settingsCanceled) { _, _ ->
                        dialog?.dismiss()
                    }

            builder.create()
        } ?: throw IllegalStateException(getString(R.string.errorDialog))
    }

    private fun refreshActivity(){
        val refresh = Intent(context, MainActivity::class.java)
        startActivity(refresh)
        activity!!.finish()
    }

}