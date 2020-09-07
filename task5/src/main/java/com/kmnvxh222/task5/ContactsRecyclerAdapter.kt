package com.kmnvxh222.task5

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_contact.view.imageView
import kotlinx.android.synthetic.main.item_contact.view.textViewInfo
import kotlinx.android.synthetic.main.item_contact.view.textViewName

class ContactsRecyclerAdapter(private var contacts: MutableList<Contacts>) : RecyclerView.Adapter<ContactsRecyclerAdapter.ContactsViewHolder>() {

    private lateinit var mItemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactsViewHolder(view)
    }

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) = holder.bind(contacts[position])

    inner class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(contact: Contacts) {
            var idSrc = 0
            try {
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
            } catch (e: Exception) {
                Log.d("ContactsRecyclerAdapter", "$e")
            }
        }

        override fun onClick(v: View) = mItemClickListener.onItemClick(v, adapterPosition)
    }

    fun updateList(searchContact: MutableList<Contacts>) {
        contacts = searchContact
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.mItemClickListener = mItemClickListener
    }

}

