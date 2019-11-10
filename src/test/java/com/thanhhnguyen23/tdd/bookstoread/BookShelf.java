package com.thanhhnguyen23.tdd.bookstoread;

import java.time.Year;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

class BookShelf {
    List<Book> books = new ArrayList<>();

    List<Book> books() {
        return Collections.unmodifiableList(books);
    }

    void add(Book... bookToAdd) {
        Arrays.stream(bookToAdd).forEach(book -> books.add(book));
    }

    // DONE -- adding new method to support Comparators
    public List<Book> arrange(Comparator<Book> criteria){
        return books.stream().sorted(criteria).collect(Collectors.toList());
    }

    public List<Book> arrange() {
        return arrange(Comparator.naturalOrder());

    }

    public Map<Year, List<Book>> groupByPublicationYear() {
        return books
                .stream()
                .collect(groupingBy(book -> Year.of(book.getPublishedOn().getYear())));
    }

    //
    public <K> Map<K, List<Book>> groupBy(Function<Book, K> fx) {
        return books
                .stream()
                .collect(groupingBy(fx));
    }
}

