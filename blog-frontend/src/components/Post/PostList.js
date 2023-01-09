import React, { useState, useEffect } from "react";
import { makeStyles } from "@material-ui/core/styles";
import Post from "../Post/Post";
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

    console.log('postList', 'denemee!!');
  const [isLoaded, setIsLoaded] = useState(false);
  const [postList, setPostList] = useState([]);

  const {userId} = useParams();

  const getPostList = async () => {
    const postList = await getAllPostsByUserId(userId);
    console.log("postList", postList);
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
          postList.map((post) => (
              <Post key={post.id} post={post} />
              ))
      }
    </div>
  );
};

export default PostList;
