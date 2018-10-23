package FurnitureRenovate.ui;

import FurnitureRenovate.control.BrandManager;
import FurnitureRenovate.control.ServiceManager;
import FurnitureRenovate.model.BeanBrand;
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

public class FrmAddService extends JDialog implements ActionListener {
    public int changeFlag = 0;
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelName = new JLabel("服务名称：");
    private JLabel labelRank = new JLabel("服务等级：");
    private JLabel labelUnitPrice = new JLabel("服务单价：");
    private JLabel labelUnit = new JLabel("计价单位：");
    private JLabel labelHour = new JLabel("需要时间：");

    private JTextField edtName = new JTextField(20);
    private JTextField edtRank = new JTextField(20);
    private JTextField edtUnitPrice = new JTextField(20);
    private JTextField edtUnit = new JTextField(20);
    private JTextField edtHour = new JTextField(20);


    public FrmAddService(JDialog f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelName);
        workPane.add(edtName);
        workPane.add(labelRank);
        workPane.add(edtRank);
        workPane.add(labelUnitPrice);
        workPane.add(edtUnitPrice);
        workPane.add(labelUnit);
        workPane.add(edtUnit);
        workPane.add(labelHour);
        workPane.add(edtHour);

        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(360, 250);
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
            FrmAddService.this.setVisible(false);
            changeFlag = 1;
            return;
        }
        else if (e.getSource() == this.btnOk) {
            String name = this.edtName.getText();

            int rank = 0;
            try {
                rank = Integer.parseInt(this.edtRank.getText());
            }catch (NumberFormatException e1){
                JOptionPane.showMessageDialog(null, "等级请勿输入数字以外的字符", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }

            String unit = this.edtUnit.getText();

            int unitprice = 0;
            try {
                unitprice = Integer.parseInt(this.edtUnitPrice.getText());
            }catch (NumberFormatException e1){
                JOptionPane.showMessageDialog(null, "单价请勿输入数字以外的字符", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }

            int hour = 0;
            try {
                hour = Integer.parseInt(this.edtHour.getText());
            }catch (NumberFormatException e1){
                JOptionPane.showMessageDialog(null, "时间请勿输入数字以外的字符", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }


            try {
                (new ServiceManager()).AddService(name,rank,unitprice,unit,hour);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

}
