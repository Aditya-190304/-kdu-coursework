import React from "react";
import styles from "./Section.module.scss";

export const Section = ({
  title,
  children,
}: {
  title: string;
  children: React.ReactNode;
}) => (
  <section className={styles.section}>
    <h2 className={styles.title}>{title}</h2>
    <div className={styles.content}>{children}</div>
  </section>
);
