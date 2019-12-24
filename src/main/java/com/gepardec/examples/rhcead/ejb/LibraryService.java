package com.gepardec.examples.rhcead.ejb;

import com.gepardec.examples.rhcead.dto.LibraryDto;
import com.gepardec.examples.rhcead.jpa.Library;

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
public class LibraryService {

    @PersistenceContext(unitName = "library")
    private EntityManager em;


    @TransactionAttribute(TransactionAttributeType.NEVER)
    public LibraryDto byId(final long id) {
        final Library entity = em.find(Library.class, id);
        if (entity != null) {
            return LibraryTranslator.toDto(entity);
        }
        return null;
    }

    @TransactionAttribute(TransactionAttributeType.NEVER)
    public List<LibraryDto> list() {
        return LibraryTranslator.toDto(em.createNamedQuery("listAllLibraries").getResultList());
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public LibraryDto createOrUpdate(final LibraryDto dto) {
        return dto;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean delete(final long id) {
        final Library library = em.find(Library.class, id);
        if (library == null) {
            return false;
        }
        em.remove(library);
        return true;
    }

}
