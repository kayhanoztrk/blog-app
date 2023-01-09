import * as React from 'react';
import { styled } from '@mui/material/styles';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardMedia from '@mui/material/CardMedia';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Collapse from '@mui/material/Collapse';
import Avatar from '@mui/material/Avatar';
import IconButton, { IconButtonProps } from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import { red } from '@mui/material/colors';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ShareIcon from '@mui/icons-material/Share';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import Button from '@mui/material/Button';
import { makeStyles } from '@material-ui/core/styles';
import CommentIcon from '@mui/icons-material/Comment';
import Container from '@material-ui/core/Container';
import Comment from '../Comment/Comment';
import CommentForm from '../Comment/CommentForm';
import { Link } from 'react-router-dom';

const useStyles = makeStyles((theme) => ({
    root: {
      width: 800,
      textAlign : "left",
      margin : 20
    },
    media: {
      height: 0,
      paddingTop: '56.25%', // 16:9
    },
    expand: {
      transform: 'rotate(0deg)',
      marginLeft: 'auto',
      transition: theme.transitions.create('transform', {
        duration: theme.transitions.duration.shortest,
      }),
    },
    avatar: {
      background: 'linear-gradient(45deg, #2196F3 30%, #21CBF3 90%)',
    },
    link: {
        textDecoration : "none",
        boxShadow : "none",
        color : "white"
    }
  }));


interface ExpandMoreProps extends IconButtonProps {
  expand: boolean;
}

const ExpandMore = styled((props: ExpandMoreProps) => {
  const { expand, ...other } = props;
  return <IconButton {...other} />;
})(({ theme, expand }) => ({
  transform: !expand ? 'rotate(0deg)' : 'rotate(180deg)',
  marginLeft: 'auto',
  transition: theme.transitions.create('transform', {
    duration: theme.transitions.duration.shortest,
  }),
}));

const Post = (props) => {
  const [expanded, setExpanded] = React.useState(false);

  const {id, title, text} = props.post;


  const classes = useStyles();

  const handleExpandClick = () => {
    setExpanded(!expanded);
    console.log('expanded', expanded);
  };

  return (
    <div>
    <Card className={classes.root}>
      <CardContent>
        <Typography variant='h3'  gutterBottom>
          {title}
        </Typography>
        <Typography variant="h4" component="div">
         Heading
        </Typography>
        <Typography sx={{ mb: 1.5 }} color="text.secondary">
          describes the heading
        </Typography>
        <Typography variant="body1">
        {text}
          <br />
        </Typography>
      </CardContent>
      <CardActions>
        <Button size="small">
          <Link to={{pathname: '/post/' + id }}>Read More.. </Link></Button>
      </CardActions>
      <Collapse in={expanded} timeout="auto" unmountOnExit>
                    <Container fixed className = {classes.container}>
                      <Comment username="kayhan"/>
                      <Comment username="selin"/>
                      <CommentForm />
                    </Container>
                </Collapse>

      <IconButton onClick={handleExpandClick}>
      {expanded}
          <CommentIcon></CommentIcon>
      </IconButton>
    </Card>
    </div>
  );
}

export default Post;