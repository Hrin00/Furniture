package FurnitureRenovate.model;

public class BeanBrand {
    private int brandId;
    private String brandName;
    private String brandDescribe;

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandDescribe() {
        return brandDescribe;
    }

    public void setBrandDescribe(String brandDescribe) {
        this.brandDescribe = brandDescribe;
    }


/*    public static final String[] tableTitles={"品牌号","品牌名","品牌描述"};
    *//*
     * 请自行根据javabean的设计修改本函数代码，col表示界面表格中的列序号，0开始
     *//*
    public String getCell(int col){
        if(col==0) return this.brandId+"";
        else if(col==1) return this.brandName;
        else if(col==3) return this.brandDescribe;
        else return "";
    }*/
}
