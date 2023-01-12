import React, { useState, useEffect } from "react";
import { makeStyles } from "@material-ui/core/styles";
import Post from "../Post/Post";
import { getAllPosts } from "../../service/post";
import NotFound from "../NotFound/NotFound";
import { POST_NOT_FOUND_MESSAGE } from "../../constants/Messages";

const useStyles = makeStyles((theme) => ({
  container: {
    display: "flex",
    flexWrap: "wrap",
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#f0f5ff",
  },
}));

const Home = () => {
  const [isLoaded, setIsLoaded] = useState(false);
  const [postList, setPostList] = useState([]);

  const getPostList = async () => {
    const postList = await getAllPosts();
    console.log("postList", postList);
    setPostList(postList);
    setIsLoaded(true);
  };

  useEffect(() => {
    getPostList();
  }, []);

  const test = "deneme";

  const classes = useStyles();
  return (
    <div className={classes.container}>
      {postList.length > 0 ? (
        postList.map((post) => <Post key={post.id} post={post} />)
      ) : (
        <NotFound message={POST_NOT_FOUND_MESSAGE} />
      )}
    </div>
  );
};

export default Home;
