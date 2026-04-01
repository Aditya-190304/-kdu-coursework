import type { Book } from "../../book-library-manager/src/models/Book";
import { BookCard } from "../BookCard/BookCard";
import styles from "./styles.module.scss";

export const BookList = ({ books }: { books: Book[] }) => {
  
  if (books.length === 0) {
    return (
      <div className={styles.emptyState}>
        no books found matching your request.
      </div>
    );
  }

  return (
    <div className={styles.listContainer}>
      {books.map((book) => (
        <BookCard key={book.id} book={book} />
      ))}
    </div>
  );
};