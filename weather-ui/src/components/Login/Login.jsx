import React from "react";
import {Avatar, Button, FormControlLabel, Grid, Link, Paper, TextField, Typography} from '@material-ui/core';
import LockOutLinedIcon from '@material-ui/icons/LockOutlined';
import Checkbox from '@material-ui/core/Checkbox';
import {Formik, Form} from "formik";

import styles from './Login.module.css'

const Login = ({handleChange}) => {

  const buttonStyle={margin: "8px 0"}

  return (

    <Grid>
      <Paper className={styles.container}>
        <Grid align='center'>
          <Avatar className={styles.avatarcontainer}><LockOutLinedIcon/></Avatar>
          <h2>Sign In</h2>
        </Grid>
       <Formik>
         {(props) => (
           <Form>
             <TextField label='Username' placeholder='Enter username' fullWidth required/>
             <TextField label='Password' placeholder='Enter password' type='password' fullWidth required/>
             <FormControlLabel
               control={
                 <Checkbox
                   name="checkedB"
                   color="primary"
                 />
               }
               label="Remember me"
             />
             <Button type={"submit"} color={"primary"} variant={"contained"} style={buttonStyle} fullWidth>SIGN IN</Button>
           </Form>
         )}
       </Formik>

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

      </Paper>
    </Grid>

  )
}

export default Login;