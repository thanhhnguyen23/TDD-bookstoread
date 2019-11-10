package com.thanhhnguyen23.tdd.bookstoread;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A bookshelf")
@ExtendWith(BooksParameterResolver.class)
public class BookShelfSpec {

    private BookShelf shelf;
    private Book effectiveJava;
    private Book codeComplete;
    private Book mythicalManMonth;
    private Book cleanCode;


    @BeforeEach
    // version 2
    void init(Map<String, Book> books) {
    // version 1
//    void init() throws Exception{

        shelf = new BookShelf();

        // version 2
        this.effectiveJava = books.get("Effective Java");
        this.codeComplete = books.get("Code Complete");
        this.mythicalManMonth = books.get("The Mytical Man-Month");
        this.cleanCode = books.get("Clean Code");

        // version 1 // tightly coupled test data
//        effectiveJava = new Book ("Effective Java", "Joshua Bloch", LocalDate.of(2008, Month.MAY, 8));
//        codeComplete = new Book("Code Compelte", "Steve McConnel", LocalDate.of(2004, Month.JUNE, 9));
//        mythicalManMonth = new Book ("The Mytical Man-Month", "Fredrick Phillips Brooks", LocalDate.of(1975, Month.JANUARY, 1));
//        cleanCode = new Book ("Clean Code", "Robert C. Martin", LocalDate.of(2008, Month.AUGUST, 11));
    }
    @Nested
    @DisplayName("is empty")
    class isEmpty{
        @Test
        @DisplayName("when no book is added to it")
        public void empyBookShelfWhenNoBookAdded(){

        }
        @Test
        @DisplayName("when add is called without books")
        void emptyBookShelfWhenAddIsCalledWithoutBooks(){

        }

    }
    @Nested
    @DisplayName("after adding books")
    class BooksAreAdded{
        @Test
        @DisplayName("contains two books")
        void bookshelfContainsTwoBOoksWhenTwoBooksAdded(){

        }
        @Test
        @DisplayName("returns an immutable books collection to client")
        void bookshelfIsImmutableForClient(){

        }

    }

    @Test
    @DisplayName("books inside bookshelf are grouped according to user provided criteria (group by author name)")
    void groupBooksByUserProvidedCriteria(){
        shelf.add(effectiveJava,codeComplete,mythicalManMonth,cleanCode);
        Map<String, List<Book>> booksByAuthor = shelf.groupBy(Book::getAuthor);

        assertThat(booksByAuthor)
                .containsKey("Joshua Bloch")
                .containsValues(Collections.singletonList(effectiveJava));

        assertThat(booksByAuthor)
                .containsKey("Steve McConnel")
                .containsValues(Collections.singletonList(codeComplete));

        assertThat(booksByAuthor)
                .containsKey("Fredrick Phillips Brooks")
                .containsValues(Collections.singletonList(mythicalManMonth));

        assertThat(booksByAuthor)
                .containsKey("Robert C. Martin")
                .containsValues(Collections.singletonList(cleanCode));
    }

    @Test
    @DisplayName("bookshelf is grouped by publication year")
    void groupBooksInsideBookShelfByPublicationYear(){
        shelf.add(effectiveJava,codeComplete,mythicalManMonth,cleanCode);

        Map<Year,List<Book>> booksByPublicationYear = shelf.groupByPublicationYear();

        assertThat(booksByPublicationYear)
                .containsKey(Year.of(2008))
                .containsValues(Arrays.asList(effectiveJava, cleanCode));

        assertThat(booksByPublicationYear)
                .containsKey(Year.of(2004))
                .containsValues(Collections.singletonList(codeComplete));

        assertThat(booksByPublicationYear)
                .containsKey(Year.of(1975))
                .containsValues(Collections.singletonList(mythicalManMonth));
    }

    @Test
    @DisplayName("bookshelf is arranged lexigraphically by book title")
    void bookshelfArrangedByBookTitle() { // version 3
        shelf.add(effectiveJava,codeComplete,mythicalManMonth);
        List<Book> books = shelf.arrange();

        assertEquals(Arrays.asList(codeComplete,effectiveJava,mythicalManMonth),
                books,
                () -> "Books in a bookshelf should be arranged lexigraphically by book title");
    }

    @Test
    @DisplayName("bookshelf is arranged by user provided criteria (by book title lexigraphically)")
    void bookshelfArrangedByUserProvidedCriteria(){
        shelf.add(effectiveJava, codeComplete, mythicalManMonth);

        // version 2
        Comparator<Book> reversed = Comparator.<Book>naturalOrder().reversed();
        List<Book> books = shelf.arrange(reversed);
        assertThat(books).isSortedAccordingTo(reversed);

        // version 1
//        List<Book> books = shelf.arrange(Comparator.<Book>naturalOrder().reversed());
//        assertEquals(Arrays.asList(mythicalManMonth, effectiveJava, codeComplete),
//                books,
//                () -> "Books in a bookshelf are arranged in descending order of book title");
    }

    @Test
    @DisplayName("bookshelf is in insertion order")
    void booksInBookShelfAreInInsertionOrderAfterCallingArrange() {
        shelf.add(effectiveJava, codeComplete, mythicalManMonth);

        shelf.arrange();
        List<Book> books = shelf.books();
        assertEquals(Arrays.asList(effectiveJava, codeComplete, mythicalManMonth), books, () -> "Books in bookshelf are in insertion order");
    }


    @Test
    @DisplayName("bookshelf returns an immutable books collection to client")
    void booksReturnedFromBookShelfIsImmutable(){
        shelf.add(effectiveJava, codeComplete);

       List<Book> books = shelf.books();
       try{
           books.add(new Book("Head First C#","Jennifer Greene", LocalDate.of(2013, Month.SEPTEMBER, 16)));
           fail(() -> "Should not be able to add book to books");

       } catch (Exception e){
           assertTrue(e instanceof UnsupportedOperationException, () -> "Should throw UnsupportedOperationException.");
        }

    }

    @Test
    @DisplayName("bookshelf is empty when no book is added")
    void emptyBookShelfWhenAddIsCalledWithoutBooks(){
        shelf.add();
        List<Book> books = shelf.books();
        assertTrue(books.isEmpty(), () -> "BookShelf should be empty");
    }

    @Test
    @DisplayName("bookshelf contains two books when two books are added")
    void bookshelfContainsTwoBooksWhenTwoBooksAdded(){

        shelf.add(effectiveJava, codeComplete);

        List<Book> books = shelf.books();
        assertEquals(2, books.size(), () -> "BookShelf should have two books");
    }

    @Test
    @DisplayName("bookshelf is empty when no book is added")
    void shelfEmptyWhenNoBookAdded() {
        List<Book> books = shelf.books();
        assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
    }
}
