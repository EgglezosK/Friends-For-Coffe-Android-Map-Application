package dpsd.forites.egglezos.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by JUNED on 6/16/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;

    List<GetDataAdapter> getDataAdapter;

    ImageLoader imageLoader1;


    public RecyclerViewAdapter(List<GetDataAdapter> getDataAdapter, Context context){

        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {

        GetDataAdapter getDataAdapter1 =  getDataAdapter.get(position);

        imageLoader1 = ServerImageParseAdapter.getInstance(context).getImageLoader();

        imageLoader1.get(getDataAdapter1.getImageServerUrl(),
                ImageLoader.getImageListener(
                        Viewholder.networkImageView,//Server Image
                        R.mipmap.ic_launcher,//Before loading server image the default showing image.
                        android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                )
        );

        Viewholder.networkImageView.setImageUrl(getDataAdapter1.getImageServerUrl(), imageLoader1);

        Viewholder.ImageTitleNameView.setText(getDataAdapter1.getImageTitleName());



        SharedPreferences prefs = DemoListView.getInstance().getSharedPreferences("LIKES", Context.MODE_PRIVATE);
        int pageNumber=prefs.getInt(getDataAdapter1.getImageTitleName(), 0);
        if(pageNumber == 1){
            //likeview.setImageResource(R.drawable.like_enabled);
            Viewholder.likeview.setImageResource(R.drawable.like_enabled);
        }else{
            //likeview.setImageResource(R.drawable.like);
            Viewholder.likeview.setImageResource(R.drawable.like);
        }


        final double latitude = GPSTracker.latitude;
        double longitude = GPSTracker.longitude;
        String txtLon = getDataAdapter1.getUserLon();
        String txtlat = getDataAdapter1.getUserlat();

        double final_lat = Double.parseDouble(txtlat);
        double final_lon  = Double.parseDouble(txtLon);


        double test = distance(latitude,longitude,final_lat,final_lon);
        if(test != 0){
            Viewholder.networkUserLon.setText(String.valueOf(new DecimalFormat("##.###").format(test)) + " km");
            }else{
            Viewholder.networkUserLon.setText("(You)");

        }


        //





    }

    @Override
    public int getItemCount() {

        return getDataAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView ImageTitleNameView;
        public NetworkImageView networkImageView ;
        public TextView networkUserLon;
        public ImageView likeview;
        public ViewHolder(View itemView) {

            super(itemView);

            ImageTitleNameView = (TextView) itemView.findViewById(R.id.textView_item) ;

            networkImageView = (NetworkImageView) itemView.findViewById(R.id.VollyNetworkImageView1) ;

            networkUserLon = (TextView) itemView.findViewById(R.id.lonuser) ;
            likeview = (ImageView) itemView.findViewById(R.id.imageView3);


        }
    }



    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }



}