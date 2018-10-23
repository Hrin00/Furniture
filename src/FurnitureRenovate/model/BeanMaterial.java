package FurnitureRenovate.model;

public class BeanMaterial {
    private int materialId;
    private int brandId;
    private int sortId;
    private String materialName;
    private String specification;
    private String model;
    private String color;
    private int materialUnitPrice;
    private String materialUnit;

    private String sortName;
    private String brandName;

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getMaterialUnitPrice() {
        return materialUnitPrice;
    }

    public void setMaterialUnitPrice(int materialUnitPrice) {
        this.materialUnitPrice = materialUnitPrice;
    }

    public String getMaterialUnit() {
        return materialUnit;
    }

    public void setMaterialUnit(String materialUnit) {
        this.materialUnit = materialUnit;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /*
    public static final String[] tableTitles={"材料编号","材料名","品牌","类别","规格","型号","花色","材料单价","计价单位"};
    *//*
     * 请自行根据javabean的设计修改本函数代码，col表示界面表格中的列序号，0开始
     *//*
    public String getCell(int col){
        if(col==0) return this.materialId+"";
        else if(col==1) return this.;
        else if(col==3) return this.brandDescribe;
        else return "";
    }*/
}
