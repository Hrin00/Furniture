package FurnitureRenovate.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import FurnitureRenovate.model.BeanSort;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.*;

import FurnitureRenovate.util.*;
import FurnitureRenovate.ui.*;
import FurnitureRenovate.control.*;


public class FrmSort extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private Button btnAdd = new Button("添加");
    private Button btnModify = new Button("修改");
    private Button btnDel = new Button("删除");
    private JTextField edtKeyword = new JTextField(10);
    private Button btnSearch = new Button("查询类型名");
    private Object tblTitle[] = {"类型名", "描述"};
    private Object tblData[][];
    List<BeanSort> sorts = null;
    DefaultTableModel tablmod = new DefaultTableModel();
    private JTable dataTable = new JTable(tablmod);

    private void reloadTable() {
        try {
            sorts = (new SortManager()).search(this.edtKeyword.getText());
            tblData = new Object[sorts.size()][2];
            for (int i = 0; i < sorts.size(); i++) {
                tblData[i][0] = sorts.get(i).getSortName();
                tblData[i][1] = sorts.get(i).getSortDescribe();
            }
            tablmod.setDataVector(tblData, tblTitle);
            this.dataTable.validate();
            this.dataTable.repaint();
        } catch (BaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public FrmSort(Frame f, String s, boolean b) {
        super(f, s, b);
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
        this.setSize(800, 600);
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
            FrmAddSort dlg = new FrmAddSort(this, "添加类型", true);
            dlg.setVisible(true);
            if(dlg.changeFlag == 0)
                this.reloadTable();
        }
        else if (e.getSource() == this.btnModify) {
            int i = this.dataTable.getSelectedRow();
            if (i < 0) {
                JOptionPane.showMessageDialog(null, "请选择类型", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanSort sort = this.sorts.get(i);

            FrmModifySort dlg = new FrmModifySort(this, "修改类型", true);
            dlg.sort = sort;
            dlg.setVisible(true);
            if(dlg.sort != null)
                this.reloadTable();
        }
        else if (e.getSource() == this.btnDel) {
            int i = this.dataTable.getSelectedRow();
            if (i < 0) {
                JOptionPane.showMessageDialog(null, "请选择类型", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanSort sort = sorts.get(i);

            if (JOptionPane.showConfirmDialog(this, "确定删除" + sort.getSortName() + "吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                try {
                    (new SortManager()).DeleteSort(sort);
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
