package FurnitureRenovate.control;

import FurnitureRenovate.model.BeanFurniture;
import FurnitureRenovate.model.BeanRoom;
import FurnitureRenovate.util.BaseException;
import FurnitureRenovate.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;

public class RoomManager {
    //室
    public void AddShi(int houseId, int index) throws BaseException{
        try{
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();

            BeanRoom room  = new BeanRoom();

            room.setHouseId(houseId);
            room.setArea(20);
            room.setRoomName("卧室0"+Integer.toString(index));

            session.save(room);

            tx.commit();
            session.close();
        }catch(HibernateException e){
            e.printStackTrace();
            throw  new BaseException("数据库操作异常");
        }
    }
    //厅
    public void AddTing(int houseId, int index) throws BaseException{
        try{
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();

            BeanRoom room  = new BeanRoom();

            room.setHouseId(houseId);
            room.setArea(40);
            room.setRoomName("客厅0"+Integer.toString(index));

            session.save(room);

            tx.commit();
            session.close();
        }catch(HibernateException e){
            e.printStackTrace();
            throw  new BaseException("数据库操作异常");
        }
    }
    //卫
    public void AddWei(int houseId, int index) throws BaseException{
        try{
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();

            BeanRoom room  = new BeanRoom();

            room.setHouseId(houseId);
            room.setArea(10);
            room.setRoomName("浴室0"+Integer.toString(index));

            session.save(room);

            tx.commit();
            session.close();
        }catch(HibernateException e){
            e.printStackTrace();
            throw  new BaseException("数据库操作异常");
        }
    }
    //厨
    public void AddChu(int houseId, int index) throws BaseException{
        try {
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();

            BeanRoom room  = new BeanRoom();

            room.setHouseId(houseId);
            room.setArea(10);
            room.setRoomName("厨房0"+Integer.toString(index));

            session.save(room);

            tx.commit();
            session.close();
        }catch(HibernateException e){
            e.printStackTrace();
            throw  new BaseException("数据库操作异常");
        }
    }
    public List<BeanRoom> LoadALL(int houseId) throws BaseException{
        List<BeanRoom> rooms = new ArrayList<BeanRoom>();
        try {
            Session session = HibernateUtil.getSession();
            String hql = "from BeanRoom where houseId = ?";
            Query query = session.createQuery(hql);
            query.setInteger(0,houseId);
            rooms = query.list();
            session.close();
        }catch(HibernateException e){
            e.printStackTrace();
            throw  new BaseException("数据库操作异常");
        }
        return rooms;

    }
    public void DeleteRoom(BeanRoom room) throws BaseException{
        try{
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();

            String hql = "select count(*) from BeanFurniture where roomId = ?";
            Query query = session.createQuery(hql);
            query.setInteger(0,room.getRoomId());
            if(query.uniqueResult() != null) if((long)query.uniqueResult() > (long)0) throw new BaseException("房间下有装修方案，无法删除");

            BeanRoom r = (BeanRoom)session.get(BeanRoom.class,room.getRoomId());
            session.delete(r);

            tx.commit();
            session.close();
        }catch(HibernateException e){
            e.printStackTrace();
            throw  new BaseException("数据库操作异常");
        }

    }

    public int CountBudget(BeanRoom room) throws BaseException{
        int roomBudget = 0;
        try{
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();

            List<BeanFurniture> furnitures = new ArrayList<BeanFurniture>();
            furnitures = (new FurnitureManager()).LoadAll(room.getRoomId());

            for(int i = 0; i < furnitures.size(); i++){
                roomBudget += furnitures.get(i).getSumBudget();
            }
            session.close();
        }catch(HibernateException e){
            e.printStackTrace();
            throw  new BaseException("数据库操作异常");
        }
        return roomBudget;

    }

}
