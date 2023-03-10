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

import { sendRequest } from "../../service/auth";

const Register = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [isRegistered, setIsRegistered] = useState(false);
  const [error, setError] = useState("");
  const theme = createTheme();

  const history = useNavigate();

  const handleUsername = (val) => {
    setUsername(val);
  };

  const handlePassword = (val) => {
    setPassword(val);
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    const process = "register";
    const userInfo = {
      username: username,
      password: password,
    };

    const result = await sendRequest(process, userInfo);

    console.log("result", result);
    if (result.message != "username already in use") {
      console.log("result", result);
      localStorage.setItem("tokenKey", result.accessToken);
      localStorage.setItem("currentUser", result.userId);
      localStorage.setItem("username", username);

      setUsername("");
      setPassword("");

      //it will redirect to auth page again.
      history("/user/" + localStorage.getItem("currentUser"));
    } else {
      history("/register");
    }
  };

  return (
    <ThemeProvider theme={theme}>
      {isRegistered ? (
        <Stack sx={{ width: "100%" }} spacing={2}>
          <Alert severity="success">User is registered!</Alert>
        </Stack>
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
          <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Register
          </Typography>
          <Box component="form" noValidate sx={{ mt: 1 }}>
            <TextField
              margin="normal"
              required
              fullWidth
              id="email"
              label="Username"
              name="username"
              autoComplete="username"
              autoFocus
              value={username}
              onChange={(e) => handleUsername(e.target.value)}
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="password"
              label="Password"
              type="password"
              id="password"
              autoComplete="current-password"
              value={password}
              onChange={(e) => handlePassword(e.target.value)}
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
              onClick={(e) => handleRegister(e)}
            >
              Register
            </Button>

            <Grid container>
              <Grid item>
                <Link to="/register">{"Don't have an account? Sign Up"}</Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
};

export default Register;
