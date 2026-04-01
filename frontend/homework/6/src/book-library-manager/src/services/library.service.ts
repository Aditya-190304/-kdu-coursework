import { Book } from "../models/Book";
import { books } from "../data/books.data";
import { log } from "../utils/logger";

export class LibraryService {
  
  fetchBooksFromData(): Book[] {
    return books;
  }

  searchBooks(query: string, currentList: Book[] = books): Book[] {
     const lowerQuery = query.toLowerCase();
    const results = currentList.filter(book =>
      book.title.toLowerCase().includes(lowerQuery) ||
       book.author.toLowerCase().includes(lowerQuery)
    );
    log(`[search] query: "${query}" | found: ${results.length}`);
    
    return results;
  }

  getAvailableBooks(currentList: Book[] = books): Book[] {
     return currentList.filter(book => book.available);
  }

  getBooksByYearRange(start: number, end: number, currentList: Book[] = books): Book[] {
    return currentList.filter(book => book.year >= start && book.year <= end);
  }

  filterByGenre(genre: string, currentList: Book[] = books): Book[] {
    if (!genre) return currentList;
    return currentList.filter(book => book.genre === genre);
  }

  filterByRating(minRating: number, currentList: Book[] = books): Book[] {
    if (minRating <= 0) return currentList;
    return currentList.filter(book => book.rating >= minRating);
  }
}