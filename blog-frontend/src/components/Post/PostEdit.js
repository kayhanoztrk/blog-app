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
  const [isLoading, setIsLoading] = useState(false);
  const [isSaved, setIsSaved] = useState(false);

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [isPublished, setIsPublished] = useState(false);
  const [error, setError] = useState(null);

  const theme = createTheme();

  const classes = useStyles();

  const navigate = useNavigate();

  const { postId } = props;

  const getPostDetail = async () => {
    const postDetail = await getPostById(postId);

    console.log("detailllll:", postDetail);

    setPostDetail(postDetail);
    setIsLoading(true);
    setTitle(postDetail.title);
    setDescription(postDetail.text);
    setIsPublished(postDetail.isPublished);
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

  const handleIsPublished = (val) => {
    setIsPublished(!isPublished);
  };

  const handleUpdatePostSubmit = async (e) => {
    e.preventDefault();

    const post = {
      title: title,
      text: description,
      isPublished: isPublished,
      userId: 1,
    };

    console.log("postUpdate", post);
    const result = await updatePostById(postId, post);
    console.log("updateDonen", result);
    if (result.errorMessage == null) {
      setTitle("");
      setDescription("");
      setError(null);
      setIsSaved(true);
      setTimeout(() => {
        navigate("/");
      }, 3000);
    } else {
      setError(result.errorMessage);
      setTimeout(() => {
        navigate("/");
      }, 2000);
    }
  };

  let myRef = {};
  return (
    <>
      {console.log("isSaved", isSaved)}
      {isLoading ? (
        <ThemeProvider theme={theme}>
          {isSaved && error == null ? (
            <Alert severity="success">{POST_UPDATED_VALID}</Alert>
          ) : (
            ""
          )}

          {error != null ? <Alert severity="error">{error}</Alert> : ""}

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
                  required
                  inputRef={myRef}
                  margin="normal"
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
                  required
                  inputRef={myRef}
                  margin="normal"
                  fullWidth
                  label="Post Description"
                  name="description"
                  autoFocus
                  value={description}
                  onChange={(e) => handlePostDescription(e.target.value)}
                />
                <FormControlLabel
                  control={<Checkbox defaultChecked={postDetail.isPublished} />}
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
      ) : (
        ""
      )}
    </>
  );
};

export default PostEdit;
