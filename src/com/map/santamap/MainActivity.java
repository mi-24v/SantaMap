package com.map.santamap;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends Activity {
	//GoogleApiClientくん
	private GoogleApiClient googleclient;
	//GoogleMapオブジェクトの宣言
	private GoogleMap googlemap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createGoogleclient();
        createMap();
    }
    
    @Override
    protected void onDestroy(){
    	super.onDestroy();
    	googleclient.disconnect();
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
        //↑のリスナ
        googlemap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener(){
			@Override
			public boolean onMyLocationButtonClick() {
				// TODO 現在地にフォーカス
				return false;
			}
		});

        // 東京駅の位置、ズーム設定 -> 現在地にしたい()
        CameraPosition camerapos = new CameraPosition.Builder()
                .target(new LatLng(35.681382, 139.766084)).zoom(15.5f).build();

        // 地図の中心の変更する
        googlemap.moveCamera(CameraUpdateFactory.newCameraPosition(camerapos));
    }
    
    private void createGoogleclient(){
    	this.googleclient = new GoogleApiClient.Builder(getApplicationContext())
    	.addApi(LocationServices.API)
    	.addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
			@Override
			public void onConnectionSuspended(int arg){
				// GoogleApiClient の接続が中断された場合に呼ばれる
				// arg は GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST
				//     GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED
				switch(arg){
				case GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST:
					showtoast("ネットワークに接続しているか確認してください。");
					break;
				case GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED:
					showtoast("切断されました。");
					break;
				}
			}
			
			@Override
			public void onConnected(Bundle arg0) {
				// GoogleApiClient の接続に成功した場合に呼ばれる
				//位置情報取得
				LocationServices.FusedLocationApi.requestLocationUpdates(googleclient, LocationRequest.create(), new LocationListener(){
					@Override
					public void onLocationChanged(Location location) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		})
    	.addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener(){
			@Override
			public void onConnectionFailed(ConnectionResult result){
				showtoast(result.getErrorMessage());
			}
		})
    	.build();
    	if(!googleclient.isConnected()){
    		googleclient.connect();
    	}
    }

    private void createMap(){
    	// MapFragmentオブジェクトを取得
    	MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);

    	mapFragment.getMapAsync(new OnMapReadyCallback() {

			@Override
			public void onMapReady(GoogleMap googleMap) {
				googlemap = googleMap;
				mapinit();
			}
		});
    }
    
    private void showtoast(String msg){
    	Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
    
}
