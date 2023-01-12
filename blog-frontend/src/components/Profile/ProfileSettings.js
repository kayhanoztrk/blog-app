import React, { useEffect, useState } from "react";
import { styled } from "@mui/material/styles";
import Card from "@mui/material/Card";
import CardHeader from "@mui/material/CardHeader";
import CardMedia from "@mui/material/CardMedia";
import CardContent from "@mui/material/CardContent";
import CardActions from "@mui/material/CardActions";
import Collapse from "@mui/material/Collapse";
import Avatar from "@mui/material/Avatar";
import IconButton, { IconButtonProps } from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import { red } from "@mui/material/colors";
import FavoriteIcon from "@mui/icons-material/Favorite";
import ShareIcon from "@mui/icons-material/Share";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import MoreVertIcon from "@mui/icons-material/MoreVert";
import Button from "@mui/material/Button";
import { makeStyles } from "@material-ui/core/styles";
import CommentIcon from "@mui/icons-material/Comment";
import Container from "@material-ui/core/Container";
import Comment from "../Comment/Comment";
import CommentForm from "../Comment/CommentForm";
import { Link, useNavigate } from "react-router-dom";
import { useParams } from "react-router-dom";
import { getPostById } from "../../service/post";
import Author from "../Author/Author";
import NotFound from "../NotFound/NotFound";
import { getUserById } from "../../service/user";
import { updateUserInfo } from "../../service/user";
import { USER_UPDATE_VALID } from "../../constants/Messages";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import Box from "@mui/material/Box";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import { Alert } from "@mui/material";

const useStyles = makeStyles((theme) => ({
  root: {
    width: 800,
    textAlign: "left",
    margin: 20,
  },
  media: {
    height: 0,
    paddingTop: "56.25%", // 16:9
  },
  expand: {
    transform: "rotate(0deg)",
    marginLeft: "auto",
    transition: theme.transitions.create("transform", {
      duration: theme.transitions.duration.shortest,
    }),
  },
  avatar: {
    background: "linear-gradient(45deg, #2196F3 30%, #21CBF3 90%)",
  },
  link: {
    textDecoration: "none",
    boxShadow: "none",
    color: "white",
  },
}));

interface ExpandMoreProps extends IconButtonProps {
  expand: boolean;
}

const ExpandMore = styled((props: ExpandMoreProps) => {
  const { expand, ...other } = props;
  return <IconButton {...other} />;
})(({ theme, expand }) => ({
  transform: !expand ? "rotate(0deg)" : "rotate(180deg)",
  marginLeft: "auto",
  transition: theme.transitions.create("transform", {
    duration: theme.transitions.duration.shortest,
  }),
}));

const ProfileSettings = () => {
  const navigator = useNavigate();
  const theme = createTheme();

  const [expanded, setExpanded] = useState(false);
  const [userDetail, setUserDetail] = useState({});
  const [isLoaded, setIsLoaded] = useState(false);
  const [error, setError] = useState(null);

  const [username, setUsername] = useState("");
  const [bio, setUserBio] = useState("");
  const [message, setMessage] = useState("");

  const { postId } = useParams();
  const classes = useStyles();
  const navigate = useNavigate();

  const getUserDetail = async () => {
    const userDetail = await getUserById(localStorage.getItem("currentUser"));
    setUserDetail(userDetail);
    setUsername(userDetail.username);
    setUserBio(userDetail.bio);
    setIsLoaded(true);
  };

  useEffect(() => {
    if (localStorage.getItem("currentUser") == null) {
      navigator("/");
    }

    getUserDetail();
  }, []);

  const handleUsername = (val) => {
    setUsername(val);
  };

  const handleUserbio = (val) => {
    setUserBio(val);
  };
  const handleExpandClick = (e) => {
    e.preventDefault();
    setExpanded(!expanded);
  };

  const handleSaveUserInfo = async (e) => {
    e.preventDefault();

    const userInfo = {
      username: username,
      bio: bio,
    };
    const response = await updateUserInfo(
      localStorage.getItem("currentUser"),
      userInfo
    );

    if (response != null) {
      setUsername("");
      setUserBio("");
      setMessage(USER_UPDATE_VALID);
      setTimeout(() => navigate(-1), 2000);
    }
  };

  return (
    <ThemeProvider theme={theme}>
      {message != null ? (
        <Alert severity="success">{USER_UPDATE_VALID}</Alert>
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
            Update User Info
          </Typography>
          <Box component="form" noValidate sx={{ mt: 1 }}>
            <TextField
              margin="normal"
              required
              fullWidth
              label="User name info"
              name="username"
              autoComplete="username"
              autoFocus
              onChange={(e) => handleUsername(e.target.value)}
              value={username}
            />
            <TextField
              className={classes.description}
              margin="normal"
              fullWidth
              required
              label="User Bio"
              name="userbio"
              autoFocus
              onChange={(e) => handleUserbio(e.target.value)}
              value={bio}
            />

            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
              onClick={handleSaveUserInfo}
            >
              Save User Info
            </Button>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
};

export default ProfileSettings;
