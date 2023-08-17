package com.P.pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServiceClass {
    private final DaoClass custRepo;

    @Autowired
    public ServiceClass(DaoClass custRepo) {
        this.custRepo = custRepo;
    }

    public EntityClass save(EntityClass entity) {
        return custRepo.add(entity);
    }

    public List<EntityClass> get() {
        return custRepo.get();
    }

    public Page<EntityClass> getPaginated(Pageable pageable) {
        return custRepo.getAllPaginated(pageable);
    }

    public EntityClass update(EntityClass updatedEntity, Integer id) {
        EntityClass existingEntity = custRepo.getById(id);
        if (existingEntity != null) {
            existingEntity.setTask_name(updatedEntity.getTask_name());
            existingEntity.setTask_description(updatedEntity.getTask_description());
            existingEntity.setTask_status(updatedEntity.getTask_status());
            return custRepo.update(existingEntity);
        } else {
            // Handle the case where the entity with the given ID doesn't exist
            return null;
        }
    }

    public String delete(int id) {
        return custRepo.delete(id);
    }
}
