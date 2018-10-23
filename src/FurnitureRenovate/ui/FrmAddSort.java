package FurnitureRenovate.ui;


import FurnitureRenovate.control.BrandManager;
import FurnitureRenovate.control.SortManager;
import FurnitureRenovate.model.BeanSort;
import FurnitureRenovate.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrmAddSort extends JDialog implements ActionListener {
    public int changeFlag = 0;
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelName = new JLabel("类型名称：");
    private JLabel labelDescribe = new JLabel("类型描述：");

    private JTextField edtName = new JTextField(20);
    private JTextField edtDescribe = new JTextField(20);


    public FrmAddSort(JDialog f, String s, boolean b) {
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
                changeFlag = 1;
            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnCancel) {
            FrmAddSort.this.setVisible(false);
            changeFlag = 1;
            return;
        }
        else if (e.getSource() == this.btnOk) {
            String name = this.edtName.getText();
            String describe = this.edtDescribe.getText();

            try {
                (new SortManager()).AddSort(name,describe);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

}
