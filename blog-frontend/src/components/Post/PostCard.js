import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { makeStyles } from "@material-ui/core/styles";
import Author from "../Author/Author";
import { getAllPosts } from "../../service/post";
import { Box } from "@material-ui/core";
import { CardContent } from "@material-ui/core";
import { Card } from "@material-ui/core";
import { Typography } from "@material-ui/core";
import { IconButton } from "@material-ui/core";
import { useTheme } from "@mui/material/styles";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import { deletePostById } from "../../service/post";
import { useNavigate } from "react-router-dom";
import { Alert } from "@mui/material";
import { POST_DELETED_VALID } from "../../constants/Messages";
import PostEdit from "./PostEdit";
import { CardActions } from "@mui/material";
import { Button } from "@mui/material";
import PublishIcon from "@mui/icons-material/Publish";

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

  const { postInfo, refreshPostList, isOwner } = props;

  const [isDeleted, setIsDeleted] = useState(false);
  const [editMode, setEditMode] = useState(false);
  const [isPublished, setIsPublished] = useState(!postInfo.isPublished);

  const handleEdit = () => {
    setEditMode(true);
  };

  const handleDelete = async () => {
    const postId = postInfo.id;
    const response = await deletePostById(postId);

    if (response != null) {
      setIsDeleted(true);
      setTimeout(() => refreshPostList(), 1000);
    }
  };

  const handlePublish = () => {
    console.log("handlePublishh!!");
  };

  return (
    <>
      {!editMode ? (
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
            {isOwner ? (
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
                {!isPublished ? (
                  <IconButton
                    aria-label="next"
                    style={{ justifyContent: "flex-end" }}
                    onClick={handlePublish}
                  >
                    {theme.direction === "rtl" ? (
                      <PublishIcon />
                    ) : (
                      <PublishIcon />
                    )}
                  </IconButton>
                ) : (
                  ""
                )}
              </Box>
            ) : (
              ""
            )}
            <CardActions>
              <Button size="small">
                <Link to={{ pathname: "/post/" + postInfo.id }}>
                  Read More..{" "}
                </Link>
              </Button>
            </CardActions>
          </Box>
        </Card>
      ) : (
        <PostEdit postId={postInfo.id} />
      )}
    </>
  );
};

export default PostCard;
