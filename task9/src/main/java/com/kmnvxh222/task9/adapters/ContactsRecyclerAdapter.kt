package com.kmnvxh222.task9.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kmnvxh222.task9.model.Contact
import com.kmnvxh222.task9.model.EnumTypeInfo
import com.kmnvxh222.task9.R
import kotlinx.android.synthetic.main.item_contact.view.imageView
import kotlinx.android.synthetic.main.item_contact.view.textViewInfo
import kotlinx.android.synthetic.main.item_contact.view.textViewName

class ContactsRecyclerAdapter(private var contacts: MutableList<Contact>) : RecyclerView.Adapter<ContactsRecyclerAdapter.ContactsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactsViewHolder(view)
    }

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) = holder.bind(contacts[position])

    class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(contact: Contact) {
            var idSrc = 0
            itemView.textViewName.text = contact.name

            when (contact.typeInfo) {
                EnumTypeInfo.email.toString() -> {
                    itemView.textViewInfo.text = contact.info
                    idSrc = R.drawable.contact_mail
                }
                EnumTypeInfo.phone.toString() -> {
                    itemView.textViewInfo.text = contact.info
                    idSrc = R.drawable.contact_phone
                }
            }
            itemView.imageView.setImageResource(idSrc)
        }

    }

    fun updateList(searchContact: MutableList<Contact>) {
        contacts = searchContact
        notifyDataSetChanged()
    }

}
