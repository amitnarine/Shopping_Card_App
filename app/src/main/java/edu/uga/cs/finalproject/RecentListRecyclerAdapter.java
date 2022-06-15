package edu.uga.cs.finalproject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RecentListRecyclerAdapter extends RecyclerView.Adapter<RecentListRecyclerAdapter.JobLeadHoldertwo>{
    public static final String CONVTYPE = "edu.uga.cs.finalproject";

    private String m_Text = "";
    private int postion = 0;
    public Context context;

    public static final String DEBUG_TAG = "JobLeadRecyclerAdapter";

    private List<Item> itemList;

    public RecentListRecyclerAdapter( List<Item> jobLeadList ) {
        this.itemList = jobLeadList;
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    static class JobLeadHoldertwo extends RecyclerView.ViewHolder {

        TextView itemName;
        TextView price;
        TextView url;
        TextView comments;
        Button button;
        TextView recentuser;
        //TextView price;


        public JobLeadHoldertwo(View itemView ) {
            super(itemView);

            itemName = (TextView)itemView.findViewById(R.id.companytwo);
            //price = (TextView) itemView.findViewById( R.id.phone );
            button = (Button)itemView.findViewById(R.id.delete);
            price = (TextView) itemView.findViewById(R.id.theprice);
            recentuser = (TextView) itemView.findViewById(R.id.recentuser);
            //url = (TextView) itemView.findViewById( R.id.url );
            //comments = (TextView) itemView.findViewById( R.id.comments );

        }

    }


    @Override
    public JobLeadHoldertwo onCreateViewHolder(ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.recent, parent, false );
        return new JobLeadHoldertwo( view );
    }


    // This method fills in the values of the Views to show a JobLead
    @Override
    public void onBindViewHolder(JobLeadHoldertwo holder, int position ) {
        Item item = itemList.get( position );

        if(item.getPurchased() == false) {

            holder.itemView.setVisibility(View.GONE);
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            params.height = 0;
            params.width = 0;
            holder.itemView.setLayoutParams(params);

        }
        Log.d( DEBUG_TAG, "onBindViewHolder: " + item );

        Log.d( DEBUG_TAG, "PRICEEEEE: " + item.getPrice().toString() );

        holder.itemName.setText( item.getItemName());

        String test = item.getPrice().toString();
        holder.price.setText(test);
        //holder.price.setText( item.getPrice().toString() );

        String testtwo = item.getUser().toString();
        holder.recentuser.setText(testtwo);


        class ButtonClickListener implements View.OnClickListener
        {


            private static final String DEBUG_TAG = "ListRecyclerAdapter";

            @Override
            public void onClick(View v) {

                for(int i = 0;i < itemList.size();i++) {

                    if(itemList.get(i).getItemName().equalsIgnoreCase(item.getItemName())) {
                        Item item = itemList.get(i);
                        item.setPurchased(false);

                        DatabaseReference leadersRef = FirebaseDatabase.getInstance().getReference("jobleads");
                        Query query = leadersRef.orderByChild("itemName").equalTo(item.getItemName());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                for (DataSnapshot child : snapshot.getChildren()) {
                                    //currentScore = child.child("Score").getValue(Integer.class);
                                    child.getRef().child("purchased").setValue(false);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });



                    }

                }



                Intent intent = new Intent(v.getContext(), ListManagementActivity.class);
                //intent.putExtra(CONVTYPE, item.getItemName());
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
