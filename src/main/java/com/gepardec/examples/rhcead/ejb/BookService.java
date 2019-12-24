package com.gepardec.examples.rhcead.ejb;

import com.gepardec.examples.rhcead.dto.BookDto;
import com.gepardec.examples.rhcead.jpa.Book;

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
public class BookService {

    @PersistenceContext(unitName = "library")
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.NEVER)
    public BookDto byId(final long id) {
        final Book entity = em.find(Book.class, id);
        if (entity != null) {
            return BookTranslator.toDto(entity);
        }
        return null;
    }

    @TransactionAttribute(TransactionAttributeType.NEVER)
    public List<BookDto> list() {
        return BookTranslator.toDto(em.createNamedQuery("listAllBooks").getResultList());
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public BookDto createOrUpdate(final BookDto bookDto) {
        return null;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean delete(final long id) {
        final Book book = em.find(Book.class, id);
        if (book == null) {
            return false;
        }
        em.remove(book);
        return true;
    }
}
