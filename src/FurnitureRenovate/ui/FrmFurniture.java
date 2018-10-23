package FurnitureRenovate.ui;

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

public class FrmFurniture extends JDialog implements ActionListener{
    public int roomId = 0;
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelQuantity = new JLabel("装修数量:");
    private JTextField edtQuantity = new JTextField(10);
    private Object tblTitle[] = {"材料", "材料数量", "材料单位", "服务名", "服务等级","服务价格(元)","计价单位","服务时间(h)"};
    private Object tblData[][];
    List<BeanMaterialService> materialServices = new ArrayList<BeanMaterialService>();
    DefaultTableModel tablmod = new DefaultTableModel();
    private JTable dataTable = new JTable(tablmod);

    private void reloadTable() {
        try {
            materialServices = (new MaterialServiceManager()).LoadAll();
            tblData = new Object[materialServices.size()][9];
            for (int i = 0; i < materialServices.size(); i++) {
                tblData[i][0] = materialServices.get(i).getBrandName()
                        +materialServices.get(i).getMaterial().getColor()
                        +materialServices.get(i).getMaterial().getModel()
                        +materialServices.get(i).getMaterial().getSpecification()
                        +materialServices.get(i).getMaterial().getMaterialName();
                tblData[i][1] = materialServices.get(i).getMaterialQuantity()+"";
                tblData[i][2] = materialServices.get(i).getMaterial().getMaterialUnit();
                tblData[i][3] = materialServices.get(i).getService().getServiceName();
                tblData[i][4] = materialServices.get(i).getService().getServiceRank()+"";
                tblData[i][5] = materialServices.get(i).getService().getServiceUnitPrice()+"";
                tblData[i][6] = materialServices.get(i).getService().getServiceUnit()+"";
                tblData[i][7] = materialServices.get(i).getService().getHour()+"";
            }
            tablmod.setDataVector(tblData, tblTitle);
            this.dataTable.validate();
            this.dataTable.repaint();
        } catch (BaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public FrmFurniture(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelQuantity);
        workPane.add(edtQuantity);
        this.getContentPane().add(workPane, BorderLayout.NORTH);

        //提取现有数据
        this.reloadTable();
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);


        // 屏幕居中显示
        this.setSize(1400, 600);
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
            int i = this.dataTable.getSelectedRow();
            if (i < 0) {
                JOptionPane.showMessageDialog(null, "请选择装修方案", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                BeanMaterialService materialService = this.materialServices.get(i);
                BeanFurniture furniture = new BeanFurniture();
                furniture.setMaterialServiceId(materialServices.get(i).getMaterialServiceId());
                furniture.setRoomId(roomId);
                int quantity = 0;
                try{
                    quantity = Integer.parseInt(this.edtQuantity.getText());
                }catch (NumberFormatException e1){
                    JOptionPane.showMessageDialog(null, "数量请勿输入数字以外的字符", "错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                furniture.setServiceQuantity(quantity);
                (new FurnitureManager()).AddFurniture(furniture);
                this.setVisible(false);
            }catch (BaseException e1){
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }

        }
        else if (e.getSource() == this.btnCancel) {
            this.setVisible(false);
            return;
        }
    }

}

