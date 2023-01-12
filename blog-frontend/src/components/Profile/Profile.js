import React, { useState, useRef, useEffect } from "react";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import { makeStyles } from "@material-ui/core";
import Modal from "@mui/material/Modal";
import {
  ListItem,
  List,
  ListItemSecondaryAction,
  Radio,
} from "@material-ui/core";
import { flexbox } from "@mui/system";
import Box from "@mui/joy/Box";
import SettingsIcon from "@mui/icons-material/Settings";
import { Link } from "react-router-dom";
import PostTab from "../Post/PostTab";

const useStyles = makeStyles({
  root: {
    minWidth: 345,
    margin: 50,
  },
  modal: {
    display: "flex",
    maxWidth: 200,
  },
});
const Profile = (props) => {
  console.log(props);
  const { id: userId, username, password, bio } = props;
  //const [selectedValue, setSelectedValue] = useState(avatarId);

  const classes = useStyles();

  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => {
    setOpen(false);
    //saveAvatar(selectedValue);
  };
  const handleChange = (event) => {
    //setSelectedValue(event.target.value);
  };

  return (
    <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr" }}>
      <Card className={classes.root}>
        <CardContent>
          <Typography gutterBottom variant="h5" component="h2">
            {username}
          </Typography>
          <Typography variant="body2" color="textSecondary" component="p">
            {bio ? bio : "bio is null"}
          </Typography>
        </CardContent>

        {
          //localStorage.getItem("currentUser") == userId ?
          <Box sx={{ display: "flex" }}>
            <Link to="/settings">
              <SettingsIcon />
            </Link>

            <Button
              variant="contained"
              size="sm"
              color="primary"
              aria-label="Explore Bahamas Islands"
              sx={{ ml: "auto", fontWeight: 600 }}
            >
              <Link to="/post/create"> Add New Post</Link>
            </Button>
          </Box>
        }
      </Card>
      <PostTab />
    </div>
  );
};

export default Profile;
