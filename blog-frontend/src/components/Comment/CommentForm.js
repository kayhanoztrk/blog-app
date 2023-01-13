import React, { useState } from "react";
import { Link } from "react-router-dom";
import {
  CardContent,
  InputAdornment,
  OutlinedInput,
  Avatar,
} from "@material-ui/core";
import { makeStyles } from "@material-ui/core/styles";
import Button from "@mui/material/Button";
import { saveComment } from "../../service/comment";

const useStyles = makeStyles((theme) => ({
  comment: {
    display: "flex",
    flexWrap: "wrap",
    justifyContent: "flex-start",
    alignItems: "center",
  },
  small: {
    width: theme.spacing(4),
    height: theme.spacing(4),
  },
  link: {
    textDecoration: "none",
    boxShadow: "none",
    color: "white",
  },
}));

const CommentForm = (props) => {
  const { comment, username, setCommentRefresh } = props;
  const classes = useStyles();
  const [commentText, setCommentText] = useState("");

  const handleChange = (val) => {
    setCommentText(val);
  };

  const handleSubmit = () => {
    saveComment({
      postId: comment.postId,
      userId: localStorage.getItem("currentUser"),
      text: commentText,
    });
    setCommentText("");
    setCommentRefresh();
  };

  return (
    <CardContent className={classes.comment}>
      <OutlinedInput
        id="outlined-adornment-amount"
        multiline
        inputProps={{ maxLength: 25 }}
        fullWidth
        onChange={(e) => handleChange(e.target.value)}
        startAdornment={
          <InputAdornment position="start">
            <Link
              className={classes.link}
              to={{ pathname: "/users/" + comment.userId }}
            >
              <Avatar aria-label="recipe" className={classes.small}>
                {username.charAt(0).toUpperCase()}
              </Avatar>
            </Link>
          </InputAdornment>
        }
        endAdornment={
          <InputAdornment position="end">
            <Button
              variant="contained"
              style={{
                background: "linear-gradient(45deg, #2196F3 30%, #21CBF3 90%)",
                color: "white",
              }}
              onClick={handleSubmit}
            >
              Comment
            </Button>
          </InputAdornment>
        }
        value={commentText}
        style={{ color: "black", backgroundColor: "white" }}
      ></OutlinedInput>
    </CardContent>
  );
};

export default CommentForm;
