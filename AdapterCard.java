package com.example.aspirasilapor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class AdapterCard extends RecyclerView.Adapter<AdapterCard.ViewHolder> {
    private ArrayList<Card> daftarChat;
    private Context mContext;

    public AdapterCard(ArrayList<Card> daftarChat, Context mContext) {
        this.daftarChat = daftarChat;
        this.mContext = mContext;

        /////
        //
        ///
        //

        ///
        ///
        ///
        //
        //
        //
        //
        //
        //
        //
        //
        ///
        //
        //
        //

        //
        //
        ///
        //
        //
        //

        ////
        ////
        ////
        ///
        ///
        ///
        /////
        ////
        ////
        ////
        ///
        /////
        ///
        ///
        ///
        ////
        ////
        ///
        ///
        ////
        ////
        ///
        ///
        /////
        ///
        ///
        ///
        ////
        ////
        ///
        ///
        ///
        ///
        ///
        ///
        ///
        /////
        ////
        /////
        ////
        ///
        ////
        ////
        ////
        /////
        ///
        ///
        //
        //
        //
        ///
        ///
        ///
        ///
        ///
        //

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AdapterCard.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.menu_card, parent, false)) {
        };
    }

    @Override
    public void onBindViewHolder(AdapterCard.ViewHolder holder, int position) {
        Card card = daftarChat.get(position);
        holder.bindTo(card);

    }

    @Override
    public int getItemCount() {
        return daftarChat.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private EditText kategori, tanggal, deskripsi;
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            kategori = itemView.findViewById(R.id.txKategori);
            tanggal = itemView.findViewById(R.id.txTanggal);
            deskripsi = itemView.findViewById(R.id.txDesk);
            image = itemView.findViewById(R.id.cardImg);

            itemView.setOnClickListener(this);
        }

        @SuppressLint("StaticFieldLeak")
        public void bindTo(final Card card) {
            kategori.setText(card.getKategori());
            tanggal.setText(card.getTanggal());
            deskripsi.setText(card.getDeskripsi());

            final StorageReference islandRef = FirebaseStorage.getInstance().getReference().child("images/" + card.getImagePath());

            final long ONE_MEGABYTE = 10* 1024 * 1024;
            islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Drawable d = Drawable.createFromStream(new ByteArrayInputStream(bytes), null);
                    image.setImageDrawable(d);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    image.setImageResource(R.drawable.ic_launcher_background);
                }
            });
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(mContext, kategori.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }
}

package com.example.aspirasilapor;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.design.widget.NavigationView;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.Toast;

        import com.firebase.client.ChildEventListener;
        import com.firebase.client.DataSnapshot;
        import com.firebase.client.Firebase;
        import com.firebase.client.FirebaseError;
        import com.google.firebase.auth.FirebaseAuth;

        import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Firebase fb;
    ArrayList<String> tanggal = new ArrayList<>();
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Firebase.setAndroidContext(this);

        fb = new Firebase("https://aspirasistore.firebaseio.com/Lapor/Tanggal");
        lv = (ListView) findViewById(R.id.Listview);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tanggal);

        lv.setAdapter(arrayAdapter);
        fb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                tanggal.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), TampilanAwal.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Toast.makeText(MainActivity.this, "Thanks for visited", Toast.LENGTH_SHORT).show();
        startActivity(intent);

    }
    private void aboutus(){
        Intent intent = new Intent(getApplicationContext(), AboutUs.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private void feedback(){
        Intent intent = new Intent(getApplicationContext(), Feedback.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private void petunjukpenggunaan(){
        Intent intent = new Intent(getApplicationContext(), PetunjukPenggunaan.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private void notlpdarurat(){
        Intent intent = new Intent(getApplicationContext(), Notlpdarurat.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private void lapor(){
        Intent intent = new Intent(getApplicationContext(), Lapor.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private void statuslaporan(){
        Intent intent = new Intent(getApplicationContext(), StatusLaporan.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_lapor) {
            lapor();

        } else if (id == R.id.nav_nomortelepondarurat) {
            notlpdarurat();

        } else if (id == R.id.nav_petunjukpenggunaan) {
            petunjukpenggunaan();

        } else if (id == R.id.nav_feedback) {
            feedback();
        } else if (id == R.id.nav_aboutus) {
            aboutus();

        } else if (id == R.id.keluar) {
            logout();
        }
        else if (id == R.id.nav_statuslaporan) {
            statuslaporan();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
//package com.example.aspirasilapor;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.design.widget.NavigationView;
//import android.support.v4.view.GravityCompat;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBarDrawerToggle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import com.firebase.client.ChildEventListener;
//import com.firebase.client.DataSnapshot;
//import com.firebase.client.Firebase;
//import com.firebase.client.FirebaseError;
//import com.google.firebase.auth.FirebaseAuth;
//
//import java.util.ArrayList;
//
//public class MainActivity extends AppCompatActivity
//        implements NavigationView.OnNavigationItemSelectedListener {
//
//    Firebase fb;
//    ArrayList<String> tanggal = new ArrayList<>();
//    ListView lv;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
//        Firebase.setAndroidContext(this);
//
//        fb = new Firebase("https://aspirasistore.firebaseio.com/Lapor/Tanggal");
//        lv = (ListView) findViewById(R.id.Listview);
//
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tanggal);
//
//        lv.setAdapter(arrayAdapter);
//        fb.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                String value = dataSnapshot.getValue(String.class);
//                tanggal.add(value);
//                arrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
//    }
//
//
//
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void logout(){
//        FirebaseAuth.getInstance().signOut();
//        Intent intent = new Intent(getApplicationContext(), TampilanAwal.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        Toast.makeText(MainActivity.this, "Thanks for visited", Toast.LENGTH_SHORT).show();
//        startActivity(intent);
//
//    }
//    private void aboutus(){
//        Intent intent = new Intent(getApplicationContext(), AboutUs.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//    private void feedback(){
//        Intent intent = new Intent(getApplicationContext(), Feedback.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//    private void petunjukpenggunaan(){
//        Intent intent = new Intent(getApplicationContext(), PetunjukPenggunaan.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//    private void notlpdarurat(){
//        Intent intent = new Intent(getApplicationContext(), Notlpdarurat.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//    private void lapor(){
//        Intent intent = new Intent(getApplicationContext(), Lapor.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//    private void statuslaporan(){
//        Intent intent = new Intent(getApplicationContext(), StatusLaporan.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//
//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_lapor) {
//            lapor();
//
//        } else if (id == R.id.nav_nomortelepondarurat) {
//            notlpdarurat();
//
//        } else if (id == R.id.nav_petunjukpenggunaan) {
//            petunjukpenggunaan();
//
//        } else if (id == R.id.nav_feedback) {
//            feedback();
//        } else if (id == R.id.nav_aboutus) {
//            aboutus();
//
//        } else if (id == R.id.keluar) {
//            logout();
//        }
//        else if (id == R.id.nav_statuslaporan) {
//            statuslaporan();
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
//
//}

///




package com.example.aspirasilapor;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;

public class Card {
    String imagePath,kategori,tanggal,deskripsi;

    public Card(String imagePath, String nama, String harga, String deskripsi) {
        this.imagePath = imagePath;
        this.kategori = nama;
        this.tanggal = harga;
        this.deskripsi = deskripsi;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategoria(String kategori) {
        this.kategori = kategori;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}



/
        package com.example.aspirasilapor;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.design.widget.NavigationView;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.Toast;

        import com.firebase.client.ChildEventListener;
        import com.firebase.client.DataSnapshot;
        import com.firebase.client.Firebase;
        import com.firebase.client.FirebaseError;
        import com.google.firebase.auth.FirebaseAuth;

        import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Firebase fb;
    ArrayList<String> tanggal = new ArrayList<>();
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Firebase.setAndroidContext(this);

        fb = new Firebase("https://aspirasistore.firebaseio.com/Lapor/Tanggal");
        lv = (ListView) findViewById(R.id.Listview);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tanggal);

        lv.setAdapter(arrayAdapter);
        fb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                tanggal.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }
});
        }



@Override
public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
        drawer.closeDrawer(GravityCompat.START);
        } else {
        super.onBackPressed();
        }
        }

@Override
public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
        }
@Override
public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

@Override
public void onCancelled(FirebaseError firebaseError) {

        }
        });
        }



@Override
public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
        drawer.closeDrawer(GravityCompat.START);
        } else {
        super.onBackPressed();
        }
        }

@Override
public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
        }


