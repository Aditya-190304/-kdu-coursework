import React, { useState } from "react";
import styles from "./RegistrationForm.module.scss";
import type { RegData } from "../../types";

interface FormProps {
  on_submit: (data: RegData) => void;
}

const RegistrationForm: React.FC<FormProps> = ({ on_submit }) => {
  const [user_info, setUserInfo] = useState<RegData>({
    fullName: "",
    email_address: "",
    selectedEvent: "",
    user_msg: "",
  });

  const handleInput = (
    e: React.ChangeEvent<
      HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement
    >,
  ) => {
    setUserInfo({ ...user_info, [e.target.name]: e.target.value });
  };

  function validate_and_submit(e: React.FormEvent) {
    e.preventDefault();

    if (user_info.email_address.includes("@")) {
      on_submit(user_info);
    } else {
      alert("Please check your email format!");
    }
  }

  return (
    <form className={styles.formwrapp} onSubmit={validate_and_submit}>
      <div className={styles.field}>
        <label>Name *</label>
        <input
          name="fullName"
          placeholder="Enter your full name"
          required
          onChange={handleInput}
        />
      </div>
      <div className={styles.field}>
        <label>Email *</label>
        <input
          name="email_address"
          type="email"
          placeholder="your.email@example.com"
          required
          onChange={handleInput}
        />
      </div>
      <div className={styles.field}>
        <label>Select Event *</label>
        <select name="selectedEvent" required onChange={handleInput}>
          <option value="">Choose an event</option>
          <option value="tech-conf-2024">Tech Conference 2024</option>
          <option value="cloud-summit">Cloud summit</option>
          <option value="cloud-summit">KDU</option>
        </select>
      </div>
      <div className={styles.field}>
        <label>Message</label>
        <textarea
          name="user_msg"
          placeholder="Additional comments (optional)"
          onChange={handleInput}
        />
      </div>
      <button type="submit" className={styles.submitBtn}>
        Register For Event
      </button>
    </form>
  );
};

export default RegistrationForm;
