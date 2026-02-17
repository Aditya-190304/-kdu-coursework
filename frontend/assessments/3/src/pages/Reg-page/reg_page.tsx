import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAppDispatch, useAppSelector } from '../../app/hooks';
import { submit_Registration } from '../../features/events/eventsSlice';
import RegistrationForm from '../../components/RegistrationForm/RegistrationForm';
import styles from './reg_page.module.scss';

const RegPage = () => { 
    const jump = useNavigate(); 
    const dispatch = useAppDispatch();
    const { regId } = useAppSelector((state) => state.events);

    useEffect(() => {
       
        if (regId) {
            jump(`/status/${regId}`);
        }
    }, [regId, jump]); 

    return (
        <div className={styles.container}>
            <div className={styles.blue_banner}>Event Registration</div>
            <RegistrationForm on_submit={(d) => dispatch(submit_Registration(d))} />
                
        </div>
    );
};

export default RegPage;