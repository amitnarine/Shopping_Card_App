package edu.uga.cs.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SettleCosts extends AppCompatActivity {

    private static final String DEBUG_TAG = "SettleCosts";
    public List<String> userList;

    public List<Double> numberList;
    public TextView listUser;
    public TextView costUser;
    private Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settle);

         userList= new ArrayList<String>();

        homeButton = findViewById( R.id.settlehome );
        homeButton.setOnClickListener(new homeButtonClickListener());

        listUser= findViewById(R.id.listuser);
        costUser= findViewById(R.id.listcost);


        for(int i = 0;i < ReviewListActivity.jobLeadsList.size();i++) {

            Item item = ReviewListActivity.jobLeadsList.get(i);
            if(ReviewListActivity.jobLeadsList.get(i).getPurchased() == false) {

                //item.setPurchased(true);
                //item.setPrice(dprice);
                //Log.d( DEBUG_TAG, "Name of searched item: " + item.getItemName() );
                //Log.d( DEBUG_TAG, "Is it Purchased: " + item.getPurchased() );
                //Log.d( DEBUG_TAG, "Price of item: " + item.getPrice() );
            } else if (!(userList.contains(ReviewListActivity.jobLeadsList.get(i).getUser()))) {

                userList.add(ReviewListActivity.jobLeadsList.get(i).getUser());

            } else {


            }

        }

        //print out userList

        Log.d( DEBUG_TAG, "list userList contents: " + userList.toString() );


        numberList = new ArrayList<Double>(userList.size());
        Double cost;

        for(int i = 0;i < userList.size();i++) {


             cost = 0.0;

            for(int x = 0;x < ReviewListActivity.jobLeadsList.size();x++) {

                Item item = ReviewListActivity.jobLeadsList.get(x);
                if(ReviewListActivity.jobLeadsList.get(x).getPurchased() == false) {


                } else if (userList.get(i).equalsIgnoreCase(ReviewListActivity.jobLeadsList.get(x).getUser())) {

                    cost = cost + ReviewListActivity.jobLeadsList.get(x).getPrice();

                } else {


                }


            }

            numberList.add(i,cost);


        }

        Log.d( DEBUG_TAG, " list numList contents: " + numberList.toString() );



        listUser.setText(userList.toString());
        costUser.setText(numberList.toString());


        DatabaseReference leadersRef = FirebaseDatabase.getInstance().getReference("jobleads");
        Query query = leadersRef.orderByChild("purchased").equalTo(true);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    //currentScore = child.child("Score").getValue(Integer.class);
                    child.getRef().removeValue();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private class homeButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ListManagementActivity.class);
            view.getContext().startActivity(intent);
        }
    }
}
