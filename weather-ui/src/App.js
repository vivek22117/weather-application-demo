import React from "react";

import {Dashboard, Home, Navbar, SignInOutContainer} from "./components";
import {grey} from "@material-ui/core/colors";
import {Container} from "@material-ui/core";
import {BrowserRouter, Route, Switch} from "react-router-dom";
import {getCurrentUser} from "./Utils/Common";
import Management from "./components/Management/Management";

const appStyles = {width: "100vw", height: "100vh", backgroundColor: grey};

class App extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      isAuthenticated: false,
      currentUser: undefined,
      showAdminPage: false
    };
  }
  componentDidMount() {
    const user = getCurrentUser();

    console.log("user is:" + user);

    if (user) {
      this.setUserState(user, true, true);
    }
  };

  setUserState = (user, isAuthenticate, showAdminPage) => {
    this.setState({
      isAuthenticated: isAuthenticate,
      currentUser: user,
      showAdminPage: showAdminPage
    })
  };


  render() {

    return (
      <BrowserRouter>
        <Container style={appStyles} disableGutters>
          <Navbar userState={this.state}
                  setUserState={this.setUserState}
          />
          <Switch>
            <Route exact
                   path={"/"}
                   render={props => (
                     <Home isAuthenticated={this.state.isAuthenticated}/>
                   )}
            />
            <Route exact
                   path={"/manage-user"}
                   render={props => (
                     <SignInOutContainer {...props}
                                         isAuthenticated={this.state.isAuthenticated}
                                         setUserState={this.setUserState}
                     />
                   )}
            />
            <Route exact
                   path={"/dashboard"}
                   render={props => (
                     <Dashboard {...props} isAuthenticated={this.state.isAuthenticated}/>
                   )}
            />
            <Route exact
                   path={"/admin"}
                   render={props => (
                     <Management {...props} isAuthenticated={this.state.isAuthenticated}/>
                   )}
            />
          </Switch>
        </Container>
      </BrowserRouter>
    )
  };
}

export default App;
