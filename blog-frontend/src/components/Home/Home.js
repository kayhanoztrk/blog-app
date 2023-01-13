import React, { useState, useEffect, useRef } from "react";
import { makeStyles } from "@material-ui/core/styles";
import Post from "../Post/Post";
import { getAllPostsPublished } from "../../service/post";
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
  const isHome = useRef(true);

  const getPostList = async () => {
    const postList = await getAllPostsPublished();
    setPostList(postList);
    setIsLoaded(true);
  };

  useEffect(() => {
    getPostList();
    isHome.current = true;
  }, []);

  const classes = useStyles();
  return (
    <div className={classes.container}>
      {postList ? (
        postList.map((post) => (
          <Post
            key={post.id}
            post={post}
            expanded={true}
            isHome={isHome.current}
          />
        ))
      ) : (
        <NotFound message={POST_NOT_FOUND_MESSAGE} />
      )}
    </div>
  );
};

export default Home;
