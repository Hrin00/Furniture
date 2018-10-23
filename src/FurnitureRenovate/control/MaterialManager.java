package FurnitureRenovate.control;

import FurnitureRenovate.model.*;
import FurnitureRenovate.util.BaseException;
import FurnitureRenovate.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class MaterialManager {
    public void AddMaterial(int brandId, int sortId, String name, String specification, String model, String color, int materialUnitPrice, String materialUnit) throws BaseException {
        if(name.equals("") ) throw new BaseException("材料名不能为空");
        if(materialUnit.equals("") ) throw new BaseException("计价单位不能为空");
        try{
            Session session = HibernateUtil.getSession();
            BeanMaterial material = new BeanMaterial();
            material.setBrandId(brandId);
            material.setSortId(sortId);
            material.setMaterialName(name);
            material.setSpecification(specification);
            material.setModel(model);
            material.setColor(color);
            material.setMaterialUnitPrice(materialUnitPrice);
            material.setMaterialUnit(materialUnit);

            Transaction tx = session.beginTransaction();
            session.save(material);
            tx.commit();
            session.close();
        }catch(HibernateException e){
            e.printStackTrace();
            throw  new BaseException("数据库操作异常");
        }
    }

    public List<BeanMaterial> LoadAll() throws BaseException {
        List<BeanMaterial> result = new ArrayList<BeanMaterial>();
        try{

            Session session = HibernateUtil.getSession();
            String hql = "from BeanMaterial";
            Query query = session.createQuery(hql);
            result = query.list();
            for(int i = 0; i < result.size(); i++){
                BeanMaterial material = result.get(i);
                BeanSort sort = (BeanSort)session.get(BeanSort.class,material.getSortId());
                material.setSortName(sort.getSortName());
                BeanBrand brand = (BeanBrand)session.get(BeanBrand.class,material.getBrandId());
                material.setBrandName(brand.getBrandName());
            }
            session.close();
        }catch (HibernateException e){
            e.printStackTrace();
            throw  new BaseException("数据库操作异常");
        }
        return result;
    }

    public void ChangeMaterial(BeanMaterial material, String specification, String model, String color, int materialUnitPrice) throws BaseException{
        try{
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();

            BeanMaterial m = (BeanMaterial)session.get(BeanMaterial.class,material.getMaterialId());
            m.setSpecification(specification);
            m.setModel(model);
            m.setColor(color);
            m.setMaterialUnitPrice(materialUnitPrice);

            tx.commit();

            session.close();
        }catch(HibernateException e){
            e.printStackTrace();
            throw  new BaseException("数据库操作异常");
        }
    }

    public List<BeanMaterial> search(String keyword) throws BaseException{
        List<BeanMaterial> materials = new ArrayList<BeanMaterial>();
        try{
            Session session = HibernateUtil.getSession();
            String hql = "from BeanMaterial where materialName like ?";
            Query query = session.createQuery(hql);
            query.setString(0,"%"+keyword+"%");
            materials = query.list();
            for(int i = 0; i < materials.size(); i++){
                BeanMaterial material = materials.get(i);
                BeanSort sort = (BeanSort)session.get(BeanSort.class,material.getSortId());
                material.setSortName(sort.getSortName());
                BeanBrand brand = (BeanBrand)session.get(BeanBrand.class,material.getBrandId());
                material.setBrandName(brand.getBrandName());
            }

            session.close();
        }catch (HibernateException e){
            e.printStackTrace();
            throw new BaseException("数据库操作异常");
        }
        return materials;
    }
    public void DeleteMaterial(BeanMaterial material) throws BaseException{
        try{
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();
            String hql = "select count(*) from BeanMaterialService where materialId = ?";
            Query query = session.createQuery(hql);
            query.setInteger(0,material.getMaterialId());
            if(query.uniqueResult() != null)
                if((long)query.uniqueResult() > (long)0)
                    throw new BaseException("该材料下有可选装修方案，无法删除");

            BeanMaterial m = (BeanMaterial)session.get(BeanMaterial.class,material.getMaterialId());
            session.delete(m);
            tx.commit();
            session.close();
        }catch(HibernateException e){
            e.printStackTrace();
            throw  new BaseException("数据库操作异常");
        }
    }

}
