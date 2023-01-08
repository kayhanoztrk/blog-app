import React, {useState, useEffect} from "react";
import {Link} from "react-router-dom";
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import { makeStyles } from '@material-ui/core/styles';
import { LockOpen, NoEncryption } from "@mui/icons-material";

const useStyles = makeStyles((theme) => ({

    root:{
        flexGrow:1,
    },
    menuButton: {
        marginRight: theme.spacing(2),
    },
    title: {
        flexGrow: 1,
        textAlign: "left",
        textDecoration: "none",
        color: "white"
    },
    link: {
        textDecoration: "none",
        boxShadow: "none",
        color: "white"
    }
}));

const Navbar = () => {
    const classes = useStyles();
    return(
       /* <div>
            <ul>
           <li><Link to='/home'>Ana Sayfa</Link></li>
            <li><Link to='/post'>Post Page</Link></li>
            </ul>
        </div>
        */
        <Box sx={{ flexGrow: 1 }}>
        <AppBar position="static">
          <Toolbar>
            <IconButton
              size="large"
              edge="start"
              color="inherit"
              aria-label="menu"
              sx={{ mr: 2 }}
            >
            </IconButton>
            <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
              <Link className={classes.link} to="/home">HomePage</Link>
            </Typography>
            <Link className={classes.link} to="/home">User</Link>
          </Toolbar>
        </AppBar>
      </Box>
    )
}

export default Navbar;