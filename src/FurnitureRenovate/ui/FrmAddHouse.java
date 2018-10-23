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



public class FrmAddHouse extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelName = new JLabel("房屋名称：");
    private JLabel shi = new JLabel("卧室间数");
    private JLabel ting = new JLabel("客厅间数：");
    private JLabel wei = new JLabel("浴室间数：");
    private JLabel chu = new JLabel("厨房间数：");

    private JComboBox numTing = new JComboBox(new String[]{"0", "1", "2","3","4","5","6","7","8","9"});
    private JComboBox numShi = new JComboBox(new String[]{"0", "1", "2","3","4","5","6","7","8","9"});
    private JComboBox numwei = new JComboBox(new String[]{"0", "1", "2","3","4","5","6","7","8","9"});
    private JComboBox numChu = new JComboBox(new String[]{"0", "1", "2","3","4","5","6","7","8","9"});
    private JTextField edtName = new JTextField(20);
    public FrmAddHouse(JFrame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelName);
        workPane.add(edtName);
        workPane.add(shi);
        workPane.add(numShi);
        workPane.add(ting);
        workPane.add(numTing);
        workPane.add(chu);
        workPane.add(numChu);
        workPane.add(wei);
        workPane.add(numwei);

        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(950, 90);
        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2 - 100);

        this.validate();
        this.btnOk.addActionListener(this);
        this.btnCancel.addActionListener(this);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel) {
            this.setVisible(false);
            return;
        }
        else if(e.getSource()==this.btnOk){
            String name=this.edtName.getText();
            int nShi = Integer.parseInt(this.numShi.getSelectedItem().toString());
            int nTing = Integer.parseInt(this.numTing.getSelectedItem().toString());
            int nChu = Integer.parseInt(this.numChu.getSelectedItem().toString());
            int nWei = Integer.parseInt(this.numwei.getSelectedItem().toString());
            try {
                BeanHouse house = new BeanHouse();
                house.setHouseName(name);
                house.setUserId(BeanUser.currentLoginUser.getUserId());
                house = (new HouseManager()).AddHouse(house);

                for(int i = 1; i <= nShi; i++)
                    (new RoomManager()).AddShi(house.getHouseId(),i);
                for(int i = 1; i <= nTing; i++)
                    (new RoomManager()).AddTing(house.getHouseId(),i);
                for(int i = 1; i <= nChu; i++)
                    (new RoomManager()).AddChu(house.getHouseId(),i);
                for(int i = 1; i <= nWei; i++)
                    (new RoomManager()).AddWei(house.getHouseId(),i);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }

        }

    }


}
