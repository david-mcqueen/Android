package co.uk.introapps.NiniMilano.Database;

import android.provider.BaseColumns;

public class Treatment {
	/*
	 * Define the database column names, can be called throughout the program to eliminate any typing errors
	 */
	
	public static final class TreatmentItem implements BaseColumns{
		
		private TreatmentItem() {}
		
		public static final String TABLE_NAME = "Treatment";
		
		public static final String TREATMENT_ID = "_id";
		
		public static final String CATEGORY_ID = "CategoryID";
		
		public static final String NAME = "TreatmentName";
		
		public static final String PRICE = "TreatmentPrice";
		
		public static final String PRICE_TO = "TreatmentPriceTo";
		
		public static final String RANGED_RICE = "TreatmentRangedPrice";
		
		public static final String BY_QUOTATION = "TreatmentQuotation";
		
		public static final String COMPLIMENTARY = "TreatmentComplimntary";
		
		public static final String DATE_CREATED = "TreatmentDateCreated";
		
		public static final String DEFAULT_SORT_ORDER = Treatment.TreatmentItem.CATEGORY_ID;
		
	}

}
