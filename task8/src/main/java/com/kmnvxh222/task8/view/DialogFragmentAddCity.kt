package com.kmnvxh222.task8.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.kmnvxh222.task8.R
import com.kmnvxh222.task8.model.City
import com.kmnvxh222.task8.presenter.CityPresenter
import com.kmnvxh222.task8.presenter.CityPresenterInterface
import com.kmnvxh222.task8.repository.LocalRepository

class DialogFragmentAddCity(context: Context): DialogFragment(){

    private val presenter: CityPresenterInterface = CityPresenter(localRepository = LocalRepository(context))
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val editText = EditText(context)
        editText.hint = getString(R.string.minsk)

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.enter_the_city_name)
                    .setView(editText)
                    .setPositiveButton(R.string.done) { _, _ ->
                        var cityName = ""
//                        editText.addTextChangedListener(object : TextWatcher {
//                            override fun afterTextChanged(s: Editable?) {
////                                cityName = s.toString()
//                            }
//                            override fun beforeTextChanged(s: CharSequence, start: Int,count: Int, after: Int){
//                                cityName = s.toString()
//                            }
//
//                            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                                cityName = s.toString()
//                            }
//                        }
//                        )
                        cityName = editText.text.toString()
                        val city = City(cityName,System.currentTimeMillis())
                        presenter.addCity(city)
                        dialog?.dismiss()
                    }
                    .setNegativeButton(R.string.cancel) { _, _ ->
                        dialog?.dismiss()
                    }
            builder.create()
        }?: throw IllegalStateException(getString(R.string.errorDialog))
    }
}