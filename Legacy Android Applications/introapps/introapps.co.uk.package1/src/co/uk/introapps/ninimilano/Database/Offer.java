package co.uk.introapps.NiniMilano.Database;

import android.provider.BaseColumns;

public class Offer {

public static final class OfferItem implements BaseColumns{
		
		private OfferItem() {}
		
		public static final String TABLE_NAME = "Offer";
		
		public static final String OFFER_ID = "_id";
		
		public static final String OFFER_TYPE_ID = "OfferTypeID";
		
		public static final String DATE_CREATED = "DateCreated";
		
		public static final String OFFER_CODE = "OfferCode";
		
		public static final String OFFER_PRICE = "OfferPrice";
		
		public static final String OFFER_DESCRIPTION = "Description";
		
		public static final String OFFER_NOTE = "Note";
		
		public static final String OFFER_ACTIVE = "Active";
		
		public static final String OFFER_MONDAY = "Monday";
		
		public static final String OFFER_TUESDAY = "Tuesday";
		
		public static final String OFFER_WEDNESDAY = "Wednesday";
		
		public static final String OFFER_THURSDAY = "Thursday";
		
		public static final String OFFER_FRIDAY = "Friday";
		
		public static final String OFFER_SATURDAY = "Saturday";
		
		public static final String OFFER_SUNDAY = "Sunday";		
		
		public static final String DEFAULT_SORT_ORDER = Offer.OfferItem.OFFER_ID;
		
	}

}
