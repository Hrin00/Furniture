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


public class FrmDisplayFuniture extends JDialog{


    private JLabel labelRoomBudget = new JLabel("房间预算");
    private JLabel labelHouseBudget = new JLabel("房屋预算");
    private JLabel labelhousebudget = new JLabel("    ");
    private JLabel labelroombudget = new JLabel("    ");

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

    private void reloadHouseTable(BeanUser user){
        try {
            houses = (new HouseManager()).LoadAll(user.getUserId());
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
    public FrmDisplayFuniture(BeanUser user,JDialog f, String s, boolean b){
        super(f,s,b);

        budgetBar.add(labelHouseBudget);
        budgetBar.add(labelhousebudget);
        budgetBar.add(new JLabel("                  "));
        budgetBar.add(labelRoomBudget);
        budgetBar.add(labelroombudget);


        this.reloadHouseTable(user);
        this.getContentPane().add(new JScrollPane(this.dataHouseTable), BorderLayout.WEST);
        //选择当前的房屋
        this.dataHouseTable.addMouseListener(new MouseAdapter (){
            @Override
            public void mouseClicked(MouseEvent e) {
                int i=FrmDisplayFuniture.this.dataHouseTable.getSelectedRow();
                if(i<0) {
                    return;
                }
                FrmDisplayFuniture.this.reloadFurnitureTable();
                try {
                    FrmDisplayFuniture.this.reloadRoomTable(i);
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
                int i=FrmDisplayFuniture.this.dataRoomTable.getSelectedRow();
                if(i<0) {
                    return;
                }
                try {
                    FrmDisplayFuniture.this.reloadFurnitureTable(i);
                } catch (BaseException e1) {
                    e1.printStackTrace();
                }

            }

        });
        JScrollPane scrollPane = new JScrollPane(this.dataFurnitureTable);
        scrollPane.setPreferredSize(new Dimension(650, 150));
        this.getContentPane().add(scrollPane,BorderLayout.EAST);
        this.setSize(2000, 1500);

    }
}
