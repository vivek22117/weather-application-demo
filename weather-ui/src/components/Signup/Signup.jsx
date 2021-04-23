import React from "react";
import {Avatar, Button, FormControlLabel, Grid, Link, Paper, TextField, Typography} from "@material-ui/core";
import AddCircleOutlineOutlined from "@material-ui/icons/AddCircleOutlineOutlined";
import Checkbox from "@material-ui/core/Checkbox";

import styles from './Signup.module.css';

const Signup = () => {

  const headerStyle = {margin: "0px"}
  const buttonStyle={margin: "8px 0"}

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
          <TextField label='Name' placeholder='Enter your name' fullWidth required/>
          <TextField label='Email' placeholder='Enter email' fullWidth required/>
          <TextField label='DOB' placeholder='Enter Date Of Birth'  fullWidth required/>
          <TextField label='Password' placeholder='Enter password' type='password' fullWidth required/>
          <TextField label='Confirm Password' placeholder='Re-enter password' type='password' fullWidth required/>
          <Button type={"submit"} color={"primary"} variant={"contained"} style={buttonStyle} fullWidth>SIGN UP</Button>
        </form>
      </Paper>
    </Grid>
  )
}

export default Signup;