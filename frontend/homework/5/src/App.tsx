import { useMemo } from "react";
import { useLibrary } from "./hooks/useLibrary";
import { BookList } from "./components/BookList/BookList";
import { SearchBar } from "./components/SearchBar/serchbar";
import { Filters } from "./components/Filters/Filters";
import { StatsPanel } from "./components/StatsPannel/StatsPanel";
import { books } from "./book-library-manager/src/data/books.data"; 

function App() {
  const { 
    filteredBooks, 
    setQuery, 
    setGenre, 
    setRating,
    setAvailable, 
    setStart,     
    setEnd        
  } = useLibrary();

  const uniqueGenres = useMemo(() => {
    return Array.from(new Set(books.map((b) => b.genre))).sort();
  }, []);

  return (
    <div>
      <h1>Book Library</h1>
      <SearchBar setQuery={setQuery} />
      
     
      <Filters 
        setGenre={setGenre} 
        setRating={setRating} 
        setAvailable={setAvailable} 
        setStart={setStart}         
        setEnd={setEnd}             
        genres={uniqueGenres} 
      />
      
      <StatsPanel books={filteredBooks} />
      <BookList books={filteredBooks} />
    </div>
  );
}

export default App;