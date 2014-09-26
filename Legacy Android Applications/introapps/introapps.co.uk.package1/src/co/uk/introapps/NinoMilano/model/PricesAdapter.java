package co.uk.introapps.NinoMilano.Model;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import co.uk.introapps.NinoMilano.R;
import co.uk.introapps.NinoMilano.Background.DownloadImage;

public class PricesAdapter extends BaseExpandableListAdapter{

	
	private List<PricesCategory> catList;
	private int itemLayoutId;
	private int groupLayoutId;
	private Context ctx;
	private static final String TAG = "Nino-ExpandableAdapter";

	public PricesAdapter(List<PricesCategory> catList, Context ctx) {

		this.itemLayoutId = R.layout.list_row;
		this.groupLayoutId = R.layout.list_header;
		this.catList = catList;
		this.ctx = ctx;
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return catList.get(groupPosition).getItemList().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return catList.get(groupPosition).getItemList().get(childPosition).hashCode();
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		View v = convertView;
		PricesItem det = catList.get(groupPosition).getItemList().get(childPosition);
		String imageURL = "http://www.introapps.co.uk/images/" + det.getImageURL();

		if (v == null) {
			LayoutInflater inflater = (LayoutInflater)ctx.getSystemService
				      (Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.list_row, parent, false);
		}

		TextView itemName = (TextView) v.findViewById(R.id.treatment);
		TextView itemPrice = (TextView) v.findViewById(R.id.price);
		TextView itemDesc = (TextView) v.findViewById(R.id.category);
		ImageView itemImage = (ImageView) v.findViewById(R.id.list_image);
		
		new DownloadImage(itemImage).execute(imageURL);

		
		itemName.setText(det.getName());
		itemPrice.setText(det.getPrice());
		itemDesc.setText(det.getDescr());

		return v;

	}

	@Override
	public int getChildrenCount(int groupPosition) {
		int size = catList.get(groupPosition).getItemList().size();
		System.out.println("Child for group ["+groupPosition+"] is ["+size+"]");
		return size;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return catList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
	  return catList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return catList.get(groupPosition).hashCode();
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		View v = convertView;

		if (v == null) {
			LayoutInflater inflater = (LayoutInflater)ctx.getSystemService
				      (Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.list_header, parent, false);
		}

		TextView groupName = (TextView) v.findViewById(R.id.categoryName);


		PricesCategory cat = catList.get(groupPosition);

		groupName.setText(cat.getName());

		return v;

	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}


}
