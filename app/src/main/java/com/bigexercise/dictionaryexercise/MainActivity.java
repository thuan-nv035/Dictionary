package com.bigexercise.dictionaryexercise;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;

    //Xong bài 7 tiếp theo là bài 8
    MenuItem menuSetting; // Để mapping với menuSetting icon
    DictionaryFragment dictionaryFragment;
    BookmarkFragment bookmarkFragment;
    //DetailFragment detailFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,toolbar,0,0);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
//                .setDrawerLayout(drawer)
//                .build();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dictionaryFragment = new DictionaryFragment();
        bookmarkFragment = new BookmarkFragment();
        //detailFragment = new DetailFragment();
        goToFragment(dictionaryFragment, true);

        dictionaryFragment.setOnFragmentListener(new FragmentListener() {
            @Override
            public void OnItemClick(String value) {
                //Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();
                // Khi thực hiện ở đây nó sẽ nhảy vào hàm setOnFragment ở DictionaryFragment sau đó gán biến toàn cục cho biến cục bộ và thực thi
                goToFragment(DetailFragment.getNewInstance(value),false);
            }
        });
        bookmarkFragment.setOnFragmentListener(new FragmentListener() {
            @Override
            public void OnItemClick(String value) {
                //Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();
                goToFragment(DetailFragment.getNewInstance(value),false);
            }
        });
//tìm kiếm
        EditText edit_search = findViewById(R.id.edit_search);
        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                dictionaryFragment.filterValue(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    // Hàm này xử lý cái gì tại sao có hàm này lại ko bị lỗi
    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // Hàm này khởi tạo cùng lúc khi oncreate chạy
        // Đây là nơi khi mà ta ấn vào cái OptionMenu mà nó có EN-VI thì nó sẽ chạy vào hàm này
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu); // Đây là chỗ dấu 3 chấm chọn setting. Là nơi chọn dịch theo như thế nào
        menuSetting = menu.findItem(R.id.action_settings); // Mapping với nhau
        // Set trạng thái khi chọn icon là En-Vi hay Vi-En
        String id =  Global.getState(this, "dic_type");
        if(id != null){
            onOptionsItemSelected(menu.findItem(Integer.valueOf(id))); // Đây là get Cái Trạng thái ta đã lưu ở trong file bằng sharePreference. Get trạng thái đã lưu để hiển thị ra màn hình
        }else {
            dictionaryFragment.resetDataSource(DB.getData(R.id.action_en_vi));
        }
        return true;
    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
    // Hàm xử lý khi click vào Item En-Vi , Vi-En
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Get cái id của cái item khi mà ta click vào
        int id = item.getItemId();
        String[] source = DB.getData(id);
        //Save State()
        Global.saveState(this, "dic_type",String.valueOf(id));
        if(id == R.id.action_en_vi){
            dictionaryFragment.resetDataSource(source);
            menuSetting.setIcon(getDrawable(R.drawable.en_vi));// Nếu select icon nào thì set icon đó hiển thị lên
        }else if(id == R.id.action_vi_en){
            dictionaryFragment.resetDataSource(source);
            menuSetting.setIcon(getDrawable(R.drawable.vi_en));
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.nav_bookmark){
            goToFragment(bookmarkFragment,false);
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    //
    void goToFragment(Fragment fragment, boolean isTop){
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        fragmentTransaction.add(R.id.fragment_container, fragment);
//        if(!isTop){
//            fragmentTransaction.addToBackStack(null); // Để làm gì
//        }
//        fragmentTransaction.commit();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(!isTop){
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }

}