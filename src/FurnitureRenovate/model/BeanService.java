package FurnitureRenovate.model;

public class BeanService {
    private int serviceId;
    private String serviceName;
    private int serviceRank;
    private int serviceUnitPrice;
    private String serviceUnit;
    private int hour;

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getServiceRank() {
        return serviceRank;
    }

    public void setServiceRank(int serviceRank) {
        this.serviceRank = serviceRank;
    }

    public int getServiceUnitPrice() {
        return serviceUnitPrice;
    }

    public void setServiceUnitPrice(int serviceUnitPrice) {
        this.serviceUnitPrice = serviceUnitPrice;
    }

    public String getServiceUnit() {
        return serviceUnit;
    }

    public void setServiceUnit(String serviceUnit) {
        this.serviceUnit = serviceUnit;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

/*    public static final String[] tableTitles={"服务号","服务名字","服务等级","服务单价","计价单位","服务时间"};
    *//*
     * 请自行根据javabean的设计修改本函数代码，col表示界面表格中的列序号，0开始
     *//*
    public String getCell(int col){
        if(col==0) return this.serviceId+"";
        else if(col==1) return this.serviceName;
        else if(col==3) return this.serviceRank+"";
        else if(col==4) return this.serviceUnitPrice+"";
        else if(col==5) return this.serviceUnit;
        else return "";
    }*/
}
