package FurnitureRenovate.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import FurnitureRenovate.model.BeanMaterial;
import FurnitureRenovate.model.BeanSort;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.*;

import FurnitureRenovate.util.*;
import FurnitureRenovate.ui.*;
import FurnitureRenovate.control.*;


public class FrmMaterial extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private Button btnAdd = new Button("添加");
    private Button btnModify = new Button("修改");
    private Button btnDel = new Button("删除");
    private JTextField edtKeyword = new JTextField(10);
    private Button btnSearch = new Button("查询材料名");
    private Object tblTitle[] = {"材料号", "材料名称","材料品牌","材料类别","规格","型号","颜色","材料单价","计价单位"};
    private Object tblData[][];
    List<BeanMaterial> materials = null;
    DefaultTableModel tablmod = new DefaultTableModel();
    private JTable dataTable = new JTable(tablmod);

    private void reloadTable() {
        try {
            materials = (new MaterialManager()).search(this.edtKeyword.getText());
            tblData = new Object[materials.size()][9];
            for (int i = 0; i < materials.size(); i++) {
                tblData[i][0] = materials.get(i).getMaterialId();
                tblData[i][1] = materials.get(i).getMaterialName();
                tblData[i][2] = materials.get(i).getBrandName();
                tblData[i][3] = materials.get(i).getSortName();
                tblData[i][4] = materials.get(i).getSpecification();
                tblData[i][5] = materials.get(i).getModel();
                tblData[i][6] = materials.get(i).getColor();
                tblData[i][7] = materials.get(i).getMaterialUnitPrice();
                tblData[i][8] = materials.get(i).getMaterialUnit();
            }
            tablmod.setDataVector(tblData, tblTitle);
            this.dataTable.validate();
            this.dataTable.repaint();
        } catch (BaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public FrmMaterial(Frame f,String s,boolean b) {
        super(f,s,b);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnAdd);
        toolBar.add(btnModify);
        toolBar.add(btnDel);
        toolBar.add(edtKeyword);
        toolBar.add(btnSearch);


        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        //提取现有数据
        this.reloadTable();
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);

        // 屏幕居中显示
        this.setSize(1200, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        this.btnAdd.addActionListener(this);
        this.btnModify.addActionListener(this);
        this.btnDel.addActionListener(this);
        this.btnSearch.addActionListener(this);
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
            FrmAddMaterial dlg = new FrmAddMaterial(this,"添加材料",true);
            dlg.setVisible(true);
            if(dlg.changeFlag == 0)
                this.reloadTable();
        }
        else if (e.getSource() == this.btnModify) {
            int i = this.dataTable.getSelectedRow();
            if (i < 0) {
                JOptionPane.showMessageDialog(null, "请选择材料", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }

            BeanMaterial material = this.materials.get(i);
            FrmModifyMaterial dlg = new FrmModifyMaterial(this, "修改材料", true);
            dlg.material = material;
            dlg.setVisible(true);
            if(dlg.material != null)
                this.reloadTable();
        }
        else if (e.getSource() == this.btnDel) {
            int i = this.dataTable.getSelectedRow();
            if (i < 0) {
                JOptionPane.showMessageDialog(null, "请选择材料", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanMaterial material = materials.get(i);
            if (JOptionPane.showConfirmDialog(this, "确定删除" + material.getMaterialName() + "吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                try {
                    (new MaterialManager()).DeleteMaterial(material);
                    this.reloadTable();
                } catch (BaseException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else if (e.getSource() == this.btnSearch) {
            this.reloadTable();
        }
    }
}
