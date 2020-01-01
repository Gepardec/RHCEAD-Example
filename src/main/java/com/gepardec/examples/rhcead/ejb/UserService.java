package com.gepardec.examples.rhcead.ejb;

import com.gepardec.examples.rhcead.dto.RoleDto;
import com.gepardec.examples.rhcead.dto.UserDto;
import com.gepardec.examples.rhcead.jpa.Role;
import com.gepardec.examples.rhcead.jpa.User;

import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/24/2019
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@RolesAllowed("ADMIN")
public class UserService {

    @PersistenceContext(unitName = "library")
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public UserDto byId(final long id) {
        final User entity = em.find(User.class, id);
        if (entity != null) {
            return UserTranslator.toDto(entity);
        }
        return null;
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<UserDto> list() {
        return UserTranslator.toDtos(em.createNamedQuery("listAllUsers").getResultList());
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public UserDto createOrUpdate(final UserDto userDto) {
        User user;
        if (userDto.getId() == null) {
            user = new User();
        } else {
            user = em.find(User.class, userDto.getId());
        }

        if (user == null) {
            return null;
        }

        user.setUsername(userDto.getUsername());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        if (userDto.getRoles() != null) {
            user.getRoles().addAll(RoleDtoTranslator.toRoles(userDto.getRoles()));
        }
        user.getRoles().add(Role.USER);

        user = em.merge(user);

        return UserTranslator.toDto(user);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean delete(final long id) {
        final User user = em.find(User.class, id);
        if (user == null) {
            return false;
        }
        em.remove(user);
        return true;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public UserDto assignRoles(final long id, final List<RoleDto> roleDtos) {
        User user = em.find(User.class, id);
        if (user == null) {
            return null;
        }

        final List<Role> roles = RoleDtoTranslator.toRoles(roleDtos);
        user.getRoles().clear();
        user.getRoles().addAll(roles);
        user = em.merge(user);

        return UserTranslator.toDto(user);
    }
}
