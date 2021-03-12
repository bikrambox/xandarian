package com.xclusive.ParcelTracking;
import static com.xclusive.ParcelTracking.Product_tracking.Code1;

import static com.xclusive.ParcelTracking.Product_tracking.courierdialog;
import static com.xclusive.ParcelTracking.Product_tracking.courierimage;
import static com.xclusive.ParcelTracking.Product_tracking.icon_url;
import static com.xclusive.ParcelTracking.Product_tracking.valid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

class CarrierAdapter extends RecyclerView.Adapter<CarrierAdapter.ViewHolder>{

List<CarrierListModel> carrierListModels ;

    public CarrierAdapter(List<CarrierListModel> carrierListModels) {
        this.carrierListModels = carrierListModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carrier_item_layout,parent,false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       // if (carrierListModels.get(position).getName().toUpperCase().contains("DHL")){
           // Toast.makeText(holder.itemView.getContext(), "if-->"+carrierListModels.get(position).getName().toUpperCase(), Toast.LENGTH_SHORT).show();
            holder.title.setText(carrierListModels.get(position).getName().toUpperCase());

            Glide.with(holder.itemView.getContext()).load("http:"+carrierListModels.get(position).getPicture()).placeholder(R.drawable.imageloading).into(holder.image);
            //Glide.with(holder.itemView.getContext()).load(R.drawable.imageloading).placeholder(R.drawable.imageloading).dontAnimate().into(holder.image);

            holder.itemView.setOnClickListener(V->{
                 courierdialog.dismiss();


                Code1 = carrierListModels.get(position).getCode();
                valid = 1;
                Glide.with(holder.itemView.getContext()).load("http:"+carrierListModels.get(position).getPicture()).placeholder(R.drawable.imageloading).into(courierimage);
                icon_url = "http:"+carrierListModels.get(position).getPicture();
//                Intent intent = new Intent(holder.itemView.getContext(),Product_tracking.class);
//                intent.putExtra("ID","");
//
//                holder.itemView.getContext().startActivity(intent);
//                customType(holder.itemView.getContext(),"fadein-to-fadeout");

            });
      //  }

    }


    @Override
    public int getItemCount() {
        return carrierListModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleid);

            image = itemView.findViewById(R.id.pictureid);
        }
    }
    public void updatelist(ArrayList<CarrierListModel> carrierAdapterArrayList){
        if ( carrierAdapterArrayList.isEmpty()){
            return;
        }

        carrierListModels = new ArrayList<>();
        carrierListModels.addAll(carrierAdapterArrayList);
        notifyDataSetChanged();


    }
}
