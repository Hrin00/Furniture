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
import com.oracle.awt.AWTUtils;


public class FrmModifySort extends JDialog implements ActionListener {
    public BeanSort sort = null;
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelName = new JLabel("类型名称：");
    private JLabel labelDescribe = new JLabel("类型描述：");

    private JTextField edtName = new JTextField(20);
    private JTextField edtDescribe = new JTextField(20);

    public FrmModifySort(JDialog f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelName);
        workPane.add(edtName);
        workPane.add(labelDescribe);
        workPane.add(edtDescribe);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(360, 180);
        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.btnOk.addActionListener(this);
        this.btnCancel.addActionListener(this);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                FrmModifySort.this.sort = null;
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnCancel) {
            this.setVisible(false);
            FrmModifySort.this.sort = null;
            return;
        } else if (e.getSource() == this.btnOk) {
            String name = this.edtName.getText();
            String describe = this.edtDescribe.getText();

            try {
                (new SortManager()).ChangeSort(sort,name,describe);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }

    }
}
