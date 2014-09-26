package co.uk.introapps.NinoMilano.Model;

import java.io.Serializable;

public class PricesItem implements Serializable {

	private long id;
	private int imgId;
	private String name;
	private String descr;
	private String price;
	private String imageURL;



	public PricesItem(long id, int imgId, String name, String descr, String price, String imageURL) {
		this.id = id;
		this.imgId = imgId;
		this.name = name;
		this.descr = descr;
		this.price = price;
		this.imageURL = imageURL;
	}


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getImgId() {
		return imgId;
	}
	public void setImgId(int imgId) {
		this.imgId = imgId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getPrice(){
		return price;
	}
	public void setPrice(String price){
		this.price = price;
	}
	public String getImageURL(){
		return this.imageURL;
	}
	public void setImageURL(String imageURL){
		this.imageURL = imageURL;
	}

}