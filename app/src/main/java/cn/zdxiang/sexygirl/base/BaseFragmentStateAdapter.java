package cn.zdxiang.sexygirl.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author jm
 * @date 16-12-13.下午2:34
 * @description BaseFragmentStateAdapter
 */

public class BaseFragmentStateAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;

    private List<String> titles;

    private FragmentManager fm;

    public BaseFragmentStateAdapter(List<Fragment> list, FragmentManager fm) {
        super(fm);
        this.fragments = list;
        this.fm = fm;
    }

    public BaseFragmentStateAdapter(List<Fragment> list, List<String> titles, FragmentManager fm) {
        super(fm);
        this.fragments = list;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
//        Fragment fragment = (Fragment) object;
//        fm.beginTransaction().remove(fragment).commit();

    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
