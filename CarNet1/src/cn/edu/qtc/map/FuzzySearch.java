//package cn.edu.qtc.map;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.content.Context;
//import android.os.Handler;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.baidu.mapapi.BMapManager;
//import com.baidu.mapapi.search.MKPoiResult;
//import com.baidu.mapapi.search.MKSearch;
//import com.baidu.mapapi.search.MKSearchListener;
//import com.baidu.mapapi.search.MKSuggestionResult;
//
///**
// * 该类为模糊搜索的相关内容
// * 
// * @author linlin
// * 
// */
//public class FuzzySearch {
//
//	private MKSearch search;
//	private MKSearchListener listener;
//	private BMapManager manager;
//	private Context context;
//	private List<String> data;
//	private Handler handler;
//
//	public FuzzySearch(BMapManager manager,Context context,Handler handler) {
//		this.handler=handler;
//		this.manager=manager;
//		this.context = context;
//		init();
//		
//		
//		
//
//	}
//
//	/**
//	 * 模糊查询
//	 */
//	public void SearchKey(String key, String city) {
//
//		search.suggestionSearch(key, city);
//		Log.i("FuzzySearch", "SearchKey");
//		
//		
//		
//	}
//	
//	public List<String> getData(){
//		return data;
//	}
//
//	/**
//	 * 搜索结果监听初始化
//	 */
//	public void init() {
//		search=new MKSearch();
//		data=new ArrayList<String>();
//		listener = new MKSearchListenerAdapter() {
//			
//			
//
//			@Override
//			public void onGetSuggestionResult(MKSuggestionResult result,
//					int error) {
//				Log.d("FuzzySearch", "99999999999999999999999999999999");
//				if (error != 0 || result == null) {
//					Toast.makeText(context, "抱歉，未找到结果", Toast.LENGTH_LONG)
//							.show();
//					return;
//				}
//				int nSize = result.getSuggestionNum();
//				String[] mStrSuggestions = new String[nSize];
//				for (int i = 0; i < nSize; i++) {
//					mStrSuggestions[i] = result.getSuggestion(i).city
//							+ result.getSuggestion(i).key;
//					Log.d("FuzzySearch", mStrSuggestions[i]+"------");
//					data.add(mStrSuggestions[i]);
//				}
//				
//				handler.sendEmptyMessage(0);
//
//				super.onGetSuggestionResult(result, error);
//			}
//
//		};
//		search.init(manager, listener);
//		
//	}
//
//}
