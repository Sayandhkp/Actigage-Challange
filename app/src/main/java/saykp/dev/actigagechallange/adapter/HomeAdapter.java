package saykp.dev.actigagechallange.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import saykp.dev.actigagechallange.R;
import saykp.dev.actigagechallange.other.Constants;
import saykp.dev.actigagechallange.services.DbAdapter;

/**
 * Created by sysboon on 06-02-2018.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    Context context;
    ArrayList<String> urlArray;
    String farmid, imageId, secret, serverId;
    int layout;
    DbAdapter dbAdapter;


    public HomeAdapter(Context context, ArrayList<String> urlArray, int layout) {
        this.urlArray = urlArray;
        this.layout = layout;

        this.context = context;
        dbAdapter = new DbAdapter(context);
        dbAdapter.open();


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (layout == Constants.LAYOUT_GRID || layout == Constants.LAYOUT_FAV) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_items_photo, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_photo_list, parent, false);
        }

        return new HomeAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String rowItem = urlArray.get(position);
//
        Picasso.get().load(rowItem).into(holder.imageView);
        holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Log.v("URL", "==" + rowItem);
//        Bitmap bmp = null;

        if (Constants.LAYOUT == Constants.LAYOUT_FAV) {
            holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Remove From Favourites").
                            setMessage("Are you sure to remove this image from Favourite List").
                            setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    dbAdapter.deleteImageUrl(urlArray.get(position));
                                    urlArray.remove(position);
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();

                    return true;
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return urlArray.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        protected ImageView imageView;


        public ViewHolder(View rootView) {
            super(rootView);
            imageView = (ImageView) rootView.findViewById(R.id.imageViewPhoto);


        }
    }
}
