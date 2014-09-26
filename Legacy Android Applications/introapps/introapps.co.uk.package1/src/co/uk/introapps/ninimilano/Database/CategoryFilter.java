package co.uk.introapps.NiniMilano.Database;

import android.provider.BaseColumns;

public class CategoryFilter {
	/*
	 * Define the database column names, can be called throughout the program to eliminate any typing errors
	 */

	public static final class CategoryFilterItem implements BaseColumns{
		
		private CategoryFilterItem() {}
		
		public static final String TABLE_NAME = "CategoryFilter";
		
		public static final String FILTER_ID = "_id";
		
		public static final String PARENT_ID = "ParentID";
		
		public static final String CHILD_ID = "ChildID";
		
		public static final String TREATMENT_ID = "TreatmentID";
	
	}

}
