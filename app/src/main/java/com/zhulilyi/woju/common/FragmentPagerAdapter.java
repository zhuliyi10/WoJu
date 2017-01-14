package com.zhulilyi.woju.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * Description: Fragment的PagerAdapter
 * Creator: zhuliyi
 * date: 2017/1/9
 */
public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

	private List<Fragment> fragments;

	public FragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

}
