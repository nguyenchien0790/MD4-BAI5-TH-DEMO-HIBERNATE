package rikkei.academy.service;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rikkei.academy.model.Customer;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
@Transactional
public class CustomerServiceIMPL implements ICustomerService{
    public static SessionFactory sessionFactory;
    public static EntityManager entityManager;
    static {
        System.out.println("test ket noi");
        try {
            sessionFactory = new Configuration().configure("hibernate.conf.xml").buildSessionFactory();
            entityManager =sessionFactory.createEntityManager();
            System.out.println("ket noi thanh cong");

        }catch (HibernateException e){
            System.out.println("ket noi that bai");
            e.printStackTrace();
        }

    }

    @Override
    public List<Customer> findAll() {
        String queryStr = "SELECT c FROM Customer AS c";
        TypedQuery<Customer> query = entityManager.createQuery(queryStr,Customer.class);
        return query.getResultList();
    }

    @Override
    public Customer findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void save(Customer customer) {
        Session session = null;
        Transaction transaction= null;
        try {
            session =sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(customer);
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            if (transaction != null){
                transaction.isActive();
            }
        }finally {
            if (session != null){
                session.close();
            }
        }

    }
}
