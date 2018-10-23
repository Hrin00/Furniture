package FurnitureRenovate.control;

import FurnitureRenovate.util.BaseException;
import FurnitureRenovate.model.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import FurnitureRenovate.util.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;

/*private int materialServiceId;
private int materialId;
private int serviceId;
private int materialQuantity;
//材料名 材料数量 材料单位 服务名
private String materialName;
private String materialUnit;
private String serviceName;*/

public class MaterialServiceManager {
    public void AddMaterialService(int materialId, int serviceId ,int materialQuantity) throws BaseException {
        try {
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();

            BeanService service = (BeanService)session.get(BeanService.class,serviceId);
            if(service == null) throw new BaseException("不存在这个编号的服务");
            BeanMaterial material = (BeanMaterial)session.get(BeanMaterial.class,materialId);
            if(material == null) throw new BaseException("不存在这个编号的材料");

            BeanMaterialService materialService = new BeanMaterialService();
            materialService.setServiceId(serviceId);
            materialService.setMaterialId(materialId);
            materialService.setMaterialQuantity(materialQuantity);

            session.save(materialService);

            tx.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new BaseException("数据库操作异常");
        }
    }

    public void ChangeMaterialService(BeanMaterialService materialService, int quantity) throws BaseException {
        try {
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();


            BeanMaterialService ms = (BeanMaterialService)session.get(BeanMaterialService.class,materialService.getMaterialServiceId());

            ms.setMaterialQuantity(quantity);

            tx.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new BaseException("数据库操作异常");
        }
    }

    public List<BeanMaterialService> LoadAll() throws BaseException{
        List<BeanMaterialService> result = new ArrayList<BeanMaterialService>();
        try{
            Session session = HibernateUtil.getSession();
            String hql = "from BeanMaterialService ";
            Query query = session.createQuery(hql);
            result = query.list();
            for (int i = 0; i < result.size(); i++){
                BeanMaterialService materialService = result.get(i);
                BeanMaterial material = (BeanMaterial)session.get(BeanMaterial.class,materialService.getMaterialId());
                if(material == null) throw new BaseException("不存在这个编号的材料");
                materialService.setMaterial(material);
                BeanBrand brand = (BeanBrand)session.get(BeanBrand.class,material.getBrandId());
                materialService.setBrandName(brand.getBrandName());
                BeanService service = (BeanService)session.get(BeanService.class,materialService.getServiceId());
                if(service == null) throw new BaseException("不存在这个编号的服务");
                materialService.setService(service);
            }
            session.close();
        }catch (HibernateException e) {
            e.printStackTrace();
            throw new BaseException("数据库操作异常");
        }
        return result;
    }

    public void DeleteMaterialService(BeanMaterialService materialService) throws BaseException {
        try {
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();
            //判断furniture
            String hql = "select count(*) from BeanFurniture where materialServiceId = ?";
            Query query = session.createQuery(hql);
            query.setInteger(0,materialService.getMaterialServiceId());
            if(query.uniqueResult() != null) if((long)query.uniqueResult() > (long)0) throw new BaseException("有装修方案在使用此项服务，无法删除");

            BeanMaterialService ms = (BeanMaterialService)session.get(BeanMaterialService.class,materialService.getMaterialServiceId());

            session.delete(ms);

            tx.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new BaseException("数据库操作异常");
        }
    }

    public static void main(String[] args) throws BaseException {
        MaterialServiceManager materialServiceManager = new MaterialServiceManager();
        List<BeanMaterialService> materialServices = new ArrayList<BeanMaterialService>();
        materialServices = materialServiceManager.LoadAll();
        for(BeanMaterialService ms:materialServices){
            System.out.println(ms.getMaterialId());
            System.out.println(ms.getServiceId());
            System.out.println(ms.getMaterialQuantity());

        }

        BeanMaterialService ms = new BeanMaterialService();
        ms.setMaterialServiceId(2);
        materialServiceManager.DeleteMaterialService(ms);
    }
}