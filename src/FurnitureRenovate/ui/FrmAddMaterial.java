package FurnitureRenovate.ui;

import FurnitureRenovate.control.BrandManager;
import FurnitureRenovate.control.SortManager;
import FurnitureRenovate.model.BeanBrand;
import FurnitureRenovate.model.BeanSort;
import FurnitureRenovate.control.*;
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

public class FrmAddMaterial extends JDialog implements ActionListener {
    public int changeFlag = 0;
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelName = new JLabel("材料名称:");
    private JLabel labelSpecification = new JLabel("材料规格:");
    private JLabel labelModel = new JLabel("材料型号:");
    private JLabel labelColor = new JLabel("材料颜色:");
    private JLabel labelUnitPrice = new JLabel("材料单价:");
    private JLabel labelUnit = new JLabel("计价单位:");
    private JLabel labelBrand = new JLabel("品牌名:");
    private JLabel labelSort = new JLabel("类别名:");

    private JTextField edtName = new JTextField(20);
    private JTextField edtSpecification = new JTextField(20);
    private JTextField edtModel = new JTextField(20);
    private JTextField edtColor = new JTextField(20);
    private JTextField edtUnitPrice = new JTextField(20);
    private JTextField edtUnit = new JTextField(20);

    private Map<String, BeanSort> sortMap_name = new HashMap<String, BeanSort>();
    private Map<String, BeanBrand> brandMap_name = new HashMap<String, BeanBrand>();

    private JComboBox cmbSort = null;
    private JComboBox cmbBrand = null;

    public FrmAddMaterial(Dialog f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelName);
        workPane.add(edtName);
        workPane.add(labelSpecification);
        workPane.add(edtSpecification);
        workPane.add(labelModel);
        workPane.add(edtModel);
        workPane.add(labelColor);
        workPane.add(edtColor);
        workPane.add(labelUnitPrice);
        workPane.add(edtUnitPrice);
        workPane.add(labelUnit);
        workPane.add(edtUnit);
        workPane.add(labelBrand);
        //提取品牌信息
        try {
            List<BeanBrand> brands = (new BrandManager()).LoadAll();
            String[] strbrnads = new String[brands.size()];
            for (int i = 0; i < brands.size(); i++) {
                strbrnads[i] = brands.get(i).getBrandName();
                this.brandMap_name.put(brands.get(i).getBrandName(), brands.get(i));
            }
            this.cmbBrand = new JComboBox(strbrnads);
            workPane.add(this.cmbBrand);

        } catch (BaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        workPane.add(labelSort);
        //提取类别信息
        try {
            List<BeanSort> sorts = (new SortManager()).LoadAll();
            String[] strsotrs = new String[sorts.size()];
            for (int i = 0; i < sorts.size(); i++) {
                strsotrs[i] = sorts.get(i).getSortName();
                this.sortMap_name.put(sorts.get(i).getSortName(), sorts.get(i));
            }
            this.cmbSort = new JComboBox(strsotrs);
            workPane.add(this.cmbSort);

        } catch (BaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(350, 300);
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
    public void actionPerformed(ActionEvent e)  {
        if (e.getSource() == this.btnCancel) {
            FrmAddMaterial.this.setVisible(false);
            changeFlag = 1;
            return;
        }
        else if (e.getSource() == this.btnOk) {
            String name = this.edtName.getText();
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
            String unit = this.edtUnit.getText();
            int brandid = 0;
            int sortid = 0;

            if (this.cmbBrand.getSelectedIndex() >= 0) {
                BeanBrand b = this.brandMap_name.get(this.cmbBrand.getSelectedItem().toString());
                if (b != null) brandid = b.getBrandId();
            }

            if(this.cmbSort.getSelectedIndex() >= 0){
                BeanSort s = this.sortMap_name.get(this.cmbSort.getSelectedItem().toString());
                if(s != null) sortid = s.getSortId();
            }


            try {
                (new MaterialManager()).AddMaterial(brandid,sortid,name,specification,model,color,unitprice,unit);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

}
