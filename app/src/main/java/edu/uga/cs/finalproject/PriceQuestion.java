package edu.uga.cs.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriceQuestion extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter recyclerAdapter;
    public static final String CONVTYPE = "edu.uga.cs.finalproject";

    private static final String DEBUG_TAG = "MainActivity";

    private static final int RC_SIGN_IN = 123;

    public static String test;
    public static String usertest;

    public double dprice;
    public String duser;
    public String itemName;

    public Item ditem;

    EditText price;
    EditText enter;

    //private List<Item> jobLeadsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pricequestion);

        TextView username = findViewById(R.id.username);
        enter = findViewById(R.id.enterusername);

        Button back = findViewById(R.id.back);
        TextView question = findViewById(R.id.question);
         price = findViewById(R.id.price);
        //TextView name = findViewById(R.id.name);

        Intent intent = getIntent();
        itemName = intent.getStringExtra(CONVTYPE);
        //name.setText(itemName);

        Log.d( DEBUG_TAG, "itemName: " + itemName );


        back.setOnClickListener(new BackButtonClickListener());

        // get a Firebase DB instance reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("jobleads");

        /*
        jobLeadsList = new ArrayList<Item>();

        // Set up a listener (event handler) to receive a value for the database reference, but only one time.
        // This type of listener is called by Firebase once by immediately executing its onDataChange method.
        // We can use this listener to retrieve the current list of JobLeads.
        // Other types of Firebase listeners may be set to listen for any and every change in the database
        // i.e., receive notifications about changes in the data in real time (hence the name, Realtime database).
        // This listener will be invoked asynchronously, as no need for an AsyncTask, as in the previous apps
        // to maintain job leads.
        myRef.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot ) {
                // Once we have a DataSnapshot object, knowing that this is a list,
                // we need to iterate over the elements and place them on a List.
                for( DataSnapshot postSnapshot: snapshot.getChildren() ) {
                    Item jobLead = postSnapshot.getValue(Item.class);
                    jobLeadsList.add(jobLead);
                    Log.d( DEBUG_TAG, "ReviewJobLeadsActivity.onCreate(): added: " + jobLead );
                }
                Log.d( DEBUG_TAG, "ReviewJobLeadsActivity.onCreate(): setting recyclerAdapter" );


                // Now, create a JobLeadRecyclerAdapter to populate a ReceyclerView to display the job leads.
                recyclerAdapter = new ListRecyclerAdapter( jobLeadsList );
                //recyclerView.setAdapter( recyclerAdapter );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        } );


         */
        //Log.d( DEBUG_TAG, "Size of List " + ReviewListActivity.jobLeadsList.size() );

        for(int i = 0;i < ReviewListActivity.jobLeadsList.size();i++) {

            if(ReviewListActivity.jobLeadsList.get(i).getItemName().equalsIgnoreCase(itemName)) {
                Item item = ReviewListActivity.jobLeadsList.get(i);
                item.setPurchased(true);

                ditem = item;
                //item.setPrice(dprice);
                Log.d( DEBUG_TAG, "Name of searched item: " + item.getItemName() );
                Log.d( DEBUG_TAG, "Is it Purchased: " + item.getPurchased() );
                Log.d( DEBUG_TAG, "Price of item: " + item.getPrice() );




            }

        }


    }

    // A button listener class to start a Firebase sign-in process
    private class BackButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            test = price.getText().toString();
            usertest = enter.getText().toString();




            ditem.setUser(usertest);
            duser = ditem.getUser();

            dprice = Double.parseDouble(test);
            ditem.setPrice(dprice);

            for(int i = 0; i < ReviewListActivity.jobLeadsList.size();i++) {

                Log.d( DEBUG_TAG, "loop Name of searched item: " + ReviewListActivity.jobLeadsList.get(i).getItemName() );
                Log.d( DEBUG_TAG, " loop Is it Purchased: " + ReviewListActivity.jobLeadsList.get(i).getPurchased()  );
                Log.d( DEBUG_TAG, " loop Price of item: " + ReviewListActivity.jobLeadsList.get(i).getPrice()  );
                Log.d( DEBUG_TAG, "loop user: " + ReviewListActivity.jobLeadsList.get(i).getUser() );

            }
            //Log.d( DEBUG_TAG, "Key: " + ditem.getKey() );

           // FirebaseDatabase database = FirebaseDatabase.getInstance();
           // DatabaseReference myRef = database.getReference("jobleads");

            DatabaseReference leadersRef = FirebaseDatabase.getInstance().getReference("jobleads");
            Query query = leadersRef.orderByChild("itemName").equalTo(ditem.getItemName());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        //currentScore = child.child("Score").getValue(Integer.class);
                        child.getRef().child("price").setValue(dprice);
                        child.getRef().child("purchased").setValue(true);
                        child.getRef().child("user").setValue(duser);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });




            /*
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("jobleads");

            DatabaseReference selectedRef = myRef.child(ditem.getItemName());
            Map<String, Object> hopperUpdates = new HashMap<>();
            hopperUpdates.put("price",dprice );
            selectedRef.updateChildren(hopperUpdates);


             */

            /*
            DatabaseReference users = FirebaseDatabase.getInstance().getReference("jobleads");
            Query query = users.orderByChild("itemName").equalTo(ditem.getItemName());

            //Query userQuery = users.orderByChild("userEmail").equalTo(email);
            Log.d( DEBUG_TAG, " supposed CompanyName:  " + ditem.getItemName() );
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Item user = dataSnapshot.getValue(Item.class);

                    Log.d( DEBUG_TAG, "supposed user CompanyName:  " + user.getItemName() );

                    //user.setPrice(dprice);
                    //user.setPurchased(true);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


 */


            //working retrive data
            /*
            Log.d( DEBUG_TAG, "ditem name: " + ditem.getItemName() );

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("jobleads");
            myRef.orderByChild("itemName").equalTo(ditem.getItemName()).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                    System.out.println(dataSnapshot.getKey());
                    Item user = dataSnapshot.getValue(Item.class);
                    user.setPrice(dprice);
                    user.setPurchased(true);

                    Log.d( DEBUG_TAG, "onChildAdded aaaaaaa: " + user.getItemName() );

                }
                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                    System.out.println(dataSnapshot.getKey());

                    Item user = dataSnapshot.getValue(Item.class);

                    Log.d( DEBUG_TAG, "onChildAdded bbbbb: " + user.getItemName() );

                    //user.setPrice(dprice);
                    //user.setPurchased(true);
                }
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    System.out.println(dataSnapshot.getKey());
                    Item user = dataSnapshot.getValue(Item.class);

                    Log.d( DEBUG_TAG, "onChildRemoved cccccc: " + user.getItemName() );
                }
                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
                    System.out.println(dataSnapshot.getKey());
                    Item user = dataSnapshot.getValue(Item.class);

                    Log.d( DEBUG_TAG, "onChildMoved ddddddd: " + user.getItemName() );
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getMessage());

                }

                // ...
            });


             */




            Intent intent = new Intent(v.getContext(), ListManagementActivity.class);
            //intent.putExtra(CONVTYPE, item.getItemName());
            v.getContext().startActivity(intent);




        }


    }

}
