package pojos;

/**
 * Created by prasi on 21/4/17.
 */
public class OrderPojo
{
    private int sourceId;
    private int destinationId;
    private int truckTypeId;
    private int loadingPointId;
    private int unloadingPointId;
    private String requiredBy;
    private int transporterId;
    private String driver;
    private String truckNo;
    private int price;
    private int loadingCharge;
    private int unloadingCharge;

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    public int getTruckTypeId() {
        return truckTypeId;
    }

    public void setTruckTypeId(int truckTypeId) {
        this.truckTypeId = truckTypeId;
    }

    public int getLoadingPointId() {
        return loadingPointId;
    }

    public void setLoadingPointId(int loadingPointId) {
        this.loadingPointId = loadingPointId;
    }

    public int getUnloadingPointId() {
        return unloadingPointId;
    }

    public void setUnloadingPointId(int unloadingPointId) {
        this.unloadingPointId = unloadingPointId;
    }

    public String getRequiredBy() {
        return requiredBy;
    }

    public void setRequiredBy(String requiredBy) {
        this.requiredBy = requiredBy;
    }

    public int getTransporterId() {
        return transporterId;
    }

    public void setTransporterId(int transporterId) {
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getLoadingCharge() {
        return loadingCharge;
    }

    public void setLoadingCharge(int loadingCharge) {
        this.loadingCharge = loadingCharge;
    }

    public int getUnloadingCharge() {
        return unloadingCharge;
    }

    public void setUnloadingCharge(int unloadingCharge) {
        this.unloadingCharge = unloadingCharge;
    }

    @Override
    public String toString() {
        return "{" +
                "sourceId:" + sourceId +
                ", destinationId:" + destinationId +
                ", truckTypeId:" + truckTypeId +
                ", loadingPointId:" + loadingPointId +
                ", unloadingPointId:" + unloadingPointId +
                ", requiredBy:'" + requiredBy + '\'' +
                ", transporterId:" + transporterId +
                ", driver:'" + driver + '\'' +
                ", truckNo:'" + truckNo + '\'' +
                ", price:" + price +
                ", loadingCharge:" + loadingCharge +
                ", unloadingCharge:" + unloadingCharge +
                '}';
    }
}
