export type Genre =
  | "fiction"| "non-fiction"| "sci-fi"| "fantasy"| "biography"| "history";

export interface Book {
  id: number;
  title: string;
  author: string;
  genre: Genre;
  year: number;
  pages: number;
  rating: number;
  available: boolean;
  description?: string;
}
