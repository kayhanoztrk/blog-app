import logo from './logo.svg';
import './App.css';
import Home from './components/Home/Home';
import Post from './components/Post/Post';
import Header from './components/Header/Header';
import { BrowserRouter, Routes, Route, Redirect, Router,Switch } from "react-router-dom";

function App() {
  return (
    <div>
    <BrowserRouter>
    <Header></Header>
    <Switch>
       <Route exact path="/home" component={Home}></Route>
       <Route exact path="/post" component={Post}></Route>
    </Switch>
     </BrowserRouter>
     </div>
  );
}

export default App;
