package mems.jiaochuan.com.mems_2000.ui.fragment;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import java.util.ArrayList;

import mems.jiaochuan.com.mems_2000.R;
import mems.jiaochuan.com.mems_2000.base.BasePager;
import mems.jiaochuan.com.mems_2000.base.impl.HomePager;
import mems.jiaochuan.com.mems_2000.base.impl.PersonalPager;
import mems.jiaochuan.com.mems_2000.base.impl.WarningPager;
import mems.jiaochuan.com.mems_2000.ui.activity.MainActivity;
import mems.jiaochuan.com.mems_2000.ui.view.NoScrollViewPager;
import mems.jiaochuan.com.mems_2000.utils.PrefUtils;

public class ContentFragment extends BaseFragment {

	private NoScrollViewPager mViewPager;
	
	private ArrayList<BasePager> mPagers;//3个标签页的集合
	
	private RadioGroup rgGroup;
	private RadioButton mRbPersonal;

	@Override
	public void initData() {
		mPagers = new ArrayList<BasePager>();
		//添加3个标签页
		mPagers.add(new HomePager(mActivity));
		mPagers.add(new WarningPager(mActivity));
		mPagers.add(new PersonalPager(mActivity));

		mViewPager.setAdapter(new ContentAdapter());
		boolean login = PrefUtils.getBoolean(mActivity, "login", false);
		//手动加载第一页数据
		if (login){
			mRbPersonal.setChecked(true);
			mPagers.get(MainActivity.page).initData();
			mViewPager.setCurrentItem(MainActivity.page,false);
		}else {
			mPagers.get(0).initData();
		}


		//底栏标签切换监听
		rgGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_home:
					//首页
					mViewPager.setCurrentItem(0, false);//参数2：表示是否具有滑动动画
					break;
				case R.id.rb_warning:
					//告警
					mViewPager.setCurrentItem(1, false);
					break;
				case R.id.rb_personal:
					//个人中心
					mViewPager.setCurrentItem(2, false);
					break;
				}
			}
		});
		
	}

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.fragment_content, null);
		mViewPager = (NoScrollViewPager)view.findViewById(R.id.vp_content);
		rgGroup = (RadioGroup) view.findViewById(R.id.rg_group);
		mRbPersonal = (RadioButton) view.findViewById(R.id.rb_personal);
		return view;
	}
	
	class ContentAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return mPagers.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		
		@SuppressWarnings("deprecation")
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			BasePager pager = mPagers.get(position);
			View view = pager.mRootView;//获取当前页面对象的布局
			
			//pager.initData();//初始化数据,ViewPager会默认加载下一个页面，为了节省流量和性能，不要在此处调用初始化方法
			
			container.addView(view);
			
			return view;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
		}
	}

}
