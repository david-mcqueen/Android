package co.uk.introapps.NiniMilano.Database;

import android.provider.BaseColumns;

public class CategoryChild {
	/*
	 * Define the database column names, can be called throughout the program to eliminate any typing errors
	 */

	public static final class CategoryChildItem implements BaseColumns{
		
		private CategoryChildItem() {}
		
		public static final String TABLE_NAME = "CategoryChild";
		
		public static final String CATEGORY_ID = "_id";
		
		public static final String PARENT_ID = "ParentID";
		
		public static final String NAME = "CategoryName";
		
		public static final String DATE_CREATED = "CategoryDateCreated";
		
		public static final String IMAGE_URL = "CategoryImageURL";
		
		//Default order will be parent ID, as it will be displayed under the parent.
		public static final String DEFAULT_SORT_ORDER = CategoryChild.CategoryChildItem.PARENT_ID;
	
	}

}
