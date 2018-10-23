package FurnitureRenovate.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import FurnitureRenovate.model.BeanBrand;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.Map;

import javax.swing.*;

import FurnitureRenovate.model.BeanMaterial;
import FurnitureRenovate.model.BeanSort;
import FurnitureRenovate.util.*;
import FurnitureRenovate.ui.*;
import FurnitureRenovate.control.*;
import com.oracle.awt.AWTUtils;


public class FrmModifyMaterial extends JDialog implements ActionListener {
    public BeanMaterial material = null;
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelSpecification = new JLabel("材料规格:");
    private JLabel labelModel = new JLabel("材料型号:");
    private JLabel labelColor = new JLabel("材料颜色:");
    private JLabel labelUnitPrice = new JLabel("材料单价:");

    private JTextField edtSpecification = new JTextField(20);
    private JTextField edtModel = new JTextField(20);
    private JTextField edtColor = new JTextField(20);
    private JTextField edtUnitPrice = new JTextField(20);


    public FrmModifyMaterial(JDialog f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelSpecification);
        workPane.add(edtSpecification);
        workPane.add(labelModel);
        workPane.add(edtModel);
        workPane.add(labelColor);
        workPane.add(edtColor);
        workPane.add(labelUnitPrice);
        workPane.add(edtUnitPrice);


        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(360, 200);
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
                FrmModifyMaterial.this.material = null;
            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnCancel) {
            this.setVisible(false);
            FrmModifyMaterial.this.material = null;
            return;
        } else if (e.getSource() == this.btnOk) {

            String specification = this.edtSpecification.getText();
            String model = this.edtModel.getText();
            String color = this.edtColor.getText();

            int unitprice = 0;
            try {
                unitprice = Integer.parseInt(this.edtUnitPrice.getText());
            }catch (NumberFormatException e1){
                JOptionPane.showMessageDialog(null, "单价请勿输入数字以外的字符", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                (new MaterialManager()).ChangeMaterial(material,specification,model,color,unitprice);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }

    }
}
