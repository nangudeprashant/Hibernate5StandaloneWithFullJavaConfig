package com.javalive.main;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.javalive.databaseutil.HibernateUtil;

public class OperationsOnTheBasisOfORM {
    public static void main(String[] args) {
    	com.javalive.entity.Student student1 = new com.javalive.entity.Student("Name73", "Address73");
    	com.javalive.entity.Student student2 = new com.javalive.entity.Student("Name74", "Address74");
        Transaction transaction = null;
        System.out.println("Table contents before starting any operation:");
        new OperationsOnTheBasisOfORM().getStudentList();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            System.out.println("\n\n");
            // save the student objects
            session.save(student1);
            session.save(student2);
            // commit transaction
            transaction.commit();
            System.out.println("Displaying student list after inserting new entires");
            new OperationsOnTheBasisOfORM().getStudentList();
            // update the student objects
            student1.setAddress("NewName73");
            student1.setName("NewName73");
            student2.setAddress("NewName74");
            student2.setName("NewName74");
            transaction = session.beginTransaction();
            System.out.println("\n\n");
            session.update(student1);
            session.update(student2);
            // commit transaction
            transaction.commit();
            System.out.println("Displaying student list after updating the entires");
            new OperationsOnTheBasisOfORM().getStudentList();
            // delete the student objects
            transaction = session.beginTransaction();
            System.out.println("\n\n");
            session.delete(student1);
            session.delete(student2);
            // commit transaction
            transaction.commit();
            System.out.println("Displaying student list after deleting the entires");
            new OperationsOnTheBasisOfORM().getStudentList();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        finally {
        	System.out.println("In the finally block.");
        	HibernateUtil.shutdown();//Closing all open resources.
        }
    }
    public void getStudentList() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List < com.javalive.entity.Student > students = session.createQuery("from Student").list();
			for(com.javalive.entity.Student s:students){
				System.out.println(s.toString());
			};
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}