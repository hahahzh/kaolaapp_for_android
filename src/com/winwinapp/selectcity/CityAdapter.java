package com.winwinapp.selectcity;

import java.util.List;

import com.winwinapp.koala.R;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class CityAdapter extends CityListAdapter {

	public CityAdapter(Context _context, int _resource,
			List<CityItemInterface> _items)
	{
		super(_context, _resource, _items);
	}

	public void populateDataForRow(View parentView, CityItemInterface item,
			int position)
	{
		View infoView = parentView.findViewById(R.id.infoRowContainer);
		TextView nicknameView = (TextView) infoView
				.findViewById(R.id.cityName);

		nicknameView.setText(item.getDisplayInfo());
	}
}
