import React from "react";

import styles from './Alert.module.css'
import {useState, useEffect} from "react";

const Alert = (props) => {
  const [show, setShow] = useState(true);

  useEffect(() => {
    const timeId = setTimeout(() => {
      setShow(false);
    }, 5000);

    return () => clearTimeout(timeId);
  }, []);

  console.log(show);

  return (
    show &&
    <div className={styles.alert}
         style={props.alert.isDashboard ? {position: "absolute"} : {position: "static", fontSize: ""}}>
      <h3 style={props.alert.isDashboard ? {fontSize: "1.8rem"} : {fontSize: "1rem"}}>{props.alert.alert}</h3>
    </div>
  );
};

export default Alert;