import React, { useState, useEffect } from "react";
import { CardContent } from "@mui/material";
import { InputAdornment } from "@mui/material";
import { OutlinedInput } from "@mui/material";
import { TextField } from "@mui/material";

const Comment = (props) => {
  const { username } = props;

  return (
    <CardContent>
      <OutlinedInput
        disabled
        id="outlined-adornment-amount"
        multiline
        inputProps={{ maxLength: 25 }}
        fullWidth
        startAdornment={
          <InputAdornment position="start">
            denemelerceeee! + {username}
          </InputAdornment>
        }
        style={{ color: "black", backgroundColor: "white" }}
      ></OutlinedInput>
    </CardContent>
  );
};

export default Comment;
