package com.lucianoalbuquerque18gmail.app.simplelist0110.dominio.adapter;

import android.content.Context;

import androidx.viewpager.widget.PagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AdapterAberturaTuto extends PagerAdapter {

  private LayoutInflater inflater;
  private int[]layouts;
  private Context context;


  public AdapterAberturaTuto(int[] layouts, Context context) {
    this.layouts = layouts;
    this.context = context;
  }

  @Override
  public int getCount() {
    return layouts.length;
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view==object;
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View v = inflater.inflate(layouts[position], container, false);
    container.addView(v);
    return v;
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    View v = (View)object;
    container.removeView(v);
  }
}
