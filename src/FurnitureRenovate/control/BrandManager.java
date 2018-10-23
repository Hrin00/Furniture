package FurnitureRenovate.control;

import FurnitureRenovate.model.BeanBrand;
import FurnitureRenovate.model.BeanSort;
import FurnitureRenovate.util.BaseException;
import FurnitureRenovate.util.DbException;
import FurnitureRenovate.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class BrandManager {
    public void AddBrand(String name , String describe) throws BaseException {
        if(name.equals("")) throw new BaseException("品牌名不能为空");
        try{
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();
            String hql = "select count(*) from BeanBrand where brandName = ?";
            Query query = session.createQuery(hql);
            query.setString(0,name);
            if(query.uniqueResult() != null){
                if((long)query.uniqueResult() > (long)0) {
                    throw new BaseException("已经存在该品牌");
                }
            }

            BeanBrand brand = new BeanBrand();
            brand.setBrandName(name);
            brand.setBrandDescribe(describe);

            session.save(brand);
            tx.commit();
            session.close();
        }catch(HibernateException e){
            e.printStackTrace();
            throw  new BaseException("数据库操作异常");
        }
    }

    public List<BeanBrand> LoadAll() throws BaseException {
        List<BeanBrand> result=new ArrayList<BeanBrand>();

        try {
            Session session = HibernateUtil.getSession();
            String hql = "from BeanBrand ";
            Query query = session.createQuery(hql);
            result = query.list();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new DbException(e);
        }finally {

        }

        return result;
    }

    public void ChangeBrand(BeanBrand brand,String name,String describe) throws BaseException {
        if(name.equals("")) throw new BaseException("品牌名不能为空");
        try{
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();

            String hql = "select count(*) from BeanBrand where brandName = ?";
            Query query = session.createQuery(hql);
            query.setString(0,name);
            if(query.uniqueResult() != null){
                if((long)query.uniqueResult() > (long)0) {
                    //throw new BaseException("已经存在该品牌");
                }
            }


            BeanBrand b = (BeanBrand) session.get(BeanBrand.class,brand.getBrandId());

            b.setBrandName(name);
            b.setBrandDescribe(describe);

            tx.commit();
            session.close();
        }catch (HibernateException e){
            e.printStackTrace();
            throw new BaseException("数据库操作异常");
        }
    }

    public void DeleteBrand(BeanBrand brand) throws BaseException{
        try{
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();

            String hql = "select count(*) from BeanMaterial where brandId = ?";
            Query query = session.createQuery(hql);
            query.setInteger(0,brand.getBrandId());
            if(query.uniqueResult() != null){
                if((long)query.uniqueResult() > (long)0) {
                    throw new BaseException("该品牌下有材料，无法删除");
                }
            }

            BeanBrand b = (BeanBrand)session.get(BeanBrand.class,brand.getBrandId());
            session.delete(b);
            tx.commit();

            session.close();
        }catch (HibernateException e){
            e.printStackTrace();
            throw new BaseException("数据库操作异常");
        }
    }

    public List<BeanBrand> search(String keyword) throws BaseException{
        List<BeanBrand> brands = new ArrayList<BeanBrand>();
        try{
            Session session = HibernateUtil.getSession();

            String hql = "from BeanBrand where brandName like ?";
            Query query = session.createQuery(hql);
            query.setString(0,"%"+keyword+"%");
            brands = query.list();
            session.close();
        }catch (HibernateException e){
            e.printStackTrace();
            throw new BaseException("数据库操作异常");
        }
        return brands;
    }
}
