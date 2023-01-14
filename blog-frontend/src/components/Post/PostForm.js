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
import Stack from "@mui/material/Stack";
import { makeStyles } from "@material-ui/core/styles";

import { sendAuthProcess } from "../../service/auth";
import { savePost } from "../../service/post";
import { POST_SAVED_VALID, POST_SAVED_INVALID } from "../../constants/Messages";

const useStyles = makeStyles((theme) => ({
  root: {
    width: 800,
    textAlign: "left",
    margin: 20,
  },
}));

const PostForm = () => {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [tags, setTags] = useState("");
  const [error, setError] = useState(null);
  const [isSaved, setIsSaved] = useState(false);

  const theme = createTheme();

  const classes = useStyles();

  const navigate = useNavigate();

  const handlePostTitle = (val) => {
    setTitle(val);
  };

  const handlePostDescription = (val) => {
    setDescription(val);
  };

  const handlePostTags = (val) => {
    setTags(val);
  };

  const handleSavePostSubmit = async (e) => {
    e.preventDefault();

    const tagList = tags.split(",").map((tag) => ({
      text: tag,
    }));

    const post = {
      title: title,
      text: description,
      tags: tagList,
      userId: localStorage.getItem("currentUser"),
    };

    const result = await savePost(post);

    if (result.errorMessage == null) {
      setTitle("");
      setDescription("");
      setTags("");
      setError(null);
      setIsSaved(true);
      setTimeout(() => {
        navigate("/");
      }, 3000);
    } else {
      setError(result.errorMessage);
      setTimeout(() => {
        navigate("/");
      }, 1500);
    }
  };

  return (
    <ThemeProvider theme={theme}>
      {isSaved ? <Alert severity="success">{POST_SAVED_VALID}</Alert> : ""}
      {error != null ? (
        <Alert severity="error">
          {POST_SAVED_INVALID}
          {error}
        </Alert>
      ) : (
        ""
      )}

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
            Create Post
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
              value={tags}
              onChange={(e) => handlePostTags(e.target.value)}
            />
            (you can split your tags with using ',')
            <Button
              variant="contained"
              component="label"
              margin="normal"
              fullWidth
            >
              Upload File
              <input accept="image/*" type="file" hidden />
            </Button>
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
              onClick={handleSavePostSubmit}
            >
              Create Post
            </Button>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
};

export default PostForm;
