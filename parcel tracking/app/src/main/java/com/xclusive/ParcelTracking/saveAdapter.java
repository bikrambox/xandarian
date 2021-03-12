package com.xclusive.ParcelTracking;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import static com.xclusive.ParcelTracking.Product_tracking.Code1;
import static com.xclusive.ParcelTracking.Product_tracking.carrier_code;
import static com.xclusive.ParcelTracking.Product_tracking.courierimage;
import static com.xclusive.ParcelTracking.Product_tracking.input;

class saveAdapter extends RecyclerView.Adapter<saveAdapter.ViewHolder> {
sqlDB dataBaseLite;
    List<savedmodel>savedmodelList;

    public saveAdapter(List<savedmodel> savedmodelList) {
        this.savedmodelList = savedmodelList;
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saveditems,parent,false);
        return new ViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
             holder.id.setText(savedmodelList.get(position).getId());
             holder.name.setText(savedmodelList.get(position).getCarriername());
             Glide.with(holder.itemView.getContext()).load(savedmodelList.get(position).getUrl()).into(holder.icon);
             holder.cardView.setOnClickListener(V->{
//                 input.setText(savedmodelList.get(position).getId());
//                 Code1 = savedmodelList.get(position).getCarriername();
//                 Glide.with(holder.itemView.getContext()).load(savedmodelList.get(position).getUrl()).into(courierimage);
                 Intent main1 = new Intent(holder.itemView.getContext(),Product_tracking.class);
                 main1.putExtra("ID",savedmodelList.get(position).getId());
                 main1.putExtra("CID",savedmodelList.get(position).getCarriername());
                 main1.putExtra("URL",savedmodelList.get(position).getUrl());
                 holder.itemView.getContext().startActivity(main1);


             });


             holder.deletebtn.setOnClickListener(V->{
                 AlertDialog alertDialog1 = new AlertDialog.Builder(holder.itemView.getContext())
                         .setTitle(R.string.delete)
                         .setMessage(R.string.deleteinfo)
                         .setIcon(R.drawable.deletebtn)
                         .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 delete1(holder,position);
                                 dialog.cancel();

                             }
                         }).setNeutralButton(R.string.cancel1, new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 dialog.cancel();
                             }
                         }).create();
                 alertDialog1.show();
             });
    }

    private void delete1(ViewHolder holder, int position) {
        dataBaseLite = new sqlDB(holder.itemView.getContext());
        int re =  dataBaseLite.deletedata(savedmodelList.get(position).getId());

        if (re>0){

           savedmodelList.remove(position);
            notifyItemRemoved(position);

            Toast.makeText(holder.itemView.getContext(), R.string.delete, Toast.LENGTH_SHORT).show();
        }
        
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return savedmodelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id,name;
        ImageView icon;
        CardView cardView;
        ImageButton deletebtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id =itemView.findViewById(R.id.trackid);
            name =itemView.findViewById(R.id.carrierid);
            cardView =itemView.findViewById(R.id.cardview);
            icon =itemView.findViewById(R.id.carrierimagesaved);
            deletebtn =itemView.findViewById(R.id.deletebtn);
        }
    }
}
