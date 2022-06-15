package edu.uga.cs.finalproject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListRecyclerAdapter extends RecyclerView.Adapter<ListRecyclerAdapter.JobLeadHolder>{

    public static final String CONVTYPE = "edu.uga.cs.finalproject";

    private String m_Text = "";
    private int postion = 0;
    public Context context;

    public static final String DEBUG_TAG = "JobLeadRecyclerAdapter";

    private List<Item> itemList;

    public ListRecyclerAdapter( List<Item> jobLeadList ) {
        this.itemList = jobLeadList;
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    static class JobLeadHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        TextView price;
        TextView url;
        TextView comments;
        Button button;


        public JobLeadHolder(View itemView ) {
            super(itemView);

            itemName = (TextView) itemView.findViewById( R.id.company);
            //price = (TextView) itemView.findViewById( R.id.phone );
            button = (Button)itemView.findViewById(R.id.delete);
            //url = (TextView) itemView.findViewById( R.id.url );
            //comments = (TextView) itemView.findViewById( R.id.comments );

        }

    }

    @Override
    public JobLeadHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.job_lead, parent, false );
        return new JobLeadHolder( view );
    }



    // This method fills in the values of the Views to show a JobLead
    @Override
    public void onBindViewHolder( JobLeadHolder holder, int position ) {
        Item item = itemList.get( position );

        if(item.getPurchased() == true) {

            holder.itemView.setVisibility(View.GONE);
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            params.height = 0;
            params.width = 0;
            holder.itemView.setLayoutParams(params);

        }
        Log.d( DEBUG_TAG, "onBindViewHolder: " + item );

        holder.itemName.setText( item.getItemName());
        //holder.price.setText( item.getPrice().toString() );



        class ButtonClickListener implements View.OnClickListener
        {


            private static final String DEBUG_TAG = "ListRecyclerAdapter";

            @Override
            public void onClick(View v) {


                Log.d( DEBUG_TAG, item.getItemName() );
                //item.setPurchased(true);


                Intent intent = new Intent(v.getContext(), PriceQuestion.class);
                intent.putExtra(CONVTYPE, item.getItemName());
                v.getContext().startActivity(intent);

            }
        }


        holder.button.setOnClickListener(new ButtonClickListener() );


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}

