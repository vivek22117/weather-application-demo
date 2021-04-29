import React from "react";
import {AppBar, Button, IconButton, Toolbar, Typography} from "@material-ui/core";
import {Link, useHistory} from "react-router-dom";
import {AccountCircle} from "@material-ui/icons";
import {removeUserSession} from "../../Utils/Common";

const Navbar = ({userState, setUserState}) => {
  const history = useHistory();

  console.log("In Navbar " + JSON.stringify(userState));

  const appStyle = {width: "auto"};

  const handleLogout = () => {
    removeUserSession();
    setUserState(undefined, false, false);
    history.push('/manage-user');
    window.location.reload(false);
  };

  return (
    <AppBar position={"static"} style={appStyle}>
      <Toolbar>
        <Typography variant={"h6"} style={{flexGrow: 1}}>
          Weather Dashboard
        </Typography>
        <Button color={"inherit"} component={Link} to="/">Home</Button>

        {userState.isAuthenticated &&
        <Button color={"inherit"} component={Link} to="/dashboard">Dashboard</Button>
        }

        {userState.showAdminPage &&
        <Button color={"inherit"} component={Link} to="/admin">Management</Button>
        }

        {!userState.isAuthenticated &&
        <Button color={"inherit"} component={Link} to="/manage-user">Manage User</Button>
        }
        {userState.isAuthenticated &&
        <>
          <Button color={"inherit"} component={Link} onClick={() => handleLogout()} to="/logout">Logout</Button>
          <IconButton color={"inherit"} aria-label={"account"}
                      aria-haspopup="true"
          >
            <AccountCircle/>
          </IconButton>
        </>
        }

      </Toolbar>

    </AppBar>

  );
};

export default Navbar;