package co.uk.introapps.NiniMilano.Database;

import android.provider.BaseColumns;

public class CategoryParent {
	/*
	 * Define the database column names, can be called throughout the program to eliminate any typing errors
	 */

	public static final class CategoryParentItem implements BaseColumns{
		
		private CategoryParentItem() {}
		
		public static final String TABLE_NAME = "CategoryParent";
		
		public static final String CATEGORY_ID = "_id";
		
		public static final String NAME = "CategoryName";
		
		public static final String DATE_CREATED = "CategoryDateCreated";
		
		public static final String DEFAULT_SORT_ORDER = CategoryParent.CategoryParentItem.CATEGORY_ID;
	
	}

}
