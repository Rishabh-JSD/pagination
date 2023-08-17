package com.P.pagination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DaoImpl implements DaoClass {

    @PersistenceContext
    private EntityManager em;

    private static final Logger logger = LoggerFactory.getLogger(DaoImpl.class);

    @Override
    public EntityClass add(EntityClass ec) {
        em.persist(ec);
        logger.info("System Profile has been added successfully, System Profile details=" + ec);
        return ec;
    }

    @Override
    public EntityClass update(EntityClass ec) {
        em.merge(ec);
        logger.info("System Profile has been updated successfully, System Profile details=" + ec);
        return ec;
    }

    public String delete(int id) {
        EntityClass entity = em.find(EntityClass.class, id);
        if (entity != null) {
            em.remove(entity);
            logger.info("System Profile has been deleted successfully, System Profile details=" + entity);
        }
        return "Entity with ID " + id + " deleted successfully";
    }

    @Override
    public List<EntityClass> get() {
     
        return null;
    }

    @Override
    public EntityClass getById(int id) {
        return em.find(EntityClass.class, id);
    }

    @Override
    public Page<EntityClass> getAllPaginated(Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<EntityClass> cq = cb.createQuery(EntityClass.class);
        Root<EntityClass> root = cq.from(EntityClass.class);
        
        // Apply sorting if needed
        if (pageable.getSort() != null && pageable.getSort().isSorted()) {
            List<Order> orders = new ArrayList<>();
            for (Sort.Order order : pageable.getSort()) {
                if (order.isAscending()) {
                    orders.add(cb.asc(root.get(order.getProperty())));
                } else {
                    orders.add(cb.desc(root.get(order.getProperty())));
                }
            }
            cq.orderBy(orders);
        }
        
        // Create the query with criteria and sorting
        cq.select(root);
        
        TypedQuery<EntityClass> query = em.createQuery(cq);
        
        // Apply pagination
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        
        List<EntityClass> content = query.getResultList();
        
        // Count total records
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(countQuery.from(EntityClass.class)));
        Long totalElements = em.createQuery(countQuery).getSingleResult();
        
        return new PageImpl<>(content, pageable, totalElements);
    }

}
