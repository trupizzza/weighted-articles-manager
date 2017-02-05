package com.onegolabs.wamanager.model;

import javafx.beans.property.*;

/**
 * @author dmzhg
 */
public class Article {

    private IntegerProperty materialNumber;
    private StringProperty name;
    private StringProperty description;
    private DoubleProperty price;
    private BooleanProperty weighed;
    private IntegerProperty plu;
    private IntegerProperty expiryDaysCount;
    private StringProperty expiryDate;
    private IntegerProperty labelId;
    private IntegerProperty id;
    private IntegerProperty discount;
    private BooleanProperty morePrices;
    private IntegerProperty lifeBoxTotal;
    private StringProperty manufacturerExpiryDate;
    private IntegerProperty addNo1;
    private StringProperty expiryDateShop;
    private IntegerProperty expDaysToScale;

    public Article(
            int materialNumber,
            String name,
            String description,
            double price,
            boolean weighed,
            int plu,
            int expiryDaysCount,
            String expiryDate,
            int labelId,
            int id) {
        this.materialNumber = new SimpleIntegerProperty(materialNumber);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.price = new SimpleDoubleProperty(price);
        this.weighed = new SimpleBooleanProperty(weighed);
        this.plu = new SimpleIntegerProperty(plu);
        this.expiryDaysCount = new SimpleIntegerProperty(expiryDaysCount);
        this.expiryDate = new SimpleStringProperty(expiryDate);
        this.labelId = new SimpleIntegerProperty(labelId);
        this.id = new SimpleIntegerProperty(id);
    }

    public Article(
            int materialNumber, String name, String description, boolean weighed) {
        this.materialNumber = new SimpleIntegerProperty(materialNumber);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.weighed = new SimpleBooleanProperty(weighed);
    }

    public Article() {
    }

    public String getExpiryDateShop() {
        return expiryDateShop.get();
    }

    public void setExpiryDateShop(String expiryDateShop) {
        this.expiryDateShop.set(expiryDateShop);
    }

    public StringProperty expiryDateShopProperty() {
        return expiryDateShop;
    }

    public int getExpDaysToScale() {
        return expDaysToScale.get();
    }

    public void setExpDaysToScale(int expDaysToScale) {
        this.expDaysToScale.set(expDaysToScale);
    }

    public IntegerProperty expDaysToScaleProperty() {
        return expDaysToScale;
    }

    public int getAddNo1() {
        return addNo1.get();
    }

    public void setAddNo1(int addNo1) {
        this.addNo1.set(addNo1);
    }

    public IntegerProperty addNo1Property() {
        return addNo1;
    }

    public String getManufacturerExpiryDate() {
        return manufacturerExpiryDate.get();
    }

    public void setManufacturerExpiryDate(String manufacturerExpiryDate) {
        this.manufacturerExpiryDate.set(manufacturerExpiryDate);
    }

    public StringProperty manufacturerExpiryDateProperty() {
        return manufacturerExpiryDate;
    }

    public int getLifeBoxTotal() {
        return lifeBoxTotal.get();
    }

    public void setLifeBoxTotal(int lifeBoxTotal) {
        this.lifeBoxTotal.set(lifeBoxTotal);
    }

    public int getMaterialNumber() {
        return materialNumber.get();
    }

    public void setMaterialNumber(int materialNumber) {
        this.materialNumber.set(materialNumber);
    }

    public IntegerProperty materialNumberProperty() {
        return materialNumber;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public DoubleProperty priceProperty() {
        return price;
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

    public int getPlu() {
        return plu.get();
    }

    public void setPlu(int plu) {
        this.plu.set(plu);
    }

    public IntegerProperty pluProperty() {
        return plu;
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

    public int getLabelId() {
        return labelId.get();
    }

    public void setLabelId(int labelId) {
        this.labelId.set(labelId);
    }

    public IntegerProperty labelIdProperty() {
        return labelId;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public int getDiscount() {
        return discount.get();
    }

    public void setDiscount(int discount) {
        this.discount.set(discount);
    }

    public IntegerProperty discountProperty() {
        return discount;
    }

    public boolean getMorePrices() {
        return morePrices.get();
    }

    public void setMorePrices(boolean morePrices) {
        this.morePrices.set(morePrices);
    }

    public BooleanProperty morePricesProperty() {
        return morePrices;
    }
}
