//package cn.edu.qtc.map;
//
//import android.app.Activity;
//import android.content.Context;
//
//import com.baidu.mapapi.BMapManager;
//import com.baidu.mapapi.map.MapView;
//import com.baidu.mapapi.map.RouteOverlay;
//import com.baidu.mapapi.search.MKDrivingRouteResult;
//import com.baidu.mapapi.search.MKPlanNode;
//import com.baidu.mapapi.search.MKSearch;
//
//public class RoutePlan {
//	private MapView mapView;
//	private MKSearch search;
//	private BMapManager manager;
//	private MKSearchListenerAdapter listenerAdapter;
//	private Context context;
//
//	public RoutePlan(Context context,BMapManager manager,MapView mapView) {
//		this.context=context;
//		this.manager=manager;
//		this.mapView=mapView;
//
//		init();
//	}
//
//	public void drivePlan(String startCity, MKPlanNode startPoint,
//			String endCity, MKPlanNode endPoint) {
//		search.drivingSearch(startCity, startPoint, endCity, endPoint);
//	}
//
//	private void init() {
//		search = new MKSearch();
//		listenerAdapter = new MKSearchListenerAdapter() {
//
//			@Override
//			public void onGetDrivingRouteResult(MKDrivingRouteResult result,
//					int error) {
//				
//				RouteOverlay overlay=new RouteOverlay((Activity) context, mapView);
//
//				super.onGetDrivingRouteResult(result, error);
//			}
//
//		};
//		search.init(manager, listenerAdapter);
//
//	}
//
//}
