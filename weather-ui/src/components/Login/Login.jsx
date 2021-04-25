import React, {useState} from "react";
import {useHistory} from 'react-router-dom';
import {Avatar, Button, FormControlLabel, Grid, Link, Paper, TextField, Typography} from '@material-ui/core';
import LockOutLinedIcon from '@material-ui/icons/LockOutlined';
import Checkbox from '@material-ui/core/Checkbox';
import axios from "axios";
import {setUserSession} from "../../Utils/Common";

import styles from './Login.module.css'
import {Alert} from "../index";

const Login = ({handleChange, setUserState}) => {
  const history = useHistory();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [alertData, setAlert] = useState({alert: "", isDashboard: false});

  const buttonStyle = {margin: "8px 0"}

  const onSubmit = async (event) => {
    event.preventDefault();

    const data = {
      username: username,
      password: password
    };
    console.log(data);

    if (data.password !== "" || data.username !== "") {
      axios.post('api/auth/login', data)
        .then(res => {
          console.log(res);
          if (!res.data.authenticationToken) {
            return setAlert({alert: "Authentication failed!", isDashboard: false});
          }
          setUserSession(res.data.authenticationToken, res.data.username, res.data.isAuthenticated);
          setUserState(res.data.username, res.data.isAuthenticated, true);
          history.push('/')
        })
        .catch(err => {
          console.log(err);
          setAlert({alert: err.response.data, isDashboard: false});

        });
    } else {
      setAlert({alert: "Please provide valid credentials!", isDashboard: false});
    }
  };

  return (

    <Grid>
      <Paper className={styles.container}>
        <Grid align='center'>
          <Avatar className={styles.avatarcontainer}><LockOutLinedIcon/></Avatar>
          <h2>Sign In</h2>
        </Grid>
        <TextField label='Username'
                   placeholder='Enter username' fullWidth
                   onChange={(event) =>
                     setUsername(event.target.value)
                   } required/>
        <TextField label='Password'
                   placeholder='Enter password' type='password' fullWidth
                   onChange={(event) =>
                     setPassword(event.target.value)
                   }
                   required/>
        <FormControlLabel
          control={
            <Checkbox
              name="checkedB"
              color="primary"
            />
          }
          label="Remember me"
        />
        <Button type={"submit"} color={"primary"} variant={"contained"} style={buttonStyle}
                onClick={(event) => onSubmit(event)} fullWidth>SIGN IN</Button>

        <Typography>
          <Link href="#">
            Forgot password
          </Link>
        </Typography>
        <Typography> Do you have an account?
          <Link href="#" onClick={() => handleChange("event", 1)}>
            Sign Up
          </Link>
        </Typography>
        {alertData.alert !== "" && <Alert alert={alertData} alertStatus={false}/>}
      </Paper>
    </Grid>

  );
};

export default Login;