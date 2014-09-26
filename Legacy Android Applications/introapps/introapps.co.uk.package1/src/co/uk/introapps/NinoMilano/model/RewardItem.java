package co.uk.introapps.NinoMilano.Model;

public class RewardItem {

	private String name;
	private String description;
	private String price;
	private boolean isHeader;
	private boolean active;
	private boolean [] days = new boolean[7];

	public RewardItem(String name, String description, String price) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
	}
	
	public RewardItem(String name, String description){
		super();
		this.name = name;
		this.description = description;
	}
	
	public RewardItem(String description, boolean header){
		super();
		this.description = description;
		this.isHeader = header;
	}
	
	public RewardItem(String name, String description, String price, boolean active, boolean[] days){
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.active = active;
		this.days = days;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPrice(){
		return price;
	}
	public void setPrice(String price){
		this.price = price;
	}

	public boolean getHeader() {
		return isHeader;
	}

	public void setHeader(boolean isHeader) {
		this.isHeader = isHeader;
	}
	
	public boolean[] getDays(){
		return days;
	}
	
	public void setDays(boolean[] days){
		this.days = days;
	}
}
