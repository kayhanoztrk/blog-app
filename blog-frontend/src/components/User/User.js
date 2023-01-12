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
  const [isOwner, setIsOwner] = useState(false);

  const getUser = async () => {
    const userInfo = await getUserById(userId);
    setUser(userInfo);
    setIsLoaded(true);
  };

  useEffect(() => {
    getUser();
    setIsOwner(localStorage.getItem("currentUser") == userId);
  }, []);

  return (
    <div className={classes.root}>
      {isLoaded ? (
        <Profile
          username={user.username}
          id={userId}
          bio={user.bio}
          isOwner={isOwner}
        />
      ) : (
        ""
      )}
    </div>
  );
};

export default User;
