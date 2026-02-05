import type { Book } from "../../book-library-manager/src/models/Book";
import styles from "./styles.module.scss";

type Props = {
  book: Book;
};


export const BookCard = ({ book }: Props) => {
  return (

    <div className={styles.card}>
      <h3>{book.title}</h3>
      <p><strong>Author:</strong> {book.author}</p>
      <p><strong>Genre:</strong> {book.genre}</p>
      <p><strong>Rating:</strong> {book.rating}</p>
      
      <p className={book.available ? styles.statusAvailable : styles.statusUnavailable}>
        {book.available ? "Available" : "Unavailable"}
      </p>
    </div>
  );
};