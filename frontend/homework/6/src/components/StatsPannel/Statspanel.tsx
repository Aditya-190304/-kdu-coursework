import type { Book } from "../../book-library-manager/src/models/Book";
 import styles from "./styles.module.scss";

 export const StatsPanel = ({ books }: { books: Book[] }) => {
  const avg = books.length > 0 ? books.reduce((sum, b) => sum + b.rating, 0) / books.length : 0;

  return (
    <div className={styles.statsContainer}>

      <div className={styles.statItem}>
        <span>Total Books</span>
         <span>{books.length}</span>
      </div>
     <div className={styles.statItem}>
        <span>Average Rating</span>
       <span>{avg.toFixed(2)}</span>
      </div>
      
    </div>
  );
};