package com.clientserverarchi.bookstore.storage;

import com.clientserverarchi.bookstore.models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class DataStore {
    private static final Map<Long, Book> books = new ConcurrentHashMap<>();
    private static final Map<Long, Author> authors = new ConcurrentHashMap<>();
    private static final Map<Long, Customer> customers = new ConcurrentHashMap<>();
    private static final Map<Long, Cart> carts = new ConcurrentHashMap<>();
    private static final Map<Long, Order> orders = new ConcurrentHashMap<>();

    private static final AtomicLong bookIdSequence = new AtomicLong(1);
    private static final AtomicLong authorIdSequence = new AtomicLong(1);
    private static final AtomicLong customerIdSequence = new AtomicLong(1);
    private static final AtomicLong orderIdSequence = new AtomicLong(1);

    private DataStore() {}

    public static Book addBook(Book book) {
        book.setId(bookIdSequence.getAndIncrement());
        books.put(book.getId(), book);
        return book;
    }

    public static Book getBook(Long id) {
        return books.get(id);
    }

    public static List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    public static Book updateBook(Long id, Book book) {
        book.setId(id);
        books.put(id, book);
        return book;
    }

    public static void deleteBook(Long id) {
        books.remove(id);
    }

    public static List<Book> getBooksByAuthor(Long authorId) {
        return books.values().stream().filter(book -> authorId.equals(book.getAuthorId())).collect(Collectors.toList());
    }

    //Author Methods
    public static Author addAuthor(Author author) {
        author.setId(authorIdSequence.getAndIncrement());
        authors.put(author.getId(), author);
        return author;
    }

    public static Author getAuthor(Long id) {
        return authors.get(id);
    }

    public static List<Author> getAllAuthors() {
        return new ArrayList<>(authors.values());
    }

    public static Author updateAuthor(Long id, Author author) {
        author.setId(id);
        authors.put(id, author);
        return author;
    }

    public static void deleteAuthor(Long id) {
        authors.remove(id);
    }

    //Customer Methods
    public static Customer addCustomer(Customer customer) {
        customer.setId(customerIdSequence.getAndIncrement());
        customers.put(customer.getId(), customer);
        return customer;
    }

    public static Customer getCustomer(Long id) {
        return customers.get(id);
    }

    public static List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }

    public static Customer updateCustomer(Long id, Customer customer) {
        customer.setId(id);
        customers.put(id, customer);
        return customer;
    }

    public static void deleteCustomer(Long id) {
        customers.remove(id);
    }

    //Cart Methods
    public static Cart getOrCreateCart(Long customerId) {
        return carts.computeIfAbsent(customerId, id -> {
           Cart cart = new Cart();
           cart.setCustomerId(id);
           cart.setItems(new HashMap<>());
           return cart;
        });
    }

   public static Cart getCart(Long customerId) {
        return carts.get(customerId);
   }

   public static void deleteCart(Long customerId) {
        carts.remove(customerId);
   }

   public static void addItemToCart(Long customerId, Long bookId, int quantity) {
        Cart cart = getOrCreateCart(customerId);
        cart.getItems().merge(bookId, quantity, Integer::sum);
   }

   public static void updateCartItem(Long customerId, Long bookId, int quantity) {
        Cart cart = getOrCreateCart(customerId);
        cart.getItems().put(bookId, quantity);
   }

   public static void removeItemFromCart(Long customerId, Long bookId) {
        Cart cart = carts.get(customerId);
        if (cart != null) {
            cart.getItems().remove(bookId);
        }
   }

   //Order Methods
    public static Order createOrder(Order order) {
        order.setId(orderIdSequence.getAndIncrement());
        orders.put(order.getId(), order);
        return order;
    }

    public static Order getOrder(Long orderId) {
        return orders.get(orderId);
    }

    public static List<Order> getCustomerOrders(Long customerId) {
        return orders.values().stream()
                .filter(order -> customerId.equals(order.getCustomerId()))
                .collect(Collectors.toList());
    }

    public static List<Order> getAllOrders() {
        return new ArrayList<>(orders.values());
    }

    //Helper Methods
    public static void clearAllData() {
        books.clear();
        authors.clear();
        customers.clear();
        carts.clear();
        orders.clear();

        //Reset Counters (for testing mainly)
        bookIdSequence.set(1);
        authorIdSequence.set(1);
        customerIdSequence.set(1);
        orderIdSequence.set(1);
    }

    //Data Access Methods
    public static Map<Long, Book> getBooks() {
        return books;
    }

    public static Map<Long, Author> getAuthors() {
        return authors;
    }

    public static Map<Long, Customer> getCustomers() {
        return customers;
    }

    public static Map<Long, Cart> getCarts() {
        return carts;
    }

    public static Map<Long, Order> getOrders() {
        return orders;
    }

    public static Long getNextBookId() {
        return bookIdSequence.get();
    }

    public static Long getNextAuthorId() {
        return authorIdSequence.get();
    }

    public static Long getNextCustomerId() {
        return customerIdSequence.get();
    }

    public static Long getNextOrderId() {
        return orderIdSequence.getAndIncrement();
    }

    //Initialization Methods
    public static void initializeSampleData() {
        // Sample authors
        Author author1 = new Author();
        author1.setName("J.K. Rowling");
        author1.setBiography("British author best known for the Harry Potter series");
        addAuthor(author1);

        Author author2 = new Author();
        author2.setName("George Orwell");
        author2.setBiography("English novelist and essayist");
        addAuthor(author2);

        // Sample books
        Book book1 = new Book();
        book1.setTitle("Harry Potter and the Philosopher's Stone");
        book1.setAuthorId(author1.getId());
        book1.setIsbn("9780747532743");
        book1.setPublicationYear(1997);
        book1.setPrice(12.99);
        book1.setStockQuantity(50);
        addBook(book1);

        Book book2 = new Book();
        book2.setTitle("1984");
        book2.setAuthorId(author2.getId());
        book2.setIsbn("9780451524935");
        book2.setPublicationYear(1949);
        book2.setPrice(9.99);
        book2.setStockQuantity(30);
        addBook(book2);

        // Sample customers
        Customer customer1 = new Customer();
        customer1.setName("John Doe");
        customer1.setEmail("john@example.com");
        customer1.setPassword("password123");
        addCustomer(customer1);

        Customer customer2 = new Customer();
        customer2.setName("Jane Smith");
        customer2.setEmail("jane@example.com");
        customer2.setPassword("securepass");
        addCustomer(customer2);
    }
}
