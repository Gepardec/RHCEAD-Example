package com.gepardec.examples.rhcead.ejb;

import com.gepardec.examples.rhcead.dto.BookDto;
import com.gepardec.examples.rhcead.jpa.Book;
import com.gepardec.examples.rhcead.jpa.Book_;
import com.gepardec.examples.rhcead.jpa.Library;
import com.gepardec.examples.rhcead.jpa.Library_;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 12/24/2019
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class BookService {

    @PersistenceContext(unitName = "library")
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public BookDto byId(final long id) {
        final Book entity = em.find(Book.class, id);
        if (entity != null) {
            return BookTranslator.toDto(entity);
        }
        return null;
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<BookDto> searchByName(final String name) {
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<Book> query = builder.createQuery(Book.class);
        final Root<Book> root = query.from(Book.class);
        query.where(builder.equal(root.get(Book_.NAME), name));
        return BookTranslator.toDto(em.createQuery(query).getResultList());
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<BookDto> searchByLibraryId(final long id) {
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<Book> query = builder.createQuery(Book.class);
        final Root<Book> root = query.from(Book.class);
        var libraryJoin = root.join(Book_.LIBRARIES, JoinType.INNER);
        libraryJoin.on(builder.equal(libraryJoin.get(Library_.ID), id));
        return BookTranslator.toDto(em.createQuery(query).getResultList());
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<BookDto> list() {
        return BookTranslator.toDto(em.createNamedQuery("listAllBooks").getResultList());
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<BookDto> searchBookByLibraryNameAndUserName(final String libraryName, final String name) {
        return BookTranslator.toDto(em.createNamedQuery("searchBookByUsername")
                .setParameter("libraryName", libraryName)
                .setParameter("username", name).getResultList());
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public BookDto createOrUpdate(final BookDto bookDto) {
        Book book;
        if (bookDto.getId() == null) {
            book = new Book();
        } else {
            book = em.find(Book.class, bookDto.getId());
        }
        final Library library = Optional.ofNullable(em.find(Library.class, bookDto.getLibraryId()))
                .orElseThrow(() -> new EntityNotFoundException("LibraryId not found in database"));

        if (book == null) {
            return null;
        }

        book.setName(bookDto.getName());
        book = em.merge(book);

        return BookTranslator.toDto(book);
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
