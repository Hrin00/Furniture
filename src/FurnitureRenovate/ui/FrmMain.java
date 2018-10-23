package FurnitureRenovate.ui;

import FurnitureRenovate.control.UserManager;
import FurnitureRenovate.model.BeanFurniture;
import FurnitureRenovate.model.BeanHouse;
import FurnitureRenovate.model.BeanRoom;
import FurnitureRenovate.model.BeanUser;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import FurnitureRenovate.util.*;
import FurnitureRenovate.ui.*;
import FurnitureRenovate.control.*;


public class FrmMain extends JFrame implements ActionListener{
    private static final long serialVersionUID = 1L;
        private JMenuBar menubar=new JMenuBar();
        private JMenu menu_furniture=new JMenu("装修管理"); //管理员新增装修方案
        private JMenu menu_system=new JMenu("系统管理");
        private JMenu menu_more=new JMenu("更多");         //修改密码
        private JButton displayinfo = new JButton("查询相关材料服务");

        //装修管理
        private JMenuItem  menuItem_AddFurniture=new JMenuItem("新增装修");//FrmFurniture(材料对应关系表先显示)
        private JMenuItem  menuItem_DeleteFurniture=new JMenuItem("删除装修");
        private JMenuItem  menuItem_AddHouse=new JMenuItem("新增房屋"); //Frmdialog
        private JMenuItem  menuItem_DeleteHouse=new JMenuItem("删除房屋");//Frm_Deldialog
        //系统管理
        private JMenuItem  menuItem_Brand=new JMenuItem("品牌管理"); //FrmBrand
        private JMenuItem  menuItem_Sort=new JMenuItem("材料类别管理");//FrmSort
        private JMenuItem  menuItem_Material=new JMenuItem("材料管理");//FrmMaterial
        private JMenuItem  menuItem_Service=new JMenuItem("服务管理");//FrmService
        private JMenuItem  menuItem_MaterialService=new JMenuItem("装修方案管理");//FrmService
        private JMenuItem  menuItem_User=new JMenuItem("用户管理");//FrmUser
        //更多
        private JMenuItem menuItem_ChangePwd=new JMenuItem("修改密码");
        private JMenuItem menuItem_ChangeUser = new JMenuItem("切换用户");
        private JMenuItem menuItem_Exit = new JMenuItem("关闭系统");


        private JLabel labelRoomBudget = new JLabel("房间预算");
        private JLabel labelHouseBudget = new JLabel("房屋预算");
        private JLabel labelhousebudget = new JLabel("    ");
        private JLabel labelroombudget = new JLabel("    ");

        private FrmLogin dlgLogin=null;
        private JPanel statusBar = new JPanel();
        private JPanel budgetBar = new JPanel();

    private Object tblHouseTitle[] = {"", "房屋名"};
    private Object tblHouseData[][];
    DefaultTableModel tablhousemod = new DefaultTableModel();
    private JTable dataHouseTable = new JTable(tablhousemod);

    private Object tblRoomTitle[] = {"房间名", "面积(m^2)"};
    private Object tblRoomData[][];
    DefaultTableModel tablroommod = new DefaultTableModel();
    private JTable dataRoomTable = new JTable(tablroommod);

    private Object tblFurnitureTitle[] = {"材料名", "材料总数量","材料单位","服务名","服务总数量","服务单位","服务总时间(h)","总预算(元)"};
    private Object tblFurnitureData[][];
    DefaultTableModel tablfurnituremod = new DefaultTableModel();
    private JTable dataFurnitureTable = new JTable(tablfurnituremod);

    private BeanHouse curHouse = null;
    private BeanRoom curRoom = null;
    List<BeanHouse> houses = null;
    List<BeanRoom> rooms = null;
    List<BeanFurniture> furnitures = null;

    private int reloadRoomBudget(BeanRoom room) throws BaseException{
        int budget = (new RoomManager()).CountBudget(room);
        return budget;
    }
    private int reloadRoomBudget() throws BaseException{
        return 0;
    }

    public int reloadHouseBudget(BeanHouse house)throws BaseException{
        int budget = (new HouseManager()).CountBudget(house);
        return budget;
    }

