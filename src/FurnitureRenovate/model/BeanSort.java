package FurnitureRenovate.model;

public class BeanSort {
    private int sortId;
    private String sortName;
    private String sortDescribe;

    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortDescribe() {
        return sortDescribe;
    }

    public void setSortDescribe(String sortDescribe) {
        this.sortDescribe = sortDescribe;
    }

   /* public static final String[] tableTitles={"房间名","房间类别","占地面积"};
    *//*
     * 请自行根据javabean的设计修改本函数代码，col表示界面表格中的列序号，0开始
     *//*
    public String getCell(int col){
        if(col==0) return this.sortId+"";
        else if(col==1) return this.sortName;
        else if(col==3) return this.sortDescribe;
        else return "";
    }*/
}
