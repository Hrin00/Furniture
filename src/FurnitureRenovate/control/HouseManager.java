package FurnitureRenovate.control;

import FurnitureRenovate.model.*;
import FurnitureRenovate.util.BaseException;
import FurnitureRenovate.util.DbException;
import FurnitureRenovate.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class HouseManager {
    public BeanHouse AddHouse(BeanHouse house) throws BaseException {
        if (house.getHouseName().equals("")) throw new BaseException("房屋名不能为空");
        try {
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();
            String hql = "select count(*) from BeanHouse where houseName = ?  and userId = ?";
            Query query = session.createQuery(hql);
            query.setString(0, house.getHouseName());
            query.setString(1, house.getUserId());
            if (query.uniqueResult() != null) {
                if ((long) query.uniqueResult() > (long) 0) {
                    throw new BaseException("已经存在该名字");
                }
            }

            session.save(house);

            tx.commit();

            hql = "select houseId from BeanHouse where userId = ? and houseName = ?";
            query = session.createQuery(hql);
            query.setString(0,BeanUser.currentLoginUser.getUserId());
            query.setString(1,house.getHouseName());
            int houseid = (int)query.uniqueResult();
            house.setHouseId(houseid);
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new BaseException("数据库操作异常");
        }
        return house;
    }

    public List<BeanHouse> LoadAll(String userId) throws BaseException {
        List<BeanHouse> result = new ArrayList<BeanHouse>();

        try {
            Session session = HibernateUtil.getSession();
            String hql = "from BeanHouse where userId = ?";
            Query query = session.createQuery(hql);
            query.setString(0, userId);
            result = query.list();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new BaseException("数据库操作异常");
        }

        return result;
    }


/*////////////////////////////////////////////////
{




        //改写删除  直接包括外见键一起删除
    给出对话框提示  删除会删除有关的东西





}

*//////////////////////////////////////////////


    public void DeleteHouse(BeanHouse house) throws BaseException{
        try {
            List<BeanRoom> rooms = new ArrayList<BeanRoom>();
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();
            String hql = "from BeanRoom where houseId = ?";
            Query query = session.createQuery(hql);
            query.setInteger(0,house.getHouseId());
            rooms = query.list();
            for(int i = 0; i < rooms.size(); i++){
                String h = "from BeanFurniture where roomId = ?";
                Query q = session.createQuery(h);
                q.setInteger(0,rooms.get(i).getRoomId());
                List<BeanFurniture> furnitures = new ArrayList<BeanFurniture>();
                furnitures = q.list();
                for(int j = 0; j < furnitures.size(); j++){
                    BeanFurniture furniture = (BeanFurniture)session.get(BeanFurniture.class,furnitures.get(j).getFurnitureId());
                    session.delete(furniture);
                }
                BeanRoom room = (BeanRoom)session.get(BeanRoom.class,rooms.get(i).getRoomId());
                session.delete(room);
            }

            BeanHouse h = (BeanHouse)session.get(BeanHouse.class,house.getHouseId());

            session.delete(h);

            tx.commit();
            session.close();
        }catch (HibernateException e) {
            e.printStackTrace();
            throw new BaseException("数据库操作异常");
        }
    }
    public int CountBudget(BeanHouse house) throws BaseException{
        int houseBudget = 0;
        try{
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();

            List<BeanRoom> rooms = new ArrayList<BeanRoom>();

            rooms = (new RoomManager()).LoadALL(house.getHouseId());

            for (int i = 0; i < rooms.size(); i++){
                houseBudget += (new RoomManager()).CountBudget(rooms.get(i));
            }

            session.close();
        }catch(HibernateException e){
            e.printStackTrace();
            throw  new BaseException("数据库操作异常");
        }
        return houseBudget;

    }

















}
