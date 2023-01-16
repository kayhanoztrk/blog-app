import logo from "./logo.svg";
import "./App.css";
import Home from "./components/Home/Home";
import Post from "./components/Post/Post";
import Header from "./components/Header/Header";
import User from "./components/User/User";
import PostDetail from "./components/Post/PostDetail";
import PostList from "./components/Post/PostList";
import Login from "./components/Auth/Login";
import Register from "./components/Auth/Register";
import UserList from "./components/User/UserList";
import NotFound from "./components/NotFound/NotFound";
import {
  BrowserRouter,
  Route,
  Redirect,
  Switch,
  Routes,
  Navigate,
} from "react-router-dom";
import PostForm from "./components/Post/PostForm";
import ProfileSettings from "./components/Profile/ProfileSettings";

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Header></Header>
        <Routes>
          <Route exact path="/" element={<Home />} />
          <Route exact path="/user/:userId" element={<User />} />
          <Route exact path="/users/" element={<UserList />} />
          <Route exact path="/post/:postId" element={<PostDetail />} />
          <Route exact path="/user/:userId/posts" element={<PostList />} />
          <Route exact path="/post/create" element={<PostForm />} />
          <Route exact path="/login" element={<Login />} />
          <Route exact path="/register" element={<Register />} />
          <Route exact path="/settings" element={<ProfileSettings />} />
          <Route exact path="/notfound" element={<NotFound />} />
          <Route exact path="/login">
            {localStorage.getItem("currentUser") != null ? (
              <Route path="*" element={<Home />} />
            ) : (
              <Route path="*" element={<Login />} />
            )}
          </Route>
          <Route exact path="/register">
            {localStorage.getItem("currentUser") != null ? (
              <Route path="*" element={<Home />} />
            ) : (
              <Route path="*" element={<Register />} />
            )}
          </Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
