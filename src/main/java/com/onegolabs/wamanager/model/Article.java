package com.onegolabs.wamanager.model;

import javafx.beans.property.*;

/**
 * @author dmzhg
 */
public class Article {

	private IntegerProperty materialNumber;
	private StringProperty materialName;
	private StringProperty materialDescription;
	private DoubleProperty materialPrice;
	private BooleanProperty weighed;
	private IntegerProperty pluCode;
	private IntegerProperty expiryDaysCount;
	private StringProperty expiryDate;
	private IntegerProperty labelIndex;

	public Article(
			int materialNumber,
			String materialName,
			String materialDescription,
			boolean weighed) {
		this.materialNumber = new SimpleIntegerProperty(materialNumber);
		this.materialName = new SimpleStringProperty(materialName);
		this.materialDescription = new SimpleStringProperty(materialDescription);
		this.weighed = new SimpleBooleanProperty(weighed);
	}

	private IntegerProperty uniqueID;

	public int getMaterialNumber() {
		return materialNumber.get();
	}

	public void setMaterialNumber(int materialNumber) {
		this.materialNumber.set(materialNumber);
	}

	public IntegerProperty materialNumberProperty() {
		return materialNumber;
	}

	public String getMaterialName() {
		return materialName.get();
	}

	public void setMaterialName(String materialName) {
		this.materialName.set(materialName);
	}

	public StringProperty materialNameProperty() {
		return materialName;
	}

	public String getMaterialDescription() {
		return materialDescription.get();
	}

	public void setMaterialDescription(String materialDescription) {
		this.materialDescription.set(materialDescription);
	}

	public StringProperty materialDescriptionProperty() {
		return materialDescription;
	}

	public double getMaterialPrice() {
		return materialPrice.get();
	}

	public void setMaterialPrice(double materialPrice) {
		this.materialPrice.set(materialPrice);
	}

	public DoubleProperty materialPriceProperty() {
		return materialPrice;
	}

	public boolean getWeighed() {
		return weighed.get();
	}

	public void setWeighed(boolean weighed) {
		this.weighed.set(weighed);
	}

	public BooleanProperty weighedProperty() {
		return weighed;
	}

	public int getPluCode() {
		return pluCode.get();
	}

	public void setPluCode(int pluCode) {
		this.pluCode.set(pluCode);
	}

	public IntegerProperty pluCodeProperty() {
		return pluCode;
	}

	public int getExpiryDaysCount() {
		return expiryDaysCount.get();
	}

	public void setExpiryDaysCount(int expiryDaysCount) {
		this.expiryDaysCount.set(expiryDaysCount);
	}

	public IntegerProperty expiryDaysCountProperty() {
		return expiryDaysCount;
	}

	public String getExpiryDate() {
		return expiryDate.get();
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate.set(expiryDate);
	}

	public StringProperty expiryDateProperty() {
		return expiryDate;
	}

	public int getLabelIndex() {
		return labelIndex.get();
	}

	public void setLabelIndex(int labelIndex) {
		this.labelIndex.set(labelIndex);
	}

	public IntegerProperty labelIndexProperty() {
		return labelIndex;
	}

	public int getUniqueID() {
		return uniqueID.get();
	}

	public void setUniqueID(int uniqueID) {
		this.uniqueID.set(uniqueID);
	}

	public IntegerProperty uniqueIDProperty() {
		return uniqueID;
	}
}
