import React, {useEffect, useState} from "react";

import styles from './AlertMsg.module.css'

const AlertMsg = (props) => {
  const [show, setShow] = useState(true);

  useEffect(() => {
    setTimeout(() => {
      setShow(false);
      window.location.reload(false);
    }, 4000);

  }, []);

  console.log(show);

  return (
    show &&
    <div className={styles.alert}
         style={props.alert.isDashboard ? {position: "absolute"} : {position: "static"}}>
      <h3 style={props.alert.isDashboard ? {fontSize: "1.8rem"} : {fontSize: "1rem"}}>{props.alert.alert}</h3>
    </div>
  );
};

export default AlertMsg;