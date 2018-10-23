package FurnitureRenovate.ui;

import FurnitureRenovate.control.BrandManager;
import FurnitureRenovate.control.UserManager;
import FurnitureRenovate.model.BeanBrand;
import FurnitureRenovate.model.BeanUser;
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

public class FrmChangePwd extends JDialog implements ActionListener {
    public int changeFlag = 0;
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelOldPwd = new JLabel("原密码：");
    private JLabel labelNewPwd = new JLabel("新密码：");
    private JLabel labelNewPwd2 = new JLabel("重复密码：");


    private JPasswordField edtOldPwd= new JPasswordField(20);
    private JPasswordField edtNewPwd = new JPasswordField(20);
    private JPasswordField edtNewPwd2 = new JPasswordField(19);

    public FrmChangePwd(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelOldPwd);
        workPane.add(edtOldPwd);
        workPane.add(labelNewPwd);
        workPane.add(edtNewPwd);
        workPane.add(labelNewPwd2);
        workPane.add(edtNewPwd2);

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
            FrmChangePwd.this.setVisible(false);
            return;
        }
        else if (e.getSource() == this.btnOk) {
            String oldpwd = this.edtOldPwd.getText();
            String newpwd = this.edtNewPwd.getText();
            String newpwd2 = this.edtNewPwd2.getText();

            try {
                (new UserManager()).changePwd(BeanUser.currentLoginUser,oldpwd,newpwd,newpwd2);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

}
