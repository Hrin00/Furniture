package FurnitureRenovate.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import FurnitureRenovate.model.BeanHouse;
import FurnitureRenovate.model.BeanUser;
import FurnitureRenovate.util.*;
import FurnitureRenovate.ui.*;
import FurnitureRenovate.control.*;
import com.mysql.cj.xdevapi.Warning;

public class FrmDelHouse extends JDialog implements ActionListener{
    public BeanHouse house = null;
    private JPanel toolBar = new JPanel();
    private JPanel warnN = new JPanel();
    private JPanel warnS = new JPanel();
    private JLabel warnup = new JLabel("删除房屋会删除所有与该房屋相关的信息");
    private JLabel warndown = new JLabel("确认要删除吗");
    private JButton btnOk = new JButton("确认");
    private JButton btnCancel = new JButton("取消");

    public FrmDelHouse(JFrame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);

        warnN.add(warnup);
        this.getContentPane().add(warnN,BorderLayout.NORTH);

        warnS.add(warndown);
        this.getContentPane().add(warnS,BorderLayout.CENTER);

        this.setSize(300, 130);
        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.btnOk.addActionListener(this);
        this.btnCancel.addActionListener(this);

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btnOk){
            try {
                (new HouseManager()).DeleteHouse(house);
                this.setVisible(false);
            }catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        else if(e.getSource()==btnCancel){
            this.setVisible(false);
            return;
        }

    }
}
