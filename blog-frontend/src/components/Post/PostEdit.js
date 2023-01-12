import React, { useState, useEffect } from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import { Link, useNavigate } from "react-router-dom";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import Alert from "@mui/material/Alert";
import Checkbox from "@mui/material/Checkbox";
import FormControlLabel from "@mui/material/FormControlLabel";
import { makeStyles } from "@material-ui/core/styles";
import { POST_UPDATED_VALID } from "../../constants/Messages";
import { getPostById, updatePostById } from "../../service/post";

const useStyles = makeStyles((theme) => ({
  root: {
    width: 800,
    textAlign: "left",
    margin: 20,
  },
}));

const PostEdit = (props) => {
  const [postDetail, setPostDetail] = useState({});
  const [isSaved, setIsSaved] = useState(false);

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [tags, setTags] = useState("");
  const [isPublished, setIsPublished] = useState(false);

  const theme = createTheme();

  const classes = useStyles();

  const navigate = useNavigate();

  const { postId } = props;

  const getPostDetail = async () => {
    const postDetail = await getPostById(postId);

    setPostDetail(postDetail);
    setTitle(postDetail.title);
    setDescription(postDetail.text);
    setTags(postDetail.tagList);
    setIsPublished(postDetail.isPublished);

    if (postDetail.message != null) console.log("process has been succeded!");
  };

  useEffect(() => {
    getPostDetail();
  }, []);

  const handlePostTitle = (val) => {
    setTitle(val);
  };

  const handlePostDescription = (val) => {
    setDescription(val);
  };

  const handlePostTags = (val) => {
    setTags(val);
  };
  const handleIsPublished = (val) => {
    setIsPublished(val);
  };

  const handleUpdatePostSubmit = async (e) => {
    e.preventDefault();

    console.log("handleUpdatePostSubmit", "handleUpdatePostSubmit!");

    const tagList = tags.split(",").map((tag) => ({
      text: tag,
    }));

    const post = {
      title: title,
      text: description,
      tags: tagList,
      userId: 1,
    };

    const result = await updatePostById(postId, post);

    if (result != null) {
      setTitle("");
      setDescription("");
      setTags("");
      setIsSaved(true);
      setTimeout(() => {
        navigate("/");
      }, 3000);
    }
  };

  return (
    <ThemeProvider theme={theme}>
      {isSaved ? <Alert severity="success">{POST_UPDATED_VALID}</Alert> : ""}

      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Typography component="h1" variant="h5">
            Update Post
          </Typography>
          <Box component="form" noValidate sx={{ mt: 1 }}>
            <TextField
              margin="normal"
              required
              fullWidth
              label="Post Title"
              name="title"
              autoComplete="title"
              autoFocus
              value={title}
              onChange={(e) => handlePostTitle(e.target.value)}
            />
            <TextField
              className={classes.description}
              margin="normal"
              fullWidth
              required
              label="Post Description"
              name="description"
              autoFocus
              value={description}
              onChange={(e) => handlePostDescription(e.target.value)}
            />
            <TextField
              className={classes.description}
              margin="normal"
              fullWidth
              required
              label="Post Tags"
              name="tags"
              autoFocus
              value={JSON.stringify(tags)}
              onChange={(e) => handlePostTags(e.target.value)}
            />
            (you can split your tags with using ',')
            <FormControlLabel
              control={
                <Checkbox {...(postDetail.isPublished ? `checked` : "")} />
              }
              label="on production"
              onChange={(e) => handleIsPublished(e.target.value)}
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
              onClick={handleUpdatePostSubmit}
            >
              Update Post
            </Button>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
};

export default PostEdit;
