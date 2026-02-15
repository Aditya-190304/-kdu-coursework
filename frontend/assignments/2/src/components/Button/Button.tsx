import React from "react";
import styles from "./Button.module.scss";

interface Btn_Props extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: "primary" | "secondary" | "outline";
  fullWidth?: boolean;
  active?: boolean;
}

export const Button = ({
  children,
  variant = "primary",
  fullWidth = false,
  active = false,
  className,
  ...props
}: Btn_Props) => {
  const buttonClasses =
    `${styles.button} ${styles[variant]} ${fullWidth ? styles.fullWidth : ""} ${active ? styles.active : ""} ${className || ""}`.trim();
  return (
    <button className={buttonClasses} {...props}>
      {children}
    </button>
  );
};
