import { useEffect } from "react";
import { useParams } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../../app/hooks";
import { fetchStatusData } from "../../features/events/eventsSlice";
import StatusCard from "../../components/statsCard/statsCard";
import styles from "./StatusPage.module.scss";

const Status_pg = () => {
  const { id: reg_id_param } = useParams<{ id: string }>();
  const callApi = useAppDispatch();

  const { activeRegistration, loadingState } = useAppSelector(
    (state) => state.events,
  );

  useEffect(() => {
    if (!reg_id_param) return;

    
    callApi(fetchStatusData(reg_id_param));

    
    
    const poll = setInterval(() => {
    
      if (!activeRegistration || activeRegistration.current_status === "Queued") {
        callApi(fetchStatusData(reg_id_param));
      }
    }, 3000);

    
    if (activeRegistration?.current_status && activeRegistration.current_status !== "Queued") {
      clearInterval(poll);
    }

    
    return () => clearInterval(poll);
  }, [callApi, reg_id_param, activeRegistration?.current_status]);

  return (
    <div className={styles.status_layout}>
      <div className={styles.header_blue}>Registration Status</div>

      {activeRegistration ? (
        <StatusCard
          regId={reg_id_param}
          details={{
            userName: activeRegistration.userName || "unknown",
            userEmail: activeRegistration.userEmail || "n/a",
            eventName: activeRegistration.eventName || "not Found",
            current_status: activeRegistration.current_status || "Queued", 
          }}
        />
      ) : (
        <div className={styles.loader_text}>
          {loadingState === "loading" && "checking status..."}
        </div>
      )}

      
      {activeRegistration?.current_status === "Successful" && (
        <p style={{ color: "green", marginTop: "10px", fontWeight: "bold" }}>
          Registration successful! Confirmation emails have been sent to you and the admin.
        </p>
      )}

      
      {activeRegistration?.current_status === "Failed" && (
        <p style={{ color: "red", marginTop: "10px" }}>
          There was an issue with your registration. Please try again.
        </p>
      )}
    </div>
  );
};

export default Status_pg;