package com.example.covid_19pop_up;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class mtadapter extends RecyclerView.Adapter<mtadapter.MyViewHolder> implements Filterable {

    Context context;
    ArrayList<info_class> info_classArrayList;

    ArrayList<info_class> info_classArrayList_full;


    public mtadapter(Context context, ArrayList<info_class> info_classArrayList) {
        this.context = context;
        this.info_classArrayList_full = info_classArrayList;

        this.info_classArrayList = new ArrayList<>(info_classArrayList_full);
    }

    @NonNull
    @Override
    public mtadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View v= LayoutInflater.from(context).inflate(R.layout.list_item_recycleview,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull mtadapter.MyViewHolder holder, int position) {
             info_class info_class=info_classArrayList.get(position);
             holder.tvheading.setText(info_class.headimg);
             holder.textsshow.setText(info_class.text);
    }

    @Override
    public int getItemCount() {
        return info_classArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return  Info_classFilter;
    }


    private  final  Filter Info_classFilter  = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<info_class> filtered_info_list = new ArrayList<>();
            if (charSequence == null ||charSequence.length() ==0){
                filtered_info_list.addAll(info_classArrayList_full);
            }else {
                String filterpattern = charSequence.toString().toLowerCase().trim();
                for (info_class info_class:info_classArrayList_full){
                    if (info_class.headimg.toLowerCase().contains(filterpattern))
                        filtered_info_list.add(info_class);
                }
            }
FilterResults results = new FilterResults();
            results.values = filtered_info_list;
            results.count=filtered_info_list.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
           info_classArrayList.clear();
           info_classArrayList.addAll((ArrayList)filterResults.values);
           notifyDataSetChanged();
        }
    };





    public static class MyViewHolder extends RecyclerView.ViewHolder{
       TextView tvheading,textsshow;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
             tvheading=itemView.findViewById(R.id.tvHeading);
            textsshow=itemView.findViewById(R.id.showtext);

        }
    }
}
