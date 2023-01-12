import React, { useState, useEffect } from "react";
import { makeStyles } from "@material-ui/core";
import { useParams } from "react-router-dom";
import Profile from "../../components/Profile/Profile";
import { getUserById } from "../../service/user";

const useStyles = makeStyles({
  root: {
    display: "flex",
  },
});

const User = () => {
  const { userId } = useParams();
  const classes = useStyles();
  const [user, setUser] = useState();
  const [isLoaded, setIsLoaded] = useState(false);

  const getUser = async () => {
    const userInfo = await getUserById(userId);
    console.log("USERINFO", userInfo);
    setUser(userInfo);
    setIsLoaded(true);
  };

  useEffect(() => {
    getUser();
  }, []);

  return (
    <div className={classes.root}>
      {isLoaded ? (
        <Profile username={user.username} id={userId} bio={user.bio} />
      ) : (
        ""
      )}
    </div>
  );
};

export default User;
