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

public class ServiceManager {
    public void AddService(String name, int rank, int serviceUnitPrice, String serviceUnit, int hour ) throws BaseException {
        if(name.equals("") ) throw new BaseException("服务名不能为空");
        if(serviceUnit.equals("") ) throw new BaseException("计价单位不能为空");
        try{
            Session session = HibernateUtil.getSession();

            BeanService service = new BeanService();
            service.setServiceName(name);
            service.setServiceRank(rank);
            service.setServiceUnitPrice(serviceUnitPrice);
            service.setServiceUnit(serviceUnit);
            service.setHour(hour);

            Transaction tx = session.beginTransaction();
            session.save(service);
            tx.commit();
            session.close();
        }catch(HibernateException e){
            e.printStackTrace();
            throw  new BaseException("数据库操作异常");
        }
    }

    public List<BeanService> LoadAll() throws BaseException {
        List<BeanService> result = new ArrayList<BeanService>();
        try{

            Session session = HibernateUtil.getSession();
            String hql = "from BeanService";
            Query query = session.createQuery(hql);
            result = query.list();
            session.close();
        }catch (HibernateException e){
            e.printStackTrace();
            throw  new BaseException("数据库操作异常");
        }
        return result;
    }

    public void ChangeService(BeanService service,String name, int rank, int serviceUnitPrice,int hour) throws BaseException{
        try{
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();

            BeanService s = (BeanService)session.get(BeanService.class,service.getServiceId());

            s.setServiceName(name);
            s.setServiceRank(rank);
            s.setServiceUnitPrice(serviceUnitPrice);
            s.setHour(hour);
            tx.commit();

            session.close();
        }catch(HibernateException e){
            e.printStackTrace();
            throw  new BaseException("数据库操作异常");
        }
    }
    public List<BeanService> search(String keyword) throws BaseException{
        List<BeanService> services = new ArrayList<BeanService>();
        try{
            Session session = HibernateUtil.getSession();

            String hql = "from BeanService where serviceName like ?";
            Query query = session.createQuery(hql);
            query.setString(0,"%"+keyword+"%");
            services = query.list();
            session.close();
        }catch (HibernateException e){
            e.printStackTrace();
            throw new BaseException("数据库操作异常");
        }
        return services;
    }

    public void DeleteService(BeanService service) throws BaseException{
        try{
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();
            String hql = "select count(*) from BeanMaterialService where serviceId = ?";
            Query query = session.createQuery(hql);
            query.setInteger(0,service.getServiceId());
            if(query.uniqueResult() != null){
                if((long)query.uniqueResult() > (long)0) {
                    throw new BaseException("该服务下有可选装修方案，无法删除");
                }
            }

            BeanService s = (BeanService)session.get(BeanService.class,service.getServiceId());
            session.delete(s);
            tx.commit();
            session.close();
        }catch(HibernateException e){
            e.printStackTrace();
            throw  new BaseException("数据库操作异常");
        }
    }
}
