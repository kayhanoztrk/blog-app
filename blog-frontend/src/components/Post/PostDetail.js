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

const PostDetail = () => {
  console.log("postDetail is loading!!");
  const [expanded, setExpanded] = useState(false);
  const [postDetail, setPostDetail] = useState({});
  const [isLoaded, setIsLoaded] = useState(false);
  const [error, setError] = useState(null);

  const { postId } = useParams();
  const classes = useStyles();
  const navigate = useNavigate();

  const getPostDetail = async () => {
    console.log("postId", postId);
    const postDetail = await getPostById(postId);

    setPostDetail(postDetail);
    setIsLoaded(true);

    if (postDetail.message != null) setError(postDetail.message);
  };

  const handleExpandClick = (e) => {
    e.preventDefault();
    setExpanded(!expanded);
  };

  useEffect(() => {
    getPostDetail();
  }, []);

  return isLoaded && error == null ? (
    <div>
      <Card className={classes.root}>
        <CardContent>
          <Typography variant="h3" gutterBottom>
            {postDetail.title}
          </Typography>
          <Typography variant="body1">
            {postDetail.text}
            <br />
          </Typography>
        </CardContent>
        <CardActions></CardActions>
        <Collapse in={expanded} timeout="auto" unmountOnExit>
          <Container fixed className={classes.container}>
            <Comment username="kayhan" />
            <Comment username="selin" />
            <CommentForm />
          </Container>
        </Collapse>

        <IconButton onClick={(e) => handleExpandClick}>
          {expanded}
          <CommentIcon></CommentIcon>
        </IconButton>
      </Card>
      <Author user={postDetail.user} />
    </div>
  ) : (
    <NotFound message={error} />
  );
};

export default PostDetail;
