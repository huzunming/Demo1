//package cn.edu.qtc.map;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.util.Log;
//import android.widget.Toast;
//import cn.edu.qtc.main.R;
//
//import com.baidu.mapapi.BMapManager;
//import com.baidu.mapapi.map.ItemizedOverlay;
//import com.baidu.mapapi.map.MapController;
//import com.baidu.mapapi.map.MapView;
//import com.baidu.mapapi.map.OverlayItem;
//import com.baidu.mapapi.search.MKPoiInfo;
//import com.baidu.mapapi.search.MKPoiResult;
//import com.baidu.mapapi.search.MKSearch;
//import com.baidu.mapapi.search.MKSearchListener;
//import com.baidu.platform.comapi.basestruct.GeoPoint;
//
///**
// * 该类提供搜索附近点
// * 
// * @author linlin
// * 
// */
//public class PoiOverlaySearch {
//
//	private MKSearch search;
//	private MKSearchListener SearchListener;
//	private Context context;
//	private MapView mapView;
//	private BMapManager manager;
//	private ArrayList<MKPoiInfo> list;
//
//	public PoiOverlaySearch(MapView mapView, BMapManager manager,
//			Context context) {
//		this.mapView = mapView;
//		this.manager = manager;
//		this.context = context;
//		init();
//	}
//
//	public void SearchNearBy(GeoPoint point) {
//
//		if (point != null) {
//			search.poiSearchNearBy("加油站", point, 2000);
//		} else {
//			Log.i("PoiOverlaySearch", "nill");
//		}
//
//	}
//
//	public void init() {
//		search = new MKSearch();
//
//		SearchListener = new MKSearchListenerAdapter() {
//
//			@Override
//			public void onGetPoiResult(MKPoiResult result, int type, int error) {
//				ItemizedOverlay overlay = new ItemizedOverlay(context
//						.getResources().getDrawable(
//								R.drawable.icon_route_nearby_gas_station),
//						mapView) {
//					@Override
//					protected boolean onTap(int arg0) {
//						super.onTap(arg0);
//						Log.i("ssssssssssssssssssssssssss", "ssssssssssssss");
//						return true;
//					}
//
//				};
//
//				setdata(overlay, result);
//
//				mapView.getOverlays().add(overlay);
//				mapView.refresh();
//
//				super.onGetPoiResult(result, type, error);
//			}
//
//		};
//
//		search.init(manager, SearchListener);
//	}
//
//	protected void setdata(ItemizedOverlay<OverlayItem> overlay,
//			MKPoiResult result) {
//		if (result.getAllPoi() != null) {
//
//			list = result.getAllPoi();
//			for (int i = 0; i < list.size(); ++i) {
//				OverlayItem item = new OverlayItem(list.get(i).pt, "", "");
//				overlay.addItem(item);
//			}
//		}
//
//	}
//
//}
