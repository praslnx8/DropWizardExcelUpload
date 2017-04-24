package pojos;

/**
 * Created by prasi on 21/4/17.
 */
public class OrderPojo
{
    private Integer itemId;
    private Integer truckTypeId;
    private Integer loadingPointId;
    private Integer unloadingPointId;
    private String requiredBy;
    private Integer transporterId;
    private String driver;
    private String truckNo;
    private Integer price;
    private Integer loadingCharge;
    private Integer unloadingCharge;
    private Integer statusTypeId;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getTruckTypeId() {
        return truckTypeId;
    }

    public void setTruckTypeId(Integer truckTypeId) {
        this.truckTypeId = truckTypeId;
    }

    public Integer getLoadingPointId() {
        return loadingPointId;
    }

    public void setLoadingPointId(Integer loadingPointId) {
        this.loadingPointId = loadingPointId;
    }

    public Integer getUnloadingPointId() {
        return unloadingPointId;
    }

    public void setUnloadingPointId(Integer unloadingPointId) {
        this.unloadingPointId = unloadingPointId;
    }

    public String getRequiredBy() {
        return requiredBy;
    }

    public void setRequiredBy(String requiredBy) {
        this.requiredBy = requiredBy;
    }

    public Integer getTransporterId() {
        return transporterId;
    }

    public void setTransporterId(Integer transporterId) {
        this.transporterId = transporterId;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getTruckNo() {
        return truckNo;
    }

    public void setTruckNo(String truckNo) {
        this.truckNo = truckNo;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStatusTypeId() {
        return statusTypeId;
    }

    public void setStatusTypeId(Integer statusTypeId) {
        this.statusTypeId = statusTypeId;
    }

    public Integer getLoadingCharge() {
        return loadingCharge;
    }

    public void setLoadingCharge(Integer loadingCharge) {
        this.loadingCharge = loadingCharge;
    }

    public Integer getUnloadingCharge() {
        return unloadingCharge;
    }

    public void setUnloadingCharge(Integer unloadingCharge) {
        this.unloadingCharge = unloadingCharge;
    }
}
