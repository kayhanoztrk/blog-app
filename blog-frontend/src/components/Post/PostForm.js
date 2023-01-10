import React, {useState, useEffect} from "react";
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import { Link, useNavigate} from "react-router-dom";
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Alert from '@mui/material/Alert';
import Stack from '@mui/material/Stack';
import { makeStyles } from '@material-ui/core/styles';

import { sendAuthProcess } from "../../service/auth";
import { savePost } from "../../service/post";

const useStyles = makeStyles((theme) => ({
    root: {
      width: 800,
      textAlign : "left",
      margin : 20
    }
  }));

const PostForm = () => {

    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [tags, setTags] = useState("");
    const [isSaved, setIsSaved] = useState(false);

    const theme = createTheme();

    const classes = useStyles();

    const navigate = useNavigate();


    const handlePostTitle = (val) => {
        setTitle(val);
    }

    const handlePostDescription = (val) => {
        setDescription(val);
    }

    const handlePostTags = (val) => {
        setTags(val);
    }

    const handleSavePostSubmit = async (e) => {
        e.preventDefault();

        console.log('handleSavePostSubmit','denemelerceee');
        const post = {
            title: title,
            description: description,
            userId: 1
        };

        const result = await savePost(post);

        if(result != null){
            setTitle("");
            setDescription("");
            setTags("");
            setIsSaved(true);
            setTimeout(() => {navigate("/")}, 3000);
        }
    }

    return(
        <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
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
           <TextField className={classes.description}
           margin="normal"
           fullWidth
           required
           label="Post Description"
           name="description"
           autoFocus 
           value={description}
           onChange={(e) => handlePostDescription(e.target.value)}/>

        <TextField className={classes.description}
           margin="normal"
           fullWidth
           required
           label="Post Tags"
           name="tags"
           autoFocus
           value={tags}
           onChange={(e) => handlePostTags(e.target.value)}
           />

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
    )
}

export default PostForm;