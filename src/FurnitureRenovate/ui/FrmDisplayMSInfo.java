package FurnitureRenovate.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

import FurnitureRenovate.model.*;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.*;

import FurnitureRenovate.util.*;
import FurnitureRenovate.ui.*;
import FurnitureRenovate.control.*;


public class FrmDisplayMSInfo extends JDialog {
    public BeanMaterialService materialService = null;
    private Object tblMaterialTitle[] = {"材料号", "材料名称","材料品牌","材料类别","规格","型号","颜色","材料单价(元)","计价单位"};
    private Object tblMaterialData[][];
    List<BeanMaterial> materials = null;
    DefaultTableModel tablmaterialmod = new DefaultTableModel();
    private JTable dataMaterialTable = new JTable(tablmaterialmod);

    private Object tblServiceTitle[] = {"服务号", "服务名", "服务等级", "服务单价(元)", "计价单位", "需要时间(h)"};
    private Object tblServiceData[][];
    List<BeanService> services = null;
    DefaultTableModel tablservicemod = new DefaultTableModel();
    private JTable dataServiceTable = new JTable(tablservicemod);

    private void reloadMaterialTable(BeanUser user) throws SQLException{
        try {
            materials = (new UserManager()).SearchMaterial(user);
            tblMaterialData = new Object[materials.size()][9];
            for (int i = 0; i < materials.size(); i++) {
                tblMaterialData[i][0] = materials.get(i).getMaterialId();
                tblMaterialData[i][1] = materials.get(i).getMaterialName();
                tblMaterialData[i][2] = materials.get(i).getBrandName();
                tblMaterialData[i][3] = materials.get(i).getSortName();
                tblMaterialData[i][4] = materials.get(i).getSpecification();
                tblMaterialData[i][5] = materials.get(i).getModel();
                tblMaterialData[i][6] = materials.get(i).getColor();
                tblMaterialData[i][7] = materials.get(i).getMaterialUnitPrice();
                tblMaterialData[i][8] = materials.get(i).getMaterialUnit();
            }
            tablmaterialmod.setDataVector(tblMaterialData, tblMaterialTitle);
            this.dataMaterialTable.validate();
            this.dataMaterialTable.repaint();
        } catch (BaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void reloadServiceTable(BeanUser user) throws SQLException{
        try {
            services = (new UserManager()).SearchService(user);
            tblServiceData = new Object[services.size()][6];
            for (int i = 0; i < services.size(); i++) {
                tblServiceData[i][0] = services.get(i).getServiceId();
                tblServiceData[i][1] = services.get(i).getServiceName();
                tblServiceData[i][2] = services.get(i).getServiceRank();
                tblServiceData[i][3] = services.get(i).getServiceUnitPrice();
                tblServiceData[i][4] = services.get(i).getServiceUnit();
                tblServiceData[i][5] = services.get(i).getHour();
            }
            tablservicemod.setDataVector(tblServiceData, tblServiceTitle);
            this.dataServiceTable.validate();
            this.dataServiceTable.repaint();
        } catch (BaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public FrmDisplayMSInfo(BeanUser user,Dialog f, String s, boolean b) {
        super(f, s, b);

        //提取现有材料数据
        try {
            this.reloadMaterialTable(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.getContentPane().add(new JScrollPane(this.dataMaterialTable), BorderLayout.WEST);

        //提取现有服务数据
        try {
            this.reloadServiceTable(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.getContentPane().add(new JScrollPane(this.dataServiceTable), BorderLayout.EAST);


        // 屏幕居中显示
        this.setSize(1200, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();


        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //System.exit(0);
            }
        });

    }


}
