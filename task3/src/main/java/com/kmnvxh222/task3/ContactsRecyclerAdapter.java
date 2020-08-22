package com.kmnvxh222.task3;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactsRecyclerAdapter extends RecyclerView.Adapter<ContactsRecyclerAdapter.ContactsViewHolder> {

    private List<Contacts> contacts;
    static OnItemClickListener mItemClickListener;

    public ContactsRecyclerAdapter(@NonNull List<Contacts> contacts) {
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ContactsRecyclerAdapter.ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsRecyclerAdapter.ContactsViewHolder holder, int position) {

        holder.bind(contacts.get(position));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void addItem(Contacts new_contact) {
        contacts.add(new_contact);
        notifyItemChanged(contacts.indexOf(new_contact));
    }

    static class ContactsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textName;
        private TextView textInfo;
        private ImageView image;

        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textViewName);
            textInfo = itemView.findViewById(R.id.textViewInfo);
            image = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }

        public void bind(Contacts contacts) {
            int idSrc = 0;
            try {
                textName.setText(contacts.getName());
                if(contacts.getTypeInfo().equals(enumTypeInfo.email.toString())) {
                    textInfo.setText(contacts.getInfo());
                    idSrc = R.drawable.contact_mail;
                }else if(contacts.getTypeInfo().equals(enumTypeInfo.phone.toString())) {
                    textInfo.setText(contacts.getInfo());
                    idSrc = R.drawable.contact_phone;
                }
                image.setImageResource(idSrc);
            }catch(Exception e) {
                Log.d("ContactsRecyclerAdapter", "" + e);
            }

        }
    }

    public void updateList(List<Contacts> search_contact) {
        contacts = search_contact;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
