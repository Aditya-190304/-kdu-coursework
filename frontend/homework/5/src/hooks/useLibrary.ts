import { useState, useMemo } from "react";
import { LibraryService } from "../book-library-manager/src/services/library.service";

export const useLibrary = () => {
  const service = useMemo(() => new LibraryService(), []);
  const [query, setQuery] = useState("");
  const [genre, setGenre] = useState("");
  const [Rating, setRating] = useState(0);
  const [available, setAvailable] = useState(false);
  const [start, setStart] = useState(0);
  const [end, setEnd] = useState(0);

  const filteredBooks = useMemo(() => {
    let result = service.fetchBooksFromData();
    if (query) {
      result = service.searchBooks(query, result);
    }
    if (genre) {
      result = service.filterByGenre(genre, result);
    }
    if (Rating > 0) {
      result = service.filterByRating(Rating, result);
    }
    if (available) {
      result = service.getAvailableBooks(result);
    }

    if (start > 0 || end > 0) {
      const safeStart = start || 0;
      const safeEnd = end || 9999;
      result = service.getBooksByYearRange(safeStart, safeEnd, result);
    }

    return result;
  }, [service, query, genre, Rating, available, start, end]);

  return {
    filteredBooks,
    setQuery,
    setGenre,
    setRating,
    setAvailable,
    setStart,
    setEnd
  };
};