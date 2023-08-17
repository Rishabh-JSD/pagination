package com.P.pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
public class ControllerClass {

    private final ServiceClass ds;

    @Autowired
    public ControllerClass(ServiceClass ds) {
        this.ds = ds;
    }

    @PostMapping("/add")
    public EntityClass addEntity(@RequestBody EntityClass entity) {
        return ds.save(entity);
    }

    @GetMapping("/get")
    public List<EntityClass> findAllEntities() {
        return ds.get();
    }

    @GetMapping("/getp")
    public Page<EntityClass> findAllEntitiesPaginated(Pageable pageable) {
        return ds.getPaginated(pageable);
    }

    @PutMapping("/update/{id}")
    public EntityClass update(@RequestBody EntityClass entity, @PathVariable Optional<Integer> id) {
        if (id.isPresent()) {
            return ds.update(entity, id.get());
        } else {
            return null;
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEntity(@PathVariable int id) {
        return ds.delete(id);
    }
}
