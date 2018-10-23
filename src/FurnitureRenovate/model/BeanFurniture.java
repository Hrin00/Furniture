package FurnitureRenovate.model;

public class BeanFurniture {
    private int furnitureId;
    private int materialServiceId;
    private int serviceQuantity;
    private int roomId;
    //材料名 材料总数量 材料单位 服务名 服务总数量 服务单位 服务总时间  总预算
    private String materialName;
    private int materialSumQuantity;
    private String materialUnit;
    private String serviceName;
    private int serviceSumQuantity;
    private String serviceUnit;
    private int needHour;
    private int SumBudget;

    public int getFurnitureId() {
        return furnitureId;
    }

    public void setFurnitureId(int furnitureId) {
        this.furnitureId = furnitureId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getMaterialServiceId() {
        return materialServiceId;
    }

    public void setMaterialServiceId(int materialServiceId) {
        this.materialServiceId = materialServiceId;
    }

    public int getServiceQuantity() {
        return serviceQuantity;
    }

    public void setServiceQuantity(int serviceQuantity) {
        this.serviceQuantity = serviceQuantity;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public int getMaterialSumQuantity() {
        return materialSumQuantity;
    }

    public void setMaterialSumQuantity(int materialSumQuantity) {
        this.materialSumQuantity = materialSumQuantity;
    }

    public String getMaterialUnit() {
        return materialUnit;
    }

    public void setMaterialUnit(String materialUnit) {
        this.materialUnit = materialUnit;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getServiceSumQuantity() {
        return serviceSumQuantity;
    }

    public void setServiceSumQuantity(int serviceSumQuantity) {
        this.serviceSumQuantity = serviceSumQuantity;
    }

    public String getServiceUnit() {
        return serviceUnit;
    }

    public void setServiceUnit(String serviceUnit) {
        this.serviceUnit = serviceUnit;
    }

    public int getNeedHour() {
        return needHour;
    }

    public void setNeedHour(int needHour) {
        this.needHour = needHour;
    }

    public int getSumBudget() {
        return SumBudget;
    }

    public void setSumBudget(int sumBudget) {
        SumBudget = sumBudget;
    }

    /*    public static final String[] tableTitles={"装修编号","材料名","材料数量","材料预算","服务名","服务数量","服务预算","材料单价","总预算"};
    *//*
     * 请自行根据javabean的设计修改本函数代码，col表示界面表格中的列序号，0开始
     *//*
    *//*public String getCell(int col){
        if(col==0) return this.materialId+"";
        else if(col==1) return this.;
        else if(col==3) return this.brandDescribe;
        else return "";
    }*/
}
