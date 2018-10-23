package FurnitureRenovate.ui;

import javax.swing.*;
import java.awt.event.ActionListener;


import FurnitureRenovate.FurnitureRenovateUtil;
import FurnitureRenovate.control.HouseManager;
import FurnitureRenovate.control.RoomManager;
import FurnitureRenovate.model.*;
import FurnitureRenovate.control.*;
import FurnitureRenovate.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class FrmAddMaterialService extends JDialog implements ActionListener{
    public int roomId = 0;
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelMaterialQuantity = new JLabel("材料数量:");
    private JTextField edtMaterialQuantity = new JTextField(10);
    private JLabel labelMaterialId = new JLabel("材料编号:");
    private JTextField edtMaterialId = new JTextField(10);
    private JLabel labelServiceId = new JLabel("服务编号:");
    private JTextField edtServiceId = new JTextField(10);

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

    private void reloadMaterialTable() {
        try {
            materials = (new MaterialManager()).LoadAll();
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

    private void reloadServiceTable() {
        try {
            services = (new ServiceManager()).LoadAll();
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
    public FrmAddMaterialService(Dialog f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelMaterialId);
        workPane.add(edtMaterialId);
        workPane.add(labelServiceId);
        workPane.add(edtServiceId);
        workPane.add(labelMaterialQuantity);
        workPane.add(edtMaterialQuantity);
        this.getContentPane().add(workPane, BorderLayout.NORTH);

        //提取现有材料数据
        this.reloadMaterialTable();
        this.getContentPane().add(new JScrollPane(this.dataMaterialTable), BorderLayout.WEST);

        //提取现有服务数据
        this.reloadServiceTable();
        this.getContentPane().add(new JScrollPane(this.dataServiceTable), BorderLayout.EAST);


        // 屏幕居中显示
        this.setSize(1200, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        this.btnOk.addActionListener(this);
        this.btnCancel.addActionListener(this);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //System.exit(0);
            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == this.btnOk) {
            int materialid = 0;
            try {
            materialid = Integer.parseInt(this.edtMaterialId.getText());
            }catch (NumberFormatException e1){
                JOptionPane.showMessageDialog(null, "材料号请勿输入数字以外的字符", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            int serviceid = 0;
            try {
                serviceid = Integer.parseInt(this.edtServiceId.getText());
            }catch (NumberFormatException e1){
                JOptionPane.showMessageDialog(null, "服务号请勿输入数字以外的字符", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }

            int quantity = 0;
            try {
                quantity = Integer.parseInt(this.edtMaterialQuantity.getText());
            }catch (NumberFormatException e1){
                JOptionPane.showMessageDialog(null, "数量请勿输入数字以外的字符", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                (new MaterialServiceManager()).AddMaterialService(materialid,serviceid,quantity);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (e.getSource() == this.btnCancel) {
            this.setVisible(false);
            return;
        }
    }

}


