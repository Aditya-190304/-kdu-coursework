import styles from "./styles.module.scss";

interface Props {
  setQuery: (query: string) => void;
}

export const SearchBar = ({ setQuery }: Props) => {
  return (
    <div className={styles.searchPanel}>
      <input
        type="text"
        className={styles.searchInput} 
        placeholder="search by title or author..."
        onChange={(e) => setQuery(e.target.value)}
      />
    </div>
  );
};