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

    public ContactsRecyclerAdapter(@NonNull List<Contacts> contacts){
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ContactsRecyclerAdapter.ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext()).inflate (R.layout.item_contact,parent,false);
            return new ContactsViewHolder (view);
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

    static class ContactsViewHolder extends RecyclerView.ViewHolder{

        private TextView textName;
        private TextView textInfo;
        private ImageView image;

        public ContactsViewHolder(@NonNull View itemView) {
            super (itemView);
            textName = itemView.findViewById (R.id.textViewName);
            textInfo = itemView.findViewById (R.id.textViewInfo);
            image = itemView.findViewById (R.id.imageView);
        }

        public void bind(Contacts contacts){
            int id = 0;
//            String str = "No contacts.Please add";
//       Collection<String> contact = contacts.values();
////       contacts.forEach((v)-> v.indexOf ("@"));
//            for(Map.Entry<String,String> entry: contacts.entrySet()){
//                if(entry.getValue().contains("@"))
//                {
//                    id = R.drawable.contact_mail;
//                }
//                else if (!entry.getValue().contains("@"))
//                {
//                    id = R.drawable.contact_phone;
//                }
//            }
//            if(contacts == null){
//                itemsNull.setText(str);
//            }else{
            try{
                Log.d("ContactsRecyclerAdapter", "Contacts" + contacts);
                textName.setText(contacts.getName());
                if(contacts.getPhone()==null){
                    textInfo.setText(contacts.getEmail());
                    id = R.drawable.contact_mail;
                }else if(contacts.getEmail()==null){
                    textInfo.setText(contacts.getPhone());
                    id = R.drawable.contact_phone;
                }
                image.setImageResource(id);
            }catch(Exception e){
                Log.d("ContactsRecyclerAdapter", "" + e);
            }

//            }
        }
    }
}
