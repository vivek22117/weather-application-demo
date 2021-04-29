import React, {useState} from "react";
import {Avatar, Button, Grid, Paper, TextField, Typography} from "@material-ui/core";
import AddCircleOutlineOutlined from "@material-ui/icons/AddCircleOutlineOutlined";

import styles from './Signup.module.css';
import axios from "axios";
import {useHistory} from "react-router-dom";
import Alert from '@material-ui/lab/Alert';
import {AlertMsg} from "../index";

const Signup = ({props}) => {
  const history = useHistory();

  const headerStyle = {margin: "0px"}
  const buttonStyle = {margin: "8px 0"}

  const [username, setUsername] = useState("");
  const [dob, setDOB] = useState("");
  const [password, setPassword] = useState("");
  const [alertData, setAlertMsg] = useState({alert: "", isDashboard: false});

  let alertMsg;

  if (alertData.alert === "Success") {
    alertMsg = <Alert severity="success">User registration is successful!</Alert>
  } else {
    alertMsg = <AlertMsg alert={alertData}/>
  }


  const onSubmit = async (event) => {
    event.preventDefault();

    const data = {
      username: username,
      dob: dob,
      password: password
    }

    if (username !== "" || dob !== "" || password !== "") {
      axios.post('api/auth/signup', data)
        .then(res => {
          console.log(res.data);
          setAlertMsg({alert: "Success", isDashboard: false});
          history.push('/manage-user');
        })
        .catch(err => {
          console.log(err.response)
          setAlertMsg({alert: err.message, isDashboard: false});

        });
    } else {
      setAlertMsg({alert: "Please provide valid inputs!", isDashboard: false});
    }

  };

  return (
    <Grid>
      <Paper className={styles.container}>
        <Grid align='center'>
          <Avatar className={styles.avatarcontainer}><AddCircleOutlineOutlined/></Avatar>
          <h2 style={headerStyle}>Sign Up</h2>
          <Typography variant={"caption"} gutterBottom>
            Please fill this form to create an account!
          </Typography>
        </Grid>
        <form>
          <TextField label='Email' placeholder='Enter your email'
                     validators={['required', 'isEmail']}
                     errorMessages={['This field is required', 'Email is not valid']}
                     onChange={(event) =>
                       setUsername(event.target.value)
                     } fullWidth required/>
          <TextField label='DOB' placeholder='Enter Date Of Birth'
                     type="date"
                     InputLabelProps={{
                       shrink: true,
                     }}
                     errorMessages={['This field is required']}
                     onChange={(event) =>
                       setDOB(event.target.value)
                     } fullWidth required/>
          <TextField label='Password' placeholder='Enter password' type='password'
                     validators={['required']}
                     errorMessages={['This field is required']}
                     onChange={(event) =>
                       setPassword(event.target.value)
                     } fullWidth required/>
          <Button type={"submit"} color={"primary"} variant={"contained"} style={buttonStyle}
                  onClick={(event) => onSubmit(event)}
                  fullWidth>SIGN UP</Button>
        </form>
        {alertData.alert !== "" && alertMsg}
      </Paper>
    </Grid>
  );
};

export default Signup;