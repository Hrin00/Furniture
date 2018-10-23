package FurnitureRenovate.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import FurnitureRenovate.model.BeanBrand;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.*;

import FurnitureRenovate.model.BeanMaterialService;
import FurnitureRenovate.util.*;
import FurnitureRenovate.ui.*;
import FurnitureRenovate.control.*;
import com.oracle.awt.AWTUtils;


public class FrmModifyMaterialService extends JDialog implements ActionListener {
    public BeanMaterialService materialService = null;
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelQuantity = new JLabel("材料数量：");

    private JTextField edtQuantity = new JTextField(2);


    public FrmModifyMaterialService(JDialog f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelQuantity);
        workPane.add(edtQuantity);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(200, 100);
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

            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnCancel) {
            this.setVisible(false);
            return;
        } else if (e.getSource() == this.btnOk) {

            int quantity = 0;
            try {
                quantity = Integer.parseInt(this.edtQuantity.getText());
            }catch (NumberFormatException e1){
                JOptionPane.showMessageDialog(null, "数量请勿输入数字以外的字符", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                (new MaterialServiceManager()).ChangeMaterialService(materialService,quantity);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }

    }
}
