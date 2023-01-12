import React, { useState, useEffect } from "react";
import { makeStyles } from "@material-ui/core/styles";
import Post from "../Post/Post";
import { getAllPosts } from "../../service/post";
import { useParams } from "react-router-dom";

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
  console.log("homeComponent is loading!");
  const [isLoaded, setIsLoaded] = useState(false);
  const [postList, setPostList] = useState([]);

  const getPostList = async () => {
    const postList = await getAllPosts();
    console.log("postList", postList);
    setPostList(postList);
    setIsLoaded(true);
  };

  useEffect(() => {
    console.log("home useEffect etkisi!");
    getPostList();
  }, []);

  const test = "deneme";

  const classes = useStyles();
  return (
    <div className={classes.container}>
      {console.log("homepage is loading!!")}
      {postList.map((post) => (
        <Post key={post.id} post={post} />
      ))}
    </div>
  );
};

export default Home;
