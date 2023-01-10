import React, { useState, useEffect } from "react";
import { makeStyles } from "@material-ui/core/styles";
import Post from "../Post/Post";
import NotFound from "../NotFound/NotFound";
import { getAllPostsByUserId } from "../../service/post";
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

const PostList = () => {
  const [isLoaded, setIsLoaded] = useState(false);
  const [postList, setPostList] = useState([]);

  const {userId} = useParams();

  const getPostList = async () => {
    const postList = await getAllPostsByUserId(userId);
    setPostList(postList);
    setIsLoaded(true);
  };

  useEffect(() => {
    getPostList();
  },[]);

  const classes = useStyles();
  return (
      
    <div className={classes.container}>

      {
          (postList.length > 0 && isLoaded) ?
          postList.map((post) => (
              <Post key={post.id} post={post} />
              )) :
              <NotFound message="That author has not any post!"/>
              
      }
    </div>
  );
};

export default PostList;
