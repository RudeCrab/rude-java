import React, {Component} from 'react';
import {HashRouter as Router, Route, Switch} from 'react-router-dom';
import Login from "./pages/login";
import Home from "./layout/home";
import NotFound from "./pages/NotFound";



class App extends Component {

    render() {
        return (
            <Router>
                <Switch>
                    <Route exact path='/login' component={Login}/>
                    <Route exact path='/404' component={NotFound}/>
                    <Route path='/' component={Home}/>
                </Switch>
            </Router>
        );
    }
}

export default App;