    private void reloadHouseTable(){
        try {
            houses = (new HouseManager()).LoadAll(BeanUser.currentLoginUser.getUserId());
            tblHouseData = new Object[houses.size()][2];
            for (int i = 0; i < houses.size(); i++) {
                tblHouseData[i][0] = Integer.toString(i+1);
                tblHouseData[i][1] = houses.get(i).getHouseName();
            }
            tablhousemod.setDataVector(tblHouseData, tblHouseTitle);
            this.dataHouseTable.validate();
            this.dataHouseTable.repaint();
        }catch (BaseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }
    private void reloadRoomTable(int houseIdx) throws BaseException{
        if(houseIdx<0) return;
        curHouse = houses.get(houseIdx);

        this.labelhousebudget.setText(reloadHouseBudget(curHouse)+"元");
        this.labelroombudget.setText(reloadRoomBudget()+"元");

        try{
            rooms = (new RoomManager()).LoadALL(curHouse.getHouseId());
            tblRoomData = new Object[rooms.size()][2];
            for(int i = 0; i < rooms.size(); i++){
                tblRoomData[i][0] = rooms.get(i).getRoomName();
                tblRoomData[i][1] = rooms.get(i).getArea()+"";
            }
            tablroommod.setDataVector(tblRoomData,tblRoomTitle);
            this.dataRoomTable.validate();
            this.dataHouseTable.repaint();
        }catch (BaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void reloadRoomTable(){
            tblRoomData = new Object[0][2];
            tablroommod.setDataVector(tblRoomData,tblRoomTitle);
            this.dataRoomTable.validate();
            this.dataHouseTable.repaint();
    }
    private void reloadFurnitureTable (int roomIdx) throws BaseException{
        if(roomIdx<0) return;
        curRoom = rooms.get(roomIdx);
        this.labelhousebudget.setText(reloadHouseBudget(curHouse)+"元");
        this.labelroombudget.setText(reloadRoomBudget(curRoom)+"元");

        try{
            furnitures = (new FurnitureManager()).LoadAll(curRoom.getRoomId());
            tblFurnitureData = new Object[furnitures.size()][8];
            //材料名 材料总数量 材料单位 服务名 服务总数量 服务单位 服务总时间  总预算
            for(int i = 0; i < furnitures.size(); i++){
                tblFurnitureData[i][0] = furnitures.get(i).getMaterialName();
                tblFurnitureData[i][1] = furnitures.get(i).getMaterialSumQuantity();
                tblFurnitureData[i][2] = furnitures.get(i).getMaterialUnit();
                tblFurnitureData[i][3] = furnitures.get(i).getServiceName();
                tblFurnitureData[i][4] = furnitures.get(i).getServiceSumQuantity();
                tblFurnitureData[i][5] = furnitures.get(i).getServiceUnit();
                tblFurnitureData[i][6] = furnitures.get(i).getNeedHour();
                tblFurnitureData[i][7] = furnitures.get(i).getSumBudget();
            }
            tablfurnituremod.setDataVector(tblFurnitureData,tblFurnitureTitle);
            this.dataFurnitureTable.validate();
            this.dataFurnitureTable.repaint();
        }catch (BaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void reloadFurnitureTable(){
            tblFurnitureData = new Object[0][8];
            tablfurnituremod.setDataVector(tblFurnitureData,tblFurnitureTitle);
            this.dataFurnitureTable.validate();
            this.dataFurnitureTable.repaint();
    }
    public FrmMain(){

        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("家装预算系统");
        dlgLogin=new FrmLogin(this,"登陆",true);
        dlgLogin.setVisible(true);
        //菜单
        if(BeanUser.currentLoginUser.getUserRank() >= 2){
            menu_system.add(menuItem_Brand);menuItem_Brand.addActionListener(this);
            menu_system.add(menuItem_Sort);menuItem_Sort.addActionListener(this);
            menu_system.add(menuItem_Material);menuItem_Material.addActionListener(this);
            menu_system.add(menuItem_Service);menuItem_Service.addActionListener(this);
            menu_system.add(menuItem_MaterialService);menuItem_MaterialService.addActionListener(this);
            menubar.add(menu_system);
        }
        if(BeanUser.currentLoginUser.getUserRank() == 3){
            menu_system.add(menuItem_User);menuItem_User.addActionListener(this);
        }
        menu_furniture.add(menuItem_AddHouse);menuItem_AddHouse.addActionListener(this);
        menu_furniture.add(menuItem_DeleteHouse);menuItem_DeleteHouse.addActionListener(this);
        menu_furniture.add(menuItem_AddFurniture);menuItem_AddFurniture.addActionListener(this);
        menu_furniture.add(menuItem_DeleteFurniture);menuItem_DeleteFurniture.addActionListener(this);
        menubar.add(menu_furniture);

        menu_more.add(menuItem_ChangePwd);menuItem_ChangePwd.addActionListener(this);
        menu_more.add(menuItem_ChangeUser);menuItem_ChangeUser.addActionListener(this);
        menu_more.add(menuItem_Exit);menuItem_Exit.addActionListener(this);
        menubar.add(menu_more);
        this.setJMenuBar(menubar);

        budgetBar.add(labelHouseBudget);
        budgetBar.add(labelhousebudget);
        budgetBar.add(new JLabel("                  "));
        budgetBar.add(labelRoomBudget);
        budgetBar.add(labelroombudget);


        this.reloadHouseTable();
        this.getContentPane().add(new JScrollPane(this.dataHouseTable), BorderLayout.WEST);
        //选择当前的房屋
        this.dataHouseTable.addMouseListener(new MouseAdapter (){
            @Override
            public void mouseClicked(MouseEvent e) {
                int i=FrmMain.this.dataHouseTable.getSelectedRow();
                if(i<0) {
                    return;
                }
                FrmMain.this.reloadFurnitureTable();
                try {
                    FrmMain.this.reloadRoomTable(i);
                } catch (BaseException e1) {
                    e1.printStackTrace();
                }
            }

        });
        this.getContentPane().add(new JScrollPane(this.dataRoomTable),BorderLayout.CENTER);
        this.getContentPane().add(budgetBar, BorderLayout.NORTH);
        //选择当前的房间
        this.dataRoomTable.addMouseListener(new MouseAdapter (){
            @Override
            public void mouseClicked(MouseEvent e) {
                int i=FrmMain.this.dataRoomTable.getSelectedRow();
                if(i<0) {
                    return;
                }
                try {
                    FrmMain.this.reloadFurnitureTable(i);
                } catch (BaseException e1) {
                    e1.printStackTrace();
                }

            }

        });
        JScrollPane scrollPane = new JScrollPane(this.dataFurnitureTable);
        scrollPane.setPreferredSize(new Dimension(650, 150));
        this.getContentPane().add(scrollPane,BorderLayout.EAST);
        //状态栏
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel("您好!" + BeanUser.currentLoginUser.getUserName());
        statusBar.add(label);
        this.getContentPane().add(statusBar, BorderLayout.SOUTH);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setVisible(true);
    }

    private void refresh(){

        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("家装预算系统");
        //菜单
        if(BeanUser.currentLoginUser.getUserRank() >= 2){
            menu_system.add(menuItem_Brand);menuItem_Brand.addActionListener(this);
            menu_system.add(menuItem_Sort);menuItem_Sort.addActionListener(this);
            menu_system.add(menuItem_Material);menuItem_Material.addActionListener(this);
            menu_system.add(menuItem_Service);menuItem_Service.addActionListener(this);
            menu_system.add(menuItem_MaterialService);menuItem_MaterialService.addActionListener(this);
            menubar.add(menu_system);
        }
        if(BeanUser.currentLoginUser.getUserRank() == 3){
            menu_system.add(menuItem_User);menuItem_User.addActionListener(this);
        }
        menu_furniture.add(menuItem_AddHouse);menuItem_AddHouse.addActionListener(this);
        menu_furniture.add(menuItem_DeleteHouse);menuItem_DeleteHouse.addActionListener(this);
        menu_furniture.add(menuItem_AddFurniture);menuItem_AddFurniture.addActionListener(this);
        menu_furniture.add(menuItem_DeleteFurniture);menuItem_DeleteFurniture.addActionListener(this);
        menubar.add(menu_furniture);

        menu_more.add(menuItem_ChangePwd);menuItem_ChangePwd.addActionListener(this);
        menu_more.add(menuItem_ChangeUser);menuItem_ChangeUser.addActionListener(this);
        menubar.add(menu_more);
        this.setJMenuBar(menubar);

        budgetBar.add(labelHouseBudget);
        budgetBar.add(labelhousebudget);
        budgetBar.add(new JLabel("                  "));
        budgetBar.add(labelRoomBudget);
        budgetBar.add(labelroombudget);


        this.reloadHouseTable();
        this.getContentPane().add(new JScrollPane(this.dataHouseTable), BorderLayout.WEST);
        //选择当前的房屋
        this.dataHouseTable.addMouseListener(new MouseAdapter (){
            @Override
            public void mouseClicked(MouseEvent e) {
                int i=FrmMain.this.dataHouseTable.getSelectedRow();
                if(i<0) {
                    return;
                }
                FrmMain.this.reloadFurnitureTable();
                try {
                    FrmMain.this.reloadRoomTable(i);
                } catch (BaseException e1) {
                    e1.printStackTrace();
                }
            }

        });
        this.getContentPane().add(new JScrollPane(this.dataRoomTable),BorderLayout.CENTER);
        this.getContentPane().add(budgetBar, BorderLayout.NORTH);
        //选择当前的房间
        this.dataRoomTable.addMouseListener(new MouseAdapter (){
            @Override
            public void mouseClicked(MouseEvent e) {
                int i=FrmMain.this.dataRoomTable.getSelectedRow();
                if(i<0) {
                    return;
                }
                try {
                    FrmMain.this.reloadFurnitureTable(i);
                } catch (BaseException e1) {
                    e1.printStackTrace();
                }

            }

        });
        JScrollPane scrollPane = new JScrollPane(this.dataFurnitureTable);
        scrollPane.setPreferredSize(new Dimension(650, 150));
        this.getContentPane().add(scrollPane,BorderLayout.EAST);
        //状态栏
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel("您好!" + BeanUser.currentLoginUser.getUserName());
        statusBar.add(label);
        this.getContentPane().add(statusBar, BorderLayout.SOUTH);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.menuItem_AddFurniture){
            int i=FrmMain.this.dataRoomTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null, "请选择一间房间", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }

            FrmFurniture dlg = new FrmFurniture(this,"装修",true);
            dlg.roomId = rooms.get(i).getRoomId();
            dlg.setVisible(true);
            try {
                reloadFurnitureTable(i);
            } catch (BaseException e1) {
                e1.printStackTrace();
            }
            //2个预算

        }
        else if(e.getSource()==this.menuItem_DeleteFurniture){
            int i=FrmMain.this.dataFurnitureTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null, "请选择一项装修方案", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                if (JOptionPane.showConfirmDialog(this, "确定删除该装修吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                    (new FurnitureManager()).DeleteFurniture(this.furnitures.get(i));
                    //reloadfurniture
                    int j = FrmMain.this.dataRoomTable.getSelectedRow();
                    if (j < 0) {
                        return;
                    }
                    FrmMain.this.reloadFurnitureTable(j);
                }
                //刷新房间预算
                //刷新房屋预算
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        else if(e.getSource()==this.menuItem_AddHouse){
            FrmAddHouse dlg=new FrmAddHouse(this,"添加房屋",true);
            dlg.setVisible(true);
            //reloadhouse
            FrmMain.this.reloadHouseTable();
/*            //reload
            int i=FrmMain.this.dataTablePlan.getSelectedRow();
            if(i<0) {
                return;
            }
            FrmMain.this.reloadPlanStepTabel(i);
            reloadPlanTable();*/
        }
        else if(e.getSource()==this.menuItem_DeleteHouse){
            int i=FrmMain.this.dataHouseTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null, "请选择一间房物", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (JOptionPane.showConfirmDialog(this, "删除房屋会删除所有与该房屋相关的信息，确定删除" + houses.get(i).getHouseName()+ "吗？", "警告", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                //FrmDelHouse dlg = new FrmDelHouse(this, "警告", true);
                //dlg.house = houses.get(i);
                //dlg.setVisible(true);
                try {
                    (new HouseManager()).DeleteHouse(houses.get(i));
                    reloadHouseTable();
                    reloadRoomTable();
                    reloadFurnitureTable();
                }catch (BaseException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }
        else if(e.getSource()==this.menuItem_Brand){
            FrmBrand dlg = new FrmBrand(this,"品牌管理",true);
            dlg.setVisible(true);
        }
        else if(e.getSource()==this.menuItem_Sort){
            FrmSort dlg = new FrmSort(this,"类型管理",true);
            dlg.setVisible(true);
        }
        else if(e.getSource()==this.menuItem_Material){
            FrmMaterial dlg = new FrmMaterial(this,"材料管理",true);
            dlg.setVisible(true);
        }
        else if(e.getSource()==this.menuItem_Service){
            FrmService dlg = new FrmService(this,"服务管理",true);
            dlg.setVisible(true);
        }
        else if(e.getSource()==this.menuItem_MaterialService){
            FrmMaterialService dlg = new FrmMaterialService(this,"装修方案管理",true);
            dlg.setVisible(true);
        }
        else if(e.getSource()==this.menuItem_ChangePwd){
            FrmChangePwd dlg = new FrmChangePwd(this,"修改密码",true);
            dlg.setVisible(true);
        }
        else if(e.getSource()==this.menuItem_User){
            FrmUser dlg = new FrmUser(this,"用户管理",true);
            dlg.setVisible(true);
        }
        else if(e.getSource()==this.menuItem_ChangeUser){
            this.setVisible(false);
            FrmMain frm = new FrmMain();
            frm.setVisible(true);
        }
        else if(e.getSource()==this.menuItem_Exit){
            System.exit(0);
        }

    }
}
