package FurnitureRenovate.control;

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

public class SortManager {
    public void AddSort(String name , String describe) throws BaseException{
        if(name.equals("")) throw new BaseException("类型名不能为空");
        try{
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();
            String hql = "select count(*) from BeanSort where sortName = ?";
            Query query = session.createQuery(hql);
            query.setString(0,name);
            if(query.uniqueResult() != null){
                if((long)query.uniqueResult() > (long)0) {
                    throw new BaseException("已经存在该类型");
                }
            }

            BeanSort sort = new BeanSort();
            sort.setSortName(name);
            sort.setSortDescribe(describe);

            session.save(sort);
            tx.commit();
            session.close();
        }catch(HibernateException e){
            e.printStackTrace();
            throw  new BaseException("数据库操作异常");
        }
    }

    public List<BeanSort> LoadAll() throws BaseException {
        List<BeanSort> result=new ArrayList<BeanSort>();

        try {
            Session session = HibernateUtil.getSession();
            String hql = "from BeanSort ";
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

    public void ChangeSort(BeanSort sort,String name,String describe) throws BaseException {
        if(name.equals("")) throw new BaseException("类型名不能为空");
        try{
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();

            String hql = "select count(*) from BeanSort where sortName = ?";
            Query query = session.createQuery(hql);
            query.setString(0,name);
            if(query.uniqueResult() != null){
                if((long)query.uniqueResult() > (long)0) {
                    //throw new BaseException("已经存在该类型");
                }
            }


            BeanSort s = (BeanSort)session.get(BeanSort.class,sort.getSortId());

            s.setSortName(name);
            s.setSortDescribe(describe);

            tx.commit();
            session.close();
        }catch (HibernateException e){
            e.printStackTrace();
            throw new BaseException("数据库操作异常");
        }
    }

    public void DeleteSort(BeanSort sort) throws BaseException{
        try{
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();

            String hql = "select count(*) from BeanMaterial where sortId = ?";
            Query query = session.createQuery(hql);
            query.setInteger(0,sort.getSortId());
            if(query.uniqueResult() != null){
                if((long)query.uniqueResult() > (long)0) {
                    throw new BaseException("该类型下有材料，无法删除");
                }
            }

            BeanSort s = (BeanSort)session.get(BeanSort.class,sort.getSortId());
            session.delete(s);
            tx.commit();

            session.close();
        }catch (HibernateException e){
            e.printStackTrace();
            throw new BaseException("数据库操作异常");
        }
    }

    public List<BeanSort> search(String keyword) throws BaseException{
        List<BeanSort> sorts = new ArrayList<BeanSort>();
        try{
            Session session = HibernateUtil.getSession();

            String hql = "from BeanSort where sortName like ?";
            Query query = session.createQuery(hql);
            query.setString(0,"%"+keyword+"%");
            sorts = query.list();
            session.close();
        }catch (HibernateException e){
            e.printStackTrace();
            throw new BaseException("数据库操作异常");
        }
        return sorts;
    }

    public static void main(String[] args) throws BaseException{
        SortManager sortManager = new SortManager();
        sortManager.AddSort("sony","null");
    }
}
