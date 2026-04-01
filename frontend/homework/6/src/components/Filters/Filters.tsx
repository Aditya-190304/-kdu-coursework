import styles from "./styles.module.scss";

interface FiltersProps {
  setGenre: (genre: string) => void;
  setRating: (rating: number) => void;
  setAvailable: (isAvailable: boolean) => void;
  setStart: (year: number) => void;
  setEnd: (year: number) => void;
  genres: string[];
}

export const Filters = ({ 
  setGenre, 
  setRating, 
  setAvailable, 
  setStart, 
  setEnd, 
  genres 
}: FiltersProps) => {
  return (
    <div className={styles.filterContainer}>
      <select className={styles.filterControl} onChange={(e) => setGenre(e.target.value)}>
        <option value="">All Genres</option>
        {genres.map((g) => (
          <option key={g} value={g}>{g}</option>
        ))}
      </select>
      <input
        type="number"
        className={styles.filterControl}
        placeholder="Rating (0-5)"
        onChange={(e) => setRating(Number(e.target.value))}
      />

      <input
        type="number"
        className={styles.filterControl}
        placeholder="Start Year"
        onChange={(e) => setStart(Number(e.target.value))}
      />
      <input
        type="number"
        className={styles.filterControl}
        placeholder="End Year"
        onChange={(e) => setEnd(Number(e.target.value))}
      />

      <label className={styles.checkboxLabel}>
        <input 
          type="checkbox" 
          onChange={(e) => setAvailable(e.target.checked)} 
        />
        Available Only
      </label>
    </div>
  );
};