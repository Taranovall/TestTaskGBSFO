package com.gbsfo.test.task.util;

import com.gbsfo.test.task.entity.Entity;
import com.gbsfo.test.task.entity.Role;
import com.gbsfo.test.task.entity.User;
import com.gbsfo.test.task.exception.ObjectAccessDeniedException;
import com.gbsfo.test.task.exception.QueryNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.gbsfo.test.task.util.Constant.ENTITY_NOT_FOUND_ERROR_MESSAGE;
import static com.gbsfo.test.task.util.Constant.NO_ACCESS_ERROR_MESSAGE;

@Component
public class EntityRetriever {


    public  <T extends Entity> T retrieveEntityById(Long id, JpaRepository<T, Long> jpaRepository) {
        User currentUser = UserUtil.getCurrentUser();
        if (currentUser.getRole() == Role.USER) {
            Optional<T> optItem = jpaRepository.findById(id);
            return retrieveEntityIfPresent(id, currentUser, optItem);
        }

        return jpaRepository.findById(id).orElseThrow(() -> {
            String message = String.format(ENTITY_NOT_FOUND_ERROR_MESSAGE, id);
            return new QueryNotFoundException(message);
        });
    }

    /**
     * Retrieve an entity if it exists and is accessible by the current user.
     * @param entityId      the ID of the entity to retrieve
     * @param currentUser   the current user
     * @param optEntity     an Optional containing the entity, if it exists
     * @param <T>           the type of the entity
     * @return              the entity if it exists and is accessible by the current user
     * @throws QueryNotFoundException if the entity doesn't exist
     * @throws ObjectAccessDeniedException if the current user doesn't have access to the entity
     */
    private  <T extends Entity> T retrieveEntityIfPresent(Long entityId, User currentUser, Optional<T> optEntity) throws QueryNotFoundException, ObjectAccessDeniedException {
        if (optEntity.isPresent()) {
            return retrieveEntity(currentUser, optEntity.get());
        } else {
            String message = String.format(ENTITY_NOT_FOUND_ERROR_MESSAGE, entityId);
            throw new QueryNotFoundException(message);
        }
    }

    /**
     * Retrieve an entity if it is accessible by the current user.
     * @param currentUser   the current user
     * @param entity        the entity to retrieve
     * @param <T>           the type of the entity
     * @return              the entity if it is accessible by the current user
     * @throws ObjectAccessDeniedException if the current user doesn't have access to the entity
     */
    private <T extends Entity> T retrieveEntity(User currentUser, T entity) throws ObjectAccessDeniedException {
        if (entity.getUser().getId().equals(currentUser.getId())) {
            return entity;
        }

        String message = String.format(NO_ACCESS_ERROR_MESSAGE, entity.getId());
        throw new ObjectAccessDeniedException(message);
    }
}