@Override
public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

@Override
public void onCancelled(FirebaseError firebaseError) {

        }
        });
        }



@Override
public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
        drawer.closeDrawer(GravityCompat.START);
        } else {
        super.onBackPressed();
        }
        }

@Override
public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), TampilanAwal.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Toast.makeText(MainActivity.this, "Thanks for visited", Toast.LENGTH_SHORT).show();
        startActivity(intent);

    }
    private void aboutus(){
        Intent intent = new Intent(getApplicationContext(), AboutUs.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private void feedback(){
        Intent intent = new Intent(getApplicationContext(), Feedback.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        //

        package com.example.aspirasilapor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

        public class MainActivity extends AppCompatActivity
                implements NavigationView.OnNavigationItemSelectedListener {

            Firebase fb;
            ArrayList<String> tanggal = new ArrayList<>();
            ListView lv;


            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawer.addDrawerListener(toggle);
                toggle.syncState();

                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                navigationView.setNavigationItemSelectedListener(this);

                Firebase.setAndroidContext(this);

                fb = new Firebase("https://aspirasistore.firebaseio.com/Lapor/Tanggal");
                lv = (ListView) findViewById(R.id.Listview);

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tanggal);

                lv.setAdapter(arrayAdapter);
                fb.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String value = dataSnapshot.getValue(String.class);
                        tanggal.add(value);
                        arrayAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }



            @Override
            public void onBackPressed() {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    super.onBackPressed();
                }
            }

            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.main, menu);
                return true;
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                // Handle action bar item clicks here. The action bar will
                // automatically handle clicks on the Home/Up button, so long
                // as you specify a parent activity in AndroidManifest.xml.
                int id = item.getItemId();

                //noinspection SimplifiableIfStatement
                if (id == R.id.action_settings) {
                    return true;
                }

                return super.onOptionsItemSelected(item);
            }

            private void logout(){
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), TampilanAwal.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Toast.makeText(MainActivity.this, "Thanks for visited", Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
            private void aboutus(){
                Intent intent = new Intent(getApplicationContext(), AboutUs.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            private void feedback(){
                Intent intent = new Intent(getApplicationContext(), Feedback.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            private void petunjukpenggunaan(){
                Intent intent = new Intent(getApplicationContext(), PetunjukPenggunaan.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            private void notlpdarurat(){
                Intent intent = new Intent(getApplicationContext(), Notlpdarurat.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            private void lapor(){
                Intent intent = new Intent(getApplicationContext(), Lapor.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            private void statuslaporan(){
                Intent intent = new Intent(getApplicationContext(), StatusLaporan.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            @SuppressWarnings("StatementWithEmptyBody")
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();

                if (id == R.id.nav_lapor) {
                    lapor();

                } else if (id == R.id.nav_nomortelepondarurat) {
                    notlpdarurat();

                } else if (id == R.id.nav_petunjukpenggunaan) {
                    petunjukpenggunaan();

                } else if (id == R.id.nav_feedback) {
                    feedback();
                } else if (id == R.id.nav_aboutus) {
                    aboutus();

                } else if (id == R.id.keluar) {
                    logout();
                }
                else if (id == R.id.nav_statuslaporan) {
                    statuslaporan();
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }

        }
//package com.example.aspirasilapor;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.design.widget.NavigationView;
//import android.support.v4.view.GravityCompat;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBarDrawerToggle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import com.firebase.client.ChildEventListener;
//import com.firebase.client.DataSnapshot;
//import com.firebase.client.Firebase;
//import com.firebase.client.FirebaseError;
//import com.google.firebase.auth.FirebaseAuth;
//
//import java.util.ArrayList;
//
//public class MainActivity extends AppCompatActivity
//        implements NavigationView.OnNavigationItemSelectedListener {
//
//    Firebase fb;
//    ArrayList<String> tanggal = new ArrayList<>();
//    ListView lv;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
//        Firebase.setAndroidContext(this);
//
//        fb = new Firebase("https://aspirasistore.firebaseio.com/Lapor/Tanggal");
//        lv = (ListView) findViewById(R.id.Listview);
//
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tanggal);
//
//        lv.setAdapter(arrayAdapter);
//        fb.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                String value = dataSnapshot.getValue(String.class);
//                tanggal.add(value);
//                arrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
//    }
//
//
//
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void logout(){
//        FirebaseAuth.getInstance().signOut();
//        Intent intent = new Intent(getApplicationContext(), TampilanAwal.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        Toast.makeText(MainActivity.this, "Thanks for visited", Toast.LENGTH_SHORT).show();
//        startActivity(intent);
//
//    }
//    private void aboutus(){
//        Intent intent = new Intent(getApplicationContext(), AboutUs.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//    private void feedback(){
//        Intent intent = new Intent(getApplicationContext(), Feedback.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//    private void petunjukpenggunaan(){
//        Intent intent = new Intent(getApplicationContext(), PetunjukPenggunaan.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//    private void notlpdarurat(){
//        Intent intent = new Intent(getApplicationContext(), Notlpdarurat.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//    private void lapor(){
//        Intent intent = new Intent(getApplicationContext(), Lapor.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//    private void statuslaporan(){
//        Intent intent = new Intent(getApplicationContext(), StatusLaporan.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//
//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_lapor) {
//            lapor();
//
//        } else if (id == R.id.nav_nomortelepondarurat) {
//            notlpdarurat();
//
//        } else if (id == R.id.nav_petunjukpenggunaan) {
//            petunjukpenggunaan();
//
//        } else if (id == R.id.nav_feedback) {
//            feedback();
//        } else if (id == R.id.nav_aboutus) {
//            aboutus();
//
//        } else if (id == R.id.keluar) {
//            logout();
//        }
//        else if (id == R.id.nav_statuslaporan) {
//            statuslaporan();
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
//
//}
    }
    private void petunjukpenggunaan(){
        Intent intent = new Intent(getApplicationContext(), PetunjukPenggunaan.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private void notlpdarurat(){
        Intent intent = new Intent(getApplicationContext(), Notlpdarurat.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private void lapor(){
        Intent intent = new Intent(getApplicationContext(), Lapor.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private void statuslaporan(){
        Intent intent = new Intent(getApplicationContext(), StatusLaporan.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_lapor) {
            lapor();

        } else if (id == R.id.nav_nomortelepondarurat) {
            notlpdarurat();

        } else if (id == R.id.nav_petunjukpenggunaan) {
            petunjukpenggunaan();

        } else if (id == R.id.nav_feedback) {
            feedback();
        } else if (id == R.id.nav_aboutus) {
            aboutus();

        } else if (id == R.id.keluar) {
            logout();
        }
        else if (id == R.id.nav_statuslaporan) {
            statuslaporan();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
//package com.example.aspirasilapor;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.design.widget.NavigationView;
//import android.support.v4.view.GravityCompat;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBarDrawerToggle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import com.firebase.client.ChildEventListener;
//import com.firebase.client.DataSnapshot;
//import com.firebase.client.Firebase;
//import com.firebase.client.FirebaseError;
//import com.google.firebase.auth.FirebaseAuth;
//
//import java.util.ArrayList;
//
//public class MainActivity extends AppCompatActivity
//        implements NavigationView.OnNavigationItemSelectedListener {
//
//    Firebase fb;
//    ArrayList<String> tanggal = new ArrayList<>();
//    ListView lv;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
//        Firebase.setAndroidContext(this);
//
//        fb = new Firebase("https://aspirasistore.firebaseio.com/Lapor/Tanggal");
//        lv = (ListView) findViewById(R.id.Listview);
//
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tanggal);
//
//        lv.setAdapter(arrayAdapter);
//        fb.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                String value = dataSnapshot.getValue(String.class);
//                tanggal.add(value);
//                arrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
//    }
//
//
//
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void logout(){
//        FirebaseAuth.getInstance().signOut();
//        Intent intent = new Intent(getApplicationContext(), TampilanAwal.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        Toast.makeText(MainActivity.this, "Thanks for visited", Toast.LENGTH_SHORT).show();
//        startActivity(intent);
//
//    }
//    private void aboutus(){
//        Intent intent = new Intent(getApplicationContext(), AboutUs.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//    private void feedback(){
//        Intent intent = new Intent(getApplicationContext(), Feedback.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//    private void petunjukpenggunaan(){
//        Intent intent = new Intent(getApplicationContext(), PetunjukPenggunaan.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//    private void notlpdarurat(){
//        Intent intent = new Intent(getApplicationContext(), Notlpdarurat.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//    private void lapor(){
//        Intent intent = new Intent(getApplicationContext(), Lapor.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//    private void statuslaporan(){
//        Intent intent = new Intent(getApplicationContext(), StatusLaporan.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//
//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_lapor) {
//            lapor();
//
//        } else if (id == R.id.nav_nomortelepondarurat) {
//            notlpdarurat();
//
//        } else if (id == R.id.nav_petunjukpenggunaan) {
//            petunjukpenggunaan();
//
//        } else if (id == R.id.nav_feedback) {
//            feedback();
//        } else if (id == R.id.nav_aboutus) {
//            aboutus();
//
//        } else if (id == R.id.keluar) {
//            logout();
//        }
//        else if (id == R.id.nav_statuslaporan) {
//            statuslaporan();
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
//
//}
////
//////
////
////
///
//


//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.firebase.client.ChildEventListener;
//import com.firebase.client.DataSnapshot;
//import com.firebase.client.Firebase;
//import com.firebase.client.FirebaseError;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//
//import java.io.ByteArrayInputStream;
//import java.util.ArrayList;
//
//public class AdapterCard extends RecyclerView.Adapter<AdapterCard.ViewHolder> {
//    private ArrayList<Card> daftarChat;
//    private Context mContext;
//
//    public AdapterCard(ArrayList<Card> daftarChat, Context mContext) {
//        this.daftarChat = daftarChat;
//        this.mContext = mContext;
//
//        /////
//        //
//        ///
//        //
//
//        ///
//        ///
//        ///
//        //
//        //
//        //
//        //
//        //
//        //
//        //
//        //
//        ///
//        //
//        //
//        //
//
//        //
//        //
//        ///
//        //
//        //
//        //
//
//        ////
//        ////
//        ////
//        ///
//        ///
//        ///
//        /////
//        ////
//        ////
//        ////
//        ///
//        /////
//        ///
//        ///
//        ///
//        ////
//        ////
//        ///
//        ///
//        ////
//        ////
//        ///
//        ///
//        /////
//        ///
//        ///
//        ///
//        ////
//        ////
//        ///
//        ///
//        ///
//        ///
//        ///
//        ///
//        ///
//        /////
//        ////
//        /////
//        ////
//        ///
//        ////
//        ////
//        ////
//        /////
//        ///
//        ///
//        //
//        //
//        //
//        ///
//        ///
//        ///
//        ///
//        ///
//        //
//
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new AdapterCard.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.menu_card, parent, false)) {
//        };
//    }
//
//    @Override
//    public void onBindViewHolder(AdapterCard.ViewHolder holder, int position) {
//        Card card = daftarChat.get(position);
//        holder.bindTo(card);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return daftarChat.size();
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        private EditText kategori, tanggal, deskripsi;
//        private ImageView image;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            kategori = itemView.findViewById(R.id.txKategori);
//            tanggal = itemView.findViewById(R.id.txTanggal);
//            deskripsi = itemView.findViewById(R.id.txDesk);
//            image = itemView.findViewById(R.id.cardImg);
//
//            itemView.setOnClickListener(this);
//        }
//
//        @SuppressLint("StaticFieldLeak")
//        public void bindTo(final Card card) {
//            kategori.setText(card.getKategori());
//            tanggal.setText(card.getTanggal());
//            deskripsi.setText(card.getDeskripsi());
//
//            final StorageReference islandRef = FirebaseStorage.getInstance().getReference().child("images/" + card.getImagePath());
//
//            final long ONE_MEGABYTE = 10* 1024 * 1024;
//            islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                @Override
//                public void onSuccess(byte[] bytes) {
//                    Drawable d = Drawable.createFromStream(new ByteArrayInputStream(bytes), null);
//                    image.setImageDrawable(d);
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                    image.setImageResource(R.drawable.ic_launcher_background);
//                }
//            });
//        }
//
//        @Override
//        public void onClick(View v) {
//            Toast.makeText(mContext, kategori.getText().toString(), Toast.LENGTH_SHORT).show();
//        }
//    }
//}
//
//package com.example.aspirasilapor;
//
//        import android.content.Intent;
//        import android.os.Bundle;
//        import android.support.design.widget.NavigationView;
//        import android.support.v4.view.GravityCompat;
//        import android.support.v4.widget.DrawerLayout;
//        import android.support.v7.app.ActionBarDrawerToggle;
//        import android.support.v7.app.AppCompatActivity;
//        import android.support.v7.widget.Toolbar;
//        import android.view.Menu;
//        import android.view.MenuItem;
//        import android.widget.ArrayAdapter;
//        import android.widget.ListView;
//        import android.widget.Toast;
//
//        import com.firebase.client.ChildEventListener;
//        import com.firebase.client.DataSnapshot;
//        import com.firebase.client.Firebase;
//        import com.firebase.client.FirebaseError;
//        import com.google.firebase.auth.FirebaseAuth;
//
//        import java.util.ArrayList;
//
//public class MainActivity extends AppCompatActivity
//        implements NavigationView.OnNavigationItemSelectedListener {
//
//    Firebase fb;
//    ArrayList<String> tanggal = new ArrayList<>();
//    ListView lv;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
//        Firebase.setAndroidContext(this);
//
//        fb = new Firebase("https://aspirasistore.firebaseio.com/Lapor/Tanggal");
//        lv = (ListView) findViewById(R.id.Listview);
//
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tanggal);
//
//        lv.setAdapter(arrayAdapter);
//        fb.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                String value = dataSnapshot.getValue(String.class);
//                tanggal.add(value);
//                arrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
//    }
//
//
//
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void logout(){
//        FirebaseAuth.getInstance().signOut();
//        Intent intent = new Intent(getApplicationContext(), TampilanAwal.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        Toast.makeText(MainActivity.this, "Thanks for visited", Toast.LENGTH_SHORT).show();
//        startActivity(intent);
//
//    }
//    private void aboutus(){
//        Intent intent = new Intent(getApplicationContext(), AboutUs.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//    private void feedback(){
//        Intent intent = new Intent(getApplicationContext(), Feedback.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//    private void petunjukpenggunaan(){
//        Intent intent = new Intent(getApplicationContext(), PetunjukPenggunaan.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//    private void notlpdarurat(){
//        Intent intent = new Intent(getApplicationContext(), Notlpdarurat.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//    private void lapor(){
//        Intent intent = new Intent(getApplicationContext(), Lapor.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//    private void statuslaporan(){
//        Intent intent = new Intent(getApplicationContext(), StatusLaporan.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//
//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_lapor) {
//            lapor();
//
//        } else if (id == R.id.nav_nomortelepondarurat) {
//            notlpdarurat();
//
//        } else if (id == R.id.nav_petunjukpenggunaan) {
//            petunjukpenggunaan();
//
//        } else if (id == R.id.nav_feedback) {
//            feedback();
//        } else if (id == R.id.nav_aboutus) {
//            aboutus();
//
//        } else if (id == R.id.keluar) {
//            logout();
//        }
//        else if (id == R.id.nav_statuslaporan) {
//            statuslaporan();
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
//
//}
////package com.example.aspirasilapor;
////
////import android.content.Intent;
////import android.os.Bundle;
////import android.support.design.widget.NavigationView;
////import android.support.v4.view.GravityCompat;
////import android.support.v4.widget.DrawerLayout;
////import android.support.v7.app.ActionBarDrawerToggle;
////import android.support.v7.app.AppCompatActivity;
////import android.support.v7.widget.Toolbar;
////import android.view.Menu;
////import android.view.MenuItem;
////import android.widget.ArrayAdapter;
////import android.widget.ListView;
////import android.widget.Toast;
////
////import com.firebase.client.ChildEventListener;
////import com.firebase.client.DataSnapshot;
////import com.firebase.client.Firebase;
////import com.firebase.client.FirebaseError;
////import com.google.firebase.auth.FirebaseAuth;
////
////import java.util.ArrayList;
////
////public class MainActivity extends AppCompatActivity
////        implements NavigationView.OnNavigationItemSelectedListener {
////
////    Firebase fb;
////    ArrayList<String> tanggal = new ArrayList<>();
////    ListView lv;
////
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_main);
////        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
////        setSupportActionBar(toolbar);
////
////        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
////        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
////                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
////        drawer.addDrawerListener(toggle);
////        toggle.syncState();
////
////        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
////        navigationView.setNavigationItemSelectedListener(this);
////
////        Firebase.setAndroidContext(this);
////
////        fb = new Firebase("https://aspirasistore.firebaseio.com/Lapor/Tanggal");
////        lv = (ListView) findViewById(R.id.Listview);
////
////        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tanggal);
////
////        lv.setAdapter(arrayAdapter);
////        fb.addChildEventListener(new ChildEventListener() {
////            @Override
////            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
////                String value = dataSnapshot.getValue(String.class);
////                tanggal.add(value);
////                arrayAdapter.notifyDataSetChanged();
////            }
////
////            @Override
////            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
////
////            }
////
////            @Override
////            public void onChildRemoved(DataSnapshot dataSnapshot) {
////
////            }
////
////            @Override
////            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
////
////            }
////
////            @Override
////            public void onCancelled(FirebaseError firebaseError) {
////
////            }
////        });
////    }
////
////
////
////    @Override
////    public void onBackPressed() {
////        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
////        if (drawer.isDrawerOpen(GravityCompat.START)) {
////            drawer.closeDrawer(GravityCompat.START);
////        } else {
////            super.onBackPressed();
////        }
////    }
////
////    @Override
////    public boolean onCreateOptionsMenu(Menu menu) {
////        // Inflate the menu; this adds items to the action bar if it is present.
////        getMenuInflater().inflate(R.menu.main, menu);
////        return true;
////    }
////
////    @Override
////    public boolean onOptionsItemSelected(MenuItem item) {
////        // Handle action bar item clicks here. The action bar will
////        // automatically handle clicks on the Home/Up button, so long
////        // as you specify a parent activity in AndroidManifest.xml.
////        int id = item.getItemId();
////
////        //noinspection SimplifiableIfStatement
////        if (id == R.id.action_settings) {
////            return true;
////        }
////
////        return super.onOptionsItemSelected(item);
////    }
////
////    private void logout(){
////        FirebaseAuth.getInstance().signOut();
////        Intent intent = new Intent(getApplicationContext(), TampilanAwal.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        Toast.makeText(MainActivity.this, "Thanks for visited", Toast.LENGTH_SHORT).show();
////        startActivity(intent);
////
////    }
////    private void aboutus(){
////        Intent intent = new Intent(getApplicationContext(), AboutUs.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        startActivity(intent);
////    }
////    private void feedback(){
////        Intent intent = new Intent(getApplicationContext(), Feedback.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        startActivity(intent);
////    }
////    private void petunjukpenggunaan(){
////        Intent intent = new Intent(getApplicationContext(), PetunjukPenggunaan.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        startActivity(intent);
////    }
////    private void notlpdarurat(){
////        Intent intent = new Intent(getApplicationContext(), Notlpdarurat.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        startActivity(intent);
////    }
////    private void lapor(){
////        Intent intent = new Intent(getApplicationContext(), Lapor.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        startActivity(intent);
////    }
////    private void statuslaporan(){
////        Intent intent = new Intent(getApplicationContext(), StatusLaporan.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        startActivity(intent);
////    }
////
////    @SuppressWarnings("StatementWithEmptyBody")
////    @Override
////    public boolean onNavigationItemSelected(MenuItem item) {
////        // Handle navigation view item clicks here.
////        int id = item.getItemId();
////
////        if (id == R.id.nav_lapor) {
////            lapor();
////
////        } else if (id == R.id.nav_nomortelepondarurat) {
////            notlpdarurat();
////
////        } else if (id == R.id.nav_petunjukpenggunaan) {
////            petunjukpenggunaan();
////
////        } else if (id == R.id.nav_feedback) {
////            feedback();
////        } else if (id == R.id.nav_aboutus) {
////            aboutus();
////
////        } else if (id == R.id.keluar) {
////            logout();
////        }
////        else if (id == R.id.nav_statuslaporan) {
////            statuslaporan();
////        }
////
////        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
////        drawer.closeDrawer(GravityCompat.START);
////        return true;
////    }
////
////}
//
/////
//
//
//
//
//package com.example.aspirasilapor;
//
//        import android.support.v7.app.AppCompatActivity;
//        import android.os.Bundle;
//
//public class Card {
//    String imagePath,kategori,tanggal,deskripsi;
//
//    public Card(String imagePath, String nama, String harga, String deskripsi) {
//        this.imagePath = imagePath;
//        this.kategori = nama;
//        this.tanggal = harga;
//        this.deskripsi = deskripsi;
//    }
//
//    public String getImagePath() {
//        return imagePath;
//    }
//
//    public void setImagePath(String imagePath) {
//        this.imagePath = imagePath;
//    }
//
//    public String getKategori() {
//        return kategori;
//    }
//
//    public void setKategoria(String kategori) {
//        this.kategori = kategori;
//    }
//
//    public String getTanggal() {
//        return tanggal;
//    }
//
//    public void setTanggal(String tanggal) {
//        this.tanggal = tanggal;
//    }
//
//    public String getDeskripsi() {
//        return deskripsi;
//    }
//
//    public void setDeskripsi(String deskripsi) {
//        this.deskripsi = deskripsi;
//    }
//}
//
//
//
///
//        package com.example.aspirasilapor;
//
//        import android.content.Intent;
//        import android.os.Bundle;
//        import android.support.design.widget.NavigationView;
//        import android.support.v4.view.GravityCompat;
//        import android.support.v4.widget.DrawerLayout;
//        import android.support.v7.app.ActionBarDrawerToggle;
//        import android.support.v7.app.AppCompatActivity;
//        import android.support.v7.widget.Toolbar;
//        import android.view.Menu;
//        import android.view.MenuItem;
//        import android.widget.ArrayAdapter;
//        import android.widget.ListView;
//        import android.widget.Toast;
//
//        import com.firebase.client.ChildEventListener;
//        import com.firebase.client.DataSnapshot;
//        import com.firebase.client.Firebase;
//        import com.firebase.client.FirebaseError;
//        import com.google.firebase.auth.FirebaseAuth;
//
//        import java.util.ArrayList;
//
//public class MainActivity extends AppCompatActivity
//        implements NavigationView.OnNavigationItemSelectedListener {
//
//    Firebase fb;
//    ArrayList<String> tanggal = new ArrayList<>();
//    ListView lv;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
//        Firebase.setAndroidContext(this);
//
//        fb = new Firebase("https://aspirasistore.firebaseio.com/Lapor/Tanggal");
//        lv = (ListView) findViewById(R.id.Listview);
//
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tanggal);
//
//        lv.setAdapter(arrayAdapter);
//        fb.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                String value = dataSnapshot.getValue(String.class);
//                tanggal.add(value);
//                arrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
//    }
//
//
//
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//    }
//
//    @Override
//    public void onCancelled(FirebaseError firebaseError) {
//
//    }
//});
//        }
//
//
//
//@Override
//public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//        drawer.closeDrawer(GravityCompat.START);
//        } else {
//        super.onBackPressed();
//        }
//        }
//
//@Override
//public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//        }
//@Override
//public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//        }
//
//@Override
//public void onCancelled(FirebaseError firebaseError) {
//
//        }
//        });
//        }
//
//
//
//@Override
//public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//        drawer.closeDrawer(GravityCompat.START);
//        } else {
//        super.onBackPressed();
//        }
//        }
//
//@Override
//public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//        }
//
//
//@Override
//public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//        }
//
//@Override
//public void onCancelled(FirebaseError firebaseError) {
//
//        }
//        });
//        }
//
//
//
//@Override
//public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//        drawer.closeDrawer(GravityCompat.START);
//        } else {
//        super.onBackPressed();
//        }
//        }
//
//@Override
//public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//        }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void logout(){
//        FirebaseAuth.getInstance().signOut();
//        Intent intent = new Intent(getApplicationContext(), TampilanAwal.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        Toast.makeText(MainActivity.this, "Thanks for visited", Toast.LENGTH_SHORT).show();
//        startActivity(intent);
//
//    }
//    private void aboutus(){
//        Intent intent = new Intent(getApplicationContext(), AboutUs.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//    private void feedback(){
//        Intent intent = new Intent(getApplicationContext(), Feedback.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//        //
//
//        package com.example.aspirasilapor;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.design.widget.NavigationView;
//import android.support.v4.view.GravityCompat;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBarDrawerToggle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import com.firebase.client.ChildEventListener;
//import com.firebase.client.DataSnapshot;
//import com.firebase.client.Firebase;
//import com.firebase.client.FirebaseError;
//import com.google.firebase.auth.FirebaseAuth;
//
//import java.util.ArrayList;
//
//        public class MainActivity extends AppCompatActivity
//                implements NavigationView.OnNavigationItemSelectedListener {
//
//            Firebase fb;
//            ArrayList<String> tanggal = new ArrayList<>();
//            ListView lv;
//
//
//            @Override
//            protected void onCreate(Bundle savedInstanceState) {
//                super.onCreate(savedInstanceState);
//                setContentView(R.layout.activity_main);
//                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//                setSupportActionBar(toolbar);
//
//                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//                drawer.addDrawerListener(toggle);
//                toggle.syncState();
//
//                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//                navigationView.setNavigationItemSelectedListener(this);
//
//                Firebase.setAndroidContext(this);
//
//                fb = new Firebase("https://aspirasistore.firebaseio.com/Lapor/Tanggal");
//                lv = (ListView) findViewById(R.id.Listview);
//
//                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tanggal);
//
//                lv.setAdapter(arrayAdapter);
//                fb.addChildEventListener(new ChildEventListener() {
//                    @Override
//                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                        String value = dataSnapshot.getValue(String.class);
//                        tanggal.add(value);
//                        arrayAdapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                    }
//
//                    @Override
//                    public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                    }
//
//                    @Override
//                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {
//
//                    }
//                });
//            }
//
//
//
//            @Override
//            public void onBackPressed() {
//                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//                if (drawer.isDrawerOpen(GravityCompat.START)) {
//                    drawer.closeDrawer(GravityCompat.START);
//                } else {
//                    super.onBackPressed();
//                }
//            }
//
//            @Override
//            public boolean onCreateOptionsMenu(Menu menu) {
//                // Inflate the menu; this adds items to the action bar if it is present.
//                getMenuInflater().inflate(R.menu.main, menu);
//                return true;
//            }
//
//            @Override
//            public boolean onOptionsItemSelected(MenuItem item) {
//                // Handle action bar item clicks here. The action bar will
//                // automatically handle clicks on the Home/Up button, so long
//                // as you specify a parent activity in AndroidManifest.xml.
//                int id = item.getItemId();
//
//                //noinspection SimplifiableIfStatement
//                if (id == R.id.action_settings) {
//                    return true;
//                }
//
//                return super.onOptionsItemSelected(item);
//            }
//
//            private void logout(){
//                FirebaseAuth.getInstance().signOut();
//                Intent intent = new Intent(getApplicationContext(), TampilanAwal.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                Toast.makeText(MainActivity.this, "Thanks for visited", Toast.LENGTH_SHORT).show();
//                startActivity(intent);
//
//            }
//            private void aboutus(){
//                Intent intent = new Intent(getApplicationContext(), AboutUs.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//            }
//            private void feedback(){
//                Intent intent = new Intent(getApplicationContext(), Feedback.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//            }
//            private void petunjukpenggunaan(){
//                Intent intent = new Intent(getApplicationContext(), PetunjukPenggunaan.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//            }
//            private void notlpdarurat(){
//                Intent intent = new Intent(getApplicationContext(), Notlpdarurat.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//            }
//            private void lapor(){
//                Intent intent = new Intent(getApplicationContext(), Lapor.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//            }
//            private void statuslaporan(){
//                Intent intent = new Intent(getApplicationContext(), StatusLaporan.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//            }
//
//            @SuppressWarnings("StatementWithEmptyBody")
//            @Override
//            public boolean onNavigationItemSelected(MenuItem item) {
//                // Handle navigation view item clicks here.
//                int id = item.getItemId();
//
//                if (id == R.id.nav_lapor) {
//                    lapor();
//
//                } else if (id == R.id.nav_nomortelepondarurat) {
//                    notlpdarurat();
//
//                } else if (id == R.id.nav_petunjukpenggunaan) {
//                    petunjukpenggunaan();
//
//                } else if (id == R.id.nav_feedback) {
//                    feedback();
//                } else if (id == R.id.nav_aboutus) {
//                    aboutus();
//
//                } else if (id == R.id.keluar) {
//                    logout();
//                }
//                else if (id == R.id.nav_statuslaporan) {
//                    statuslaporan();
//                }
//
//                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//                drawer.closeDrawer(GravityCompat.START);
//                return true;
//            }
//
//        }
////package com.example.aspirasilapor;
////
////import android.content.Intent;
////import android.os.Bundle;
////import android.support.design.widget.NavigationView;
////import android.support.v4.view.GravityCompat;
////import android.support.v4.widget.DrawerLayout;
////import android.support.v7.app.ActionBarDrawerToggle;
////import android.support.v7.app.AppCompatActivity;
////import android.support.v7.widget.Toolbar;
////import android.view.Menu;
////import android.view.MenuItem;
////import android.widget.ArrayAdapter;
////import android.widget.ListView;
////import android.widget.Toast;
////
////import com.firebase.client.ChildEventListener;
////import com.firebase.client.DataSnapshot;
////import com.firebase.client.Firebase;
////import com.firebase.client.FirebaseError;
////import com.google.firebase.auth.FirebaseAuth;
////
////import java.util.ArrayList;
////
////public class MainActivity extends AppCompatActivity
////        implements NavigationView.OnNavigationItemSelectedListener {
////
////    Firebase fb;
////    ArrayList<String> tanggal = new ArrayList<>();
////    ListView lv;
////
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_main);
////        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
////        setSupportActionBar(toolbar);
////
////        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
////        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
////                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
////        drawer.addDrawerListener(toggle);
////        toggle.syncState();
////
////        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
////        navigationView.setNavigationItemSelectedListener(this);
////
////        Firebase.setAndroidContext(this);
////
////        fb = new Firebase("https://aspirasistore.firebaseio.com/Lapor/Tanggal");
////        lv = (ListView) findViewById(R.id.Listview);
////
////        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tanggal);
////
////        lv.setAdapter(arrayAdapter);
////        fb.addChildEventListener(new ChildEventListener() {
////            @Override
////            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
////                String value = dataSnapshot.getValue(String.class);
////                tanggal.add(value);
////                arrayAdapter.notifyDataSetChanged();
////            }
////
////            @Override
////            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
////
////            }
////
////            @Override
////            public void onChildRemoved(DataSnapshot dataSnapshot) {
////
////            }
////
////            @Override
////            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
////
////            }
////
////            @Override
////            public void onCancelled(FirebaseError firebaseError) {
////
////            }
////        });
////    }
////
////
////
////    @Override
////    public void onBackPressed() {
////        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
////        if (drawer.isDrawerOpen(GravityCompat.START)) {
////            drawer.closeDrawer(GravityCompat.START);
////        } else {
////            super.onBackPressed();
////        }
////    }
////
////    @Override
////    public boolean onCreateOptionsMenu(Menu menu) {
////        // Inflate the menu; this adds items to the action bar if it is present.
////        getMenuInflater().inflate(R.menu.main, menu);
////        return true;
////    }
////
////    @Override
////    public boolean onOptionsItemSelected(MenuItem item) {
////        // Handle action bar item clicks here. The action bar will
////        // automatically handle clicks on the Home/Up button, so long
////        // as you specify a parent activity in AndroidManifest.xml.
////        int id = item.getItemId();
////
////        //noinspection SimplifiableIfStatement
////        if (id == R.id.action_settings) {
////            return true;
////        }
////
////        return super.onOptionsItemSelected(item);
////    }
////
////    private void logout(){
////        FirebaseAuth.getInstance().signOut();
////        Intent intent = new Intent(getApplicationContext(), TampilanAwal.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        Toast.makeText(MainActivity.this, "Thanks for visited", Toast.LENGTH_SHORT).show();
////        startActivity(intent);
////
////    }
////    private void aboutus(){
////        Intent intent = new Intent(getApplicationContext(), AboutUs.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        startActivity(intent);
////    }
////    private void feedback(){
////        Intent intent = new Intent(getApplicationContext(), Feedback.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        startActivity(intent);
////    }
////    private void petunjukpenggunaan(){
////        Intent intent = new Intent(getApplicationContext(), PetunjukPenggunaan.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        startActivity(intent);
////    }
////    private void notlpdarurat(){
////        Intent intent = new Intent(getApplicationContext(), Notlpdarurat.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        startActivity(intent);
////    }
////    private void lapor(){
////        Intent intent = new Intent(getApplicationContext(), Lapor.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        startActivity(intent);
////    }
////    private void statuslaporan(){
////        Intent intent = new Intent(getApplicationContext(), StatusLaporan.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        startActivity(intent);
////    }
////
////    @SuppressWarnings("StatementWithEmptyBody")
////    @Override
////    public boolean onNavigationItemSelected(MenuItem item) {
////        // Handle navigation view item clicks here.
////        int id = item.getItemId();
////
////        if (id == R.id.nav_lapor) {
////            lapor();
////
////        } else if (id == R.id.nav_nomortelepondarurat) {
////            notlpdarurat();
////
////        } else if (id == R.id.nav_petunjukpenggunaan) {
////            petunjukpenggunaan();
////
////        } else if (id == R.id.nav_feedback) {
////            feedback();
////        } else if (id == R.id.nav_aboutus) {
////            aboutus();
////
////        } else if (id == R.id.keluar) {
////            logout();
////        }
////        else if (id == R.id.nav_statuslaporan) {
////            statuslaporan();
////        }
////
////        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
////        drawer.closeDrawer(GravityCompat.START);
////        return true;
////    }
////
////}
//    }
//    private void petunjukpenggunaan(){
//        Intent intent = new Intent(getApplicationContext(), PetunjukPenggunaan.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//    private void notlpdarurat(){
//        Intent intent = new Intent(getApplicationContext(), Notlpdarurat.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//    private void lapor(){
//        Intent intent = new Intent(getApplicationContext(), Lapor.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//    private void statuslaporan(){
//        Intent intent = new Intent(getApplicationContext(), StatusLaporan.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//
//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_lapor) {
//            lapor();
//
//        } else if (id == R.id.nav_nomortelepondarurat) {
//            notlpdarurat();
//
//        } else if (id == R.id.nav_petunjukpenggunaan) {
//            petunjukpenggunaan();
//
//        } else if (id == R.id.nav_feedback) {
//            feedback();
//        } else if (id == R.id.nav_aboutus) {
//            aboutus();
//
//        } else if (id == R.id.keluar) {
//            logout();
//        }
//        else if (id == R.id.nav_statuslaporan) {
//            statuslaporan();
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
//
//}
////package com.example.aspirasilapor;
////
////import android.content.Intent;
////import android.os.Bundle;
////import android.support.design.widget.NavigationView;
////import android.support.v4.view.GravityCompat;
////import android.support.v4.widget.DrawerLayout;
////import android.support.v7.app.ActionBarDrawerToggle;
////import android.support.v7.app.AppCompatActivity;
////import android.support.v7.widget.Toolbar;
////import android.view.Menu;
////import android.view.MenuItem;
////import android.widget.ArrayAdapter;
////import android.widget.ListView;
////import android.widget.Toast;
////
////import com.firebase.client.ChildEventListener;
////import com.firebase.client.DataSnapshot;
////import com.firebase.client.Firebase;
////import com.firebase.client.FirebaseError;
////import com.google.firebase.auth.FirebaseAuth;
////
////import java.util.ArrayList;
////
////public class MainActivity extends AppCompatActivity
////        implements NavigationView.OnNavigationItemSelectedListener {
////
////    Firebase fb;
////    ArrayList<String> tanggal = new ArrayList<>();
////    ListView lv;
////
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_main);
////        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
////        setSupportActionBar(toolbar);
////
////        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
////        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
////                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
////        drawer.addDrawerListener(toggle);
////        toggle.syncState();
////
////        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
////        navigationView.setNavigationItemSelectedListener(this);
////
////        Firebase.setAndroidContext(this);
////
////        fb = new Firebase("https://aspirasistore.firebaseio.com/Lapor/Tanggal");
////        lv = (ListView) findViewById(R.id.Listview);
////
////        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tanggal);
////
////        lv.setAdapter(arrayAdapter);
////        fb.addChildEventListener(new ChildEventListener() {
////            @Override
////            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
////                String value = dataSnapshot.getValue(String.class);
////                tanggal.add(value);
////                arrayAdapter.notifyDataSetChanged();
////            }
////
////            @Override
////            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
////
////            }
////
////            @Override
////            public void onChildRemoved(DataSnapshot dataSnapshot) {
////
////            }
////
////            @Override
////            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
////
////            }
////
////            @Override
////            public void onCancelled(FirebaseError firebaseError) {
////
////            }
////        });
////    }
////
////
////
////    @Override
////    public void onBackPressed() {
////        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
////        if (drawer.isDrawerOpen(GravityCompat.START)) {
////            drawer.closeDrawer(GravityCompat.START);
////        } else {
////            super.onBackPressed();
////        }
////    }
////
////    @Override
////    public boolean onCreateOptionsMenu(Menu menu) {
////        // Inflate the menu; this adds items to the action bar if it is present.
////        getMenuInflater().inflate(R.menu.main, menu);
////        return true;
////    }
////
////    @Override
////    public boolean onOptionsItemSelected(MenuItem item) {
////        // Handle action bar item clicks here. The action bar will
////        // automatically handle clicks on the Home/Up button, so long
////        // as you specify a parent activity in AndroidManifest.xml.
////        int id = item.getItemId();
////
////        //noinspection SimplifiableIfStatement
////        if (id == R.id.action_settings) {
////            return true;
////        }
////
////        return super.onOptionsItemSelected(item);
////    }
////
////    private void logout(){
////        FirebaseAuth.getInstance().signOut();
////        Intent intent = new Intent(getApplicationContext(), TampilanAwal.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        Toast.makeText(MainActivity.this, "Thanks for visited", Toast.LENGTH_SHORT).show();
////        startActivity(intent);
////
////    }
////    private void aboutus(){
////        Intent intent = new Intent(getApplicationContext(), AboutUs.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        startActivity(intent);
////    }
////    private void feedback(){
////        Intent intent = new Intent(getApplicationContext(), Feedback.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        startActivity(intent);
////    }
////    private void petunjukpenggunaan(){
////        Intent intent = new Intent(getApplicationContext(), PetunjukPenggunaan.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        startActivity(intent);
////    }
////    private void notlpdarurat(){
////        Intent intent = new Intent(getApplicationContext(), Notlpdarurat.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        startActivity(intent);
////    }
////    private void lapor(){
////        Intent intent = new Intent(getApplicationContext(), Lapor.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        startActivity(intent);
////    }
////    private void statuslaporan(){
////        Intent intent = new Intent(getApplicationContext(), StatusLaporan.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        startActivity(intent);
////    }
////
////    @SuppressWarnings("StatementWithEmptyBody")
////    @Override
////    public boolean onNavigationItemSelected(MenuItem item) {
////        // Handle navigation view item clicks here.
////        int id = item.getItemId();
////
////        if (id == R.id.nav_lapor) {
////            lapor();
////
////        } else if (id == R.id.nav_nomortelepondarurat) {
////            notlpdarurat();
////
////        } else if (id == R.id.nav_petunjukpenggunaan) {
////            petunjukpenggunaan();
////
////        } else if (id == R.id.nav_feedback) {
////            feedback();
////        } else if (id == R.id.nav_aboutus) {
////            aboutus();
////
////        } else if (id == R.id.keluar) {
////            logout();
////        }
////        else if (id == R.id.nav_statuslaporan) {
////            statuslaporan();
////        }
////
////        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
////        drawer.closeDrawer(GravityCompat.START);
////        return true;
////    }
////
////}
//////
////////
//////
//////
/////
////