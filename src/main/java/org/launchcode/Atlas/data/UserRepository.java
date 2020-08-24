package org.launchcode.Atlas.data;

import org.launchcode.Atlas.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    public User findByuserName(String userName);
}
