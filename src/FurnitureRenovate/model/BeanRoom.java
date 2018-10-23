package FurnitureRenovate.model;

public class BeanRoom {
        private int roomId;
        private int houseId;
        private String roomName;
        private int area;

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    /*    public static final String[] tableTitles={"房间名","房间类别","占地面积"};
         *//*
         * 请自行根据javabean的设计修改本函数代码，col表示界面表格中的列序号，0开始
         *//*
    public String getCell(int col){
        if(col==0) return this.roomName;
        else if(col==1) return this.roomSort;
        else if(col==3) return this.area+"";
        else return "";
    }*/

}
