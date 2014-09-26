package co.uk.introapps.NinoMilano.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class PricesCategory implements Serializable {

	private long id;
	private String name;
	private String descr;

	private List<PricesItem> itemList = new ArrayList<PricesItem>();

	public PricesCategory(long id, String name, String descr) {
		this.id = id;
		this.name = name;
		this.descr = descr;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public List<PricesItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<PricesItem> itemList) {
		this.itemList = itemList;
	}



}