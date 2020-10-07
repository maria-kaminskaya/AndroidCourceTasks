package com.kmnvxh222.task7.settings

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.kmnvxh222.task7.MainActivity
import com.kmnvxh222.task7.R

class SettingsDialogFragment : DialogFragment() {

    private val typeStorage = arrayOf("External Data Storage", "Internal Data Storage")
    //  private val typeStorage = resources.getStringArray(R.array.typeDataStorage)

    private val settingsSharedPreferences = SharedPreferencesSettings()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var selectType = ""
        val saveItem =settingsSharedPreferences.getSetting(context!!.applicationContext)
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
                        Toast.makeText(activity, "${resources.getString(R.string.settingsMessage)} ${typeStorage[item]}", Toast.LENGTH_SHORT).show()
                        selectType = typeStorage[item]
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
        } ?: throw IllegalStateException(resources.getString(R.string.errorDialog))
    }

    private fun refreshActivity(){
        val refresh = Intent(context, MainActivity::class.java)
        startActivity(refresh)
        activity!!.finish()
    }

}
