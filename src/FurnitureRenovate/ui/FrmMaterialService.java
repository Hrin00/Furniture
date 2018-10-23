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

public class FrmMaterialService extends JDialog implements ActionListener {
    public int roomId = 0;
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnAdd = new Button("添加");
    private Button btnModify = new Button("修改");
    private Button btnDel = new Button("删除");
    private Object tblTitle[] = {"关系编号","材料编号","材料", "材料数量", "材料单位","服务编号", "服务名", "服务等级", "服务价格(元)", "服务时间(h)"};
    private Object tblData[][];
    List<BeanMaterialService> materialServices = new ArrayList<BeanMaterialService>();
    DefaultTableModel tablmod = new DefaultTableModel();
    private JTable dataTable = new JTable(tablmod);

    private void reloadTable() {
        try {
            materialServices = (new MaterialServiceManager()).LoadAll();
            tblData = new Object[materialServices.size()][10];
            for (int i = 0; i < materialServices.size(); i++) {
                tblData[i][0] = materialServices.get(i).getMaterialServiceId()+"";
                tblData[i][1] = materialServices.get(i).getMaterialId()+"";
                tblData[i][2] = materialServices.get(i).getBrandName()
                        + materialServices.get(i).getMaterial().getColor()
                        + materialServices.get(i).getMaterial().getModel()
                        + materialServices.get(i).getMaterial().getSpecification()
                        + materialServices.get(i).getMaterial().getMaterialName();
                tblData[i][3] = materialServices.get(i).getMaterialQuantity() + "";
                tblData[i][4] = materialServices.get(i).getMaterial().getMaterialUnit();
                tblData[i][5] = materialServices.get(i).getServiceId();
                tblData[i][6] = materialServices.get(i).getService().getServiceName();
                tblData[i][7] = materialServices.get(i).getService().getServiceRank() + "";
                tblData[i][8] = materialServices.get(i).getService().getServiceUnitPrice() + "";
                tblData[i][9] = materialServices.get(i).getService().getHour() + "";
            }
            tablmod.setDataVector(tblData, tblTitle);
            this.dataTable.validate();
            this.dataTable.repaint();
        } catch (BaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public FrmMaterialService(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnAdd);
        toolBar.add(btnModify);
        toolBar.add(btnDel);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);

        //提取现有数据
        this.reloadTable();
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);


        // 屏幕居中显示
        this.setSize(1300, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        this.btnAdd.addActionListener(this);
        this.btnModify.addActionListener(this);
        this.btnDel.addActionListener(this);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //System.exit(0);
            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == this.btnAdd) {
            FrmAddMaterialService dlg = new FrmAddMaterialService(this, "添加对应关系", true);
            dlg.setVisible(true);
            this.reloadTable();
        }
        else if (e.getSource() == this.btnModify) {
            int i = this.dataTable.getSelectedRow();
            if (i < 0) {
                JOptionPane.showMessageDialog(null, "请选择关系", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanMaterialService materialService = materialServices.get(i);

            FrmModifyMaterialService dlg = new FrmModifyMaterialService(this, "修改对应关系", true);
            dlg.materialService = materialService;
            dlg.setVisible(true);
            this.reloadTable();
        }
        else if (e.getSource() == this.btnDel) {
            int i = this.dataTable.getSelectedRow();
            if (i < 0) {
                JOptionPane.showMessageDialog(null, "请选择关系", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanMaterialService materialService = materialServices.get(i);

            if (JOptionPane.showConfirmDialog(this, "确定删除编号为" +materialService.getMaterialServiceId()+ "的关系吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                try {
                    (new MaterialServiceManager()).DeleteMaterialService(materialService);
                    this.reloadTable();
                } catch (BaseException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }
}

