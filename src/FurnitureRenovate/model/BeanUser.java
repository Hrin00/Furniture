package FurnitureRenovate.model;

public class BeanUser {
    public static BeanUser currentLoginUser=null;

    private String userId;
    private String userPwd;
    private String userName;
    private int userRank;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserRank() {
        return userRank;
    }

    public void setUserRank(int userRank) {
        this.userRank = userRank;
    }

/*    public static final String[] tableTitles={"用户编号","用户密码","用户名字","用户等级"};
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
