package FurnitureRenovate.util;

import FurnitureRenovate.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class HibernateUtil {
    private static SessionFactory sessionFactory
            = new Configuration().configure().buildSessionFactory();
    public static Session getSession(){
        Session session = sessionFactory.openSession();
        return session;
    }
    public static void main(String[] args){
        Session session=getSession();
        List<BeanUser> users = session.createQuery("from BeanUser ").list();
        List<BeanBrand> brands = session.createQuery("from BeanBrand ").list();
        List<BeanHouse> houses = session.createQuery("from BeanHouse ").list();
        for(BeanHouse h:houses){
            System.out.println(h.getHouseId());
        }
        for(BeanUser u:users){
            System.out.println(u.getUserId());
        }
        for(BeanBrand b:brands){
            System.out.println(b.getBrandId());
        }
        List<BeanFurniture> furnitures = session.createQuery("from BeanFurniture ").list();
        for(BeanFurniture f:furnitures){
            System.out.println(f.getFurnitureId());
        }
        List<BeanMaterial> materials = session.createQuery("from BeanMaterial ").list();
        for(BeanMaterial m:materials){
            System.out.println(m.getMaterialId());
        }

        List<BeanMaterialService> materialServices = session.createQuery("from BeanMaterialService ").list();
        for(BeanMaterialService m:materialServices){
            System.out.println(m.getMaterialServiceId());
        }
        List<BeanRoom> rooms = session.createQuery("from BeanRoom ").list();
        for(BeanRoom r:rooms){
            System.out.println(r.getRoomId());
        }
        List<BeanService> services = session.createQuery("from BeanService ").list();
        for(BeanService r:services){
            System.out.println(r.getServiceId());
        }

        List<BeanSort> sorts = session.createQuery("from BeanSort ").list();
        for(BeanSort r:sorts){
            System.out.println(r.getSortId());
        }

        session.close();

    }
}
