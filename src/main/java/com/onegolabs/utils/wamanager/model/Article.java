package com.onegolabs.utils.wamanager.model;

/**
 * @author dmzhg
 */
public class Article {

    private int materialNumber;
    private String materialName;
    private String materialDescription;
    private double materialPrice;
    private boolean weighed;
    private int pluCode;
    private int expiryDaysCount;
    private String expiryDate;
    private int labelIndex;
    private int uniqueID;

    /**
     * @return the materialNumber
     */
    public int getMaterialNumber() {
        return materialNumber;
    }

    /**
     * @param materialNumber the materialNumber to set
     */
    public void setMaterialNumber(int materialNumber) {
        this.materialNumber = materialNumber;
    }

    /**
     * @return the materialName
     */
    public String getMaterialName() {
        return materialName;
    }

    /**
     * @param materialName the materialName to set
     */
    public void setMaterialName(final String materialName) {
        this.materialName = materialName;
    }

    /**
     * @return the materialDescription
     */
    public String getMaterialDescription() {
        return materialDescription;
    }

    /**
     * @param materialDescription the materialDescription to set
     */
    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    /**
     * @return the materialPrice
     */
    public double getMaterialPrice() {
        return materialPrice;
    }

    /**
     * @param price the materialPrice to set
     */
    public void setMaterialPrice(double price) {
        this.materialPrice = price;
    }

    /**
     * @return the weighed
     */
    public boolean isWeighed() {
        return weighed;
    }

    /**
     * @param weighted the weighed to set
     */
    public void setWeighed(String weighted) {
        this.weighed =
                weighted != null
                        && !weighted.isEmpty()
                        && weighted.equalsIgnoreCase("Y");
    }

    /**
     * @return the pluCode
     */
    public int getPluCode() {
        return pluCode;
    }

    /**
     * @param pluCode the pluCode to set
     */
    public void setPluCode(int pluCode) {
        this.pluCode = pluCode;
    }

    /**
     * @return the expiryDaysCount
     */
    public int getExpiryDaysCount() {
        return expiryDaysCount;
    }

    /**
     * @param expiryDaysCount the expiryDaysCount to set
     */
    public void setExpiryDaysCount(int expiryDaysCount) {
        this.expiryDaysCount = expiryDaysCount;
    }

    /**
     * @return the expiryDate
     */
    public String getExpiryDate() {
        return expiryDate;
    }

    /**
     * @param expiryDate the expiryDate to set
     */
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * @return the labelIndex
     */
    public int getLabelIndex() {
        return labelIndex;
    }

    /**
     * @param labelIndex the labelIndex to set
     */
    public void setLabelIndex(int labelIndex) {
        this.labelIndex = labelIndex;
    }

    /**
     * @return the uniqueID
     */
    public int getUniqueID() {
        return uniqueID;
    }

    /**
     * @param uniqueID the uniqueID to set
     */
    public void setUniqueID(int uniqueID) {
        this.uniqueID = uniqueID;
    }
}
