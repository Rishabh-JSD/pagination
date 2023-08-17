package com.P.pagination;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface DaoClass {

    EntityClass add(EntityClass ec);

    EntityClass update(EntityClass ec);

    String delete(int id);

    List<EntityClass> get();

    EntityClass getById(int id);

    // Add a new method for getting paginated results
    Page<EntityClass> getAllPaginated(Pageable pageable);
}

