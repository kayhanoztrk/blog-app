import React, { useState, useEffect } from "react";
import { makeStyles } from "@material-ui/core/styles";
import Author from "../Author/Author";
import { getAllPosts } from "../../service/post";
import { Box } from "@material-ui/core";
import { CardContent } from "@material-ui/core";
import { Card } from "@material-ui/core";
import { Typography } from "@material-ui/core";
import { IconButton } from "@material-ui/core";
import PlayArrowIcon from "@mui/icons-material/PlayArrow";
import { useTheme } from "@mui/material/styles";
import SkipNextIcon from "@mui/icons-material/SkipNext";
import SkipPreviousIcon from "@mui/icons-material/SkipPrevious";
import EditIcon from "@mui/icons-material/Edit";
import CardMedia from "@mui/material/CardMedia";
import DeleteIcon from "@mui/icons-material/Delete";
import { deletePostById } from "../../service/post";
import { useNavigate } from "react-router-dom";
import { Alert } from "@mui/material";
import { POST_DELETED_VALID } from "../../constants/Messages";

const useStyles = makeStyles((theme) => ({
  container: {
    display: "flex",
    flexWrap: "wrap",
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#f0f5ff",
  },

  text: {
    textAlign: "left",
  },
}));

const PostCard = (props) => {
  const theme = useTheme();

  const classes = useStyles();
  const navigator = useNavigate();
  console.log("props", props);
  const { postInfo, refreshPostList } = props;

  const [isDeleted, setIsDeleted] = useState(false);

  const handleEdit = () => {
    console.log("edit icon is clicked!");
  };

  const handleDelete = async () => {
    const postId = postInfo.id;
    console.log("postId", postId);
    const response = await deletePostById(postId);

    if (response != null) {
      setIsDeleted(true);
      setTimeout(() => refreshPostList(), 1000);
    }
  };

  return (
    <>
      <Card sx={{ display: "flex" }}>
        {isDeleted ? (
          <Alert severity="success">{POST_DELETED_VALID}</Alert>
        ) : (
          ""
        )}
        <Box sx={{ display: "flex", flexDirection: "column" }}>
          <CardContent sx={{ flex: "1 0 auto" }}>
            <Typography component="div">{postInfo.title}</Typography>
            <Typography component="div" className={classes.text}>
              {postInfo.text}
            </Typography>
          </CardContent>
          <Box sx={{ display: "flex", alignItems: "center", pl: 1, pb: 1 }}>
            <IconButton aria-label="previous" onClick={handleEdit}>
              {theme.direction === "rtl" ? <EditIcon /> : <EditIcon />}
            </IconButton>
            <IconButton
              aria-label="next"
              style={{ justifyContent: "flex-end" }}
              onClick={handleDelete}
            >
              {theme.direction === "rtl" ? <DeleteIcon /> : <DeleteIcon />}
            </IconButton>
          </Box>
        </Box>
      </Card>
    </>
  );
};

export default PostCard;
