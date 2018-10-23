package FurnitureRenovate.control;

import FurnitureRenovate.model.BeanFurniture;
import FurnitureRenovate.model.BeanMaterial;
import FurnitureRenovate.model.BeanMaterialService;
import FurnitureRenovate.model.BeanService;
import FurnitureRenovate.util.BaseException;
import FurnitureRenovate.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class FurnitureManager {
    public void AddFurniture(BeanFurniture furniture) throws BaseException {
        try{
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();

            session.save(furniture);

            tx.commit();
            session.close();
        }catch(HibernateException e){
            e.printStackTrace();
            throw  new BaseException("数据库操作异常");
        }
    }


    public List<BeanFurniture> LoadAll(int roomId) throws BaseException {
        List<BeanFurniture> furnitures = new ArrayList<BeanFurniture>();
        try{
            Session session = HibernateUtil.getSession();
/*
            private int furnitureId;
            private int materialServiceId;
            private int serviceQuantity;
            private int roomId;
            //材料名 材料总数量 材料单位 服务名 服务总数量 服务单位 服务总时间  总预算
                                            private String materialName;
                                            private int materialSumQuantity;
                                            private String materialUnit;
                                            private String serviceName;
                                            private int serviceSumQuantity;
                                            private String serviceUnit;
                                            private int needHour;
                                            private int SumBudget;*/
            String hql = "from BeanFurniture where roomId = ?";
            Query query = session.createQuery(hql);
            query.setInteger(0,roomId);
            furnitures = query.list();
            for (int i = 0; i < furnitures.size(); i++){
                BeanFurniture furniture = furnitures.get(i);
                BeanMaterialService materialService = (BeanMaterialService)session.get(BeanMaterialService.class,furniture.getMaterialServiceId());
                //materialSumQuantity
                furniture.setMaterialSumQuantity(materialService.getMaterialQuantity()*furniture.getServiceQuantity());
                //serviceSumQuantity
                furniture.setServiceSumQuantity(furniture.getServiceQuantity());

                BeanMaterial material = (BeanMaterial)session.get(BeanMaterial.class,materialService.getMaterialId());
                //materialName
                furniture.setMaterialName(material.getMaterialName());
                //materialUnit
                furniture.setMaterialUnit(material.getMaterialUnit());
                //materialPrice
                int materialPrice = material.getMaterialUnitPrice();

                BeanService service = (BeanService)session.get(BeanService.class,materialService.getServiceId());
                //serviceName
                furniture.setServiceName(service.getServiceName());
                //serviceUnit
                furniture.setServiceUnit(service.getServiceUnit());
                //servicePrice
                int servicePrice = service.getServiceUnitPrice();
                //hour
                int hour = service.getHour();

                //needHour
                furniture.setNeedHour(hour*furniture.getServiceSumQuantity());
                //SumBudget
                furniture.setSumBudget(materialPrice*furniture.getMaterialSumQuantity()+servicePrice*furniture.getServiceSumQuantity());

            }
            session.close();
        }catch(HibernateException e){
            e.printStackTrace();
            throw  new BaseException("数据库操作异常");
        }
        return furnitures;
    }



    public void DeleteFurniture(BeanFurniture furniture) throws BaseException{
        try{
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();

            BeanFurniture f = (BeanFurniture)session.get(BeanFurniture.class,furniture.getFurnitureId());
            session.delete(f);

            tx.commit();
            session.close();
        }catch(HibernateException e){
            e.printStackTrace();
            throw  new BaseException("数据库操作异常");
        }
    }
}
