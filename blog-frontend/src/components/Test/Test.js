import React, { useState, useEffect, useRef } from "react";
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
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import MoreVertIcon from "@mui/icons-material/MoreVert";
import Button from "@mui/material/Button";
import { makeStyles } from "@material-ui/core/styles";
import CommentIcon from "@mui/icons-material/Comment";
import Container from "@material-ui/core/Container";
import Comment from "../Comment/Comment";
import CommentForm from "../Comment/CommentForm";
import { Link } from "react-router-dom";
import clsx from "clsx";
import { getAllCommentsByPostId } from "../../service/comment";

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

const Test = (props) => {
  const [expanded, setExpanded] = useState(false);
  const [isLoaded, setIsLoaded] = useState(false);
  const [commentList, setCommentList] = useState([]);
  const [refresh, setRefresh] = useState(false);
  const isInitialMount = useRef(true);

  const { id: postId, title, text } = props.post;
  const commentText = {
    userId: 1,
    username: "boskefm",
    text: "commentText!!",
    postId: postId,
  };

  const classes = useStyles();

  const handleExpandClick = () => {
    setExpanded(!expanded);
    getAllComments();
  };

  const setCommentRefresh = () => {
    setRefresh(true);
  };

  const getAllComments = async () => {
    const commentDetailList = await getAllCommentsByPostId(postId);
    setCommentList(commentDetailList);
    setIsLoaded(true);
    setRefresh(false);
  };

  useEffect(() => {
    if (isInitialMount.current) isInitialMount.current = false;
    else {
      getAllComments();
    }
  }, [refresh]);

  return (
    <div>
      <Card className={classes.root}>
        <CardContent>
          <Typography variant="h3" gutterBottom>
            {title}
          </Typography>
          <Typography variant="body1">
            {text}
            <br />
          </Typography>
        </CardContent>
        <CardActions>
          <Button size="small">
            <Link to={{ pathname: "/post/" + postId }}>Read More.. </Link>
          </Button>

          <IconButton
            className={clsx(classes.expand, {
              [classes.expandOpen]: expanded,
            })}
            onClick={handleExpandClick}
            aria-expanded={expanded}
            aria-label="show more"
          >
            {expanded}
            <CommentIcon />
          </IconButton>
        </CardActions>

        <Collapse in={expanded} timeout="auto" unmountOnExit>
          <Container fixed className={classes.container}>
            {isLoaded
              ? commentList.map((comment) => (
                  <Comment
                    key={comment.id}
                    comment={comment}
                    id={comment.userId}
                    username={comment.username}
                  />
                ))
              : "Loading"}
            <CommentForm
              username={localStorage.getItem("username")}
              comment={commentText}
              setCommentRefresh={setCommentRefresh}
            ></CommentForm>
          </Container>
        </Collapse>
      </Card>
    </div>
  );
};

export default Test;
