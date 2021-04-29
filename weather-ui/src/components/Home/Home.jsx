import React from "react";

import styles from './Home.module.css';

const Home = ({isAuthenticated}) => {

  console.log("In home page " + isAuthenticated);

  return (
    <div className={styles.search}>
      <h1>Welcome to weather search application!</h1>
      {isAuthenticated ? <h2>You are logged in, please use Dashboard tab for search</h2>
        :
        <h2>You are not logged in user, please use ManageUser Tab for registration or login!</h2>
      }
    </div>
  );

};

export default Home;