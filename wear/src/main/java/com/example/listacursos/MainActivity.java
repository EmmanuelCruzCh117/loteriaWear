package com.example.listacursos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.wear.widget.WearableLinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;

import com.example.listacursos.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    //private ActivityMainBinding binding;
    private List<ItemList> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WearableRecyclerView recyclerView = (WearableRecyclerView)
                findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEdgeItemsCenteringEnabled(true);

        WearableLinearLayoutManager layoutManager = new WearableLinearLayoutManager(this);
        layoutManager.setOrientation(WearableLinearLayoutManager.VERTICAL);
        layoutManager.setLayoutCallback(new CustomScrollingLayoutCallback());

        recyclerView.setLayoutManager(layoutManager);

        //Items

        items.add(new ItemList(R.mipmap.carro, "Carro", "Automovil derpotivo"));
        items.add(new ItemList(R.mipmap.money, "Dinero", "Dinero en Efectivo de Java"));
        items.add(new ItemList(R.mipmap.iphone, "Iphone 13", "Iphone 13"));
        items.add(new ItemList(R.mipmap.lap, "Laptop Gamer", "Laptop Gamer ASUS ROG"));
        items.add(new ItemList(R.mipmap.pc, "PC Gamer", "PC Gamer Ultra"));

        ListAdapter listAdapter = new ListAdapter(items, new ListAdapter.AdapterCallback() {
            @Override
            public void onItemClicked(View v, int itemPosition) {
                Intent intent = new Intent(getApplicationContext(), CardActivity.class);
                intent.putExtra("titulo", items.get(itemPosition).getNameItem());
                intent.putExtra("descripcion", items.get(itemPosition).getDescriptionItem());
                intent.putExtra("imagen", items.get(itemPosition).getImageItem());
                startActivity(intent);
            }
        });

//        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //      setContentView(binding.getRoot());
        recyclerView.setAdapter(listAdapter);
    }
}
class CustomScrollingLayoutCallback extends WearableLinearLayoutManager.LayoutCallback{
    private static final float MAX_ICON_PROGRESS = 0.65f;
    private float progressToCenter;

    @Override
    public void onLayoutFinished(View child, RecyclerView parent) {
        float centerOffset =
                ((float) child.getHeight() / 2.0f) / (float) parent.getHeight();
        float yRelativeToCenterOffset =
                (child.getY() / parent.getHeight()) + centerOffset;

        progressToCenter = Math.abs(0.5f - yRelativeToCenterOffset);
        progressToCenter = Math.min(progressToCenter, MAX_ICON_PROGRESS);

        child.setScaleX(1 - progressToCenter);
        child.setScaleY(1 - progressToCenter);
    }
}

class ItemList {
    final private int imageItem;
    final private String nameItem;
    final private String description;

    public ItemList(int _imageItem, String _nameItem, String _description){
        this.imageItem = _imageItem;
        this.nameItem = _nameItem;
        this.description = _description;
    }

    public int getImageItem() { return imageItem; }
    public String getNameItem() { return nameItem; }
    public String getDescriptionItem() { return description; }
}
