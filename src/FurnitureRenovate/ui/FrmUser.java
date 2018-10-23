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

import FurnitureRenovate.model.BeanUser;
import FurnitureRenovate.util.*;
import FurnitureRenovate.control.*;


public class FrmUser extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JTextField edtKeyword = new JTextField(10);
    private Button btnDisplay = new Button("材料服务信息");
    private Button btnFurniture = new Button("装修信息");
    private Button btnSearch = new Button("查询");
    private Button btnResetPwd = new Button("重置密码");
    private Button btnDelete = new Button("删除用户");
    private Object tblTitle[] = {"用户编号","用户姓名","用户等级(1:普通用户  2,3:管理员)"};
    private Object tblData[][];
    List<BeanUser> users = null;
    DefaultTableModel tablmod = new DefaultTableModel();
    private JTable dataTable = new JTable(tablmod);

    private void reloadTable() {
        try {
            users = (new UserManager()).search(this.edtKeyword.getText());
            tblData = new Object[users.size()][3];
            for (int i = 0; i < users.size(); i++) {
                tblData[i][0] = users.get(i).getUserId();
                tblData[i][1] = users.get(i).getUserName();
                tblData[i][2] = users.get(i).getUserRank();
            }
            tablmod.setDataVector(tblData, tblTitle);
            this.dataTable.validate();
            this.dataTable.repaint();
        } catch (BaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public FrmUser(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnDisplay);
        toolBar.add(btnFurniture);
        toolBar.add(btnResetPwd);
        toolBar.add(edtKeyword);
        toolBar.add(btnSearch);
        toolBar.add(btnDelete);


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

        this.btnDisplay.addActionListener(this);
        this.btnFurniture.addActionListener(this);
        this.btnResetPwd.addActionListener(this);
        this.btnSearch.addActionListener(this);
        this.btnDelete.addActionListener(this);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == this.btnDisplay) {
            int i = this.dataTable.getSelectedRow();
            if (i < 0) {
                JOptionPane.showMessageDialog(null, "请选择用户", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanUser user = this.users.get(i);
            FrmDisplayMSInfo dlg = new FrmDisplayMSInfo(user,this, "材料服务统计", true);
            dlg.setVisible(true);
        }
        else if (e.getSource() == this.btnFurniture) {
            int i = this.dataTable.getSelectedRow();
            if (i < 0) {
                JOptionPane.showMessageDialog(null, "请选择用户", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanUser user = this.users.get(i);

            FrmDisplayFuniture dlg = new FrmDisplayFuniture(user,this, "装修信息", true);

            dlg.setVisible(true);
        }
        else if(e.getSource()==this.btnResetPwd){
            int i = this.dataTable.getSelectedRow();
            if (i < 0) {
                JOptionPane.showMessageDialog(null, "请选择用户", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanUser user = users.get(i);

            if (JOptionPane.showConfirmDialog(this, "确定将用户" + user.getUserId() + "密码重置吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                try {
                    (new UserManager()).resetPwd(user);
                } catch (BaseException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else if (e.getSource() == this.btnDelete) {
            int i = this.dataTable.getSelectedRow();
            if (i < 0) {
                JOptionPane.showMessageDialog(null, "请选择用户", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanUser user = users.get(i);

            if (JOptionPane.showConfirmDialog(this, "确定删除用户" + user.getUserId() + "吗？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                try {
                    (new UserManager()).deleteUser(user);
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
