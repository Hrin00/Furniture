package FurnitureRenovate.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import FurnitureRenovate.model.*;
import FurnitureRenovate.util.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;

public class UserManager {

    public BeanUser reg(String userId, String userName, String userPwd, String userPwd2) throws BaseException {
        if (userPwd == null || "".equals(userPwd)) throw new BaseException("密码不能为空");
        if (!userPwd.equals(userPwd2)) throw new BaseException("两次输入的密码要一致");
        if (userId == null || "".equals(userId)) throw new BaseException("用户名不能为空");
        if (userName == null || "".equals(userName)) throw new BaseException("姓名不能为空");
        for(int i = 0; i < userId.length(); i++ ){
            if(userId.charAt(i) == ' ')
                throw new BaseException("账号中不能含有非法字符");
        }

        try {
            Session session = HibernateUtil.getSession();
            BeanUser user = (BeanUser) session.get(BeanUser.class, userId);
            if (user != null)
                throw new BaseException("已经存在该用户");

            BeanUser u = new BeanUser();
            u.setUserId(userId);
            u.setUserName(userName);
            u.setUserPwd(userPwd);
            u.setUserRank(1);

            Transaction tx = session.beginTransaction();
            session.save(u);
            tx.commit();
            session.close();
            return u;
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new BaseException("数据库操作异常");
        } finally {

        }

    }

    public BeanUser login(String userId, String pwd) throws BaseException {

        try {
            userId = userId.trim();
            Session session = HibernateUtil.getSession();
            BeanUser user = (BeanUser) session.get(BeanUser.class, userId);
            if (user == null) throw new BaseException("用户不存在");
            BeanUser u = new BeanUser();
            u.setUserId(userId);
            u.setUserPwd(user.getUserPwd());
            if (!u.getUserPwd().equals(pwd)) {
                throw new BaseException("密码错误");
            }

            u.setUserName(user.getUserName());
            u.setUserRank(user.getUserRank());
            session.close();
            return u;
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new BaseException("数据库操作异常");
        } finally {

        }
    }

    public void changePwd(BeanUser user, String oldPwd, String newPwd,
                          String newPwd2) throws BaseException {
        if (newPwd == null || "".equals(newPwd)) throw new BaseException("密码不能为空");
        if (!newPwd.equals(newPwd2)) throw new BaseException("两次输入的密码要一致");

        try {
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();
            BeanUser user1 = (BeanUser) session.get(BeanUser.class, user.getUserId());
            if (user1 == null) throw new BaseException("用户不存在");
            if (!user1.getUserPwd().equals(oldPwd)) {
                throw new BaseException("原密码错误");
            }
            user1.setUserPwd(newPwd);

            tx.commit();
            session.close();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            throw new BaseException("数据库操作异常");
        } finally {

        }

    }

    public List<BeanUser> LoadAll() throws BaseException {
        List<BeanUser> result = new ArrayList<BeanUser>();

        try {
            Session session = HibernateUtil.getSession();
            String hql = "from BeanUser ";
            Query query = session.createQuery(hql);
            result = query.list();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new DbException(e);
        }
        return result;
    }

    public void resetPwd(BeanUser user) throws BaseException {
        try {
            Session session = HibernateUtil.getSession();
            BeanUser user1 = (BeanUser) session.get(BeanUser.class, user.getUserId());
            if (user1 == null) throw new BaseException("请选择用户");
            user1.setUserPwd("123456");
            Transaction tx = session.beginTransaction();
            session.save(user1);
            tx.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new BaseException("数据库操作异常");
        }
    }

    public void deleteUser(BeanUser user) throws BaseException {
        if (user.getUserRank() == 3) throw new BaseException("无法删除超级管理员");
        if (user.getUserRank() == 2) throw new BaseException("无法删除管理员");
        try {
            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();
            String hql = "from BeanHouse where userId = ?";
            Query query = session.createQuery(hql);
            query.setString(0, user.getUserId());
            if (query.list() != null) throw new BaseException("用户下存在房屋，无法删除");

            BeanUser u = (BeanUser) session.get(BeanUser.class, user.getUserId());

            session.delete(u);

            tx.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new BaseException("数据库操作异常");
        }
    }

    public List<BeanUser> search(String keyword) throws BaseException{
        List<BeanUser> users = new ArrayList<BeanUser>();
        try{
            Session session = HibernateUtil.getSession();

            String hql = "from BeanUser where userId like ? or userName like ?";
            Query query = session.createQuery(hql);
            query.setString(0,"%"+keyword+"%");
            query.setString(1,"%"+keyword+"%");
            users = query.list();
            session.close();
        }catch (HibernateException e){
            e.printStackTrace();
            throw new BaseException("数据库操作异常");
        }
        return users;
    }

    public List<BeanMaterial> SearchMaterial(BeanUser user) throws BaseException, SQLException {
        Connection conn = null;
        List<BeanMaterial> materials = new ArrayList<BeanMaterial>();
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM Renovate.Material WHERE material_id IN ( SELECT material_id FROM Material_Service WHERE material_service_id IN ( SELECT material_service_id FROM Furniture WHERE room_id IN ( SELECT room_id FROM Room WHERE house_id IN ( SELECT house_id FROM House WHERE user_id LIKE ?))))";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user.getUserId());
            java.sql.ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                BeanMaterial material = new BeanMaterial();
                material.setMaterialId(resultSet.getInt(1));
                material.setBrandId(resultSet.getInt(2));

                String s = "SELECT brand_name FROM Renovate.Brand WHERE brand_id = ?";
                java.sql.PreparedStatement p = conn.prepareStatement(s);
                p.setInt(1,material.getBrandId());
                java.sql.ResultSet r = p.executeQuery();
                if(r.next())
                    material.setBrandName(resultSet.getString(1));

                material.setSortId(resultSet.getInt(3));

                s = "SELECT sort_name FROM Renovate.Sort WHERE sort_id = ?";
                p = conn.prepareStatement(s);
                p.setInt(1,material.getSortId());
                r = p.executeQuery();
                if(r.next())
                    material.setSortName(resultSet.getString(1));

                material.setMaterialName(resultSet.getString(4));
                material.setSpecification(resultSet.getString(5));
                material.setModel(resultSet.getString(6));
                material.setColor(resultSet.getString(7));
                material.setMaterialUnitPrice(resultSet.getInt(8));
                material.setMaterialUnit(resultSet.getString(9));
                materials.add(material);
            }
            pst.close();
            resultSet.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new BaseException("数据库操作异常");
        }
        return materials;
    }

    public List<BeanService> SearchService(BeanUser user) throws BaseException, SQLException {
        Connection conn = null;
        List<BeanService> services = new ArrayList<BeanService>();
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM Service WHERE service_id IN ( SELECT material_id FROM Material_Service WHERE material_service_id IN ( SELECT material_service_id FROM Furniture WHERE room_id IN ( SELECT room_id FROM Room WHERE house_id IN ( SELECT house_id FROM House WHERE user_id LIKE ?))))";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user.getUserId());
            java.sql.ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                BeanService service = new BeanService();
                service.setServiceId(resultSet.getInt(1));
                service.setServiceName(resultSet.getString(2));
                service.setServiceRank(resultSet.getInt(3));
                service.setServiceUnitPrice(resultSet.getInt(4));
                service.setServiceUnit(resultSet.getString(5));
                service.setHour(resultSet.getInt(6));
                services.add(service);
            }


            pst.close();
            resultSet.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new BaseException("数据库操作异常");
        }
        return services;
    }

}