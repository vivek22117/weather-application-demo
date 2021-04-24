import React from "react";

import styles from './Alert.module.css'

const Alert = (props) => {
  console.log(props)
  return (
    <div className={styles.alert}
         style={props.alert.isDashboard ? {position: "absolute"} : {position: "static", fontSize: ""}}>
      <h3 style={props.alert.isDashboard ? {fontSize: "1.8rem"} : {fontSize: "1rem"}}>{props.alert.alert}</h3>
    </div>
  );
};

export default Alert;