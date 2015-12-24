package com.map.santamap;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {
	// GoogleMapオブジェクトの宣言
	private GoogleMap googlemap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createMap();
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void mapinit(){
    	// 地図タイプ設定
        googlemap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // 現在位置ボタンの表示を行なう
        googlemap.setMyLocationEnabled(true);

        // 東京駅の位置、ズーム設定
        CameraPosition camerapos = new CameraPosition.Builder()
                .target(new LatLng(35.681382, 139.766084)).zoom(15.5f).build();

        // 地図の中心の変更する
        googlemap.moveCamera(CameraUpdateFactory.newCameraPosition(camerapos));
    }

    private void createMap(){
    	// MapFragmentオブジェクトを取得
    	MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);

    	mapFragment.getMapAsync(new OnMapReadyCallback() {

			@Override
			public void onMapReady(GoogleMap googleMap) {
				// TODO 自動生成されたメソッド・スタブ
				googlemap = googleMap;
				mapinit();
			}
		});
    }

}
