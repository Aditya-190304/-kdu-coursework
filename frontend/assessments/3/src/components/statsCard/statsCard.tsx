import styles from "./status_card.module.scss";

interface StatusCardProps {
  regId: string | undefined;
  details: {
    userName: string;
    userEmail: string;
    eventName: string;
    current_status: string;
  };
}

const StatusCard = ({ regId, details }: StatusCardProps) => { 
  return (
    <div className={styles.card_container}>
      <h3>Registration ID: {regId}</h3>
      
      <div className={styles.info_section}>
        <p><strong>Name:</strong> {details?.userName}</p>
        <p><strong>Email:</strong> {details?.userEmail}</p>
        <p><strong>Event:</strong> {details?.eventName}</p>
      </div>

      <div className={styles.status_area}>
        <span>Status:</span>
        
        <div className={`${styles.tag} ${styles[details?.current_status?.toLowerCase()]}`}>
          {details?.current_status}
        </div>
      </div>
    </div>
  );
};

export default StatusCard;