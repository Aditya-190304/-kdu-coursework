package com.libraryweb;

import com.librarydomain.Book;
import com.librarydomain.User;
import com.librarydomain.repo.BookRepository;
import com.librarydomain.repo.UserRepository;
import com.libraryservice.LoanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
class ConcurrencyTest {

    @Autowired
    private LoanService loanService;
    @Autowired
    private BookRepository bookRepo;
    @Autowired
    private UserRepository userRepo;

    @Test
    void testOptimisticLockingOnBorrow() throws InterruptedException {
        // 1. Setup: Create a book and a user
        User user = new User();
        user.setUsername("racer");
        // i need to set a password even for tests because the entity requires it
        user.setPassword("pass");
        user.setRole(User.Role.MEMBER);
        userRepo.save(user);

        Book book = new Book();
        book.setTitle("Race Condition Book");
        book.setStatus(Book.BookStatus.AVAILABLE);
        bookRepo.save(book);

        // 2. Execution: Try to borrow the same book twice simultaneously
        // i use threads here to simulate two users hitting the API at the exact same millisecond
        int numberOfThreads = 2;
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        for (int i = 0; i < numberOfThreads; i++) {
            service.execute(() -> {
                try {
                    loanService.borrowBook(book.getId(), "racer");
                    successCount.incrementAndGet();
                } catch (ObjectOptimisticLockingFailureException e) {
                    // i expect exactly one thread to end up here due to the @Version check
                    failCount.incrementAndGet();
                } catch (Exception e) {
                    // ignore other errors
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        // 3. Assertion
        // i did this to ensure only ONE borrow succeeded and the other was blocked
        assertEquals(1, successCount.get(), "Only one person should successfully borrow");
        assertEquals(1, failCount.get(), "The other person should get a concurrency error");
    }
}