package com.map.santamap;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends Activity {
	public static final int MINTIME=10000 // 通知のための最小時間間隔（ミリ秒）
							,MINDISTANCE=10;// 通知のための最小距離間隔（メートル）

	// GoogleMapオブジェクトの宣言
	private GoogleMap googlemap;
	//GPS
	private LocationManager locman;
	//緯度経度
	private double latitude,longitude;
	//位置リスナ
	private LocationListener loclis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mkloclis();
        getService();
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

    @Override
    protected void onStart(){
    	super.onStart();
    	startGPS();
    }

    @Override
    protected void onStop(){
    	super.onStop();
    	stopGPS();
    }

    private void getService(){
    	locman = (LocationManager)this.getSystemService(LOCATION_SERVICE);
    	String msg = locman.isProviderEnabled(LocationManager.GPS_PROVIDER)?"OK":"NG";
    	Log.d("GPS Enabled", msg);
    	showtoast(msg);
    }

    private void mkloclis(){
    	loclis = new LocationListener() {

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO 自動生成されたメソッド・スタブ

			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO 自動生成されたメソッド・スタブ

			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO 自動生成されたメソッド・スタブ

			}

			@Override
			public void onLocationChanged(Location location) {
				// TODO 自動生成されたメソッド・スタブ
				latitude = location.getLatitude();
				longitude = location.getLongitude();
				Log.d("GPS",latitude+","+longitude);
			}
		};
    }

    private void startGPS(){
    	locman.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINTIME, MINDISTANCE, loclis);
    }

    private void stopGPS(){
    	locman.removeUpdates(loclis);
    }

    private void mapinit(){
    	// 地図タイプ設定
        googlemap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // 現在位置ボタンの表示を行なう
        googlemap.setMyLocationEnabled(true);

        // 東京駅の位置、ズーム設定
        CameraPosition camerapos = new CameraPosition.Builder()
                .target(new LatLng(latitude,longitude/*35.681382, 139.766084*/)).zoom(10.5f).build();

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

    private void showtoast(String msg){
    	Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
